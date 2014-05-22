drop user @analyticUser@ cascade;

create user @analyticUser@ identified by @analyticPassword@
default tablespace @analyticDataTablespace@
quota unlimited on @analyticDataTablespace@
quota unlimited on @analyticIndexTablespace@
temporary tablespace @analyticTempTablespace@
profile default
account unlock;

grant dba to @analyticUser@;

alter user @analyticUser@ default role dba;
