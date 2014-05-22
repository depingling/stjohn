
if [ -f /apps/ixtendx/.profile ]
then
. /apps/ixtendx/.profile
fi

if [ -f /apps/xadmin2/.profile ]
then
. /apps/xadmin2/.profile
fi

if [ -f /apps/xadmin3/.profile ]
then
. /apps/xadmin3/.profile
fi


if [ -f ../app.sh.env ]
then
. ../app.sh.env
fi

lockfile=/tmp/xsuite-content.$USER.lock

if [ -f $lockfile ]
then
  echo "error found $lockfile"
  exit
fi

echo $$ > $lockfile

thishost=`hostname`

f_get_content() {

  echo " $thishost "
  sh $XDIR/util/appcmd.sh -Dcmd=synch_content_deprecated -DrefetchAllContent=true \
    -Dthishost=$thishost:4080

}

if [ -d ../../server/defst ]
then
  cd ../../server/defst/deploy/xsuite.ear/defst.war
else
  cd ../../deploy/defst.war/
fi


# get all changes found in the database.
f_get_content

rm $lockfile


