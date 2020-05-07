SELECT * FROM STORE;
SELECT * FROM USER;
SELECT * FROM REVIEW;
SELECT * FROM WISHLIST_GAME;
SELECT * FROM SECRET_VARIABLES;
SELECT * FROM STEAM_GAMES;
SELECT * FROM PRICE_TIMELINE ORDER BY TIME;
SELECT * FROM WISHLIST_GAME_PRICE_TIMELINE;

DELETE FROM PRICE_TIMELINE WHERE URL != '' AND TIME > 0;
    
UPDATE PRICE_TIMELINE SET PRICE = 25 WHERE URL = '/6323-buy-key-steam-deep-rock-galactic' AND TIME = '2020-04-15 13:11:00';

INSERT INTO REVIEW (ID_USER, ID_GAME, RATING) VALUES
		(1,4,10) ON DUPLICATE KEY UPDATE
		RATING = 1;
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        