-- MySQL Script generated by MySQL Workbench
-- Sun Nov 22 20:59:33 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering
-- SET MODE MYSQL; /*- this throws errors in the Workbench, so temporarily comment out when instantiating DB*/
-- SET IGNORECASE=TRUE; /*- this throws errors in the Workbench, so temporarily comment out when instantiating DB*/

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema staffdevelopment
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema staffdevelopment
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS staffdevelopment;
USE `staffdevelopment` ;

-- -----------------------------------------------------
-- Table `staffdevelopment`.`activity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `staffdevelopment`.`activity` (
  `activityID` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `file` VARCHAR(45) NULL DEFAULT NULL,
  `description` VARCHAR(250) NULL DEFAULT NULL,
  PRIMARY KEY (`activityID`))
ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `staffdevelopment`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `staffdevelopment`.`tag` (
  `tagID` VARCHAR(11) NOT NULL,
  `description` VARCHAR(200) NULL DEFAULT NULL,
  `isOfficial` TINYINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`tagID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `staffdevelopment`.`objective`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `staffdevelopment`.`objective` (
  `objectiveID` INT(11) NOT NULL,
  `Activity_activityID` INT(11) NOT NULL,
  `Tag_tagID` VARCHAR(11) NOT NULL,
  PRIMARY KEY (`objectiveID`),
    FOREIGN KEY (`Activity_activityID`)
    REFERENCES `staffdevelopment`.`activity` (`activityID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (`Tag_tagID`)
    REFERENCES `staffdevelopment`.`tag` (`tagID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `staffdevelopment`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `staffdevelopment`.`role` (
--  `roleID` INT(11) NOT NULL AUTO_INCREMENT,
--   `type` VARCHAR(45) NOT NULL DEFAULT NULL,
  `role` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`role`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `staffdevelopment`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `staffdevelopment`.`siteUser` (
  `userID` INT(11) NOT NULL AUTO_INCREMENT,
  `emailAddress` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45)NOT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`userID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `staffdevelopment`.`participation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `staffdevelopment`.`participation` (
  `participationID` INT(11) NOT NULL AUTO_INCREMENT,
  `date` DATETIME NULL DEFAULT NULL,
  `siteUser_userID` INT(11) NOT NULL,
  `Activity_activityID` INT(11) NOT NULL,
  `Role_roleID` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`participationID`),
    FOREIGN KEY (`Activity_activityID`)
    REFERENCES `staffdevelopment`.`activity` (`activityID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (`siteUser_userID`)
    REFERENCES `staffdevelopment`.`siteUser` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (`Role_roleID`)
    REFERENCES `staffdevelopment`.`role` (`role`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `staffdevelopment`.`reflection`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `staffdevelopment`.`reflection` (
  `reflectionID` INT(11) NOT NULL AUTO_INCREMENT,
  `reflection` VARCHAR(45) NULL DEFAULT NULL,
  `Participation_participationID` INT(11) NOT NULL,
  `Tag_tagID` VARCHAR(11) NOT NULL,
  PRIMARY KEY (`reflectionID`),
    FOREIGN KEY (`Participation_participationID`)
    REFERENCES `staffdevelopment`.`participation` (`participationID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (`Tag_tagID`)
    REFERENCES `staffdevelopment`.`tag` (`tagID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Dummy data for participation (temp)
-- -----------------------------------------------------
INSERT INTO siteUser VALUES (null, 'testaddress@gmail.com', 'passwordtest', 'nametest');
INSERT INTO role VALUES ('0', 'placeholder');