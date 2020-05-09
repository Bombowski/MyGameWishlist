SELECT * FROM STORE;
SELECT * FROM USER;
SELECT * FROM REVIEW;
SELECT * FROM WISHLIST_GAME;
SELECT * FROM WISHLIST_GAME_PRICE_TIMELINE;
SELECT * FROM PRICE_TIMELINE;
SELECT * FROM SECRET_VARIABLES;
SELECT * FROM STEAM_GAMES;
SELECT * FROM GAME_GENRE;
SELECT * FROM GENRE;
SELECT * FROM GAME;
SELECT * FROM DEVELOPER;

DELETE FROM PRICE_TIMELINE WHERE URL != '' AND TIME > 0;
    
UPDATE PRICE_TIMELINE SET PRICE = 25 WHERE URL = '/6323-buy-key-steam-deep-rock-galactic' AND TIME = '2020-04-15 13:11:00';

INSERT INTO REVIEW (ID_USER, ID_GAME, RATING) VALUES
		(1,4,10) ON DUPLICATE KEY UPDATE
		RATING = 1;
        
        
        
        
        
SELECT
			US.NAME AS USER_NAME, RE.RATING, RE.REVIEW
		FROM
			REVIEW RE, USER US
		WHERE
			RE.ID_USER = US.ID AND
			ID_USER != 1 AND
			ID_GAME = 1;
  
        
        
        
        
        
        
        
        
        