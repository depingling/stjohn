#!/bin/sh
lockfile=/tmp/$0.lockfile

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

perl order_process.pl cwcamdb1 stjohn_prod stjohn_prod

rm $lockfile
