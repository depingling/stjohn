#!/bin/sh

rtime() {
printf "\n ===== `date` , START $*\n"
exec $*
printf "\n ===== `date` , END   $*\n"
}

if [ -f  .profile ]
then
. ./.profile 
fi
if [ -f ../.profile ]
then
. ../.profile
fi

# stage the budget reports table
cd util
#sh stage-rep.sh | tee /tmp/stage-rep.log 2>&1 &

#echo "Yesterdays stage-rep log>>>>>"
#cat /tmp/stage-rep.log

# fix the janitors closet entries
##echo "Janitors Closet"
##sh synchjc.sh
# ensure that the reporting users in the list have
# access to the accounts as the accounts are added
echo "Check Reporting Users"
sh check-reporting-users.sh
##echo "sql update script"
##sh sql_scripts.sh


# load the report tables
#echo "load reporting scheme"
#sh xsuite-load-report-db-tables.job | tee /tmp/xsuite-load-report-db-tables.job.log

cd ${XSUITE_HOME}
cd ${XDIR}/jobs
###echo "workflow queue"
##sh workflow-queue.job |tee /tmp/workflow-queue-daily.log 2>&1 &


dw=`date +%u`

if [ $dw -eq 7 ]
then
  # today is sunday do nothing
  exit
fi


#run various reports, put in the background so as not
# to hang up the system.
###echo "run generic reports"
##sh xsuite-run-generic-reports.job xsuite-daily-generic-reports.xml | tee /tmp/xsuite-daily-generic-reports.log &


cd ../util
##echo "reset cost centers"
##sh appcmd.sh -Dcmd=reset_cost_centers
###echo "create fiscal cal cache table"
###Moved to cw-vm-job02 10/24/2009
###sh appcmd.sh -Dcmd=create_fiscal_cal_cache_table
###echo "daily order reprocess"
##sh appcmd.sh -Dcmd=daily_order_reprocess

echo "END of daily jobs."

