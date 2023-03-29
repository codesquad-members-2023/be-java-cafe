insert into member values ('first','password!', 'first-name', 'first@email.com');
insert into member values ('second','password!', 'second-name', 'second@email.com');

insert into article (userid, title, contents, timestamp, deleted) values ('first', 'first-title', 'first-contents', now(), false);
insert into article (userid, title, contents, timestamp, deleted) values ('second', 'second-title', 'second-contents', now(), false);


insert into reply (articleId, userid, contents, timestamp, deleted) values ('1', 'first', '첫 번째 첫 번째 댓글', now(), false);
insert into reply (articleId, userid, contents, timestamp, deleted) values ('1', 'second', '첫 번쨰 두 번째 댓글', now(), false);

insert into reply (articleId, userid, contents, timestamp, deleted) values ('2', 'first', '두 번째글  첫 번째 댓글', now(), false);
insert into reply (articleId, userid, contents, timestamp, deleted) values ('2', 'second', '두 번째 두 번째 댓글', now(), false);