#!/bin/sh
appsh=../app.sh.env
if [ -f $appsh ]
then
. $appsh
fi
appsh=../../app.sh.env
if [ -f $appsh ]
then
. $appsh
fi
appsh=../../../app.sh.env
if [ -f $appsh ]
then
. $appsh
fi
appsh=${XDIR}/app.sh.env
if [ -f $appsh ]
then
. $appsh
fi

if [ -z ${XSUITE_HOME} ] 
then
  echo "ERROR XSUITE_HOME is not defined."
  exit
fi

java -classpath "${TJCPATH}" -Dmail.from=noreply@cleanwise.com com.cleanwise.service.apps.ApplicationsEmailTool "$1" $2 "$3" $4 $5 $6 $7 $8 $9
