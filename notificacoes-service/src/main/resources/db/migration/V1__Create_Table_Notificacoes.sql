CREATE TABLE `notificacoes` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `para` VARCHAR(220) NOT NULL,
  `mensagem` VARCHAR(2000) NOT NULL,
  `observacao` VARCHAR(2000) NULL,
  `data` datetime NULL
) 