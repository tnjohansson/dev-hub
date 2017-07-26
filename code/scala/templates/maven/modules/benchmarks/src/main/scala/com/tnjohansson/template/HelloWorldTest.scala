package com.tnjohansson.template

import org.openjdk.jmh.annotations._
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.All))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(1)
@Threads(Threads.MAX)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS, batchSize = 1)
@Measurement(iterations = 1)
class HelloWorldTest {

  private var instance: HelloWorld = _

  @Setup
  def setUp(): Unit = {
    instance = new HelloWorld()
  }

  @TearDown
  def tearDown(): Unit = {

  }

  @Benchmark
  @OperationsPerInvocation(1000)
  def dummyTest(): Any = {
    // Do something
    instance.hello
  }

}
