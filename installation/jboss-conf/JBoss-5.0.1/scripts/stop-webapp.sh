#!/bin/ksh

echo "........ stopping JBoss..."
JBOSS_HOME=@jbossHome@
export JBOSS_HOME

cd @jbossHome@/bin
sh shutdown.sh -S -s@host.address@:@NamingServicePort@

echo " DONE ........ stopping JBoss..."




