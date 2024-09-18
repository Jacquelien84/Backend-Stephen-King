-- Insert into authorities table
INSERT INTO authorities (username, authority)
VALUES  ('Jacquelien', 'ROLE_ADMIN'),
        ('Bets', 'ROLE_MODERATOR'),
        ('Karel', 'ROLE_USER');

-- Insert into users table
INSERT INTO users (username, email, password, apikey)
VALUES ('Jacquelien', 'jdoudhoff@fictional.nl', '$2a$10$7uUFnAPkEHs3ypcdK6Xvlu8MXBIksCQaA9EJ8pJPGal7dwix6Oo32', '514270'),
       ('Bets', 'betsreader@fictional.nl', '$2a$10$xUI42XAG/Uxdp79XX2SQluWZS8x1wewXEVB.UkHptXFYomWUiL/cS', '213274'),
       ('Karel', 'koudhoff@fictional.nl', '$2a$10$0/rnlDxxODqMojv9jOJtJOPO67rLPUqslv.0kEolyUyHOftx1rFlm', '316270');

-- Insert into books table
INSERT INTO books (id, title, author, original_title, released, movie_adaptation, description)
VALUES (1, 'Carrie', 'Stephen King', 'Carrie', 1974, 'Carrie 1976 - 2013',
        'Het verhaal gaat over Carrie, een zeventienjarig meisje met telekinetische gaven. Ze is een buitenbeentje op school en wordt vaak gepest en vernederd, ook door haar dominante moeder. Maar ze ontdekt haar krachten en krijgt deze steeds meer onder controle. Op het schoolbal gaan haar klasgenoten te ver en neemt Carrie wraak...'),
       (2, 'Bezeten Stad', 'Stephen King', 'Salems Lot', 1975, 'Salems Lot 1979 - 2004',
        'Ben Mears was pas zeven jaar toen hij zijn geboortestadje ''Salem''s Lot'' verliet. Nu, vijfentwintig jaar later, is hij teruggekomen om de waarheid bloot te leggen. De waarheid over de geheimzinnige verdwijningen, de spookverhalen en de geestesverschijningen...'),
       (3, 'De shining', 'Stephen King', 'The shining', 1977, 'The Shining 1980 - 1997',
        'Het verhaal speelt zich af in het verlaten Overlook Hotel ergens in de Rocky Mountains, waar schrijver Jack Torrance, zijn vrouw Wendy en hun zoontje Danny huismeesters zijn...'),
       (4, 'Satanskinderen', 'Stephen King', 'Night shift', 1978, 'Bijna alle verhalen zijn verfilmd',
        'Verhalenbundel met 19 verhalen'),
       (5, 'De Beproeving', 'Stephen King', 'The Stand', 1978, 'The Stand 1994 - 2023',
        'Het stof vormde een nauwelijks doorzichtige waas dat het stadje Arnette het aanzien van een spookstad gaf... De Grote Plaag is voorbij...'),
       (6, 'Dodelijk dilemma', 'Stephen King', 'The dead zone', 1979, 'The dead zone 2002',
        'John Smith, een jonge leraar, raakt betrokken bij een fataal auto-ongeluk en ligt 4,5 jaar in coma...'),
       (7, 'Ogen van vuur', 'Stephen King', 'Firestarter', 1980, 'Firestarter 1984 - 2022',
        'Andy McGee en Vicky Tomlinson stellen zich vrijwillig beschikbaar voor een experiment met een volslagen onbekende drug, bedacht en ontwikkeld door een bizar regeringsbureau, in kleine kring bekend als ''De Winkel''...');
