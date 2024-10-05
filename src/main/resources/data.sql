-- Locations
-- Insert 5 Hotel Rooms
INSERT INTO Locations (location_number, is_occupied, location_type)
VALUES
    (101, false, 'HOTEL_ROOM'),
    (102, false, 'HOTEL_ROOM'),
    (103, false, 'HOTEL_ROOM'),
    (104, false, 'HOTEL_ROOM'),
    (105, false, 'HOTEL_ROOM');

-- Insert 5 Tables
INSERT INTO Locations (location_number, is_occupied, location_type)
VALUES
    (201, false, 'TABLE'),
    (202, false, 'TABLE'),
    (203, false, 'TABLE'),
    (204, false, 'TABLE'),
    (205, false, 'TABLE');

-- Insert 5 Sun Loungers
INSERT INTO Locations (location_number, is_occupied, location_type)
VALUES
    (301, false, 'SUN_LOUNGER'),
    (302, false, 'SUN_LOUNGER'),
    (303, false, 'SUN_LOUNGER'),
    (304, false, 'SUN_LOUNGER'),
    (305, false, 'SUN_LOUNGER');



-- Authorities
    INSERT INTO Authorities (username, authority) VALUES
    ('admin', 'ROLE_ADMIN');

-- Users
INSERT INTO Users (username, password) VALUES
    ('admin', 'password');



-- Orders
INSERT INTO Orders (order_date, status, destination_id)
VALUES
    ('2024-10-05 12:30:00', 'PREPARING_ORDER', 1);


-- Drinks
INSERT INTO Drinks (name, description, price, is_alcoholic, size, measurement, order_id)
VALUES ('Fanta', 'Fruitige sinaasappelsmaak', 2.50, FALSE, 330, 'ml', 1);

INSERT INTO Drinks (name, description, price, is_alcoholic, size, measurement)
VALUES ('Heineken', 'Licht en verfrissend bier', 3.75, TRUE, 500, 'ml');

INSERT INTO Drinks (name, description, price, is_alcoholic, size, measurement)
VALUES ('Chardonnay', 'Droge witte wijn', 4.50, TRUE, 150, 'ml');

INSERT INTO Drinks (name, description, price, is_alcoholic, size, measurement)
VALUES ('Spa Blauw', 'Heerlijk zuiver bronwater', 2.00, FALSE, 500, 'ml');

INSERT INTO Drinks (name, description, price, is_alcoholic, size, measurement)
VALUES ('Whisky', 'Pittige en rokerige smaak', 7.00, TRUE, 50, 'ml');

-- Meal
INSERT INTO Meals (name, description, price, order_id)
VALUES ('Pizza', 'Pizza met veel smaak', 19.99, 1);

-- Ingredient with menu
INSERT INTO Ingredients (meal_id, name)
VALUES (1, 'Pizzadeeg');
INSERT INTO Ingredients (meal_id, name)
VALUES (1, 'Tomatensaus');
INSERT INTO Ingredients (meal_id, name)
VALUES (1, 'Mozzarella');
INSERT INTO Ingredients (meal_id, name)
VALUES (1, 'Olijfolie');
INSERT INTO Ingredients (meal_id, name)
VALUES (1, 'Pepperoni');
