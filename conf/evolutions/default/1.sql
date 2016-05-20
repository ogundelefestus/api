# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table bills (
  id                            integer auto_increment not null,
  customer_id                   integer,
  billed_date                   date,
  cost                          double,
  taxes                         double,
  paid                          tinyint(1) default 0,
  payment_date                  date,
  created_at                    datetime(6),
  updated_at                    datetime(6),
  constraint pk_bills primary key (id)
);

create table customers (
  id                            integer auto_increment not null,
  identity_id                   integer,
  register_date                 date,
  status                        tinyint(1) default 0,
  closing_date                  date,
  created_at                    datetime(6),
  updated_at                    datetime(6),
  constraint uq_customers_identity_id unique (identity_id),
  constraint pk_customers primary key (id)
);

create table identities (
  id                            integer auto_increment not null,
  citizen_no                    bigint,
  full_name                     varchar(255),
  birthdate                     date,
  place_of_birth                integer,
  father_name                   varchar(255),
  mother_name                   varchar(255),
  created_at                    datetime(6),
  updated_at                    datetime(6),
  constraint pk_identities primary key (id)
);

create table users (
  id                            integer auto_increment not null,
  email                         varchar(255) not null,
  password                      varbinary(64) not null,
  full_name                     varchar(255),
  created_at                    datetime(6),
  updated_at                    datetime(6),
  constraint uq_users_email unique (email),
  constraint pk_users primary key (id)
);

alter table customers add constraint fk_customers_identity_id foreign key (identity_id) references identities (id) on delete restrict on update restrict;


# --- !Downs

alter table customers drop foreign key fk_customers_identity_id;

drop table if exists bills;

drop table if exists customers;

drop table if exists identities;

drop table if exists users;

