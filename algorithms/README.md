
- [Pagerank](http://computationalculture.net/article/what_is_in_pagerank)
- [Anomaly Detection of Time Series Data Using Machine Learning & Deep Learning](https://www.xenonstack.com/blog/anomaly-detection-of-time-series-data-using-machine-learning-deep-learning)

## Probabilistic data structures

Need to [count a billion distinct values](http://highscalability.com/blog/2012/4/5/big-data-counting-how-to-count-a-billion-distinct-objects-us.html) using a very small memory footprint? [This](https://highlyscalable.wordpress.com/2012/05/01/probabilistic-structures-web-analytics-data-mining/) is an high-level overview of the most common probabilistic data structures used for data mining; [Probabilistic Counting](http://blog.getspool.com/2011/11/29/fast-easy-realtime-metrics-using-redis-bitmaps/), [Hyper Loglog](http://algo.inria.fr/flajolet/Publications/FlFuGaMe07.pdf), [Count-Min Sketch](https://sites.google.com/site/countminsketch/), [Bloom Filter](https://en.wikipedia.org/wiki/Bloom_filter).


## Joins

Non-equality joins commonly called theta joins are hard to distribute. **1-bucket-theta** [[1](http://www.ccs.neu.edu/home/mirek/papers/2011-SIGMOD-ParallelJoins.pdf)][[2](http://www.ccs.neu.edu/home/mirek/classes/2011-F-CS6240/Slides/Lecture7-large.pdf)] (with the following [enhancement proposal](http://ikee.lib.auth.gr/record/136112/files/GRI-2015-14052.pdf)) is an algorithm which evenly distributes the load in a cluster. It has been implemented in [PySpark](https://gist.github.com/CamDavidsonPilon/8750e37242c4942c1984), [Squall](https://github.com/epfldata/squall/blob/master/squall-core/src/main/java/ch/epfl/data/squall/thetajoin/matrix_assignment/ContentInsensitiveMatrixAssignment.java).
