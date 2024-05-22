-- 创建随机字符串生成函数
DROP FUNCTION IF EXISTS generate_random_string;
DELIMITER //
CREATE FUNCTION generate_random_string(length INT)
RETURNS VARCHAR(255)
DETERMINISTIC
BEGIN
    DECLARE chars VARCHAR(62) DEFAULT 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    DECLARE result VARCHAR(255) DEFAULT '';
    DECLARE i INT DEFAULT 0;
    
    WHILE i < length DO
        SET result = CONCAT(result, SUBSTRING(chars, FLOOR(1 + (RAND() * 62)), 1));
        SET i = i + 1;
    END WHILE;
    
    RETURN result;
END //
DELIMITER ;

-- 创建表格
DROP TABLE IF EXISTS `cardarrangement`;
CREATE TABLE `cardarrangement` (
  `card_id` int NOT NULL AUTO_INCREMENT,
  `card_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `card_body` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `publication_date` datetime NULL DEFAULT NULL,
  `visible` tinyint NOT NULL,
  `expiration_date` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`card_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 108 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- 创建存储过程
DELIMITER //
CREATE PROCEDURE insert_test_data(IN num_records INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    WHILE i < num_records DO
        INSERT INTO `cardarrangement` (card_title, card_body, publication_date, visible, expiration_date)
        VALUES (
            generate_random_string(10), -- card_title
            generate_random_string(20), -- card_body
            DATE_ADD('2023-01-01', INTERVAL FLOOR(RAND() * 365) DAY), -- publication_date
            FLOOR(RAND() * 2), -- visible (0 or 1)
            DATE_ADD('2023-01-01', INTERVAL FLOOR(RAND() * 730) DAY) -- expiration_date
        );
        SET i = i + 1;
    END WHILE;
END //
DELIMITER ;

-- 调用存储过程以插入指定数量的测试数据
CALL insert_test_data(129);
