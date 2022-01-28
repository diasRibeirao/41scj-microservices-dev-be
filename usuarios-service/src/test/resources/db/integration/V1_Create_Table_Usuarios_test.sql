CREATE TABLE `usuarios` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `nome` VARCHAR(40) NOT NULL,
  `sobre_nome` VARCHAR(80) NOT NULL,
  `email` VARCHAR(80) NOT NULL,
  `login` VARCHAR(20) NOT NULL,
  `telefone` VARCHAR(20) NOT NULL,
  `senha` VARCHAR(120) NOT NULL,
  `situacao` INT NOT NULL,
  `codigo_ativar` CHAR(4) NULL,  
   `data_limite_ativar` DATETIME NULL
) 