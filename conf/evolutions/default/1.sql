# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table circles (
  id                        bigint auto_increment not null,
  author_id                 bigint,
  create_at                 datetime,
  update_at                 datetime,
  name                      varchar(255),
  order_id                  integer,
  constraint pk_circles primary key (id))
;

create table circle_members (
  circle_id                 bigint,
  contact_id                bigint,
  create_at                 datetime,
  update_at                 datetime)
;

create table circle_visibilities (
  id                        bigint auto_increment not null,
  circle_id                 bigint,
  shareable_id              bigint,
  shareable_type            varchar(255),
  create_at                 datetime,
  update_at                 datetime,
  constraint pk_circle_visibilities primary key (id))
;

create table comments (
  id                        bigint auto_increment not null,
  content                   varchar(255),
  author_id                 bigint,
  create_at                 datetime,
  update_at                 datetime,
  status                    varchar(255),
  commentable_id            bigint,
  commentable_type          varchar(255),
  constraint pk_comments primary key (id))
;

create table contacts (
  id                        bigint auto_increment not null,
  owner_id                  bigint,
  person_id                 bigint,
  create_at                 datetime,
  update_at                 datetime,
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
  likeable_id               bigint,
  likeable_type             varchar(255),
  author_id                 bigint,
  create_at                 datetime,
  constraint pk_likes primary key (id))
;

create table mentions (
  id                        bigint auto_increment not null,
  user_id                   bigint,
  source_id                 bigint,
  source_type               varchar(255),
  constraint pk_mentions primary key (id))
;

create table notifications (
  id                        bigint auto_increment not null,
  create_at                 datetime,
  update_at                 datetime,
  recipient_id              bigint,
  source_id                 bigint,
  source_type               varchar(255),
  unread                    tinyint(1) default 0,
  constraint pk_notifications primary key (id))
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
  create_at                 datetime,
  update_at                 datetime,
  constraint pk_people primary key (id))
;

create table photos (
  id                        bigint auto_increment not null,
  post_id                   bigint,
  author_id                 bigint,
  create_at                 datetime,
  update_at                 datetime,
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
  update_at                 datetime,
  parent_id                 bigint,
  status                    varchar(255),
  ispublic                  tinyint(1) default 0,
  constraint pk_posts primary key (id))
;

create table profiles (
  id                        bigint auto_increment not null,
  user_id                   bigint,
  notification_type         varchar(255),
  comment_type              varchar(255),
  constraint pk_profiles primary key (id))
;

create table share_visibilities (
  id                        bigint auto_increment not null,
  recipient_id              bigint,
  shareable_id              bigint,
  shareable_type            varchar(255),
  hidden                    tinyint(1) default 0,
  create_at                 datetime,
  update_at                 datetime,
  constraint pk_share_visibilities primary key (id))
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

alter table circles add constraint fk_circles_author_1 foreign key (author_id) references users (id) on delete restrict on update restrict;
create index ix_circles_author_1 on circles (author_id);
alter table circle_members add constraint fk_circle_members_circle_2 foreign key (circle_id) references circles (id) on delete restrict on update restrict;
create index ix_circle_members_circle_2 on circle_members (circle_id);
alter table circle_members add constraint fk_circle_members_contact_3 foreign key (contact_id) references contacts (id) on delete restrict on update restrict;
create index ix_circle_members_contact_3 on circle_members (contact_id);
alter table circle_visibilities add constraint fk_circle_visibilities_circle_4 foreign key (circle_id) references circles (id) on delete restrict on update restrict;
create index ix_circle_visibilities_circle_4 on circle_visibilities (circle_id);
alter table comments add constraint fk_comments_author_5 foreign key (author_id) references users (id) on delete restrict on update restrict;
create index ix_comments_author_5 on comments (author_id);
alter table contacts add constraint fk_contacts_owner_6 foreign key (owner_id) references users (id) on delete restrict on update restrict;
create index ix_contacts_owner_6 on contacts (owner_id);
alter table contacts add constraint fk_contacts_person_7 foreign key (person_id) references users (id) on delete restrict on update restrict;
create index ix_contacts_person_7 on contacts (person_id);
alter table likes add constraint fk_likes_author_8 foreign key (author_id) references users (id) on delete restrict on update restrict;
create index ix_likes_author_8 on likes (author_id);
alter table mentions add constraint fk_mentions_user_9 foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_mentions_user_9 on mentions (user_id);
alter table notifications add constraint fk_notifications_recipient_10 foreign key (recipient_id) references users (id) on delete restrict on update restrict;
create index ix_notifications_recipient_10 on notifications (recipient_id);
alter table people add constraint fk_people_user_11 foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_people_user_11 on people (user_id);
alter table photos add constraint fk_photos_post_12 foreign key (post_id) references posts (id) on delete restrict on update restrict;
create index ix_photos_post_12 on photos (post_id);
alter table photos add constraint fk_photos_author_13 foreign key (author_id) references users (id) on delete restrict on update restrict;
create index ix_photos_author_13 on photos (author_id);
alter table posts add constraint fk_posts_author_14 foreign key (author_id) references users (id) on delete restrict on update restrict;
create index ix_posts_author_14 on posts (author_id);
alter table posts add constraint fk_posts_parent_15 foreign key (parent_id) references posts (id) on delete restrict on update restrict;
create index ix_posts_parent_15 on posts (parent_id);
alter table profiles add constraint fk_profiles_user_16 foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_profiles_user_16 on profiles (user_id);
alter table share_visibilities add constraint fk_share_visibilities_recipient_17 foreign key (recipient_id) references users (id) on delete restrict on update restrict;
create index ix_share_visibilities_recipient_17 on share_visibilities (recipient_id);
alter table users add constraint fk_users_person_18 foreign key (person_id) references people (id) on delete restrict on update restrict;
create index ix_users_person_18 on users (person_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table circles;

drop table circle_members;

drop table circle_visibilities;

drop table comments;

drop table contacts;

drop table court;

drop table likes;

drop table mentions;

drop table notifications;

drop table people;

drop table photos;

drop table posts;

drop table profiles;

drop table share_visibilities;

drop table users;

SET FOREIGN_KEY_CHECKS=1;

