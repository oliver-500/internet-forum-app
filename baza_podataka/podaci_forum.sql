insert into forum.room (name) values ("Muzika");
insert into forum.room (name) values ("Sport");
insert into forum.room (name) values ("Nauka");
insert into forum.room (name) values ("Kultura");

insert into forum.user (user_group, username, email, password, registered) values (1, "moderator", "m@gmail.com", '$2a$10$RD9qXaimn8XTFtm2nr7LXejOxGwVq.UrFwiwbvfZQuDZZMves52qa', false);
insert into forum.user (user_group, username, email, password, registered) values (0, "user", "olivereric500@gmail.com", '$2a$10$RD9qXaimn8XTFtm2nr7LXejOxGwVq.UrFwiwbvfZQuDZZMves52qa', false);

insert into forum.user (user_group, username, email, password, registered) values (2, "admin", "olivereric529@outlook.com", '$2a$10$RD9qXaimn8XTFtm2nr7LXejOxGwVq.UrFwiwbvfZQuDZZMves52qa', true);

INSERT INTO forum.comment (user_id, approved, posted_datetime, room_id, content) VALUES (1, true, '2024-03-20 10:30:00', 1, "prvi komentar");
INSERT INTO forum.comment (user_id, approved, posted_datetime, room_id, content) VALUES (1, false, '2024-03-20 10:30:00', 1, "prvi  komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarntar");


insert into forum.permission (name) values ("create");
insert into forum.permission (name) values ("update");
insert into forum.permission (name) values ("delete");

insert into forum.access (permission_id, room_id, user_id) values (1, 1, 2);
insert into forum.access (permission_id, room_id, user_id) values (2, 2, 2);
insert into forum.access (permission_id, room_id, user_id) values (2, 1, 2);
insert into forum.access (permission_id, room_id, user_id) values (2, 3, 2);

