-- Meal
INSERT INTO Meals (name, description, price)
VALUES ('Pizza', 'Pizza met veel smaak', 19.99);

-- ingredient with menu
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

-- Drinks
INSERT INTO Drinks (name, description, price, is_alcoholic, size, measurement)
VALUES ('Fanta', 'Fruitige sinaasappelsmaak', 2.50, FALSE, 330, 'ml');

INSERT INTO Drinks (name, description, price, is_alcoholic, size, measurement)
VALUES ('Heineken', 'Licht en verfrissend bier', 3.75, TRUE, 500, 'ml');

INSERT INTO Drinks (name, description, price, is_alcoholic, size, measurement)
VALUES ('Chardonnay', 'Droge witte wijn', 4.50, TRUE, 150, 'ml');

INSERT INTO Drinks (name, description, price, is_alcoholic, size, measurement)
VALUES ('Spa Blauw', 'Heerlijk zuiver bronwater', 2.00, FALSE, 500, 'ml');

INSERT INTO Drinks (name, description, price, is_alcoholic, size, measurement)
VALUES ('Whisky', 'Pittige en rokerige smaak', 7.00, TRUE, 50, 'ml');

