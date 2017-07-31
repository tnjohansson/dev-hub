package tnj.metrics

import java.io.PrintStream
import java.util.concurrent.TimeUnit
import java.util.{Locale, TimeZone}

import com.codahale.metrics.{ConsoleReporter, Meter, MetricRegistry, Timer}

import scala.concurrent.duration._
import scala.concurrent.duration.FiniteDuration


trait Instrumentation {

  def meter(names: String*): Meter

  def timer(names: String*): Timer
}

object Instrumentation {

  private[this] final class InstrumentationContext(val registry: MetricRegistry, val prefix: String)
    extends Instrumentation {

    def meter(names: String*): Meter = {
      registry.meter(MetricRegistry.name(prefix, names: _*))
    }

    override def timer(names: String*): Timer = {
      registry.timer(MetricRegistry.name(prefix, names: _*))
    }
  }

  def createContext(prefix: String): Instrumentation = {
    new InstrumentationContext(new MetricRegistry, prefix)
  }

  final def exportToConsole(instrumentation: Instrumentation,
                            every: FiniteDuration = 1 second,
                            outputTo: PrintStream = System.out,
                            locale: Locale = Locale.ENGLISH,
                            timeZone: TimeZone = TimeZone.getDefault,
                            durationsUnit: TimeUnit = TimeUnit.MILLISECONDS,
                            ratesUnit: TimeUnit = TimeUnit.SECONDS): AutoCloseable = {
    val impl = instrumentation.asInstanceOf[InstrumentationContext]
    val consoleReporter = ConsoleReporter.forRegistry(impl.registry)
      .convertDurationsTo(durationsUnit).convertRatesTo(ratesUnit)
      .formattedFor(locale)
      .formattedFor(timeZone)
      .outputTo(outputTo)
      .build()

    consoleReporter.start(every.toMillis, TimeUnit.MILLISECONDS)

    new AutoCloseable {
      override def close(): Unit = {
        consoleReporter.stop()
        consoleReporter.close()
      }
    }
  }
}
