INSERT INTO springblog.sequence_user (next_val) VALUES (2);

INSERT INTO springblog.sequence_post (next_val) VALUES (1);


insert into user (id, username, password, active)
values (1, 'admin', '123', true);

insert into user_role (user_id, roles)
values (1, 'User'), (1, 'Admin');