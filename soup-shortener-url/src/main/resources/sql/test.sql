DROP TABLE IF EXISTS Users;
CREATE TABLE Users(
    id int PRIMARY KEY auto_increment,
    login VARCHAR(255)
);

DROP TABLE IF EXISTS Links;
CREATE TABLE Links(
    id BIGINT PRIMARY KEY,
    longLink VARCHAR(255) NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);