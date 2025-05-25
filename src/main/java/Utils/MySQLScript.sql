create database restaurant_db;
USE restaurant_db;
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('admin', 'user') NOT NULL,
    full_name VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE employees (
    employee_id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
	sex VARCHAR(20),
    position VARCHAR(50), -- e.g., Waiter, Chef, Manager
    phone VARCHAR(20),
    email VARCHAR(100),
    hire_date DATE,
    salary DECIMAL(10, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    category VARCHAR(50),
    image_url VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    address TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE promotions (
    promo_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    description TEXT,
    discount_percent DECIMAL(5,2),
    start_date DATE,
    end_date DATE,
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE promotion_products (
    promo_pro_id INT PRIMARY KEY AUTO_INCREMENT,
    promotion_id INT,
    product_id INT,
    FOREIGN KEY (promotion_id) REFERENCES promotions(promo_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

/*CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    customer_id INT,
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2),
    status ENUM('pending', 'paid', 'cancelled') DEFAULT 'pending',
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

CREATE TABLE order_details (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,
    price DECIMAL(10,2),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE TABLE inventory (
    inventory_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    quantity INT,
    last_updated DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
*/

CREATE TABLE chat_logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_prompt TEXT,
    bot_response TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- --------------------------------------------------------------------
-- Bảng daily_menu: lưu danh sách món được chọn làm "Thực đơn hôm nay"
-- GUI: khi click [Thực đơn hôm nay], app sẽ:
--   SELECT p.* 
--   FROM daily_menu dm
--   JOIN products p ON dm.product_id = p.product_id
--   WHERE dm.menu_date = CURDATE();
-- --------------------------------------------------------------------
CREATE TABLE daily_menu (
    menu_date   DATE       NOT NULL,
    product_id  INT        NOT NULL,
    PRIMARY KEY (menu_date, product_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- --------------------------------------------------------------------
-- Bảng carts: tạo giỏ hàng tạm cho mỗi user
-- GUI: khi click [Đặt món tự chọn] hoặc nhập lệnh chat đặt món, 
--   nếu chưa có cart status='open' thì tạo mới; else tiếp tục dùng cart hiện tại.
-- --------------------------------------------------------------------
CREATE TABLE carts (
    cart_id     INT           AUTO_INCREMENT PRIMARY KEY,
    user_id     INT           NOT NULL,
    status      ENUM('open','ordered','cancelled') 
                                NOT NULL DEFAULT 'open',
    created_at  TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- --------------------------------------------------------------------
-- Bảng cart_items: chi tiết từng món trong giỏ
-- GUI: khi click [Giỏ hàng của tôi], app sẽ:
--   SELECT ci.*, p.name, p.price 
--   FROM cart_items ci
--   JOIN carts c     ON ci.cart_id    = c.cart_id
--   JOIN products p  ON ci.product_id = p.product_id
--   WHERE c.user_id = ? AND c.status = 'open';
-- --------------------------------------------------------------------
CREATE TABLE cart_items (
    cart_item_id INT           AUTO_INCREMENT PRIMARY KEY,
    cart_id      INT           NOT NULL,
    product_id   INT           NOT NULL,
    quantity     INT           NOT NULL DEFAULT 1,
    price        DECIMAL(10,2) NOT NULL,  -- lưu giá tại thời điểm thêm
    added_at     TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cart_id)    REFERENCES carts(cart_id)    ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- --------------------------------------------------------------------
-- Bảng orders + order_details: lưu lịch sử đặt hàng chính thức
-- GUI: khi click [Lịch sử đặt hàng], app sẽ:
--   SELECT o.*, od.product_id, od.quantity, od.price 
--   FROM orders o
--   JOIN order_details od ON o.order_id = od.order_id
--   WHERE o.user_id = ?;
-- --------------------------------------------------------------------
CREATE TABLE orders (
    order_id      INT            AUTO_INCREMENT PRIMARY KEY,
    user_id       INT,
    customer_id   INT,
    order_date    DATETIME       DEFAULT CURRENT_TIMESTAMP,
    total_amount  DECIMAL(10,2),
    status        ENUM('pending','paid','cancelled') 
                     NOT NULL DEFAULT 'pending',
    FOREIGN KEY (user_id)     REFERENCES users(user_id),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

CREATE TABLE order_details (
    order_item_id INT            AUTO_INCREMENT PRIMARY KEY,
    order_id      INT            NOT NULL,
    product_id    INT            NOT NULL,
    quantity      INT            NOT NULL,
    price         DECIMAL(10,2)  NOT NULL,
    FOREIGN KEY (order_id)      REFERENCES orders(order_id)      ON DELETE CASCADE,
    FOREIGN KEY (product_id)    REFERENCES products(product_id)
);

INSERT INTO products (name, description, price, category, image_url, is_active) VALUES
('Cơm chay thập cẩm',        'Cơm trắng ăn kèm rau củ, đậu phụ, nấm và chả chay',                32000, 'Món chính', '', 1),
('Bún riêu chay',             'Bún nước dùng đậm đà, riêu đậu, cà chua, rau sống',               30000, 'Món nước', '', 1),
('Phở chay',                  'Phở nước thơm mùi quế, hồi, kèm nấm, tàu hũ, rau thơm',           35000, 'Món nước', '', 1),
('Hủ tiếu chay',              'Hủ tiếu với nấm, cải, tàu hũ ky và nước lèo chay ngọt thanh',     30000, 'Món nước', '', 1),
('Bánh mì chay',              'Bánh mì Việt Nam nhân chả chay, pate chay, rau dưa',               18000, 'Ăn sáng', '', 1),
('Gỏi cuốn chay',             'Cuốn rau củ, bún, đậu phụ, chấm nước tương đậu phộng',             22000, 'Khai vị', '', 1),
('Nấm xào sả ớt',             'Nấm rơm, nấm đùi gà xào sả ớt cay thơm',                           32000, 'Món xào', '', 1),
('Đậu hủ chiên sả',           'Đậu phụ chiên vàng, xóc sả ớt',                                    28000, 'Món xào', '', 1),
('Chả giò chay',              'Nem cuốn nhân khoai môn, nấm, đậu xanh, chiên giòn',              30000, 'Khai vị', '', 1),
('Bò kho chay',               'Sườn non chay, cà rốt, củ cải kho với gia vị kiểu bò kho',         37000, 'Món chính', '', 1),
('Lẩu nấm chay',              'Lẩu nước nấm thanh ngọt, nhiều loại nấm và rau củ',                85000, 'Lẩu', '', 1),
('Canh chua chay',            'Canh chua miền Nam nấu từ thơm, cà chua, đậu bắp, bạc hà',         30000, 'Canh', '', 1),
('Đậu hủ sốt cà',             'Đậu phụ sốt cà chua, thêm hành ngò',                               26000, 'Món xào', '', 1),
('Bún Huế chay',              'Bún Huế với nước lèo chay, nấm, tàu hũ, chả lụa chay',             32000, 'Món nước', '', 1),
('Mì Quảng chay',             'Mì Quảng nước ít, rau, đậu phụ, nấm',                              32000, 'Món nước', '', 1),
('Cơm rang thập cẩm chay',    'Cơm chiên cùng ngũ sắc rau củ và đậu phụ',                         30000, 'Món chính', '', 1),
('Bánh xèo chay',             'Bánh xèo nhân nấm, đậu xanh, giá, ăn kèm rau sống',                32000, 'Ăn vặt', '', 1),
('Cháo nấm chay',             'Cháo gạo trắng, nấm rơm, nấm đùi gà',                              25000, 'Ăn sáng', '', 1),
('Nấm kho tiêu',              'Nấm rơm kho tiêu đậm đà, ăn kèm cơm',                             32000, 'Món kho', '', 1),
('Đậu hủ non sốt nấm',        'Đậu hủ non, nấm hương sốt dầu hào chay',                           33000, 'Món xào', '', 1),
('Miến xào rau củ',           'Miến dong xào cà rốt, đậu cô ve, nấm',                             30000, 'Món xào', '', 1),
('Đậu que xào tỏi',           'Đậu cô ve xào tỏi, thanh mát',                                     28000, 'Món xào', '', 1),
('Rau muống xào tỏi',         'Rau muống xào thơm vị tỏi',                                        27000, 'Món xào', '', 1),
('Khoai tây chiên',           'Khoai tây cắt lát chiên vàng giòn',                                25000, 'Ăn vặt', '', 1),
('Mướp đắng xào trứng chay',  'Khổ qua xào trứng gà chay',                                        29000, 'Món xào', '', 1),
('Măng xào nấm',              'Măng tươi xào nấm đùi gà, nấm rơm',                                31000, 'Món xào', '', 1),
('Súp bí đỏ chay',            'Súp bí đỏ xay nhuyễn, kem thực vật',                               25000, 'Súp', '', 1),
('Canh bí đỏ đậu xanh',       'Canh bí đỏ nấu đậu xanh',                                          25000, 'Canh', '', 1),
('Canh rong biển đậu hủ',     'Canh rong biển nấu đậu phụ',                                       27000, 'Canh', '', 1),
('Chè hạt sen long nhãn',     'Chè ngọt thanh từ hạt sen và long nhãn',                           25000, 'Tráng miệng', '', 1),
('Bánh chuối hấp nước cốt',   'Bánh chuối hấp, chan nước cốt dừa béo',                            23000, 'Tráng miệng', '', 1),
('Rau củ luộc thập cẩm',      'Đĩa rau củ luộc: su su, cà rốt, súp lơ, đậu que',                  28000, 'Ăn vặt', '', 1),
('Salad rong biển',           'Rong biển tươi trộn xốt mè rang',                                  34000, 'Khai vị', '', 1),
('Đậu hủ kho',                'Đậu phụ kho nước tương, ăn với cơm trắng',                         30000, 'Món kho', '', 1),
('Sườn non chay rim mặn',     'Sườn non chay kho nước dừa, gia vị',                               35000, 'Món kho', '', 1),
('Cà tím kho tộ',             'Cà tím kho tộ đậm đà, thêm hành lá',                               30000, 'Món kho', '', 1),
('Gỏi ngó sen chay',          'Gỏi ngó sen, đậu phộng, rau thơm',                                 32000, 'Khai vị', '', 1),
('Bún Thái chay',             'Bún nước vị Thái, nấm, rau củ, đậu phụ',                           32000, 'Món nước', '', 1),
('Chè đậu xanh nha đam',      'Chè ngọt thanh từ đậu xanh và nha đam',                            23000, 'Tráng miệng', '', 1),
('Súp ngô nấm',               'Súp ngô ngọt, nấm hương, ngò rí',                                  25000, 'Súp', '', 1),
('Súp lơ xào nấm',            'Súp lơ trắng, xanh xào nấm hương',                                 28000, 'Món xào', '', 1),
('Nấm xào cải ngọt',          'Nấm đùi gà, cải ngọt xào dầu hào chay',                            31000, 'Món xào', '', 1),
('Bắp cải xào cà rốt',        'Bắp cải, cà rốt xào dầu thực vật',                                 27000, 'Món xào', '', 1),
('Canh cải thìa nấu nấm',     'Canh cải thìa và nấm rơm thanh mát',                               27000, 'Canh', '', 1),
('Chả lụa chay',              'Chả lụa chay cắt khoanh, dùng với cơm hoặc bánh mì',               32000, 'Ăn vặt', '', 1),
('Mì xào rau củ',             'Mì xào hỗn hợp rau củ, đậu phụ',                                   32000, 'Món xào', '', 1),
('Đậu hũ hấp gừng',           'Đậu hũ non hấp với gừng và hành lá',                               27000, 'Ăn vặt', '', 1),
('Lẩu thái chay',             'Lẩu vị chua cay, nấm, rau củ, đậu hũ',                             90000, 'Lẩu', '', 1),
('Cơm gạo lứt chay',          'Cơm gạo lứt, rau củ hấp, tàu hũ ky',                               35000, 'Món chính', '', 1),
('Nước ép rau củ tổng hợp',   'Nước ép từ các loại rau củ tươi mát',                              25000, 'Đồ uống', '', 1);
