
# check for a new version of the checker
perl web_fetch.pl http://cw/sys-checker.pl > sys-checker.pl.tmp

if [ -s sys-checker.pl.tmp ]
then
  mv sys-checker.pl.tmp sys-checker.pl
fi

if [ -s sys-checker.pl ]
then
  perl sys-checker.pl
fi



