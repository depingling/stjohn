#!/bin/bash

export JAVA_OPTS="-noverify -javaagent:@jrebel.home@/jrebel.jar -Xms256m -Xmx512m -XX:MaxPermSize=256m $JAVA_OPTS"
`dirname $0`/run.sh $@
