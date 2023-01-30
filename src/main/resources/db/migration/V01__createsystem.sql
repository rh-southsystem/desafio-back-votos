CREATE TABLE `associate` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
	`created` DATETIME NOT NULL,
	`updated` DATETIME NOT NULL,
	`name` VARCHAR(255) NOT NULL,
    `cpf` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT UC_associate UNIQUE (cpf)
);

CREATE TABLE `rullies` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
	`created` DATETIME NOT NULL,
	`updated` DATETIME NOT NULL,	
	`name` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `sessions` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
	`created` DATETIME NOT NULL,
	`updated` DATETIME NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`minutes` BIGINT NOT NULL,
	`begin` DATETIME NOT NULL,
	`finish` DATETIME,
	`result` TEXT,
	PRIMARY KEY (`id`)	
);

CREATE TABLE `votes` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
	`created` DATETIME NOT NULL,
	`updated` DATETIME NOT NULL,
	`ruling_id` BIGINT NOT NULL,
	`session_id` BIGINT NOT NULL,
	`associate_id` BIGINT NOT NULL, 
	`value` VARCHAR(3) NOT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT UC_session_ruling UNIQUE (ruling_id,session_id,associate_id)
);