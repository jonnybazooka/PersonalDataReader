CREATE DATABASE IF NOT EXISTS britenet_task001;
USE britenet_task001;
CREATE USER 'britenetADMIN'@'localhost' IDENTIFIED BY 'britenetPASSWORD';
GRANT ALL ON britenet_task001.* TO 'britenetADMIN'@'localhost';
CREATE TABLE CUSTOMERS (
ID int not null auto_increment,
NAME varchar(255) not null,
SURNAME varchar(255) not null,
Age int default null,
PRIMARY KEY (ID)
);
CREATE TABLE CONTACTS (
ID int not null auto_increment,
CUSTOMER_ID int not null,
TYPE int(1) not null,
CONTACT varchar(255) not null,
PRIMARY KEY (ID),
FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMERS(ID)
);