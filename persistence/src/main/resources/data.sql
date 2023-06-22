INSERT INTO gifts
VALUES (default,'giftN1','very good gift',1000,7,'6-17-2023 12:36:00','6-17-2023 12:36:00');
INSERT INTO gifts
VALUES (default,'giftN2','Not good gift',2000,14,'6-17-2023 12:37:00','6-17-2023 12:37:00');
INSERT INTO gifts
VALUES (default,'giftN3','very bad gift',3000,21,'6-17-2023 12:38:00','6-17-2023 12:38:00');
INSERT INTO gifts
VALUES (default,'giftN4','not bad gift',4000,28,'6-17-2023 12:39:00','6-17-2023 12:39:00');

INSERT INTO gifts
VALUES (default,'giftN5','very nice gift',5000,28,'6-17-2023 12:39:00','6-17-2023 12:39:00');
INSERT INTO gifts
VALUES (default,'giftN6','not nice gift',6000,28,'6-17-2023 12:39:00','6-17-2023 12:39:00');
INSERT INTO gifts
VALUES (default,'giftN7','normal gift',7000,28,'6-17-2023 12:39:00','6-17-2023 12:39:00');
INSERT INTO gifts
VALUES (default,'giftN8','hello gift',8000,28,'6-17-2023 12:39:00','6-17-2023 12:39:00');

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
CREATE TABLE users
(
    id           SERIAL           NOT NULL,
    user_name    VARCHAR(64)      NOT NULL,
    password     VARCHAR(64)      NOT NULL,
    email        VARCHAR(64)      NOT NULL,
    PRIMARY KEY (id)

);
INSERT INTO users
VALUES (DEFAULT,'Harry','123456','Harry@gmail.com');

INSERT INTO users
VALUES (DEFAULT,'Dobby','123456','Dobby@gmail.com');

INSERT INTO users
VALUES (DEFAULT,'Draco','123456','Draco@gmail.com');