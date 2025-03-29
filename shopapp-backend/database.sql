use shopapp;
CREATE TABLE users(
    id INT PRIMARY KEY AUTO_INCREMENT,
    fullname VARCHAR(100) DEFAULT '',
    phone_number  VARCHAR(10) NOT NULL,
    address VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL DEFAULT '',
    create_at DATETIME,
    update_at DATETIME,
    is_activate TINYINT(1) DEFAULT 1,
    date_of_birth  DATE, 
    facebook_account_id INT DEFAULT 0,
    google_account_id INT DEFAULT 0
);
ALTER TABLE users ADD COLUMN role_id INT;
CREATE TABLE roles(
    id INT PRIMARY KEY,
    name VARCHAR(20) NOT NULL
    
);
ALTER TABLE users ADD FOREIGN KEY (role_id) REFERENCES roles (id);

CREATE TABLE tokens(
    id INT PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(255) UNIQUE NOT NULL,
    token_type VARCHAR(50 ) NOT NULL,
    expriation_date DATETIME,
    revoked TINYINT(1) NOT NULL,
    expried TINYINT(1) NOT NULL,
    user_id int,
    FOREIGN KEY (user_id)   REFERENCES users(id)
);
CREATE TABLE social_accounts(
    id INT PRIMARY KEY AUTO_INCREMENT,
    'provider' VARCHAR(20) NOT NULL  COMMENT ' ten nha cung cap',
    'provider_id' VARCHAR(50) NOT NULL ,
    'email' VARCHAR(150) NOT NULL ,
    'name' VARCHAR(100) NOT NULL,
    user_id int,
    FOREIGN KEY (user_id) REFERENCES users(id)        
);
CREATE TABLE categories(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL DEFAULT '' COMMENT 'Ten danh muc'
);

CREATE TABLE products(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(350) ,
    price FLOAT NOT NULL CHECK( price> 0),
    thumnail VARCHAR(300) DEFAULT '',
    description LONGTEXT DEFAULT '',    
    create_at DATETIME,
    update_at DATETIME,
    category_id int,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);
CREATE TABLE product_images(
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES products(id),
    constraint fk_product_images_product_id foreign key (product_id) references products(id) on delete cascade,
    image_url VARCHAR(300) 
);
CREATE TABLE orders(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id int,
    FOREIGN KEY (user_id) REFERENCES users(id),
    fullname VARCHAR(100),
    email VARCHAR(100),
    phone_number VARCHAR(200)  NOT NULL,
    address VARCHAR(200) NOT NULL,
    note VARCHAR(100) DEFAULT '',
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20),
    total_money FLOAT CHECK(total_money >= 0)
)
ALTER TABLE orders ADD COLUMN payment_method VARCHAR(100);
ALTER TABLE orders ADD COLUMN shipping_method VARCHAR(100); 
ALTER TABLE orders ADD COLUMN shipping_date DATE;
ALTER TABLE orders ADD COLUMN tracking_number VARCHAR(100); 
ALTER TABLE orders ADD COLUMN shipping_address VARCHAR(200); 
-- xoa 1 don hang => xoa mem => them truong active
ALTER TABLE orders ADD COLUMN is_active TINYINT(1);
--Trang thai don hang chi duoc phep la 1 trong cac gia tri sau
--pending, processing, completed, cancelled
ALTER TABLE orders 
MODIFY COLUMN status ENUM('pending', 'processing', 'completed', 'cancelled','delivered')
COMMENT 'Trang thai don hang';
CREATE TABLE order_details(
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES products(id),
    number_of_product INT CHECK(number_of_product > 0),
    price FLOAT CHECK(price >= 0),
    total_money FLOAT CHECK(total_money > 0),
    color VARCHAR(20) DEFAULT ''
);