package tnj.metrics

import java.lang.management.ManagementFactory
import java.util.concurrent.{Callable, TimeUnit}

import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.Throughput))
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(1)
@Threads(1)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS, batchSize = 1)
@Measurement(iterations = 1)
class InstrumentationTest {

  private val iterations = 10000000
  private var instance: Instrumentation = _

  @Setup
  def setUp(): Unit = {
    instance = Instrumentation.createContext("benchmark")
  }

  @TearDown
  def tearDown(): Unit = {

  }

  @Benchmark
  @OperationsPerInvocation(10000000)
  def explicitTimer(): Any = {
    val timer = instance.timer("test")
    for(i <- 0 until iterations){
      timer.time().stop()
    }
  }

  @Benchmark
  @OperationsPerInvocation(10000000)
  def timeCallable(): Any = {
    val c = new Callable[Int] {
      override def call(): Int = {
        0
      }
    }

    val timer = instance.timer("test")
    for(i <- 0 until iterations){
      timer.time(c)
    }
  }

  @Benchmark
  @OperationsPerInvocation(10000000)
  def meter(): Any = {
    val timer = instance.meter("test")
    for(i <- 0 until iterations){
      timer.mark()
    }
  }
}