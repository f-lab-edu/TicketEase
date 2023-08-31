create table if not exists ember (created_date datetime(6), id bigint not null auto_increment, nick_name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
