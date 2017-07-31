package tnj.metrics

import java.util.concurrent.Callable

import org.scalatest.{FunSuite, Matchers}


class InstrumentationTest extends FunSuite with Matchers {

  test("meter") {
    val inst = Instrumentation.createContext("test")
    val meter = inst.meter("meter")

    meter.mark()
    Thread.sleep(100)
    meter.mark()
    Thread.sleep(500)
    meter.mark()
    Thread.sleep(100)
    meter.mark()
    Thread.sleep(200)
    meter.mark()

    Instrumentation.exportToConsole(inst)

    Thread.sleep(2000)
  }

  test("timer") {
    val inst = Instrumentation.createContext("test")
    val timer = inst.timer("timer")

    val t = timer.time()
    Thread.sleep(100)
    t.stop()

    // Time a runnable
    timer.time(new Callable[Int] {
      override def call(): Int = {
        Thread.sleep(200)
        0
      }
    })

    Instrumentation.exportToConsole(inst)

    Thread.sleep(2000)
  }

  test("profiler"){
    val inst = Instrumentation.createContext("test")
    val timer = inst.timer("timer")

    for(i <- 0 until 1000000000){
      timer.time().stop()
    }
  }

}
