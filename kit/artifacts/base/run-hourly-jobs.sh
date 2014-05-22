#!/bin/sh

cd

. .profile

. $XDIR/app.sh.env

cd ${XDIR}/jobs

lockfile=/tmp/${0}.lock

if [ -f $lockfile ]
then
  printf "error ${0} found lock file $lockfile.\ncannot continue\n"
  exit
fi

echo "starting hourly work" > $lockfile
date >> $lockfile

#sh workflow-queue.job > /tmp/workflow-queue.log
sh xsuite-run-generic-reports.job xsuite-hourly-reports.xml >/tmp/run-hourly-job.sh.txt

rm $lockfile

