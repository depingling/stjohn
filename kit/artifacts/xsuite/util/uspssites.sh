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

##
sqlplus -S ${XSUITE_DBUSER}/${XSUITE_DBPASS}@${XSUITE_DBSERVER} << EOF
set head off
set newpage none

select 
  be.short_desc 
  || '  '  || be.bus_entity_id
  from clw_bus_entity be , clw_bus_entity be2, 
  clw_bus_entity_assoc bea , clw_address ca
  where be.bus_entity_type_cd = 'SITE' and
      bea.bus_entity1_id = be.bus_entity_id and
      bea.bus_entity2_id = be2.bus_entity_id and
      bea.bus_entity2_id = 100 and
      bea.bus_entity_assoc_cd = 'SITE OF ACCOUNT' and
      be.bus_entity_id = ca.bus_entity_id and
      be.bus_entity_id >= 43000 and
       be.bus_entity_id not in ( select x1.bus_entity_id
from clw_catalog_assoc x1 where x1.catalog_id = 66 and
be.bus_entity_id = x1.bus_entity_id ) and
 be.bus_entity_id not in ( select x2.bus_entity_id
  from clw_property x2 where x2.short_desc like 'Facility%' and
  be.bus_entity_id = x2.bus_entity_id)
/
quit
EOF



