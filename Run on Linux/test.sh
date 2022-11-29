#!/bin/sh

## 执行命令  ./test.sh start
export jar1=CommentSum.jar
export jar2=CommentSort.jar
export jar3=reduceSideJoin
export jar4=ProvincePartitionDriver.jar
export jar5=topFive.jar
export jar6=WriteToSequenceFile.jar
export jar7=MRWriteBzip2.jar

 
case "$1" in
 
start)
        # 启动jar1
        echo "--------jar1 开始启动--------------"
        hadoop jar $jar1
        echo "--------jar1 启动成功--------------"
 
        # 启动jar2
        echo "--------jar2---------------"
        hadoop jar $jar2
        echo "---------jar2 启动成功-----------"

        # 启动jar3
        echo "--------开始启动jar3---------------"
        hadoop jar $jar3 
        echo "---------jar3 启动成功-----------"
	
        # 启动jar4
        echo "--------开始启动jar4---------------"
        hadoop jar $jar4
        echo "---------jar4 启动成功-----------"

        # 启动jar5
        echo "--------开始启动jar5---------------"
        hadoop jar $jar5
        echo "---------jar5 启动成功-----------"

        # 启动jar6
        echo "--------开始启动jar6---------------"
        hadoop jar $jar6
        echo "---------jar6 启动成功-----------"
        
        # 启动jar7
        echo "--------开始启动jar7---------------"
        hadoop jar $jar7
        echo "---------jar7 启动成功-----------"

        ;;
esac	
exit 0
