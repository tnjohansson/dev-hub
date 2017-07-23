
- [Pagerank](http://computationalculture.net/article/what_is_in_pagerank)
- [Anomaly Detection of Time Series Data Using Machine Learning & Deep Learning](https://www.xenonstack.com/blog/anomaly-detection-of-time-series-data-using-machine-learning-deep-learning)




## Joins

Non-equality joins commonly called theta joins are hard to distribute. **1-bucket-theta** [[1](http://www.ccs.neu.edu/home/mirek/papers/2011-SIGMOD-ParallelJoins.pdf)][[2](http://www.ccs.neu.edu/home/mirek/classes/2011-F-CS6240/Slides/Lecture7-large.pdf)] (with the following [enhancement proposal](http://ikee.lib.auth.gr/record/136112/files/GRI-2015-14052.pdf)) is an algorithm which evenly distributes the load in a cluster. It has been implemented in [PySpark](https://gist.github.com/CamDavidsonPilon/8750e37242c4942c1984), [Squall](https://github.com/epfldata/squall/blob/master/squall-core/src/main/java/ch/epfl/data/squall/thetajoin/matrix_assignment/ContentInsensitiveMatrixAssignment.java).
