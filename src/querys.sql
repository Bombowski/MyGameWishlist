SELECT * FROM STORE;
SELECT * FROM USER;
SELECT * FROM WISHLIST_GAME;
SELECT * FROM SECRET_VARIABLES;
SELECT * FROM STEAM_GAMES;
SELECT * FROM PRICE_TIMELINE;

DELETE FROM PRICE_TIMELINE WHERE URL != '' AND TIME > 0;