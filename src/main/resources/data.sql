/**
 * CREATE Script for init of DB
 */

-- Create 3 OFFLINE drivers

INSERT INTO driver (id, date_created, deleted, online_status, password, username) VALUES (1, now(), FALSE, 'OFFLINE',
                                                                                          'driver01pw', 'driver01');

INSERT INTO driver (id, date_created, deleted, online_status, password, username) VALUES (2, now(), FALSE, 'OFFLINE',
                                                                                          'driver02pw', 'driver02');

INSERT INTO driver (id, date_created, deleted, online_status, password, username) VALUES (3, now(), FALSE, 'OFFLINE',
                                                                                          'driver03pw', 'driver03');

-- Create 3 ONLINE drivers

INSERT INTO driver (id, date_created, deleted, online_status, password, username) VALUES (4, now(), FALSE, 'ONLINE',
                                                                                          'driver04pw', 'driver04');

INSERT INTO driver (id, date_created, deleted, online_status, password, username) VALUES (5, now(), FALSE, 'ONLINE',
                                                                                          'driver05pw', 'driver05');

INSERT INTO driver (id, date_created, deleted, online_status, password, username) VALUES (6, now(), FALSE, 'ONLINE',
                                                                                          'driver06pw', 'driver06');

-- Create 1 OFFLINE driver with coordinate(longitude=9.5&latitude=55.954)

INSERT INTO driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username)
VALUES
  (7,
   'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127',
   now(), now(), FALSE, 'OFFLINE',
   'driver07pw', 'driver07');

-- Create 1 ONLINE driver with coordinate(longitude=9.5&latitude=55.954)

INSERT INTO driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username)
VALUES
  (8,
   'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127',
   now(), now(), FALSE, 'ONLINE',
   'driver08pw', 'driver08');

-- Create 4 cars
INSERT INTO car (id, date_created, license_plate, seat_count, convertible, rating, engine_type, manufacturer, deleted, color)
VALUES (1, now(), 'License_ABC', 4, FALSE, 5.0, 'GAS', 'Honda', FALSE, 'blue');

INSERT INTO car (id, date_created, license_plate, seat_count, convertible, rating, engine_type, manufacturer, deleted, color)
VALUES (2, now(), 'License_DEF', 4, FALSE, 2.0, 'GAS', 'Toyota', FALSE, 'white');

INSERT INTO car (id, date_created, license_plate, seat_count, convertible, rating, engine_type, manufacturer, deleted, color)
VALUES (3, now(), 'License_GHI', 6, FALSE, 3.5, 'GAS', 'Suzuki', FALSE, 'black');

INSERT INTO car (id, date_created, license_plate, seat_count, convertible, rating, engine_type, manufacturer, deleted, color)
VALUES (4, now(), 'License_JKL', 2, FALSE, 4.0, 'ELECTRIC', 'Tesla', FALSE, 'grey');

---- assign cars to driver
INSERT INTO driver_car (id, date_created, date_updated, driver_id, car_id)
VALUES (1, now(), now(), 8,1);