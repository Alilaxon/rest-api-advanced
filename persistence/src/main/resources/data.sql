INSERT INTO gifts
VALUES (default,'giftN1','very good gift',1000,7,'6-17-2023 12:36:00','6-17-2023 12:36:00'),
(default,'giftN2','Not good gift',2000,14,'6-17-2023 12:37:00','6-17-2023 12:37:00'),
(default,'giftN3','very bad gift',3000,21,'6-17-2023 12:38:00','6-17-2023 12:38:00'),
(default,'giftN4','not bad gift',4000,28,'6-17-2023 12:39:00','6-17-2023 12:39:00'),
(default,'giftN5','very nice gift',5000,28,'6-17-2023 12:39:00','6-17-2023 12:39:00'),
(default,'giftN6','not nice gift',6000,28,'6-17-2023 12:39:00','6-17-2023 12:39:00'),
(default,'giftN7','normal gift',7000,28,'6-17-2023 12:39:00','6-17-2023 12:39:00'),
(default,'giftN8','hello gift',8000,28,'6-17-2023 12:39:00','6-17-2023 12:39:00'),
(default,'giftN9','not description',9000,28,'6-17-2023 12:39:00','6-17-2023 12:39:00'),
(default,'giftN10','not description',10000,28,'6-17-2023 12:39:00','6-17-2023 12:39:00');

INSERT INTO tags
VALUES
(default,'red'),
(default,'yellow'),
(default,'green'),
(default,'black'),
(default,'white'),
(default,'blue'),
(default,'pink');

INSERT INTO gifts_tags
VALUES
(DEFAULT,(SELECT id FROM gifts WHERE gift_name ='giftN1'),
         (SELECT id FROM tags WHERE tag_name ='red')),
(DEFAULT,(SELECT id FROM gifts WHERE gift_name ='giftN1'),
        (SELECT id FROM tags WHERE tag_name ='green')),
(DEFAULT,(SELECT id FROM gifts WHERE gift_name ='giftN1'),
        (SELECT id FROM tags WHERE tag_name ='green')),
(DEFAULT,(SELECT id FROM gifts WHERE gift_name ='giftN2'),
        (SELECT id FROM tags WHERE tag_name ='red')),
(DEFAULT,(SELECT id FROM gifts WHERE gift_name ='giftN3'),
        (SELECT id FROM tags WHERE tag_name ='red')),
(DEFAULT,(SELECT id FROM gifts WHERE gift_name ='giftN4'),
        (SELECT id FROM tags WHERE tag_name ='red')),
(DEFAULT,(SELECT id FROM gifts WHERE gift_name ='giftN5'),
        (SELECT id FROM tags WHERE tag_name ='black')),
(DEFAULT,(SELECT id FROM gifts WHERE gift_name ='giftN6'),
        (SELECT id FROM tags WHERE tag_name ='white')),
(DEFAULT,(SELECT id FROM gifts WHERE gift_name ='giftN7'),
        (SELECT id FROM tags WHERE tag_name ='pink')),
(DEFAULT,(SELECT id FROM gifts WHERE gift_name ='giftN8'),
        (SELECT id FROM tags WHERE tag_name ='pink')),
(DEFAULT,(SELECT id FROM gifts WHERE gift_name ='giftN9'),
        (SELECT id FROM tags WHERE tag_name ='blue')),
(DEFAULT,(SELECT id FROM gifts WHERE gift_name ='giftN10'),
        (SELECT id FROM tags WHERE tag_name ='white'));

INSERT INTO users
VALUES (DEFAULT,'Harry','123456','Harry@gmail.com'),
(DEFAULT,'Dobby','123456','Dobby@gmail.com'),
(DEFAULT,'Draco','123456','Draco@gmail.com'),
(DEFAULT,'Neville','123456','Draco@gmail.com');

INSERT INTO roles VALUES
(DEFAULT,'ROLE_USER'),(DEFAULT,'ROLE_ADMIN');
