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

		SELECT
			IFNULL(AVG(RE.RATING), -1) AS AVERAGE_RATING, GA.ID AS ID_GAME, 
			GA.NAME AS NAME, GROUP_CONCAT(GE.NAME SEPARATOR ', ') AS GENRES
		FROM
			GAME GA LEFT JOIN REVIEW RE ON GA.ID = RE.ID_GAME,
		    GENRE GE, GAME_GENRE GG
		WHERE
			GG.ID_GENRE = GE.ID AND
		    GG.ID_GAME = GA.ID
		GROUP BY
			GA.NAME, USER_RATING, GA.ID
		ORDER BY
			GA.ID
  
        
        
        
        
        
        
        
        
        