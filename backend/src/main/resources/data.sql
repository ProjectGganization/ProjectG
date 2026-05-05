-- AccountStatus
INSERT INTO AccountStatus (account_status) VALUES 
('active'), ('inactive');

-- PaymentMethod
INSERT INTO PaymentMethod (paymentmethod) VALUES
('card'), ('bank'), ('cash');

-- EventStatus
INSERT INTO EventStatus (event_status) VALUES
('upcoming'), ('cancelled'), ('finished');

-- PostalCodes
INSERT INTO PostalCodes (postalcode, city) VALUES
('00100', 'Helsinki'),
('33100', 'Tampere'),
('90100', 'Oulu');

-- TicketType
INSERT INTO TicketType (ticket_type) VALUES
('normal'), ('vip'), ('student'), ('elder');

-- Category
INSERT INTO Category (category) VALUES
('music'), ('sports'), ('conference'), ('theatre');

-- Users HUOM seller123 ja customer123
INSERT INTO Users (email, password_hash, account_created, account_status) VALUES
('sami@gg.com', '$2a$10$Cmcuw2S0MvMQGXqd236w/eOniHB90H8e8kobZWR74ikl/NwAeRvgG', '2026-05-05 21:06:45.888161', 'active'),
('daniel@gmail.com', '$2a$10$T5KzjXKnfvmJLeZrEzHZz.9dP5uLBAbyO3Bj7b7xv7b74jfg.rPFS', '2026-05-05 21:06:45.888161', 'active');

-- Sellers
INSERT INTO Sellers (name, email, phone, user_id) VALUES
('Sami Selleri', 'sami@gg.com', '+358401234567', 1);

-- Customers
INSERT INTO Customers (firstname, lastname, email, phone, user_id) VALUES
('Daniel', 'Duunari', 'daniel@gmail.com', '+358409876543', 2),
('Sanni', 'Shoppaaja', 'sanni@gmail.com', '+358409222333', NULL),
('Riikka', 'Remes', 'riikka@gmail.com', '+358409834875', NULL);

-- Venues
INSERT INTO Venues (name, address, postalcode) VALUES
('Crazy Stadion', 'Hullutie 1', '33100'),
('Ouluareena', 'Oulutie 1', '90100'),
('Messukeskus', 'Messutie 1', '00100');

-- Events
INSERT INTO Events (title, description, seller_id, start_time, end_time, event_status, venue_id, category, photo)
VALUES
('Rock Festival 2026', 'Annual rock music festival', 1,
 '2026-06-10 15:00', '2026-06-10 23:00', 'upcoming', 1, 'music', 'rockfest.jpg'),

('Tech Conference 2026', 'Latest innovations in tech', 1,
 '2026-09-01 09:00', '2026-09-01 18:00', 'upcoming', 1, 'conference', 'techconf.jpg'),

('Winter Sports Cup', 'National-level winter sports event', 1,
 '2026-02-15 10:00', '2026-02-15 17:00', 'finished', 2, 'sports', 'sportscup.jpg');


-- Tickets
INSERT INTO Tickets (ticket_type, event_id, unitprice, in_stock, order_limit) VALUES
-- Rock Festival 2026 (event_id = 1)
('normal',  1, 59.90, 500, 5),
('vip',     1, 129.90, 100, 2),
('student', 1, 39.90, 200, 2),
('elder',   1, 35.00, 150, 2),
-- Tech Conference 2026 (event_id = 2)
('normal',  2, 199.00, 300, 2),
('student', 2, 99.00, 150, 1),
('elder',   2, 119.00, 100, 1),

-- Winter Sports Cup (event_id = 3)
('normal',  3, 25.00, 1000, 10),
('student', 3, 15.00, 300, 5),
('elder',   3, 12.00, 200, 5);