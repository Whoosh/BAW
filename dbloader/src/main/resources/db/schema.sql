CREATE SCHEMA `baw`;

CREATE TABLE IF NOT EXISTS `baw`.`album` (
  `id`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `watch_count`   INT UNSIGNED    NOT NULL DEFAULT 0,
  `like_count`    INT UNSIGNED    NOT NULL DEFAULT 0,
  `comment_count` INT UNSIGNED    NOT NULL DEFAULT 0,
  `title`         TEXT            NOT NULL,
  `create_time`   DATETIME        NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `baw`.`photo` (
  `id`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `device_id`     BIGINT UNSIGNED NOT NULL,
  `name`          TEXT            NOT NULL,
  `web_directory` TEXT            NOT NULL,
  `height`        INT UNSIGNED    NOT NULL,
  `width`         INT UNSIGNED    NOT NULL,
  PRIMARY KEY (`id`, `device_id`),
  INDEX `fk_photo_device1_idx` (`device_id` ASC),
  CONSTRAINT `fk_photo_device1`
  FOREIGN KEY (`device_id`)
  REFERENCES `baw`.`device` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `baw`.`album_has_photo` (
  `album_id` BIGINT UNSIGNED NOT NULL,
  `photo_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`album_id`, `photo_id`),
  INDEX `fk_album_has_photo_photo1_idx` (`photo_id` ASC),
  INDEX `fk_album_has_photo_album_idx` (`album_id` ASC),
  CONSTRAINT `fk_album_has_photo_album`
  FOREIGN KEY (`album_id`)
  REFERENCES `baw`.`album` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_album_has_photo_photo1`
  FOREIGN KEY (`photo_id`)
  REFERENCES `baw`.`photo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `baw`.`device_settings` (
  `id`           BIGINT UNSIGNED  NOT NULL AUTO_INCREMENT,
  `focal_length` DECIMAL UNSIGNED NOT NULL,
  `aperture`     DECIMAL UNSIGNED NOT NULL,
  `iso`          INT UNSIGNED     NOT NULL,
  `camera_shot`  VARCHAR(30)      NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `baw`.`device` (
  `id`    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `brand` VARCHAR(20)     NOT NULL,
  `model` VARCHAR(30)     NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `baw`.`device_settings_has_device` (
  `device_settings_id` BIGINT UNSIGNED NOT NULL,
  `device_id`          BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`device_settings_id`, `device_id`),
  INDEX `fk_device_settings_has_device_device1_idx` (`device_id` ASC),
  INDEX `fk_device_settings_has_device_device_settings1_idx` (`device_settings_id` ASC),
  CONSTRAINT `fk_device_settings_has_device_device_settings1`
  FOREIGN KEY (`device_settings_id`)
  REFERENCES `baw`.`device_settings` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_device_settings_has_device_device1`
  FOREIGN KEY (`device_id`)
  REFERENCES `baw`.`device` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `baw`.`shooting_characteristic` (
  `id`                     BIGINT UNSIGNED  NOT NULL AUTO_INCREMENT,
  `photo_device_id`        BIGINT UNSIGNED  NOT NULL,
  `photo_id`               BIGINT UNSIGNED  NOT NULL,
  `taken_time`             DATETIME         NOT NULL,
  `shutter_speed`          DECIMAL UNSIGNED NOT NULL,
  `exposure_compenstation` INT UNSIGNED     NOT NULL,
  PRIMARY KEY (`id`, `photo_device_id`, `photo_id`),
  INDEX `fk_shooting_characteristic_photo1_idx` (`photo_id` ASC, `photo_device_id` ASC),
  CONSTRAINT `fk_shooting_characteristic_photo1`
  FOREIGN KEY (`photo_id`, `photo_device_id`)
  REFERENCES `baw`.`photo` (`id`, `device_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `baw`.`type` (
  `id`       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `photo_id` BIGINT UNSIGNED NOT NULL,
  `type`     VARCHAR(8)      NOT NULL,
  PRIMARY KEY (`id`, `photo_id`),
  INDEX `fk_type_photo1_idx` (`photo_id` ASC),
  CONSTRAINT `fk_type_photo1`
  FOREIGN KEY (`photo_id`)
  REFERENCES `baw`.`photo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `baw`.`host` (
  `id`   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `host` TEXT            NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `baw`.`photo_has_host` (
  `photo_id` BIGINT UNSIGNED NOT NULL,
  `host_id`  BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`photo_id`, `host_id`),
  INDEX `fk_photo_has_host_host1_idx` (`host_id` ASC),
  INDEX `fk_photo_has_host_photo1_idx` (`photo_id` ASC),
  CONSTRAINT `fk_photo_has_host_photo1`
  FOREIGN KEY (`photo_id`)
  REFERENCES `baw`.`photo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_photo_has_host_host1`
  FOREIGN KEY (`host_id`)
  REFERENCES `baw`.`host` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;