-- TABELLA ROLE
INSERT INTO `roles` (`role_id`, `name`) values  ('1', 'ADMINISTRATOR');
INSERT INTO `roles` (`role_id`, `name`) values  ('2', 'COURIER');
INSERT INTO `roles` (`role_id`, `name`) values  ('3', 'CUSTOMER');

-- TABELLA LOCKER
INSERT INTO `locker` (`locker_id`, `name`) values  ('1', 'Santiago');
INSERT INTO `locker` (`locker_id`, `name`) values  ('2', 'Ettore');
INSERT INTO `locker` (`locker_id`, `name`) values  ('3', 'Dimitri');

-- TABELLA LOCKER ADDRESS
INSERT INTO `locker_address` (`locker_id`, `address`, `city`, `postal_code`) values  ('1', 'Via Roma', 'Pavia', '27100');
INSERT INTO `locker_address` (`locker_id`, `address`, `city`, `postal_code`) values  ('2', 'Corso Strada Nuova', 'Pavia', '27100');
INSERT INTO `locker_address` (`locker_id`, `address`, `city`, `postal_code`) values  ('3', 'Via Mentana', 'Pavia', '27100');


-- TABELLA SLOT

-- Locker 1
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('1', 'SMALL',  '1');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('2', 'SMALL', '1');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('3', 'SMALL', '1');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('4', 'SMALL', '1');

INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('5', 'MEDIUM', '1');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('6', 'MEDIUM', '1');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('7', 'MEDIUM', '1');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('8', 'MEDIUM', '1');


INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('9', 'BIG',  '1');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('10', 'BIG',  '1');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('11', 'BIG',  '1');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('12', 'BIG',  '1');

-- Locker 2
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('13', 'SMALL',  '2');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('14', 'SMALL', '2');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('15', 'SMALL', '2');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('16', 'SMALL', '2');

INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('17', 'MEDIUM', '2');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('18', 'MEDIUM', '2');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('19', 'MEDIUM', '2');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('20', 'MEDIUM', '2');

INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('21', 'BIG',  '2');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('22', 'BIG',  '2');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('23', 'BIG',  '2');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('24', 'BIG',  '2');

-- Locker 3
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('25', 'SMALL',  '3');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('26', 'SMALL', '3');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('27', 'SMALL', '3');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('28', 'SMALL', '3');

INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('29', 'MEDIUM', '3');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('30', 'MEDIUM', '3');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('31', 'MEDIUM', '3');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('32', 'MEDIUM', '3');

INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('33', 'BIG',  '3');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('34', 'BIG',  '3');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('35', 'BIG',  '3');
INSERT INTO `slot` (`slot_id`, `size`, `locker_id`) values  ('36', 'BIG',  '3');



	
-- TABELLA USER:

-- email: admin@admin.it, psw: admin
INSERT INTO `user` (`user_id`, `enabled`, `password`, `email`, `role_id`, `registration_date`, `first_name`, `last_name` ) values  ('1', '1', '$2a$10$PmzVenf5WfCUmlcio2VObuc3rBsiFf6CGvXvKoRR5m/xQ5ajsAhD2', 'admin@admin.it', '1', '2021/06/28 10:05:38 ', 'admin','admin');

-- email: courier@courier.it, psw: admin
INSERT INTO `user` (`user_id`, `enabled`, `password`, `email`, `role_id`, `registration_date`, `first_name`, `last_name` ) values  ('2', '1', '$2a$10$PmzVenf5WfCUmlcio2VObuc3rBsiFf6CGvXvKoRR5m/xQ5ajsAhD2', 'courier@courier.it', '2', '2021/06/28 10:05:38 ', 'courier','courier');

-- email: customer@customer.it, psw: admin
INSERT INTO `user` (`user_id`, `enabled`, `password`, `email`, `role_id`, `registration_date`, `first_name`, `last_name` ) values  ('3', '1', '$2a$10$PmzVenf5WfCUmlcio2VObuc3rBsiFf6CGvXvKoRR5m/xQ5ajsAhD2', 'customer@customer.it', '3', '2021/06/28 10:05:38 ', 'Filippo','Rossi' );

-- customer2@customer.it, psw: admin
INSERT INTO `user` (`user_id`, `enabled`, `password`, `email`, `role_id`, `registration_date`, `first_name`, `last_name` ) values  ('4', '1', '$2a$10$PmzVenf5WfCUmlcio2VObuc3rBsiFf6CGvXvKoRR5m/xQ5ajsAhD2', 'customer2@customer.it', '3', '2021/06/28 10:05:38 ', 'Alberto','Russo');

-- courier2@courier.it psw: admin
INSERT INTO `user` (`user_id`, `enabled`, `password`, `email`, `role_id`, `registration_date`, `first_name`, `last_name` ) values  ('5', '1', '$2a$10$PmzVenf5WfCUmlcio2VObuc3rBsiFf6CGvXvKoRR5m/xQ5ajsAhD2', 'courier2@courier.it', '2', '2021/06/28 10:05:38 ', 'courier2','courier2');

-- courier3@courier.it
INSERT INTO `user` (`user_id`, `enabled`, `password`, `email`, `role_id`,`registration_date`, `first_name`, `last_name` ) values  ('6', '1', '$2a$10$PmzVenf5WfCUmlcio2VObuc3rBsiFf6CGvXvKoRR5m/xQ5ajsAhD2', 'courier3@courier.it', '2', '2021/06/28 10:05:38 ', 'courier3','courier3');
	
	
	

	