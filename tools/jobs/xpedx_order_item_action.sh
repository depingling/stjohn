#!/bin/sh
lockfile=/tmp/$0.lockfile
logfile='xpedx_order_item_action.sh.log'

if [ -f $lockfile ]
then
  echo "error lock file $lockfile in place."
  exit
fi

echo $$ > $lockfile

if [ -f  /cleanwise/xadmin3/.profile ]
then
        . /cleanwise/xadmin3/.profile 
fi

unset NLS_LANG
# clean out the shared memory pool of unused data.
SID='cwcamdb1'
sqlplus stjohn_prod/stjohn_prod@${SID} @xpedx_order_item_action.sql >${logfile}

rm $lockfile

mail -s "xpedx_order_item_action.sh script failed $XDIR/util/xpedx_order_item_action.sh" jobreporting@cleanwise.com <<EOF

--- insert record into clw_order_item_action for store_id = 176648 and item status is CANCELLED---

`cat $logfile`

EOF

exit 0

