for i in $(find . -name Root);
do sed 's/192.168.1.206/cvsserver/g' $i > $i-tmp;
mv $i $i-backup;
mv $i-tmp $i;
done

sed 's/192.168.1.206/cvsserver/g' docroot/cleanwise/externals/dojo_1.1.0/dojox/data/demos/geography/United\ States\ of\ America/CVS/Root > docroot/cleanwise/externals/dojo_1.1.0/dojox/data/demos/geography/United\ States\ of\ America/CVS/Root-tmp;
mv docroot/cleanwise/externals/dojo_1.1.0/dojox/data/demos/geography/United\ States\ of\ America/CVS/Root docroot/cleanwise/externals/dojo_1.1.0/dojox/data/demos/geography/United\ States\ of\ America/CVS/Root-backup;
mv docroot/cleanwise/externals/dojo_1.1.0/dojox/data/demos/geography/United\ States\ of\ America/CVS/Root-tmp docroot/cleanwise/externals/dojo_1.1.0/dojox/data/demos/geography/United\ States\ of\ America/CVS/Root;

sed 's/192.168.1.206/cvsserver/g' docroot/cleanwise/externals/dojo_1.1.0/dojox/data/demos/geography/Mexico/Mexico\ City/CVS/Root > docroot/cleanwise/externals/dojo_1.1.0/dojox/data/demos/geography/Mexico/Mexico\ City/CVS/Root-tmp;
mv docroot/cleanwise/externals/dojo_1.1.0/dojox/data/demos/geography/Mexico/Mexico\ City/CVS/Root docroot/cleanwise/externals/dojo_1.1.0/dojox/data/demos/geography/Mexico/Mexico\ City/CVS/Root-backup;
mv docroot/cleanwise/externals/dojo_1.1.0/dojox/data/demos/geography/Mexico/Mexico\ City/CVS/Root-tmp docroot/cleanwise/externals/dojo_1.1.0/dojox/data/demos/geography/Mexico/Mexico\ City/CVS/Root;

sed 's/192.168.1.206/cvsserver/g' docroot/cleanwise/externals/dojo_1.1.0/dojox/data/demos/geography/Commonwealth\ of\ Australia/CVS/Root > docroot/cleanwise/externals/dojo_1.1.0/dojox/data/demos/geography/Commonwealth\ of\ Australia/CVS/Root-tmp;
mv docroot/cleanwise/externals/dojo_1.1.0/dojox/data/demos/geography/Commonwealth\ of\ Australia/CVS/Root docroot/cleanwise/externals/dojo_1.1.0/dojox/data/demos/geography/Commonwealth\ of\ Australia/CVS/Root-backup;
mv docroot/cleanwise/externals/dojo_1.1.0/dojox/data/demos/geography/Commonwealth\ of\ Australia/CVS/Root-tmp docroot/cleanwise/externals/dojo_1.1.0/dojox/data/demos/geography/Commonwealth\ of\ Australia/CVS/Root;

sed 's/192.168.1.206/cvsserver/g' docroot/cleanwise/externals/dojo_1.0.2/dojox/data/demos/geography/United\ States\ of\ America/CVS/Root > docroot/cleanwise/externals/dojo_1.0.2/dojox/data/demos/geography/United\ States\ of\ America/CVS/Root-tmp;
mv docroot/cleanwise/externals/dojo_1.0.2/dojox/data/demos/geography/United\ States\ of\ America/CVS/Root docroot/cleanwise/externals/dojo_1.0.2/dojox/data/demos/geography/United\ States\ of\ America/CVS/Root-backup;
mv docroot/cleanwise/externals/dojo_1.0.2/dojox/data/demos/geography/United\ States\ of\ America/CVS/Root-tmp docroot/cleanwise/externals/dojo_1.0.2/dojox/data/demos/geography/United\ States\ of\ America/CVS/Root;

sed 's/192.168.1.206/cvsserver/g' docroot/cleanwise/externals/dojo_1.0.2/dojox/data/demos/geography/Mexico/Mexico\ City/CVS/Root > docroot/cleanwise/externals/dojo_1.0.2/dojox/data/demos/geography/Mexico/Mexico\ City/CVS/Root-tmp;
mv docroot/cleanwise/externals/dojo_1.0.2/dojox/data/demos/geography/Mexico/Mexico\ City/CVS/Root docroot/cleanwise/externals/dojo_1.0.2/dojox/data/demos/geography/Mexico/Mexico\ City/CVS/Root-backup;
mv docroot/cleanwise/externals/dojo_1.0.2/dojox/data/demos/geography/Mexico/Mexico\ City/CVS/Root-tmp docroot/cleanwise/externals/dojo_1.0.2/dojox/data/demos/geography/Mexico/Mexico\ City/CVS/Root;

sed 's/192.168.1.206/cvsserver/g' docroot/cleanwise/externals/dojo_1.0.2/dojox/data/demos/geography/Commonwealth\ of\ Australia/CVS/Root > docroot/cleanwise/externals/dojo_1.0.2/dojox/data/demos/geography/Commonwealth\ of\ Australia/CVS/Root-tmp;
mv docroot/cleanwise/externals/dojo_1.0.2/dojox/data/demos/geography/Commonwealth\ of\ Australia/CVS/Root docroot/cleanwise/externals/dojo_1.0.2/dojox/data/demos/geography/Commonwealth\ of\ Australia/CVS/Root-backup;
mv docroot/cleanwise/externals/dojo_1.0.2/dojox/data/demos/geography/Commonwealth\ of\ Australia/CVS/Root-tmp docroot/cleanwise/externals/dojo_1.0.2/dojox/data/demos/geography/Commonwealth\ of\ Australia/CVS/Root;


cd docroot/cleanwise/externals/dojo_1.0.2/dojox/data/demos/geography;
rm United;
rm Commonwealth;
cd Mexico;
rm Mexico;

cd ~/webapp/stjohn;
cd docroot/cleanwise/externals/dojo_1.1.0/dojox/data/demos/geography;
rm United;
rm Commonwealth;
cd Mexico;
rm Mexico;

