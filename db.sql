SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `datasets` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `datasets` ;

-- -----------------------------------------------------
-- Table `datasets`.`AccessPoints`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `datasets`.`AccessPoints` ;

CREATE  TABLE IF NOT EXISTS `datasets`.`AccessPoints` (
  `id` INT NOT NULL ,
  `user` VARCHAR(20) NOT NULL ,
  `ssid` VARCHAR(45) NULL ,
  `bssid` VARCHAR(45) NULL ,
  `rssi` INT NULL ,
  `frequency` INT NULL ,
  `timestamp` TIMESTAMP NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `datasets`.`Battery`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `datasets`.`Battery` ;

CREATE  TABLE IF NOT EXISTS `datasets`.`Battery` (
  `id` INT NOT NULL ,
  `user` VARCHAR(45) NOT NULL ,
  `level` INT NULL ,
  `plugged` INT NULL ,
  `timestamp` TIMESTAMP NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `datasets`.`BaseStations`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `datasets`.`BaseStations` ;

CREATE  TABLE IF NOT EXISTS `datasets`.`BaseStations` (
  `id` INT NOT NULL ,
  `user` VARCHAR(45) NOT NULL ,
  `operator` VARCHAR(45) NULL ,
  `mcc` INT NULL ,
  `mnc` INT NULL ,
  `lac` INT NULL ,
  `cid` INT NULL ,
  `latitude` DOUBLE NULL ,
  `longtitude` DOUBLE NULL ,
  `timestamp` TIMESTAMP NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `datasets`.`GPS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `datasets`.`GPS` ;

CREATE  TABLE IF NOT EXISTS `datasets`.`GPS` (
  `id` INT NOT NULL ,
  `user` VARCHAR(45) NOT NULL ,
  `latitude` DOUBLE NULL ,
  `longtitude` DOUBLE NULL ,
  `timestamp` TIMESTAMP NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
