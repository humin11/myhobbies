# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table court (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  logo                      varchar(255),
  address                   varchar(255),
  telephone                 varchar(255),
  type                      integer,
  businesshours             varchar(255),
  businfo                   varchar(255),
  parking                   varchar(255),
  price                     varchar(255),
  longitude                 double,
  latitude                  double,
  constraint pk_court primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  username                  varchar(255),
  password                  varchar(255),
  permission                varchar(255),
  isinit                    tinyint(1) default 0,
  status                    tinyint(1) default 0,
  create_at                 datetime,
  update_at                 datetime,
  locked_at                 datetime,
  unlocked_at               datetime,
  constraint pk_user primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table court;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

