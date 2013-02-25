# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table aspects (
  id                        bigint auto_increment not null,
  author_id                 bigint,
  create_at                 datetime,
  name                      varchar(255),
  constraint pk_aspects primary key (id))
;

create table aspect_members (
  aspect_id                 bigint,
  contact_id                bigint)
;

create table comments (
  id                        bigint auto_increment not null,
  content                   varchar(255),
  author_id                 bigint,
  create_at                 datetime,
  modify_at                 datetime,
  status                    varchar(255),
  post_id                   bigint,
  type                      varchar(255),
  constraint pk_comments primary key (id))
;

create table contacts (
  id                        bigint auto_increment not null,
  owner_id                  bigint,
  member_id                 bigint,
  create_at                 datetime,
  constraint pk_contacts primary key (id))
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

create table likes (
  id                        bigint auto_increment not null,
  post_id                   bigint,
  comment_id                bigint,
  type                      varchar(255),
  author_id                 bigint,
  create_at                 datetime,
  constraint pk_likes primary key (id))
;

create table mentions (
  id                        bigint auto_increment not null,
  user_id                   bigint,
  post_id                   bigint,
  comment_id                bigint,
  constraint pk_mentions primary key (id))
;

create table people (
  id                        bigint auto_increment not null,
  nickname                  varchar(255),
  summary                   varchar(255),
  location                  varchar(255),
  gender                    varchar(255),
  qq                        varchar(255),
  height                    float,
  weight                    float,
  user_id                   bigint,
  constraint pk_people primary key (id))
;

create table photos (
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
  constraint pk_photos primary key (id))
;

create table posts (
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
  constraint pk_posts primary key (id))
;

create table post_shares (
  post_id                   bigint,
  person_id                 bigint,
  aspect_id                 bigint,
  type                      varchar(255),
  author_id                 bigint,
  create_at                 datetime)
;

create table users (
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
  constraint pk_users primary key (id))
;

alter table aspects add constraint fk_aspects_author_1 foreign key (author_id) references users (id) on delete restrict on update restrict;
create index ix_aspects_author_1 on aspects (author_id);
alter table aspect_members add constraint fk_aspect_members_aspect_2 foreign key (aspect_id) references aspects (id) on delete restrict on update restrict;
create index ix_aspect_members_aspect_2 on aspect_members (aspect_id);
alter table aspect_members add constraint fk_aspect_members_contact_3 foreign key (contact_id) references contacts (id) on delete restrict on update restrict;
create index ix_aspect_members_contact_3 on aspect_members (contact_id);
alter table comments add constraint fk_comments_author_4 foreign key (author_id) references users (id) on delete restrict on update restrict;
create index ix_comments_author_4 on comments (author_id);
alter table comments add constraint fk_comments_post_5 foreign key (post_id) references posts (id) on delete restrict on update restrict;
create index ix_comments_post_5 on comments (post_id);
alter table contacts add constraint fk_contacts_owner_6 foreign key (owner_id) references users (id) on delete restrict on update restrict;
create index ix_contacts_owner_6 on contacts (owner_id);
alter table contacts add constraint fk_contacts_member_7 foreign key (member_id) references users (id) on delete restrict on update restrict;
create index ix_contacts_member_7 on contacts (member_id);
alter table likes add constraint fk_likes_post_8 foreign key (post_id) references posts (id) on delete restrict on update restrict;
create index ix_likes_post_8 on likes (post_id);
alter table likes add constraint fk_likes_comment_9 foreign key (comment_id) references comments (id) on delete restrict on update restrict;
create index ix_likes_comment_9 on likes (comment_id);
alter table likes add constraint fk_likes_author_10 foreign key (author_id) references users (id) on delete restrict on update restrict;
create index ix_likes_author_10 on likes (author_id);
alter table mentions add constraint fk_mentions_user_11 foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_mentions_user_11 on mentions (user_id);
alter table mentions add constraint fk_mentions_post_12 foreign key (post_id) references posts (id) on delete restrict on update restrict;
create index ix_mentions_post_12 on mentions (post_id);
alter table mentions add constraint fk_mentions_comment_13 foreign key (comment_id) references comments (id) on delete restrict on update restrict;
create index ix_mentions_comment_13 on mentions (comment_id);
alter table people add constraint fk_people_user_14 foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_people_user_14 on people (user_id);
alter table photos add constraint fk_photos_post_15 foreign key (post_id) references posts (id) on delete restrict on update restrict;
create index ix_photos_post_15 on photos (post_id);
alter table photos add constraint fk_photos_author_16 foreign key (author_id) references users (id) on delete restrict on update restrict;
create index ix_photos_author_16 on photos (author_id);
alter table posts add constraint fk_posts_author_17 foreign key (author_id) references users (id) on delete restrict on update restrict;
create index ix_posts_author_17 on posts (author_id);
alter table posts add constraint fk_posts_parent_18 foreign key (parent_id) references posts (id) on delete restrict on update restrict;
create index ix_posts_parent_18 on posts (parent_id);
alter table post_shares add constraint fk_post_shares_post_19 foreign key (post_id) references posts (id) on delete restrict on update restrict;
create index ix_post_shares_post_19 on post_shares (post_id);
alter table post_shares add constraint fk_post_shares_person_20 foreign key (person_id) references users (id) on delete restrict on update restrict;
create index ix_post_shares_person_20 on post_shares (person_id);
alter table post_shares add constraint fk_post_shares_aspect_21 foreign key (aspect_id) references aspects (id) on delete restrict on update restrict;
create index ix_post_shares_aspect_21 on post_shares (aspect_id);
alter table post_shares add constraint fk_post_shares_author_22 foreign key (author_id) references users (id) on delete restrict on update restrict;
create index ix_post_shares_author_22 on post_shares (author_id);
alter table users add constraint fk_users_person_23 foreign key (person_id) references people (id) on delete restrict on update restrict;
create index ix_users_person_23 on users (person_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table aspects;

drop table aspect_members;

drop table comments;

drop table contacts;

drop table court;

drop table likes;

drop table mentions;

drop table people;

drop table photos;

drop table posts;

drop table post_shares;

drop table users;

SET FOREIGN_KEY_CHECKS=1;

