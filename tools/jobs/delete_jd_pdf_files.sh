#For Production server
#Old
#WEBDEPLOY=/apps/xadminProd/webapp/EJBServer5/server/defst/deploy/defst.war 

#WEBDEPLOY=/apps/xadminProd/webapp/EJBServer5/server/defst/deploy/xsuite.ear/defst.war 

#For QA and Dev boxes
#Old
#WEBDEPLOY=/espendwise/xstjohn/webapp/EJBServer5/server/defst/deploy/defst.war 

WEBDEPLOY=/espendwise/xstjohn/webapp/EJBServer5/server/defst/deploy/xsuite.ear/defst.war 

cd $WEBDEPLOY/en/products/msds
rm jdws_tmp_*