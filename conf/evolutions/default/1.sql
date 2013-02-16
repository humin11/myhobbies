# --- First database schema

# --- !Ups

create table user (
  id                        	bigint not null primary key,
  email                      	varchar(255) not null,
  username                      varchar(255) not null,
  password                      varchar(255) not null,
  isinit						tinyint,
  status						tinyint,
  create_at						timestamp,
  update_at						timestamp,
  locked_at						timestamp,
  unlocked_at					timestamp
);

create index ix_user_id on user (id, username, password, isinit, status);

create table people (
  id                        	bigint not null primary key,
  nickname                      varchar(255),
  realname                      varchar(255),
  summary						varchar(1024),
  playposition					varchar(255),
  location						varchar(255),
  nativeplace					varchar(255),
  gender						varchar(255),
  birthday						date,
  maritalstatus					varchar(25),
  bloodtype						varchar(25),
  qq							varchar(25),
  msn							varchar(100),
  hobbies						varchar(255),
  height						int,
  weight						int,
  user_id						bigint not null,
  foreign key(user_id)			references user(id) on delete cascade
);

create index ix_people_id people (id, nickname, realname, hobbies, height, weight, user_id);

create table company (
 id 							bigint not null primary key,
 name							varchar(255),
 province						varchar(255),
 city							varchar(255),
 city_id						bigint,
 job							varchar(255),
 start_at						date,
 end_at							date,
 user_id						bigint not null,
 foreign key(user_id)			references user(id) on delete cascade
);

-- for city table

create index ix_company_id company (id, name, province, city, position, user_id);

create table school (
 id							bigint not null primary key,
 name						varchar(255),
 major						varchar(255),
 school_id					bitint,
 start_at					date,
 end_at						date,
 user_id						bigint not null,
 foreign key(user_id)			references user(id) on delete cascade
);

-- for school table

create index ix_school_id school (id, name, major, user_id);

create table attribute (
 id						bigint not null primary key,
 strength				int,
 agility				int,
 intelligence			int,
 charm					int,
 status					varchar(255),
 user_id				bigint not null,
 foreign key(user_id)			references user(id) on delete cascade
);

create index ix_attribute_id attribute (id, strength, agility, intelligence, charm, status, user_id);

create table avatar (
 id 					bigint not null primary key,
 small					varchar(255),
 middle					varchar(255),
 big					varchar(255),
 origin					varchar(255),
 user_id				bigint not null,
 foreign key(user_id)			references user(id) on delete cascade
);

create index ix_avatar_id avatar (id, small, middle, big, origin, user_id);

create table tag (
 id					bigint not null primary key,
 name				varchar(255),
 object_id			bigint not null,
 object_type		tinyint
);

create index ix_tag_id tag (id, name, object_id, object_type);

create table album (
 id 			bigint not null primary key,
 name			varchar(255),
 description	varchar(1024),
 create_at				timestamp,
 update_at				timestamp,
 user_id				bigint not null,
 foreign key(user_id)			references user(id) on delete cascade
);

create index ix_album_id album (id, name, user_id);

create table picture (
 id					bigint not null primary key,
 small				varchar(255),
 middle					varchar(255),
 big					varchar(255),
 origin					varchar(255),
 name					varchar(255),
 description			varchar(2048),
 kind					varchar(255),
 create_at				timestamp,
 update_at				timestamp
);

create index ix_picture_id picture (id, name, small, middle, big, origin);

create table album2picture (
 id					bigint not null primary key,
 album_id			bigint not null,
 picture_id			bigint not null,
 foreign key(album_id)			references album(id) on delete cascade,
 foreign key(picture_id)		references picture(id) on delete cascade
);

create index ix_album_picture album2picture (album_id, picture_id);

create table message ();

create table reply ();

create table ground ();

create table friend ();

create table brand ();

create table goods ();

create table message2picture ();

create table goods2picture ();

create table ground2picture ();

create table goods2picture ();

# --- !Downs

drop table if exists user;

drop table if exists people;

drop table if exists company;

drop table if exists attribute;

drop table if exists avatar;

drop table if exists tag;

drop table if exists picture;

drop table if exists tag;

