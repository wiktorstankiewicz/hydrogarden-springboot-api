create table circuit (id integer not null auto_increment, version integer, code varchar(255), name varchar(255), user_id integer, primary key (id)) engine=InnoDB;
create table circuit_schedule (id integer not null auto_increment, version integer, enabled bit not null, end_date datetime(6), frequency_days integer not null, start_date datetime(6), watering_time integer not null, circuit_id integer, primary key (id)) engine=InnoDB;
create table generated_task (id integer not null auto_increment, version integer, datetime datetime(6), done bit not null, mode varchar(255), circuit_id integer, user_id integer, primary key (id)) engine=InnoDB;
create table user (id integer not null auto_increment, version integer, hash_password varchar(255), user_name varchar(255), primary key (id)) engine=InnoDB;
alter table circuit add constraint FKnh13qt8jj3hko6xfepfsl1yxy foreign key (user_id) references user (id);
alter table circuit_schedule add constraint FK34417y9ycakv6tauwtnljxlgn foreign key (circuit_id) references circuit (id);
alter table generated_task add constraint FKby4l4rt6licxp3ohq81yraru3 foreign key (circuit_id) references circuit (id);
alter table generated_task add constraint FKn53xcwvd2dyerbjd00gm9kot5 foreign key (user_id) references user (id);
