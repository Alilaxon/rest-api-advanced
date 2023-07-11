INSERT INTO gifts
VALUES (default,'giftN1','very good gift',1000,7,'2023-05-25','2023-05-25');
INSERT INTO gifts
VALUES (default,'giftN2','Not good gift',2000,14,'2023-05-25','2023-05-25');
INSERT INTO gifts
VALUES (default,'giftN3','very bad gift',3000,21,'2023-05-25','2023-05-25');
INSERT INTO gifts
VALUES (default,'giftN4','not bad gift',4000,28,'2023-05-25','2023-05-25');

INSERT INTO tags
VALUES (default,'red');
INSERT INTO tags
VALUES (default,'yellow');
INSERT INTO tags
VALUES (default,'green');
INSERT INTO tags
VALUES (default,'black');

INSERT INTO gifts_tags
VALUES (DEFAULT,(SELECT id FROM gifts WHERE gift_name ='giftN1'),
        (SELECT id FROM tags WHERE tag_name ='red'));

INSERT INTO gifts_tags
VALUES (DEFAULT,(SELECT id FROM gifts WHERE gift_name ='giftN1'),
        (SELECT id FROM tags WHERE tag_name ='green'));

INSERT INTO gifts_tags
VALUES (DEFAULT,(SELECT id FROM gifts WHERE gift_name ='giftN1'),
        (SELECT id FROM tags WHERE tag_name ='green'));

INSERT INTO gifts_tags
VALUES (DEFAULT,(SELECT id FROM gifts WHERE gift_name ='giftN2'),
        (SELECT id FROM tags WHERE tag_name ='red'));
INSERT INTO gifts_tags
VALUES (DEFAULT,(SELECT id FROM gifts WHERE gift_name ='giftN3'),
        (SELECT id FROM tags WHERE tag_name ='red'));

INSERT INTO gifts_tags
VALUES (DEFAULT,(SELECT id FROM gifts WHERE gift_name ='giftN4'),
        (SELECT id FROM tags WHERE tag_name ='red'));

INSERT INTO users
VALUES (DEFAULT,'user','123456','user@gmail.com');

INSERT INTO users
VALUES (DEFAULT,'Harry','123456','Harry@gmail.com');

INSERT INTO users
VALUES (DEFAULT,'Dobby','123456','Dobby@gmail.com');

INSERT INTO users
VALUES (DEFAULT,'Draco','123456','Draco@gmail.com');

INSERT INTO users
VALUES (DEFAULT,'Neville','123456','Draco@gmail.com');

INSERT INTO orders
VALUES (DEFAULT,1000,1,1,'2023-05-25');