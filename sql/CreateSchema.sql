drop user @dbUser@ cascade;

create user @dbUser@ identified by @dbPassword@
default tablespace @dbDataTablespace@
quota unlimited on @dbDataTablespace@
quota unlimited on @dbIndexTablespace@
temporary tablespace @dbTempTablespace@
profile default
account unlock;

grant dba to @dbUser@;

alter user @dbUser@ default role dba;
