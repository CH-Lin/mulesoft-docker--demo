create database demo;
USE demo;
CREATE USER if NOT EXISTS 'demouser'@'localhost' IDENTIFIED BY 'demo!pass';
CREATE USER if NOT EXISTS 'demouser'@'%' IDENTIFIED BY 'demo!pass';
GRANT ALL ON demo.* TO 'demouser'@'localhost';
GRANT ALL ON demo.* TO 'demouser'@'%';
CREATE TABLE IF NOT EXISTS Employee (EmployeeID INT UNSIGNED AUTO_INCREMENT, EmployeeName VARCHAR(200) NOT NULL, EmployeeEmail VARCHAR(200) NOT NULL, Salary INT UNSIGNED NOT NULL, PRIMARY KEY (EmployeeID), UNIQUE (EmployeeName, EmployeeEmail)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS Reimbursement (ReimbursementID INT UNSIGNED AUTO_INCREMENT, ReimbursementTitle TEXT NOT NULL, SubmissionDate DATE, Amount INT NOT NULL, EmployeeID INT UNSIGNED NOT NULL, PRIMARY KEY (ReimbursementID)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
FLUSH PRIVILEGES;
