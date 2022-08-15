#!/bin/bash
#author liuyag
#since  2022/8/15

PID=`ps -ef | grep test-spring-boot-env  | grep -v grep | awk '{print $2}'`
echo "PID = ${PID} will be killed"
kill -9 $PID
echo "PID = ${PID} has been killed"
