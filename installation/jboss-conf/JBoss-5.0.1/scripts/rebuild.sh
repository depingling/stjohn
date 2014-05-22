sh rebuild-worker.sh $1 &> script.log
#sleep for a little so the server log has finished starting up and we can include it
sleep 200
XDIR=/cleanwise/xadmin3/webapp/jboss-4.0.5/xsuite
export XDIR
HOST=`hostname`
sh jboss-4.0.5/xsuite/util/send-email.sh "Rebuild Results ${HOST}" bstevens@cleanwise.com "todo get log" script.log $XDIR/../se
rver/defst/log/server.log