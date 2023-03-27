insert into users(userId, password, name, email) values ('UserA', 'a', 'a', 'a@a')
insert into users(userId, password, name, email) values ('UserB', 'b', 'b', 'b@b')
insert into users(userId, password, name, email) values ('UserC', 'c', 'c', 'c@c')

insert into articles(writer, title, contents ) values ('UserA','title A','contents A')
insert into articles(writer, title, contents ) values ('UserB','title B','contents B')
insert into articles(writer, title, contents ) values ('UserC','title C','contents C')

insert into REPLIES(articleId, writer, content) values ('1', 'UserA', '이것은 댓글 내용 A 입니당.')
insert into REPLIES(articleId, writer, content) values ('1', 'UserB', '이것은 댓글 내용 B 입니당.')
insert into REPLIES(articleId, writer, content) values ('2', 'UserC', '이것은 내용 입니당.')

