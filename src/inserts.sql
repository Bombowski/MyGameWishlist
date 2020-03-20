INSERT INTO USER(NAME,EMAIL,ADMIN) VALUES
	('Patryk','patryk080998@gmail.com',1),
    ('Bombo','bumboxowatosciowowaty@gmail.com',0);

INSERT INTO SHOP(NAME) VALUES
	('Instant Gaming'),
    ('Steam'),
    ('G2A');
    
INSERT INTO GAME(NAME) VALUES
	('Minecraft'),
    ('Deep Rock Galactic'),
    ('Payday 2'),
    ('GTA5'),
    ('Cyberpunk 2077');
    
INSERT INTO REVIEW(ID_USER,ID_GAME,RATING) VALUES
    (1, 1, 7),
    (1, 2, 7),
    (1, 4, 6),
    (1, 5, 6),
    (2, 1, 3),
    (2, 2, 3),
    (2, 3, 3),
    (2, 4, 2),
    (2, 5, 2);
    
INSERT INTO LIST(ID_USER, GAME_URL, ID_SHOP, NAME, DEFUALT_PRICE) VALUES
	(1,'URL HERE',2,'DRG',25.00),
    (1,'URL HERE1',1,'DRG',25.00),
    (1,'URL HERE3',2,'DRG',25.00),
    (1,'URL HERE4',2,'DRG',25.00),
    (1,'URL HERE5',3,'DRG',25.00),
    (1,'URL HERE6',2,'DRG',25.00),
    (1,'URL HERE7',1,'DRG',25.00);
    