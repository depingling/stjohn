#!/bin/sh

XSUITE_HOME=/apps/ixtendx
JBOSS_VERSION=JBoss-2.4.4_Jetty-3.1.5-1

cd ${XSUITE_HOME}
. .profile

cd ${XSUITE_HOME}/${JBOSS_VERSION}/jboss/xsuite/jobs
cd ../util
# disable the lagasse address report, durval Fri Nov  3 16:28:43 EST 2006
#sh load-address-log.sh
#sh make-lagasse-address-file.sh changes

cd ../jobs
sh xsuite-run-generic-reports.job xsuite-weekly-reports.xml

