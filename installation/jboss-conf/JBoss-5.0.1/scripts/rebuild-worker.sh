echo "use parameter: master if database build options (dbrefcd, load_i18n, etc) 
should be called"
echo "see cvs.log for cvs information"
echo "see build.log for ant build inforamtion"
cd stjohn
echo "doing cvs update..."
cvs -q update -d 
echo "done cvs update"

cd ..
echo "stopping server"
sh stop-webapp.sh
cd stjohn
echo "doing build"
ant clean deployall
if [ "$1" = "master" ]
then
echo "********** Doing a master build **********"
echo "calling dbrefcds"
ant dbrefcd
fi
cd ..
echo "done doing build"
echo "starting server"
sh start-webapp.sh
#wait for server to startup


if [ "$1" = "master" ]
then
echo "sleeping..."
sleep 200
echo "doing i18n (master build)"
echo "********** Doing a master build **********"
echo "calling load_i18n"
cd stjohn
ant load_i18n
echo "done i18n"
fi
