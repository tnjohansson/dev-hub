package tnj.performance.memory.layout

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.util.Random

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.Throughput))
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(1)
@Threads(Threads.MAX)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS, batchSize = 1)
@Measurement(iterations = 1)
class CacheAlignedFields {

  private var items = Array.empty[Fields]

  @Param(Array("aligned", "unaligned"))
  var testType: String = _

  @Param(Array("10000000"))
  var size: Int = _

  @Setup
  def setUp(): Unit = {
    if(testType == "aligned"){
      items = Array.fill(size)( new AlignedFields() )
    }
    else {
      items = Array.fill(size)( new UnAlignedFields() )
    }
  }

  @Benchmark
  @OperationsPerInvocation(10000000)
  def alignedFields(): Any = {
    for( i <- items.indices ){
      val j = Random.nextInt(100)
      items(j).doSomething()
    }
  }
}

trait Fields {
  def doSomething(): Unit
}

private class AlignedFields extends Fields{
  private val field4: Int = 1
  private val field5: Long = 1
  private val field6: Boolean = false
  private val field7: Int = 1
  private val field8: Long = 1
  private val field9: Long = 1
  private val field10: Long = 1

  private var field1: Long = 1
  private var field2: Long = 1
  private var field3: Long = 1

  def doSomething(): Unit ={
    field1 = field1 + 1
    field2 = field1 + 1
    field3 = field2 + 1
  }
}

private class UnAlignedFields extends Fields{

  private var field1: Long = 1
  private val field4: Int = 1
  private val field5: Long = 1
  private val field6: Boolean = false
  private val field7: Int = 1
  private var field2: Long = 1
  private val field8: Long = 1
  private val field9: Long = 1
  private val field10: Long = 1
  private var field3: Long = 1

  def doSomething(): Unit ={
    field1 = field1 + 1
    field2 = field1 + 1
    field3 = field2 + 1
  }

}
