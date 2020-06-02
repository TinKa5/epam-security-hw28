INSERT INTO users(username,password) VALUES ('admin','$2y$12$kEVYZqKmqXgbZFDGXjyvaeLBlL6clrBp80eriwaeIX04/UEkbs9iS');
INSERT INTO users(username, password) VALUES ('user1','$2y$12$f8J2UNGySijGJXF.t8mgqeiWd2qnrr3ewzz66b/3GWnCYV76bgS9G');

INSERT INTO authorities(username, authority) VALUES ('admin','ROLE_ADMIN');
INSERT INTO authorities(username, authority) VALUES ('user1','ROLE_USER');

SELECT * FROM users JOIN authorities a on users.username = a.username