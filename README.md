
- [Free programming books](https://github.com/EbookFoundation/free-programming-books/blob/master/free-programming-books.md)
- [A collection of links for streaming algorithms and data structures](https://gist.github.com/debasishg/8172796)
- [Everything You Always Wanted to Know About Fsync](http://blog.httrack.com/blog/2013/11/15/everything-you-always-wanted-to-know-about-fsync/)

https://www.stephanboyer.com/post/132/what-are-covariance-and-contravariance

https://sadanand-singh.github.io/posts/treebasedmodels/
https://dev.to/vaidehijoshi/counting-linearly-with-counting-sort

Random throughts and reminders

# Programming

## Floating point numbers

Floating point numbers (_such as float/decimal_) represents values as an aproximation in many cases. For example `(1/3+1/12+1/8+1/30) = 0.6083333332` is represented as a Java double as `0.6083333333333333` and as a Java float as `0.60833335`. Read [this](http://www.volkerschatz.com/science/float.html) to understand the pitfalls of using floats.  

## Value types please

The day the JVM supports [value types](http://www.jesperdj.com/2015/10/04/project-valhalla-value-types) will be a good day! Structs will enable more efficient code as GC/memory pressure is likely to improve. Simple things as less pointer chasing and better [CPU cache utilization](http://jacksondunstan.com/articles/3399) would help.

## Class layout matters

[This](http://psy-lob-saw.blogspot.co.uk/2013/05/know-thy-java-object-memory-layout.html) article has an interesting section about improving the likelihood of fields in a class ending up on the same cache line.  

``` java
public abstract class AbstractHistogram implements Serializable {
  // "Cold" accessed fields. Not used in the recording code path:
  long highestTrackableValue;
  int numberOfSignificantValueDigits;

  int bucketCount;
  int subBucketCount;
  int countsArrayLength;

  HistogramData histogramData;

  // Bunch "Hot" accessed fields (used in the the value recording code path) here, near the end, so
  // that they will have a good chance of ending up in the same cache line as the counts array reference
  // field that subclass implementations will add.
  int subBucketHalfCountMagnitude;
  int subBucketHalfCount;
  long subBucketMask;  
}
```

Use a tool like [Java Object Layout](http://openjdk.java.net/projects/code-tools/jol) to explore the layout of an JVM class.

# Hardware

# IO

The following benchmark numbers are from [userbenchmark](http://www.userbenchmark.com) and is shown as a representative comparison between different technologies.

| Type            | Repr. Device                                                                                                                               | Seq. Read    | Seq. Write  | 4K Read [1] | 4K Write [1] |
| --------------- | ------------------------------------------------------------------------------------------------------------------------------------------ |------------- | ----------- | ----------- | ------------ |
| Mechanical disk | [WD WD6001FZWX 6TB](http://hdd.userbenchmark.com/WD-Black-6TB-2015/Rating/3519)                                                            | 217 MB/s     | 248 MB/s    | 7.31 MB/s   | 3.3 MB/s     |
| SATA SSD disk   | [2.5" SATA III 1TB](http://www.samsung.com/us/computing/memory-storage/solid-state-drives/ssd-850-pro-2-5-sata-iii-1tb-mz-7ke1t0bw)        | 530 MB/s     | 505 MB/s    | 32.3 MB/s   | 87.7 MB/s    |
| PCIe SSD disk   | [960 PRO NVMe M.2 1TB](http://www.samsung.com/us/computing/memory-storage/solid-state-drives/ssd-850-pro-2-5-sata-iii-1tb-mz-7ke1t0bw)     | 2,206 MB/s   | 1,696 MB/s  | 42.8 MB/s   | 133 MB/s     |
| RAM             | [16GB DDR4 DRAM](http://www.corsair.com/en-us/vengeance-lpx-16gb-2x8gb-ddr4-dram-3000mhz-c15-memory-kit-black-cmk16gx4m2b3000c15)          | 31,400 MB/s  | 30,700 MB/s |             |              |

_[1] Random read/write in 4k chunks_

## CPU

### CPU Caches

Most developers consider memory as RAM only but it is important to take CPU caches into account. With good code/data locality the CPU cache can have an significant impact on performance. Consider the following [latency numbers](https://people.eecs.berkeley.edu/~rcs/research/interactive_latency.html)

| Action                 | Latency |
| ---------------------- | ------- |
| L1 cache reference     | 2ns     |
| L2 cache reference     | 4ns     |
| Main memory reference  | 100ns   |

CPU caches consists of small amounts of very fast memory tightly coupled with the CPU. The memory is read in cache lines from the main memory and both reads and writes are cached. Read [this](https://www.akkadia.org/drepper/cpumemory.pdf) excellent paper for a deep dive.

Code with highly random memory access patterns can cause a high CPU cache eviction rate.

The following table lists the cache sizes and latencies for an [Intel Skylake](http://www.7-cpu.com/cpu/Skylake.html) processor

| Cache    | Size     | Acc. Latency     |
| -------- | -------- | ---------------- |
| L1       | 32  KB   | 4ns              |
| L2       | 256 KB   | 8ns              |
| L3       | 8 MB     | 57ns             |
| RAM      | > GB     | 120ns [1]        |

_[1] RAM is only accessed if none of the caches holds the data. So for RAM access the latency is L1+L2+L3+51ns=120ns_

The only known profiler which will identify cache misses is [Intel VTune Performance Analyzer](https://software.intel.com/en-us/intel-vtune-amplifier-xe). On Linux the [perf](https://developers.redhat.com/blog/2014/03/10/determining-whether-an-application-has-poor-cache-performance-2) or [papi](http://icl.cs.utk.edu/papi) can be used. And [PCM](https://github.com/opcm/pcm) looks interesting.

### Memory

https://mechanical-sympathy.blogspot.co.uk/2011/07/false-sharing.html


TODO: http://blog.omega-prime.co.uk/?p=197
TODO: https://en.wikipedia.org/wiki/Data_structure_alignment
TODO: https://en.wikipedia.org/wiki/Translation_lookaside_buffer

### Memory pages

The page size is the smallest unit of data for memory managed by a virtual memory management OS. Using the page size as the allocation unit can have an significant performance impact for IO bound workloads.

A high page swap rate might indicate that not enough RAM is available and disk is used which is significantly slower than RAM.

When a process requests memory it is allocated in virtual space and the memory can be marked as [copy on write](https://en.wikipedia.org/wiki/Copy-on-write).  

# Data Analysis

## Joins

Non-equality joins commonly called theta joins are hard to distribute. **1-bucket-theta** [[1](http://www.ccs.neu.edu/home/mirek/papers/2011-SIGMOD-ParallelJoins.pdf)][[2](http://www.ccs.neu.edu/home/mirek/classes/2011-F-CS6240/Slides/Lecture7-large.pdf)] (with the following [enhancement proposal](http://ikee.lib.auth.gr/record/136112/files/GRI-2015-14052.pdf)) is an algorithm which evenly distributes the load in a cluster. It has been implemented in [PySpark](https://gist.github.com/CamDavidsonPilon/8750e37242c4942c1984), [Squall](https://github.com/epfldata/squall/blob/master/squall-core/src/main/java/ch/epfl/data/squall/thetajoin/matrix_assignment/ContentInsensitiveMatrixAssignment.java).

# Algorithms

## Hash

Hash functions can be one way which is fundamental for [hashing passwords](https://security.stackexchange.com/questions/11717/why-are-hash-functions-one-way-if-i-know-the-algorithm-why-cant-i-calculate-t). Check [this](http://www.metamorphosite.com/one-way-hash-encryption-sha1-data-software) for how a hash is calculated.

# Data structures

[HdrHistogram](https://github.com/HdrHistogram/HdrHistogram)

## CRDTs

[CRDT Resources](https://syncfree.lip6.fr/index.php/crdt-resources)

## Probabilistic data structures

Need to [count a billion distinct values](http://highscalability.com/blog/2012/4/5/big-data-counting-how-to-count-a-billion-distinct-objects-us.html) using a very small memory footprint? [This](https://highlyscalable.wordpress.com/2012/05/01/probabilistic-structures-web-analytics-data-mining/) is an high-level overview of the most common probabilistic data structures used for data mining; [Probabilistic Counting](http://blog.getspool.com/2011/11/29/fast-easy-realtime-metrics-using-redis-bitmaps/), [Hyper Loglog](http://algo.inria.fr/flajolet/Publications/FlFuGaMe07.pdf), [Count-Min Sketch](https://sites.google.com/site/countminsketch/), [Bloom Filter](https://en.wikipedia.org/wiki/Bloom_filter).

## Memory friendly data-structures

[Datastructures for external memory](http://blog.omega-prime.co.uk/?p=197)

- [Judy arrays](http://judy.sourceforge.net/)
- [kD-tree](http://pointclouds.org/documentation/tutorials/kdtree_search.php)
- [Z-order curve](https://gist.github.com/jaredwinick/5073432)

# Distributed Systems

- [Software Engineering Advice from
Building Large-Scale Distributed Systems](http://static.googleusercontent.com/media/research.google.com/en/us/people/jeff/stanford-295-talk.pdf)

# Performance

TODO: [Instruction Retired](https://software.intel.com/en-us/node/544403)
TODO: [JMH - Java Microbenchmark Harness](http://tutorials.jenkov.com/java-performance/jmh.html)


# Analytics

https://gist.github.com/debasishg/8172796

Clustering

[Introduction to K-means Clustering](https://www.datascience.com/blog/introduction-to-k-means-clustering-algorithm-learn-data-science-tutorials)

Interactive Visualisations

- [Histogram](https://tinlizzie.org/histograms/?source=techstories.org)

Supporting math

- [Numerical Linear Algebra for Coders](https://github.com/fastai/numerical-linear-algebra)

- [Pagerank](http://computationalculture.net/article/what_is_in_pagerank)
- [Anomaly Detection of Time Series Data Using Machine Learning & Deep Learning](https://www.xenonstack.com/blog/anomaly-detection-of-time-series-data-using-machine-learning-deep-learning)

# Timeseries

Compression

[Compression of floating point timeseries](http://blog.omega-prime.co.uk/?p=184)
