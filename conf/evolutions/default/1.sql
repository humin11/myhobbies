# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table comment (
  id                        bigint auto_increment not null,
  content                   varchar(255),
  author_id                 bigint,
  create_at                 datetime,
  modify_at                 datetime,
  status                    varchar(255),
  type                      varchar(255),
  constraint pk_comment primary key (id))
;

create table contact (
  id                        bigint auto_increment not null,
  create_at                 datetime,
  constraint pk_contact primary key (id))
;

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

create table groups (
  id                        bigint auto_increment not null,
  author_id                 bigint,
  create_at                 datetime,
  name                      varchar(255),
  constraint pk_groups primary key (id))
;

create table groups_member (
  group_id                  bigint,
  contact_id                bigint)
;

create table likes (
  id                        bigint auto_increment not null,
  type                      varchar(255),
  author_id                 bigint,
  create_at                 datetime,
  constraint pk_likes primary key (id))
;

create table person (
  id                        bigint auto_increment not null,
  nickname                  varchar(255),
  summary                   varchar(255),
  location                  varchar(255),
  gender                    varchar(255),
  qq                        varchar(255),
  height                    float,
  weight                    float,
  user_id                   bigint,
  constraint pk_person primary key (id))
;

create table photo (
  id                        bigint auto_increment not null,
  post_id                   bigint,
  author_id                 bigint,
  create_at                 datetime,
  small                     varchar(255),
  medium                    varchar(255),
  large                     varchar(255),
  name                      varchar(255),
  photo_path                varchar(255),
  photo_name                varchar(255),
  constraint pk_photo primary key (id))
;

create table post (
  id                        bigint auto_increment not null,
  content                   varchar(255),
  author_id                 bigint,
  create_at                 datetime,
  modify_at                 datetime,
  parent_id                 bigint,
  status                    varchar(255),
  shareable                 tinyint(1) default 0,
  commentable               tinyint(1) default 0,
  likeable                  tinyint(1) default 0,
  constraint pk_post primary key (id))
;

create table post_share (
  post_id                   bigint,
  share_person_id           bigint,
  share_group_id            bigint,
  share_type                varchar(255),
  author_id                 bigint,
  create_at                 datetime)
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
  person_id                 bigint,
  constraint pk_user primary key (id))
;

alter table comment add constraint fk_comment_author_1 foreign key (author_id) references user (id) on delete restrict on update restrict;
create index ix_comment_author_1 on comment (author_id);
alter table groups add constraint fk_groups_author_2 foreign key (author_id) references user (id) on delete restrict on update restrict;
create index ix_groups_author_2 on groups (author_id);
alter table groups_member add constraint fk_groups_member_group_3 foreign key (group_id) references groups (id) on delete restrict on update restrict;
create index ix_groups_member_group_3 on groups_member (group_id);
alter table groups_member add constraint fk_groups_member_contact_4 foreign key (contact_id) references contact (id) on delete restrict on update restrict;
create index ix_groups_member_contact_4 on groups_member (contact_id);
alter table likes add constraint fk_likes_author_5 foreign key (author_id) references user (id) on delete restrict on update restrict;
create index ix_likes_author_5 on likes (author_id);
alter table person add constraint fk_person_user_6 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_person_user_6 on person (user_id);
alter table photo add constraint fk_photo_post_7 foreign key (post_id) references post (id) on delete restrict on update restrict;
create index ix_photo_post_7 on photo (post_id);
alter table photo add constraint fk_photo_author_8 foreign key (author_id) references user (id) on delete restrict on update restrict;
create index ix_photo_author_8 on photo (author_id);
alter table post add constraint fk_post_author_9 foreign key (author_id) references user (id) on delete restrict on update restrict;
create index ix_post_author_9 on post (author_id);
alter table post add constraint fk_post_parent_10 foreign key (parent_id) references post (id) on delete restrict on update restrict;
create index ix_post_parent_10 on post (parent_id);
alter table post_share add constraint fk_post_share_post_11 foreign key (post_id) references post (id) on delete restrict on update restrict;
create index ix_post_share_post_11 on post_share (post_id);
alter table post_share add constraint fk_post_share_share_person_12 foreign key (share_person_id) references user (id) on delete restrict on update restrict;
create index ix_post_share_share_person_12 on post_share (share_person_id);
alter table post_share add constraint fk_post_share_share_group_13 foreign key (share_group_id) references groups (id) on delete restrict on update restrict;
create index ix_post_share_share_group_13 on post_share (share_group_id);
alter table post_share add constraint fk_post_share_author_14 foreign key (author_id) references user (id) on delete restrict on update restrict;
create index ix_post_share_author_14 on post_share (author_id);
alter table user add constraint fk_user_person_15 foreign key (person_id) references person (id) on delete restrict on update restrict;
create index ix_user_person_15 on user (person_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table comment;

drop table contact;

drop table court;

drop table groups;

drop table groups_member;

drop table likes;

drop table person;

drop table photo;

drop table post;

drop table post_share;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

