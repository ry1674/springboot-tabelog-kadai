INSERT IGNORE INTO categories (id, name) VALUES (1, '焼肉');
INSERT IGNORE INTO categories (id, name) VALUES (2, '寿司');
INSERT IGNORE INTO categories (id, name) VALUES (3, 'ラーメン');
INSERT IGNORE INTO categories (id, name) VALUES (4, '定食');
INSERT IGNORE INTO categories (id, name) VALUES (5, 'カレー');
INSERT IGNORE INTO categories (id, name) VALUES (6, '喫茶店');
INSERT IGNORE INTO categories (id, name) VALUES (7, '中華料理');
INSERT IGNORE INTO categories (id, name) VALUES (8, 'イタリア料理');
INSERT IGNORE INTO categories (id, name) VALUES (9, '担々麺');
INSERT IGNORE INTO categories (id, name) VALUES (10, 'フランス料理');
INSERT IGNORE INTO categories (id, name) VALUES (11, 'スペイン料理');
INSERT IGNORE INTO categories (id, name) VALUES (12, '韓国料理');
INSERT IGNORE INTO categories (id, name) VALUES (13, 'タイ料理');
INSERT IGNORE INTO categories (id, name) VALUES (14, '海鮮料理');
INSERT IGNORE INTO categories (id, name) VALUES (15, 'ステーキ');
INSERT IGNORE INTO categories (id, name) VALUES (16, 'ハンバーグ');
INSERT IGNORE INTO categories (id, name) VALUES (17, 'ハンバーガー');
INSERT IGNORE INTO categories (id, name) VALUES (18, 'そば');
INSERT IGNORE INTO categories (id, name) VALUES (19, 'うどん');
INSERT IGNORE INTO categories (id, name) VALUES (20, 'お好み焼き');
INSERT IGNORE INTO categories (id, name) VALUES (21, 'たこ焼き');
INSERT IGNORE INTO categories (id, name) VALUES (22, '鍋料理');
INSERT IGNORE INTO categories (id, name) VALUES (23, 'パン');
INSERT IGNORE INTO categories (id, name) VALUES (24, 'スイーツ');
INSERT IGNORE INTO categories (id, name) VALUES (25, '和食');
INSERT IGNORE INTO categories (id, name) VALUES (26, 'おでん');
INSERT IGNORE INTO categories (id, name) VALUES (27, '焼き鳥');
INSERT IGNORE INTO categories (id, name) VALUES (28, 'すき焼き');
INSERT IGNORE INTO categories (id, name) VALUES (29, 'しゃぶしゃぶ');
INSERT IGNORE INTO categories (id, name) VALUES (30, '天ぷら');
INSERT IGNORE INTO categories (id, name) VALUES (31, '揚げ物');
INSERT IGNORE INTO categories (id, name) VALUES (32, '天丼');
INSERT IGNORE INTO categories (id, name) VALUES (33, '鉄板焼き');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, min_price, max_price, open_time, close_time, postal_code, address, phone_number, closed_day) VALUES (1, 1, '店舗1', 'restaurant01.jpg', '説明1', 1000, 5000, '11:00', '23:00', '111-1111', '愛知県名古屋市中区栄1-1-1', '111-1111-1111', '月曜日');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, min_price, max_price, open_time, close_time, postal_code, address, phone_number, closed_day) VALUES (2, 2, '店舗2', 'restaurant02.jpg', '説明2', 1000, 5000, '11:00', '23:00', '222-2222', '愛知県名古屋市中区栄1-1-2', '111-1111-1111', '月曜日,火曜日');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, min_price, max_price, open_time, close_time, postal_code, address, phone_number, closed_day) VALUES (3, 3, '店舗3', 'restaurant03.jpg', '説明3', 1000, 5000, '11:00', '23:00', '333-3333', '愛知県名古屋市中区栄1-1-3', '111-1111-1111', '土曜日,日曜日');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, min_price, max_price, open_time, close_time, postal_code, address, phone_number, closed_day) VALUES (4, 4, '店舗4', 'restaurant04.jpg', '説明4', 1000, 5000, '11:00', '23:00', '444-4444', '愛知県名古屋市中区栄1-1-4', '111-1111-1111', '月曜日');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, min_price, max_price, open_time, close_time, postal_code, address, phone_number, closed_day) VALUES (5, 5, '店舗5', 'restaurant05.jpg', '説明5', 1000, 5000, '11:00', '23:00', '555-5555', '愛知県名古屋市中区栄1-1-5', '111-1111-1111', '月曜日');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, min_price, max_price, open_time, close_time, postal_code, address, phone_number, closed_day) VALUES (6, 6, '店舗6', 'restaurant06.jpg', '説明6', 1000, 5000, '11:00', '23:00', '666-6666', '愛知県名古屋市中区栄1-1-6', '111-1111-1111', '月曜日');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, min_price, max_price, open_time, close_time, postal_code, address, phone_number, closed_day) VALUES (7, 7, '店舗7', 'restaurant07.jpg', '説明7', 1000, 5000, '11:00', '23:00', '777-7777', '愛知県名古屋市中区栄1-1-7', '111-1111-1111', '月曜日');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, min_price, max_price, open_time, close_time, postal_code, address, phone_number, closed_day) VALUES (8, 8, '店舗8', 'restaurant08.jpg', '説明8', 1000, 5000, '11:00', '23:00', '888-8888', '愛知県名古屋市中区栄1-1-8', '111-1111-1111', '月曜日');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, min_price, max_price, open_time, close_time, postal_code, address, phone_number, closed_day) VALUES (9, 9, '店舗9', 'restaurant09.jpg', '説明9', 1000, 5000, '11:00', '23:00', '999-9999', '愛知県名古屋市中区栄1-1-9', '111-1111-1111', '月曜日');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, min_price, max_price, open_time, close_time, postal_code, address, phone_number, closed_day) VALUES (10, 10, '店舗10', 'restaurant10.jpg', '説明10', 1000, 5000, '11:00', '23:00', '000-0000', '愛知県名古屋市中区栄1-1-10', '111-1111-1111', '月曜日');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, min_price, max_price, open_time, close_time, postal_code, address, phone_number, closed_day) VALUES (11, 11, '店舗11', 'restaurant11.jpg', '説明11', 1000, 5000, '11:00', '23:00', '000-1111', '愛知県名古屋市中区栄1-1-11', '111-1111-1111', '月曜日');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, min_price, max_price, open_time, close_time, postal_code, address, phone_number, closed_day) VALUES (12, 12, '店舗12', 'restaurant12.jpg', '説明12', 1000, 5000, '11:00', '23:00', '000-2222', '愛知県名古屋市中区栄1-1-12', '111-1111-1111', '月曜日');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, min_price, max_price, open_time, close_time, postal_code, address, phone_number, closed_day) VALUES (13, 13, '店舗13', 'restaurant13.jpg', '説明13', 1000, 5000, '11:00', '23:00', '000-3333', '愛知県名古屋市中区栄1-1-13', '111-1111-1111', '月曜日');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, min_price, max_price, open_time, close_time, postal_code, address, phone_number, closed_day) VALUES (14, 14, '店舗14', 'restaurant14.jpg', '説明14', 1000, 5000, '11:00', '23:00', '000-4444', '愛知県名古屋市中区栄1-1-14', '111-1111-1111', '月曜日');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, min_price, max_price, open_time, close_time, postal_code, address, phone_number, closed_day) VALUES (15, 15, '店舗15', 'restaurant15.jpg', '説明15', 1000, 5000, '11:00', '23:00', '000-5555', '愛知県名古屋市中区栄1-1-15', '111-1111-1111', '月曜日');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, min_price, max_price, open_time, close_time, postal_code, address, phone_number, closed_day) VALUES (16, 16, '店舗16', 'restaurant16.jpg', '説明16', 1000, 5000, '11:00', '23:00', '000-6666', '愛知県名古屋市中区栄1-1-16', '111-1111-1111', '月曜日');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, min_price, max_price, open_time, close_time, postal_code, address, phone_number, closed_day) VALUES (17, 17, '店舗17', 'restaurant17.jpg', '説明17', 1000, 5000, '11:00', '23:00', '000-7777', '愛知県名古屋市中区栄1-1-17', '111-1111-1111', '月曜日');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, min_price, max_price, open_time, close_time, postal_code, address, phone_number, closed_day) VALUES (18, 18, '店舗18', 'restaurant18.jpg', '説明18', 1000, 5000, '11:00', '23:00', '000-8888', '愛知県名古屋市中区栄1-1-18', '111-1111-1111', '月曜日');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, min_price, max_price, open_time, close_time, postal_code, address, phone_number, closed_day) VALUES (19, 19, '店舗19', 'restaurant19.jpg', '説明19', 1000, 5000, '11:00', '23:00', '000-9999', '愛知県名古屋市中区栄1-1-19', '111-1111-1111', '月曜日');
INSERT IGNORE INTO restaurants (id, category_id, name, image_name, description, min_price, max_price, open_time, close_time, postal_code, address, phone_number, closed_day) VALUES (20, 20, '店舗20', 'restaurant20.jpg', '説明20', 1000, 5000, '11:00', '23:00', '111-0000', '愛知県名古屋市中区栄1-1-20', '111-1111-1111', '月曜日');
INSERT IGNORE INTO roles (id, name) VALUES (1, 'ROLE_GENERAL');
INSERT IGNORE INTO roles (id, name) VALUES (2, 'ROLE_ADMIN');
INSERT IGNORE INTO roles (id, name) VALUES (3, 'ROLE_PAID');
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (1, '侍 太郎', 'サムライ タロウ', '101-0022', '東京都千代田区神田練塀町300番地', '090-1234-5678', 'taro.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 1, true);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (2, '侍 花子', 'サムライ ハナコ', '101-0022', '東京都千代田区神田練塀町300番地', '090-1234-5678', 'hanako.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 2, true);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (3, '侍 義勝', 'サムライ ヨシカツ', '638-0644', '奈良県五條市西吉野町湯川X-XX-XX', '090-1234-5678', 'yoshikatsu.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (4, '侍 幸美', 'サムライ サチミ', '342-0006', '埼玉県吉川市南広島X-XX-XX', '090-1234-5678', 'sachimi.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (5, '侍 雅', 'サムライ ミヤビ', '527-0209', '滋賀県東近江市佐目町X-XX-XX', '090-1234-5678', 'miyabi.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (6, '侍 正保', 'サムライ マサヤス', '989-1203', '宮城県柴田郡大河原町旭町X-XX-XX', '090-1234-5678', 'masayasu.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (7, '侍 真由美', 'サムライ マユミ', '951-8015', '新潟県新潟市松岡町X-XX-XX', '090-1234-5678', 'mayumi.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (8, '侍 安民', 'サムライ ヤスタミ', '241-0033', '神奈川県横浜市旭区今川町X-XX-XX', '090-1234-5678', 'yasutami.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (9, '侍 章緒', 'サムライ アキオ', '739-2103', '広島県東広島市高屋町宮領X-XX-XX', '090-1234-5678', 'akio.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (10, '侍 祐子', 'サムライ ユウコ', '601-0761', '京都府南丹市美山町高野X-XX-XX', '090-1234-5678', 'yuko.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (11, '侍 秋美', 'サムライ アキミ', '606-8235', '京都府京都市左京区田中西春菜町X-XX-XX', '090-1234-5678', 'akimi.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (12, '侍 信平', 'サムライ シンペイ', '673-1324', '兵庫県加東市新定X-XX-XX', '090-1234-5678', 'shinpei.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reservation_datetime, number_of_people) VALUES (1, 1, 1, '2023-04-01 18:00', 2);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reservation_datetime, number_of_people) VALUES (2, 2, 1, '2023-04-02 18:00', 2);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reservation_datetime, number_of_people) VALUES (3, 3, 1, '2023-04-03 18:00', 2);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reservation_datetime, number_of_people) VALUES (4, 4, 1, '2023-04-04 18:00', 2);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reservation_datetime, number_of_people) VALUES (5, 5, 1, '2023-04-05 18:00', 2);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reservation_datetime, number_of_people) VALUES (6, 6, 1, '2023-04-06 18:00', 2);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reservation_datetime, number_of_people) VALUES (7, 7, 1, '2023-04-07 18:00', 2);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reservation_datetime, number_of_people) VALUES (8, 8, 1, '2023-04-08 18:00', 2);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reservation_datetime, number_of_people) VALUES (9, 9, 1, '2023-04-09 18:00', 2);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reservation_datetime, number_of_people) VALUES (10, 10, 1, '2023-04-10 18:00', 2);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reservation_datetime, number_of_people) VALUES (11, 11, 1, '2023-04-11 18:00', 2);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reservation_datetime, number_of_people) VALUES (12, 12, 1, '2023-04-12 18:00', 2);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (1, 2, 1, 2, 'レビュー1');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (2, 3, 1, 3, 'レビュー1');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (3, 4, 1, 4, 'レビュー1');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (4, 5, 1, 5, 'レビュー1');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (5, 6, 1, 4, 'レビュー1');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (6, 7, 1, 4, 'レビュー1');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (7, 8, 1, 4, 'レビュー1');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (8, 9, 1, 4, 'レビュー1');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (9, 10, 1, 4, 'レビュー1');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (10, 11, 1, 4, 'レビュー1');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (11, 12, 1, 4, 'レビュー1');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (12, 2, 2, 2, 'レビュー2');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (13, 3, 2, 3, 'レビュー2');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (14, 4, 2, 4, 'レビュー2');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (15, 5, 2, 5, 'レビュー2');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (16, 6, 2, 5, 'レビュー2');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (17, 7, 2, 5, 'レビュー2');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (18, 8, 2, 5, 'レビュー2');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (19, 9, 2, 5, 'レビュー2');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (20, 10, 2, 5, 'レビュー2');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (21, 11, 2, 5, 'レビュー2');
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, review, comment) VALUES (22, 12, 2, 5, 'レビュー2');
INSERT IGNORE INTO favorites (id, user_id, restaurant_id, favorite) VALUES (1, 1, 1, 2);
INSERT IGNORE INTO favorites (id, user_id, restaurant_id, favorite) VALUES (2, 1, 2, 2);
INSERT IGNORE INTO favorites (id, user_id, restaurant_id, favorite) VALUES (3, 1, 3, 2);
INSERT IGNORE INTO favorites (id, user_id, restaurant_id, favorite) VALUES (4, 1, 4, 2);
INSERT IGNORE INTO favorites (id, user_id, restaurant_id, favorite) VALUES (5, 1, 5, 2);
INSERT IGNORE INTO favorites (id, user_id, restaurant_id, favorite) VALUES (6, 1, 6, 2);
INSERT IGNORE INTO favorites (id, user_id, restaurant_id, favorite) VALUES (7, 1, 7, 2);
INSERT IGNORE INTO favorites (id, user_id, restaurant_id, favorite) VALUES (8, 1, 8, 2);
INSERT IGNORE INTO favorites (id, user_id, restaurant_id, favorite) VALUES (9, 1, 9, 2);
INSERT IGNORE INTO favorites (id, user_id, restaurant_id, favorite) VALUES (10, 1, 10, 2);
INSERT IGNORE INTO favorites (id, user_id, restaurant_id, favorite) VALUES (11, 1, 11, 2);

