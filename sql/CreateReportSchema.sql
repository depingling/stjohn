drop user @reportUser@ cascade;

create user @reportUser@ identified by @reportPassword@
default tablespace @dbDataTablespace@
quota unlimited on @dbDataTablespace@
quota unlimited on @dbIndexTablespace@
temporary tablespace @dbTempTablespace@
profile default
account unlock;

grant dba to @reportUser@;

alter user @reportUser@ default role dba;
