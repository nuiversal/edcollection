--通道（EC_CHANNEL）

alter table EC_CHANNEL add column CHANNEL_WIDTH float ;
alter table EC_CHANNEL add column CHANNEL_HEIGHT float ;
alter table EC_CHANNEL add column CHANNEL_MATERIAL  varchar(50) ;


--电缆槽（EC_CHANNEL_DLC）

create table if not exists ec_channel_dlc
(
   OBJ_ID               varchar(50) not null,
   PRJID                varchar(32),
   ORGID                varchar(32),
   YXBH                 varchar(40),
   SSDS                 varchar(32),
   YWDW                 varchar(32),
   CJSJ                 datetime,
   GXSJ                 datetime,
   primary key (OBJ_ID)
);


        