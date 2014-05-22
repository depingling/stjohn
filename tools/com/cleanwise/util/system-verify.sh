#!/bin/sh

# make sure that the host and ports match the JBoss 
# installation that you are trying to talk to.

. ${XDIR}/app.sh.env


TJCPATH="${TJCPATH}${PSEP}ojdbc14.jar${PSEP}."

lcmd=$0
toEmail=systemverification@cleanwise.com
#. ../app.sh.env


lockfile=/tmp/xsuite.lock.$lcmd
if [ -f $lockfile ]
then
  sh ../util/send-email.sh "$0, `hostname` " "$toEmail"  "ERROR lock on for $lcmd system may be stuck."
  echo "ERROR lock on for $lcmd system may be stuck."
  hostname
  exit
fi

echo $$ > $lockfile

TJCPATH=${TJCPATH}${PSEP}httpunit.jar${PSEP}nekohtml.jar
echo "-------- STARTING"
date
java -classpath "${TJCPATH}" -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -Dhost=`hostname` -Dport=18443 \
 -DuserName=Tester -DpassWord=cwtester \
 -Demail=${toEmail} \
 -DsearchText="Managed Distributors" \
 com.cleanwise.service.apps.SystemVerification 
date
echo "-------- DONE"

rm $lockfile
