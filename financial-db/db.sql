create database demo;
USE demo;
CREATE USER if NOT EXISTS 'demouser'@'localhost' IDENTIFIED BY 'demo!pass';
CREATE USER if NOT EXISTS 'demouser'@'%' IDENTIFIED BY 'demo!pass';
GRANT ALL ON demo.* TO 'demouser'@'localhost';
GRANT ALL ON demo.* TO 'demouser'@'%';
CREATE TABLE IF NOT EXISTS Budget (Year INT UNSIGNED PRIMARY KEY, Amount BIGINT UNSIGNED NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8;
FLUSH PRIVILEGES;
