INSERT INTO roles(rolename) VALUES ('ROLE_USER'), ('ROLE_ADMIN'), ('ROLE_MODERATOR');

INSERT INTO users (username, email, password, apikey)
    VALUES ('Jacquelien', 'jdoudhoff@fictional.nl', 'salem', '514270'),
           ('Bets', 'betsreader@fictional.nl', 'baseballcard', '213274'),
           ('Karel', 'koudhoff@fictional.nl', 'icecremetruck', '316270'),

INSERT INTO authorities (username, authority)
VALUES  ('Jacquelien', 'ROLE_ADMIN'),
        ('Bets', 'ROLE_MODERATOR'),
        ('Karel', 'ROLE_USER');