#!/bin/sh

. $XDIR/app.sh.env   

if [ $# -eq 0 ] 
then
  echo "Usage: $0 filename"
  exit
fi

tstamp=`date +%Y-%m-%d-%H:%M:%S`
tf=/tmp/$0.$tstamp
ff=${tf}.1
cat $1 | tr "\052" "=" > $ff

ct850=`grep "ST=850=" $ff | wc -l`

if [ $ct850 -eq 0 ]
then
    echo "no 850s found"
    exit
fi

posfound=` grep "BEG=00=SA=" $ff  | cut -f 4 -d "=" `

f_checkpo () {
thispo=$1
thisfile=$2


echo "DB check for po=$thispo"
echo " ----- po=$thispo file=$thisfile " > uspstresfile.txt
sqlplus $XSUITE_DBUSER/$XSUITE_DBPASS@$XSUITE_DBSERVER<<EOF     

spool uspstresfile.txt
select 'POCOUNT=' || count(*) from clw_order where request_po_num = '$thispo';
quit;
EOF

poc2=`grep "POCOUNT=0" uspstresfile.txt | wc -l`
if [ $poc2 -eq 1 ] 
then
  echo "File=$thisfile PO=$thispo NOT in the database"
else
  echo "File=$thisfile PO=$thispo IS in the database"
fi

}


for po in $posfound
do
  f_checkpo $po $1
done
