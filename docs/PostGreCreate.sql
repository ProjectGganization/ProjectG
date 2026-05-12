-- 1) DICTIONARIES
CREATE TABLE AccountStatus (
	account_status VARCHAR(50) NOT NULL
		CHECK (account_status IN ('active', 'inactive'))
			PRIMARY KEY
);

CREATE TABLE PaymentMethod (
	paymentmethod VARCHAR(50)
		CHECK (paymentmethod IN ('card', 'bank', 'cash'))
			PRIMARY KEY
);

CREATE TABLE EventStatus (
	event_status VARCHAR(50)
		CHECK (event_status IN ('upcoming', 'cancelled', 'finished'))
			PRIMARY KEY
);

CREATE TABLE PostalCodes(
	postalcode VARCHAR(5) PRIMARY KEY,
	city VARCHAR(250) NOT NULL
);

CREATE TABLE TicketType (
	ticket_type VARCHAR(50) PRIMARY KEY
);

CREATE TABLE Category (
	category VARCHAR(50) PRIMARY KEY
);
	
	
-- 2) TABLES
CREATE TABLE Users (
	user_id SERIAL PRIMARY KEY
	,email VARCHAR(225) NOT NULL UNIQUE
	,password_hash VARCHAR(225) NOT NULL
	,account_created TIMESTAMP NOT NULL
		DEFAULT NOW()
	,account_status varchar(50) NOT NULL REFERENCES AccountStatus(account_status)
);

CREATE TABLE Customers (
	customer_id SERIAL PRIMARY KEY
	,firstname varchar (100) NOT NULL
	,lastname varchar(100) NOT NULL
	,email varchar(250) NOT NULL
	,phone varchar(25) NOT NULL
	,user_id INT REFERENCES Users(user_id)
);

CREATE TABLE Sellers (
	seller_id SERIAL PRIMARY KEY
	,name VARCHAR (100) NOT NULL
	,email VARCHAR(250) NOT NULL
	,phone VARCHAR(25)
	,user_id INT NOT NULL REFERENCES Users(user_id)
);

CREATE TABLE Orders (
	order_id SERIAL PRIMARY KEY
	,customer_id INT NOT NULL REFERENCES Customers(customer_id)
	,date TIMESTAMP NOT NULL
	,is_refunded BOOLEAN NOT NULL
		DEFAULT FALSE
	,is_paid BOOLEAN NOT NULL
	,paymentmethod VARCHAR(50) REFERENCES PaymentMethod(paymentmethod)
);

CREATE TABLE Venues (
	venue_id SERIAL PRIMARY KEY
	,name VARCHAR(250) NOT NULL
	,address VARCHAR(250) NOT NULL
	,postalcode VARCHAR (5) NOT NULL REFERENCES PostalCodes(postalcode)
);

CREATE TABLE Events(
	event_id SERIAL PRIMARY KEY
	,title VARCHAR(250) NOT NULL
	,description VARCHAR(250)
	,seller_id INT NOT NULL REFERENCES Sellers(seller_id)
	,start_time TIMESTAMP NOT NULL
	,end_time TIMESTAMP NOT NULL
	,event_status VARCHAR(50) NOT NULL REFERENCES EventStatus(event_status)
	,venue_id INT NOT NULL REFERENCES Venues(venue_id)
	,category VARCHAR(50) NOT NULL REFERENCES Category(category)
	,photo VARCHAR (260)
);

CREATE TABLE Tickets (
	ticket_id SERIAL PRIMARY KEY
	,ticket_type VARCHAR(50) NOT NULL REFERENCES TicketType(ticket_type)
	,event_id INT NOT NULL REFERENCES Events(event_id)
    ,unitprice DECIMAL(10,2) NOT NULL 
		CHECK (unitprice >= 0)
    ,in_stock INT NOT NULL 
		CHECK (in_stock >= 0)
	,order_limit INT 
		CHECK (order_limit > 0)
);

CREATE TABLE IssuedTickets(
	issuedticket_id SERIAL PRIMARY KEY
	,qr_code VARCHAR(250) NOT NULL UNIQUE
	,order_id INT NOT NULL REFERENCES Orders(order_id)
	,ticket_id INT NOT NULL REFERENCES Tickets(ticket_id)
	,used_at BOOLEAN NOT NULL
		DEFAULT FALSE
);

CREATE TABLE OrderDetails (
    order_id INT NOT NULL REFERENCES Orders(order_id)
    ,ticket_id INT NOT NULL REFERENCES Tickets(ticket_id)
    ,unitprice DECIMAL(10,2) NOT NULL
    ,quantity INT NOT NULL
    ,PRIMARY KEY (order_id, ticket_id)
	,seller_id INT NOT NULL REFERENCES Sellers(seller_id)
);