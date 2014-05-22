# make sure that the host and ports match the JBoss 
# installation that you are trying to talk to.

. ../app.sh.env

# the sqlplus client does not support this env variable
unset NLS_LANG

if [ -z ${XSUITE_HOME} ] 
then
  echo "XSUITE_HOME is not defined."
  exit
fi

## Field legend
##
## A: action code? (A = add, C = change, D = delete) 
## B: site id (ship to id for the distributor)
## C: account number 
## D: site name
## E: account name
## F: address 1
## G: address 2
## H: city
## I: state
## J: zip code
##
##
sqlplus -S ${XSUITE_DBUSER}/${XSUITE_DBPASS}@${XSUITE_DBSERVER} << EOF
set head off
set wrap off
set linesize 300
set width 
spool address-report.txt 

select '"A","' 
  || be.bus_entity_id 
  || '","' || be2.erp_num 
  || '","' || be.short_desc 
  || '","' || be2.short_desc 
  || '","' || ca.address1 
  || '","' || ca.address2 
  || '","' || ca.city 
  || '","' || ca.state_province_cd 
  || '","' || ca.postal_code 
  || '"' 
  from clw_bus_entity be , clw_bus_entity be2, 
  clw_bus_entity_assoc bea , clw_address ca
  where be.bus_entity_type_cd = 'SITE'and
      bea.bus_entity1_id = be.bus_entity_id and
      bea.bus_entity2_id = be2.bus_entity_id and
      bea.bus_entity_assoc_cd = 'SITE OF ACCOUNT' and
      be.bus_entity_id = ca.bus_entity_id
/
quit
EOF



