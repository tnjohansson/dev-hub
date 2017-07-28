package tnj.performance.memory.layout

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.concurrent.{Await, Future}
import scala.util.Random
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration


@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.Throughput))
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(1)
@Threads(Threads.MAX)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS, batchSize = 1)
@Measurement(iterations = 1)
class CachedAlignedVolatile {

  private val iterations = 10000000

  @Benchmark
  @OperationsPerInvocation(10000000)
  def alignedFields(): Any = {
    //val state = new SharedStatePadding()

    val f1 = Future{
      for(i <- 0 until iterations){
        SharedStatePadding.cursor = i
        //state.increment()
      }
    }

    val f2 = Future{
      for(i <- 0 until iterations){
        SharedStatePadding.cursor = i
        //state.increment()
      }
    }

    Await.result( Future.sequence(Seq(f1, f2)), Duration.Inf)
  }

  @Benchmark
  @OperationsPerInvocation(10000000)
  def unAlignedFields(): Any = {
    //val state = new SharedStateNoPadding()

    val f1 = Future{
      for(i <- 0 until iterations){
        //state.increment()
        SharedStateNoPadding.cursor = i
      }
    }

    val f2 = Future{
      for(i <- 0 until iterations){
        //state.increment()
        SharedStateNoPadding.cursor = i
      }
    }

    Await.result( Future.sequence(Seq(f1, f2)), Duration.Inf)
  }

}

object SharedStateNoPadding {
  //private val p1, p2, p3, p4, p5, p6, p7 = 0

  @volatile var cursor: Long = 0

  //private val p8, p9, p10, p11, p12, p13, p14 = 0

  def increment(): Unit ={
    cursor += 1
  }

}

object SharedStatePadding {
  //private val p1, p2, p3, p4, p5, p6, p7 = 0

  @volatile var cursor: Long = 0

  val p8, p9, p10, p11, p12, p13 = 0

  def increment(): Unit ={
    cursor += 1
  }

}




