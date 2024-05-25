-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema forum
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema forum
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `forum` DEFAULT CHARACTER SET utf8 ;
USE `forum` ;

-- -----------------------------------------------------
-- Table `forum`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_group` INT NULL,
  `username` VARCHAR(20) NULL,
  `email` VARCHAR(45) NULL,
  `password` VARCHAR(150) NULL,
  `registered` TINYINT(1) NULL,
  `activation_code` VARCHAR(20) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `forum`.`permission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`permission` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `forum`.`room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`room` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `forum`.`access`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`access` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `permission_id` INT NOT NULL,
  `room_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_access_permission1_idx` (`permission_id` ASC) VISIBLE,
  INDEX `fk_access_room1_idx` (`room_id` ASC) VISIBLE,
  INDEX `fk_access_user_comment1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_access_permission1`
    FOREIGN KEY (`permission_id`)
    REFERENCES `forum`.`permission` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_access_room1`
    FOREIGN KEY (`room_id`)
    REFERENCES `forum`.`room` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_access_user_comment1`
    FOREIGN KEY (`user_id`)
    REFERENCES `forum`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `forum`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `approved` TINYINT(1) NULL,
  `posted_datetime` DATETIME NULL,
  `room_id` INT NOT NULL,
  `content` VARCHAR(500) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comment_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_comment_room1_idx` (`room_id` ASC) VISIBLE,
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `forum`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_room1`
    FOREIGN KEY (`room_id`)
    REFERENCES `forum`.`room` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `forum`.`log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`log` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `message` VARCHAR(300) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;




insert into forum.room (name) values ("Muzika");
insert into forum.room (name) values ("Sport");
insert into forum.room (name) values ("Nauka");
insert into forum.room (name) values ("Kultura");

insert into forum.user (user_group, username, email, password, registered) values (1, "moderator", "m@gmail.com", '$2a$10$RD9qXaimn8XTFtm2nr7LXejOxGwVq.UrFwiwbvfZQuDZZMves52qa', false);
insert into forum.user (user_group, username, email, password, registered) values (0, "user", "olivereric500@gmail.com", '$2a$10$RD9qXaimn8XTFtm2nr7LXejOxGwVq.UrFwiwbvfZQuDZZMves52qa', false);

insert into forum.user (user_group, username, email, password, registered) values (2, "admin", "olivereric529@outlook.com", '$2a$10$RD9qXaimn8XTFtm2nr7LXejOxGwVq.UrFwiwbvfZQuDZZMves52qa', true);

INSERT INTO forum.comment (user_id, approved, posted_datetime, room_id, content) VALUES (1, true, '2024-03-20 10:30:00', 1, "prvi komentar");
INSERT INTO forum.comment (user_id, approved, posted_datetime, room_id, content) VALUES (1, false, '2024-03-20 10:30:00', 1, "prvi  komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarrvi komentarntar");


insert into forum.permission (name) values ("create");
insert into forum.permission (name) values ("update");
insert into forum.permission (name) values ("delete");

insert into forum.access (permission_id, room_id, user_id) values (1, 1, 2);
insert into forum.access (permission_id, room_id, user_id) values (2, 2, 2);
insert into forum.access (permission_id, room_id, user_id) values (2, 1, 2);
insert into forum.access (permission_id, room_id, user_id) values (2, 3, 2);

