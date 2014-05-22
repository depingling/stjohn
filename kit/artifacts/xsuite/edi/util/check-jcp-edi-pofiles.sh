fl=`find ../processed -name "*M--*" -mtime -30 `

if [ ! -d bk ]
then
  mkdir bk
fi

vf=bk/jcpverifyout.txt
echo "------------------------------ start" > $vf
for f in $fl
do
  sh verify-jcp-pofile.sh $f >> $vf
done
echo "------------------------------ end" >> $vf

mf=/tmp/$0.mail

echo "POs missing in database" > $mf
grep File= $vf | grep NOT >> $mf

echo "\nPOs In the database" >> $mf
grep File= $vf | grep IS >> $mf

mail edi-order-file-check@cleanwise.com<<EOF
subject: $0 

`cat $mf`

EOF



