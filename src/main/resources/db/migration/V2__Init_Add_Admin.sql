INSERT INTO springblog.sequence_user (next_val) VALUES (2);

INSERT INTO springblog.sequence_post (next_val) VALUES (1);


insert into user (id, username, password, active)
values (1, 'admin', '$2a$08$fBc2Vp8RGzbA0CVeYoHSzeVAEI10ZP7UGjqeSacDZsX.J78u705nm', true);

insert into user_role (user_id, roles)
values (1, 'User'), (1, 'Admin');
