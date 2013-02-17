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

create index ix_people_id on people (id, nickname, realname, hobbies, height, weight, user_id);

create table company (
 id 							bigint not null primary key,
 name							varchar(255),
 province						varchar(255),
 city							varchar(255),
 city_id						bigint
);

-- for city table

create index ix_company_id on company (id, name, province, city);

create table company_member (
 id							bigint not null primary key,
 company_id					bigint not null,
 job						varchar(255),
 jobtitle					varchar(255),
 start_at					date,
 end_at						date,
 user_id					bigint not null,
 foreign key(company_id)	references company(id) on delete cascade,
 foreign key(user_id)		references user(id) on delete cascade
);

create index ix_company_member_id on company_member (id, company_id, user_id);

create table school (
 id							bigint not null primary key,
 name						varchar(255),
 city_id					bigint
);

-- for school table

create index ix_school_id on school (id, name);

create table school_member (
 id							bigint not null primary key,
 school_id					bigint not null,
 user_id					bigint not null,
 major						varchar(255),
 start_at					date,
 end_at						date,
 foreign key(school_id)		references school(id) on delete cascade,
 foreign key(user_id)		references user(id) on delete cascade
);

create index ix_school_member_id on school_member (id, school_id, user_id, major);

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

create index ix_attribute_id on attribute (id, strength, agility, intelligence, charm, status, user_id);

create table avatar (
 id 					bigint not null primary key,
 small					varchar(255),
 middle					varchar(255),
 big					varchar(255),
 origin					varchar(255),
 user_id				bigint not null,
 foreign key(user_id)			references user(id) on delete cascade
);

create index ix_avatar_id on avatar (id, user_id);
create index ix_avatar_pic on avatar (id, small, middle, big, origin);

create table tag (
 id					bigint not null primary key,
 name				varchar(255),
 object_id			bigint not null,
 object_type		tinyint
);

create index ix_tag_id on tag (id, name, object_id, object_type);

create table album (
 id 			bigint not null primary key,
 name			varchar(255),
 description	varchar(1024),
 create_at				timestamp,
 update_at				timestamp,
 object_id			bigint not null,
 object_type		tinyint
);

create index ix_album_id on album (id, name, object_id, object_type);

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

create index ix_picture_id on picture (id, name);
create index ix_picture_pic on picture (id, small, middle, big, origin);

create table message (
 id					bigint not null primary key,
 user_id			bigint not null,
 message			varchar(2048),
 object_type		tinyint,
 object_id			bigint,
 create_at			timestamp,
 update_at			timestamp,
 foreign key(user_id)		references user(id) on delete cascade
);

create index ix_message_id on message (id, user_id, object_type, object_id, update_at);

create table reply (
 id					bigint not null primary key,
 user_id			bigint not null,
 message			varchar(2048),
 message_id			bigint,
 create_at			timestamp,
 update_at			timestamp,
 foreign key(user_id)		references user(id) on delete cascade
);

create index ix_reply_id on reply (id, user_id, message_id, update_at);

create table support (
 id					bigint not null primary key,
 user_id			bigint not null,
 message_id			bigint,
 create_at			timestamp,
 update_at			timestamp,
 foreign key(user_id)		references user(id) on delete cascade
);

create index ix_support_id on support (id, user_id, message_id, update_at);

create table ground (
 id					bigint not null primary key,
 name				varchar(255),
 logo				varchar(255),
 address			varchar(1024),
 telephone			varchar(32),
 type				tinyint,
 businesshours		varchar(255),
 businfo			varchar(1024),
 parking			varchar(255),
 price				varchar(100),
 longitude			varchar(20),
 latitude			varchar(20)
);

create index ix_ground_id on ground (id, name, type, longitude, latitude);

create table friend (
 id					bigint not null primary key,
 user_id			bigint not null,
 friend_id			bigint not null,
 create_at			timestamp,
 update_at			timestamp,
 foreign key(user_id)		references user(id) on delete cascade,
 foreign key(friend_id)		references user(id) on delete cascade
);

create index ix_friend_id on friend (id, user_id, friend_id, update_at);

create table groups (
 id					bigint not null primary key,
 name				varchar(255),
 createuser			bigint not null,
 logo				varchar(255),
 contact			varchar(255),
 description		varchar(2048),
 create_at			timestamp,
 update_at			timestamp,
 foreign key(createuser)		references user(id) on delete cascade
);

create index ix_groups_id on groups (id, name, createuser, update_at);

create table groups_member (
 id 				bigint not null primary key,
 groups_id			bigint not null,
 user_id			bigint not null,
 create_at			timestamp,
 update_at			timestamp,
 foreign key(user_id)		references user(id) on delete cascade,
 foreign key(groups_id)		references groups(id) on delete cascade
);

create index ix_groups_member_id on groups_member (id, groups_id, user_id, update_at);

create table brand (
 id					bigint not null primary key,
 name				varchar(255),
 logo				varchar(255),
 history			varchar(2048),
 description		varchar(2048),
 score				double
);

create index ix_brand_id on brand (id, name, score);

create table goods (
 id					bigint not null primary key,
 name				varchar(255),
 logo				varchar(255),
 price				varchar(255),
 score				double,
 color				varchar(255),
 metarial			varchar(255),
 weight				double,
 origin				varchar(255),
 releasedate		date,
 crowd				varchar(255),
 ground				varchar(255),
 description		varchar(2048),
 brand_id			bigint not null
);

create index ix_goods_id on goods (id, name, price, score, crowd);

create table object_picture (
 id					bigint not null primary key,
 object_id			bigint not null,
 object_type		tinyint,
 picture_id			bigint not null,
 foreign key(picture_id)		references picture(id) on delete cascade
);

create index ix_object_picture on object_picture (id, object_id, object_type, picture_id);

create table goods_purchase (
 id					bigint not null primary key,
 purchasetype		tinyint,
 goods_id			bigint not null,
 user_id			bigint not null,
 create_at			timestamp,
 update_at			timestamp,
 foreign key(goods_id)			references goods(id) on delete cascade
);

create index ix_goods_purchase_id on goods_purchase(id,goods_id, user_id, purchasetype);

# --- !Downs

drop table if exists user;

drop table if exists people;

drop table if exists company;

drop table if exists attribute;

drop table if exists avatar;

drop table if exists tag;

drop table if exists picture;

drop table if exists tag;

