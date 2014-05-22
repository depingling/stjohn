#!/bin/sh

blog=/tmp/$0.log
echo "===" > $blog
echo " starting build-and-test.sh " | tee $blog

cd
. .bashrc

stop-test.sh

if [ -f stjohn ]
then
  echo "cannot have a file named stjohn"
  mv stjohn stjohn.bak
fi

if [ -d stjohn ]
then
  sleep 60
  cd stjohn
  ant clobber
  cd ..
  rm -fr stjohn
fi

# clean up the previous bits
rm -fr JBoss-2.4.4_Jetty-3.1.5-1/jboss/deploy/cleanwise.war
rm -fr JBoss-2.4.4_Jetty-3.1.5-1/jetty/cleanwiseConf/*

cvs co stjohn > co.log 2>&1

cd stjohn
cp ../installation.properties .

ant database install deployall dbrefcd >& ../build.log

cd 
cp log4j.properties JBoss-2.4.4_Jetty-3.1.5-1/jboss/conf/cleanwise

start-test.sh
printf " sleeping for ..."
sleep 300
echo "  going again ..."

cd stjohn

ant load_en_US0 load_currency compiletests runtests  >& ../tests.log 

cd ..

if [ $# -eq 0 ]
then
sh send-build-email.sh tests.log
fi




