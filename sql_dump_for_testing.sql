-- we don't know how to generate schema programming_school (class Schema) :(
create table if not exists exercise
(
	id int(11) unsigned auto_increment
		primary key,
	title varchar(255) not null,
	description text null
)
;

create table if not exists user_group
(
	id int(11) unsigned auto_increment
		primary key,
	name varchar(255) not null,
	constraint name
		unique (name)
)
;

create table if not exists users
(
	id bigint unsigned auto_increment
		primary key,
	username varchar(255) not null,
	email varchar(255) not null,
	password varchar(255) not null,
	user_group_id int(11) unsigned null,
	constraint email
		unique (email),
	constraint users_user_group_id_fk
		foreign key (user_group_id) references user_group (id)
)
;

create table if not exists solution
(
	id int(11) unsigned auto_increment
		primary key,
	created datetime not null,
	updated datetime null,
	description text null,
	exercise_id int(11) unsigned null,
	users_id bigint unsigned null,
	constraint solution_exercise_id_fk
		foreign key (exercise_id) references exercise (id),
	constraint solution_users_id_fk
		foreign key (users_id) references users (id)
)
;

INSERT INTO programming_school.user_group (id, name) VALUES (1, 'KAT_JEE_S04');

INSERT INTO programming_school.users (id, username, email, password, user_group_id) VALUES (3, 'Test Testowy', 'test@test.test', 'md5(test)', 1);
INSERT INTO programming_school.users (id, username, email, password, user_group_id) VALUES (4, 'adsf', 'asdf', 'sadf', null);
INSERT INTO programming_school.users (id, username, email, password, user_group_id) VALUES (5, 'erfhuj8a09hf', '423q653526', 'f2', null);
INSERT INTO programming_school.users (id, username, email, password, user_group_id) VALUES (6, 'asdf242232', '234234324235dfgbzxgq3456y456srtyh', 'shs5', null);
INSERT INTO programming_school.users (id, username, email, password, user_group_id) VALUES (7, '1298418412', 'af19211', 'f12', null);
INSERT INTO programming_school.users (id, username, email, password, user_group_id) VALUES (9, 'asfcv892u296', 'f2t2y ', 'fga', 1);
INSERT INTO programming_school.users (id, username, email, password, user_group_id) VALUES (10, 'fa234265', 'f2yu222', 'af2621621', null);
INSERT INTO programming_school.users (id, username, email, password, user_group_id) VALUES (11, '12661g gw AT2', '2GF 23G236Y12', '1341', null);
INSERT INTO programming_school.users (id, username, email, password, user_group_id) VALUES (13, 'f230gi jg230i9 j23g', '2 g 23gh', 'AEFG', 1);
INSERT INTO programming_school.users (id, username, email, password, user_group_id) VALUES (14, 'wf 23g 2 t6y2', ' 2g45h', '235', null);
INSERT INTO programming_school.users (id, username, email, password, user_group_id) VALUES (18, 'hory31313131', 'hory31431313151561', '$2a$10$MF9VHwN3tY2krs0jWkze.eeQNSX3b6SHqRtP.malCndCvH.JeXVKK', null);
INSERT INTO programming_school.users (id, username, email, password, user_group_id) VALUES (19, 'piliszko', 'pilich', '$2a$10$Paf844U0tIaJ3BebKY7LPeuBlATH2jFm3AciyLubOMeqcymlNTqWq', null);
INSERT INTO programming_school.users (id, username, email, password, user_group_id) VALUES (20, 'asfd2341', 'sdaf222222', '$2a$10$6z7qaMUxL4VliUs2RFqvve/cPhV/9DLnDu5wmvoCraxQ3TERSVaVa', null);