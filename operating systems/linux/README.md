
# Processes

| Command                | Info                    |
| ---------------------- | ----------------------- |
| killall -9 java        | Kill all Java processes |

# Files

| Command                                  | Info                                                 |
| ---------------------------------------- | ---------------------------------------------------- |
| du -a \| sort -n -r \| head -n 5         | Lists top 5 largest folders in the current directory |
| rsync -a -r -v <source dir> <target dir> | Syncs folders using rsync                            |

# Architecture

## Cgroups 

[Cgroups](https://wiki.archlinux.org/index.php/cgroups), initially called Process Containers, is Kernel feature to limit and control resource usage for processes. [Docker](https://docs.docker.com/engine/docker-overview/#control-groups) relies on Cgroups to share available hardware resources to containers and optionally enforce limits and constraints.  



