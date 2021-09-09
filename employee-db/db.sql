create database demo;
USE demo;
CREATE USER if NOT EXISTS 'demouser'@'localhost' IDENTIFIED BY 'demo!pass';
CREATE USER if NOT EXISTS 'demouser'@'%' IDENTIFIED BY 'demo!pass';
GRANT ALL ON demo.* TO 'demouser'@'localhost';
GRANT ALL ON demo.* TO 'demouser'@'%';
CREATE TABLE IF NOT EXISTS Employee (EmployeeID INT UNSIGNED AUTO_INCREMENT, EmployeeName VARCHAR(200) NOT NULL, EmployeeEmail VARCHAR(200) NOT NULL, Salary INT UNSIGNED NOT NULL, PRIMARY KEY (EmployeeID), UNIQUE (EmployeeName, EmployeeEmail)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS Reimbursement (ReimbursementID INT UNSIGNED AUTO_INCREMENT, ReimbursementTitle TEXT NOT NULL, SubmissionDate DATE, Amount INT NOT NULL, EmployeeID INT UNSIGNED NOT NULL, PRIMARY KEY (ReimbursementID)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
FLUSH PRIVILEGES;
INSERT INTO Employee (EmployeeName, EmployeeEmail, Salary) VALUES ('John', 'Che-Hung Lin <edward730109@gmail.com>', 1200000);
INSERT INTO Employee (EmployeeName, EmployeeEmail, Salary) VALUES ('Emma', 'Che-Hung Lin <edward730109@gmail.com>', 1200000);
INSERT INTO Reimbursement (ReimbursementTitle, SubmissionDate, Amount, EmployeeID) VALUES ('Notebook PC', '2021-06-10', 30000, 1);
INSERT INTO Reimbursement (ReimbursementTitle, SubmissionDate, Amount, EmployeeID) VALUES ('Transportation expenses', '2021-06-30', 2700, 1);
INSERT INTO Reimbursement (ReimbursementTitle, SubmissionDate, Amount, EmployeeID) VALUES ('Printer', '2021-06-20', 10000, 2);
INSERT INTO Reimbursement (ReimbursementTitle, SubmissionDate, Amount, EmployeeID) VALUES ('Monitor', '2021-06-20', 20000, 2);
INSERT INTO Reimbursement (ReimbursementTitle, SubmissionDate, Amount, EmployeeID) VALUES ('Book', '2021-08-10', 2100, 2);
INSERT INTO Reimbursement (ReimbursementTitle, SubmissionDate, Amount, EmployeeID) VALUES ('Printer Ink', '2021-08-20', 5210, 2);
INSERT INTO Reimbursement (ReimbursementTitle, SubmissionDate, Amount, EmployeeID) VALUES ('Transportation expenses', '2021-07-10', 3000, 2);
INSERT INTO Reimbursement (ReimbursementTitle, SubmissionDate, Amount, EmployeeID) VALUES ('Transportation expenses', '2021-07-30', 2500, 1);
INSERT INTO Reimbursement (ReimbursementTitle, SubmissionDate, Amount, EmployeeID) VALUES ('Transportation expenses', '2021-08-30', 2000, 1);
INSERT INTO Reimbursement (ReimbursementTitle, SubmissionDate, Amount, EmployeeID) VALUES ('Transportation expenses', '2021-08-29', 3500, 2);
