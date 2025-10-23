CREATE DATABASE bankdb;
USE bankdb;

CREATE TABLE accounts (
  account_no INT PRIMARY KEY AUTO_INCREMENT,
  holder_name VARCHAR(100),
  balance DOUBLE DEFAULT 0
);

CREATE TABLE transactions (
  txn_id INT PRIMARY KEY AUTO_INCREMENT,
  account_no INT,
  type VARCHAR(20),
  amount DOUBLE,
  txn_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
