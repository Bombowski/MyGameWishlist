INSERT INTO USER(NAME,EMAIL,ADMIN) VALUES
	('Patryk','patryk080998@gmail.com',1),
    ('Bombo','bumboxowatosciowowaty@gmail.com',0);

INSERT INTO SHOP(NAME, URL) VALUES
	('Instant Gaming','https://www.instant-gaming.com/en/'),
    ('Steam','https://store.steampowered.com/'),
    ('G2A','https://www.g2a.com/');
    
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
    
INSERT INTO LIST(ID_USER) VALUES (1),(2);
    
INSERT INTO WISHLIST_GAME(URL, ID_USER, ID_SHOP, NAME, DEFAULT_PRICE, CURRENT_PRICE, LAST_NOTIFIED_PRICE) VALUES
	('URL',1,1,'NAME',25.00,25.00,25.00),
    ('URL2',1,3,'NAME',25.00,25.00,25.00),
    ('URL2',2,3,'NAME',25.00,25.00,25.00),
    ('URL3',2,2,'NAME',25.00,25.00,25.00);
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    