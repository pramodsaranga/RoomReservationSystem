DROP DATABASE IF EXISTS RoomReservationSystem;
CREATE DATABASE IF NOT EXISTS RoomReservationSystem;
SHOW DATABASES ;
USE RoomReservationSystem;
#--------------------------------------------
DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer(
    customerId VARCHAR (20) ,
    customerName VARCHAR (30) NOT NULL DEFAULT  'Unknown',
    address VARCHAR (30),
    nic VARCHAR (20),
    contact VARCHAR (20),
    CONSTRAINT PRIMARY KEY (customerId)
);
SHOW TABLES ;
DESCRIBE Customer;
#------------------------------------------------
DROP TABLE IF EXISTS Room;
CREATE TABLE IF NOT EXISTS Room(
    roomId VARCHAR (20) ,
    roomType VARCHAR (30),
    floor VARCHAR (30),
    price DOUBLE,
    CONSTRAINT PRIMARY KEY (roomId)
);
SHOW TABLES ;
DESCRIBE Room;
#--------------------------------------------------
DROP TABLE IF EXISTS Bill;
CREATE TABLE IF NOT EXISTS Bill(
    billId VARCHAR (20) ,
    bookingCost DOUBLE ,
    mealPlanCost DOUBLE ,
    billDate DATE ,
    total DOUBLE ,
    CONSTRAINT PRIMARY KEY (billId)
);
SHOW TABLES ;
DESCRIBE Bill;
#-----------------------------------------------------
DROP TABLE IF EXISTS Employee;
CREATE TABLE IF NOT EXISTS Employee(
    empId VARCHAR (20),
    empName VARCHAR (30),
    address VARCHAR (30),
    NIC VARCHAR (20),
    birthDay DATE ,
    contact VARCHAR (20),
    post VARCHAR (20),
     CONSTRAINT PRIMARY KEY (empId)
    );
SHOW TABLES ;
DESCRIBE Employee;
#---------------------------------
DROP TABLE IF EXISTS Login;
CREATE TABLE IF NOT EXISTS Login(
    password VARCHAR (20),
    userName VARCHAR (20),
    CONSTRAINT PRIMARY KEY (password)
);
SHOW TABLES ;
DESCRIBE Login;
#------------------------------------------------------
DROP TABLE IF EXISTS BookingDetails;
CREATE TABLE IF NOT EXISTS BookingDetails(
    bookingId VARCHAR (20),
    roomId VARCHAR (20),
    customerId VARCHAR (20),
    checkingIn DATE ,
    checkingInTime VARCHAR (20),
    checkingOut DATE ,
    checkingOutTime VARCHAR (20),
    cost DOUBLE ,
    CONSTRAINT PRIMARY KEY (bookingId,roomId,customerId),
    CONSTRAINT FOREIGN KEY (roomId) REFERENCES Room(roomId) ON DELETE CASCADE ON UPDATE CASCADE,
     CONSTRAINT FOREIGN KEY (customerId) REFERENCES Customer(customerId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE BookingDetails;
#-------------------------------------------------------
DROP TABLE IF EXISTS MealPlan;
CREATE TABLE IF NOT EXISTS MealPlan(
    mealPlanId VARCHAR (20),
    bookingId VARCHAR (20),
    customerId VARCHAR (20),
    mealPlanType VARCHAR (30),
    mealPlanPrice DOUBLE,
    total DOUBLE ,
    CONSTRAINT PRIMARY KEY (mealPlanId,bookingId,customerId),
    CONSTRAINT FOREIGN KEY (bookingId) REFERENCES BookingDetails(bookingId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (customerId) REFERENCES Customer(customerId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE MealPlan;
#--------------------------------------------------------
DROP TABLE IF EXISTS Payment;
CREATE TABLE IF NOT EXISTS Payment(
    billId VARCHAR (20) ,
    bookingCost DOUBLE ,
    mealPlanCost DOUBLE ,
    billDate DATE ,
    total DOUBLE ,
    CONSTRAINT PRIMARY KEY (billId)
);
SHOW TABLES ;
DESCRIBE Payment;
#------------------------------------------------------------

SHOW TABLES ;