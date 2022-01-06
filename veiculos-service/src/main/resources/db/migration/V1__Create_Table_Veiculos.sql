CREATE TABLE `veiculos` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `placa` CHAR(8) NOT NULL,
  `marca` VARCHAR(80) NOT NULL,
  `modelo` VARCHAR(80) NOT NULL,
  `cor` VARCHAR(20) NOT NULL,
  `parceiro_id` BIGINT NOT NULL
) 
