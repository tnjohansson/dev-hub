
- [Free programming books](https://github.com/EbookFoundation/free-programming-books/blob/master/free-programming-books.md)
- [A collection of links for streaming algorithms and data structures](https://gist.github.com/debasishg/8172796)
- [Everything You Always Wanted to Know About Fsync](http://blog.httrack.com/blog/2013/11/15/everything-you-always-wanted-to-know-about-fsync/)

Random throughts and reminders

# Programming

## Floating point numbers

Floating point numbers (_such as float/decimal_) represents values as an aproximation in many cases. For example `(1/3+1/12+1/8+1/30) = 0.6083333332` is represented as a Java double as `0.6083333333333333` and as a Java float as `0.60833335`. Read [this](http://www.volkerschatz.com/science/float.html) to understand the pitfalls of using floats.  

## Hardware

## CPU

### CPU Caches

Most developers consider memory as RAM only but it is important to take CPU caches into account. With good code and data locality the CPU cache can have an significant impact on performance. Consider the following [latency numbers](https://people.eecs.berkeley.edu/~rcs/research/interactive_latency.html)

| Action                 | Latency |
| ---------------------- | ------- |
| L1 cache reference     | 2ns     |
| L2 cache reference     | 4ns     |
| Main memory reference  | 100ns   |

CPU caches consists of small amounts of very fast memory tightly coupled with the CPU. The memory is read in cache lines from the main memory and both reads and writes are cached. Read [this](https://www.akkadia.org/drepper/cpumemory.pdf) excellent paper for a deep dive.

Code with highly random memory access patterns can cause a high CPU cache eviction rate.

### Memory

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
