package tnj.performance.concurrency

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.Throughput))
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(1)
@Threads(Threads.MAX)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS, batchSize = 1)
@Measurement(iterations = 5)
class VolatileAccessPatterns {

  private val nrThreads = 2
  private val iterations = 10000000

  @Benchmark
  @OperationsPerInvocation(10000000)
  def objectVolatile(): Any = {
    val state = ObjectVolatile
    run[ObjectVolatile.type](nrThreads, state, state => {
      for (i <- 0 until iterations) {
        state.cursor = i
      }
    })
  }

  @Benchmark
  @OperationsPerInvocation(10000000)
  def privateObjectVolatile(): Any = {
    val state = PrivateObjectVolatile
    run[PrivateObjectVolatile.type](nrThreads, state, state => {
      for (i <- 0 until iterations) {
        state.set(i)
      }
    })
  }

  @Benchmark
  @OperationsPerInvocation(10000000)
  def classVolatile(): Any = {
    val state = new PublicClassVolatile()
    run[PublicClassVolatile](nrThreads, state, state => {
      for (i <- 0 until iterations) {
        state.cursor = i
      }
    })
  }

  @Benchmark
  @OperationsPerInvocation(10000000)
  def privateClassVolatile(): Any = {
    val state = new PrivateClassVolatile()
    run[PrivateClassVolatile](nrThreads, state, state => {
      for (i <- 0 until iterations) {
        state.set(i)
      }
    })
  }

  @Benchmark
  @OperationsPerInvocation(10000000)
  def privateThisClassVolatile(): Any = {
    val state = new PrivateThisClassVolatile()
    run[PrivateThisClassVolatile](nrThreads, state, state => {
      for (i <- 0 until iterations) {
        state.set(i)
      }
    })
  }

  private def run[T](nrThreads: Int, t: T, a: T => Unit): Unit = {
    val f = (0 until nrThreads).map(_ => {
      Future {
        a(t)
      }
    })

    Await.result(Future.sequence(f), Duration.Inf)
  }

  object ObjectVolatile {
    @volatile var cursor: Long = 0
  }

  object PrivateObjectVolatile {
    @volatile private var cursor: Long = 0

    def set(v: Long): Unit = {
      cursor = v
    }
  }

  class PublicClassVolatile {
    @volatile var cursor: Long = 0
  }

  class PrivateClassVolatile {
    @volatile private var cursor: Long = 0

    def set(v: Long): Unit = {
      cursor = v
    }
  }

  class PrivateThisClassVolatile {
    @volatile private[this] var cursor: Long = 0

    def set(v: Long): Unit = {
      cursor = v
    }
  }

}




