package com.tnjohansson.template

import org.scalatest.{FunSuite, Matchers}


class HelloWorldTest extends FunSuite with Matchers {
  test("say hi"){
    assert(new HelloWorld().hello == "hi")
  }
}
