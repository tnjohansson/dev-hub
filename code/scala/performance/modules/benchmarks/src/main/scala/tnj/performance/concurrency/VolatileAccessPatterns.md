This benchmark checks if different access patterns for volatile fields defined in objects/classes matters in a multi-threaded 
context. The volatile field is defined in the following ways;

``` java
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
```

The benchmarks shows that there is no significant difference. 

```
Benchmark                                          Mode  Cnt          Score         Error  Units
VolatileAccessPatterns.classVolatile              thrpt    5   54267124.342 ±  257891.669  ops/s
VolatileAccessPatterns.objectVolatile             thrpt    5   54352312.529 ±  103471.106  ops/s
VolatileAccessPatterns.privateClassVolatile       thrpt    5   54341334.391 ±  189218.728  ops/s
VolatileAccessPatterns.privateObjectVolatile      thrpt    5   54259414.828 ±  297920.104  ops/s
VolatileAccessPatterns.privateThisClassVolatile   thrpt    5   49668824.975 ± 9795143.010  ops/s
```

The overhead of the concurrent access to the volatile field can be compared with the following single thread access;

```
Benchmark                                          Mode  Cnt          Score         Error  Units
VolatileAccessPatterns.classVolatileSingleThread  thrpt    5  125102683.592 ± 4612197.530  ops/s
VolatileAccessPatterns.objectVolatileSingleThread thrpt    5  132227975.328 ± 1788799.931  ops/s
```
