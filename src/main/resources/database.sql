-- Create DB: toysdb
CREATE DATABASE toysdb
CHARACTER SET utf8
COLLATE utf8_general_ci;

-- Create some Table
CREATE TABLE toysdb.categorys (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE toysdb.baskets (
  id int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id)
);

CREATE TABLE toysdb.items (
  id int(11) NOT NULL AUTO_INCREMENT,
  article varchar(255) DEFAULT NULL,
  name varchar(50) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  price int(11) DEFAULT NULL,
  category_id int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_items_category_id FOREIGN KEY (category_id)
  REFERENCES toysdb.categorys (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);


CREATE TABLE toysdb.orders (
  id int(11) NOT NULL AUTO_INCREMENT,
  address varchar(255) DEFAULT NULL,
  pay varchar(255) DEFAULT NULL,
  cost int(11) DEFAULT NULL,
  date datetime DEFAULT NULL,
  basket_id int(11) DEFAULT NULL,
  status varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_orders_basket_id FOREIGN KEY (basket_id)
  REFERENCES toysdb.baskets (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE toysdb.orderitem (
  id int(11) NOT NULL AUTO_INCREMENT,
  item_id int(11) DEFAULT NULL,
  quantity int(11) DEFAULT NULL,
  order_id int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_orderitem_item_id FOREIGN KEY (item_id)
  REFERENCES toysdb.items (id) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT FK_orderitem_order_id FOREIGN KEY (order_id)
  REFERENCES toysdb.orders (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE toysdb.users (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(50) DEFAULT NULL,
  basket_id int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX basket_id (basket_id),
  CONSTRAINT FK_users_basket_id FOREIGN KEY (basket_id)
  REFERENCES toysdb.baskets (id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;


-- Insert Data for test

SET NAMES 'utf8';

INSERT INTO toysdb.categorys(id, name) VALUES
(1, 'Cars');
INSERT INTO toysdb.categorys(id, name) VALUES
(2, 'Airplanes');

INSERT INTO toysdb.items(id, article, name, description, price, category_id) VALUES
(1, 'cars1', 'MacWin', 'Molniya Macwin', 100, 1);
INSERT INTO toysdb.items(id, article, name, description, price, category_id) VALUES
(2, 'cars2', 'Metter', 'Friend Macwins', 50, 1);
INSERT INTO toysdb.items(id, article, name, description, price, category_id) VALUES
(3, 'air1', 'TU154', 'Russian airplane', 200, 2);
INSERT INTO toysdb.items(id, article, name, description, price, category_id) VALUES
(4, 'air2', 'Stels', 'Europe airplane', 500, 2);

