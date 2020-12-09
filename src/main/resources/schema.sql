-- MySQL Script generated by MySQL Workbench
-- Sun Nov 22 20:59:33 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering
SET MODE MYSQL;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema staffdevelopment
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema staffdevelopment
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS developmenttoolkit;
USE developmenttoolkit ;

DROP TABLE IF EXISTS participation;
DROP TABLE IF EXISTS reflection;
DROP TABLE IF EXISTS objective;
DROP TABLE IF EXISTS activity;
DROP TABLE IF EXISTS siteUser;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS role;



-- -----------------------------------------------------
-- Table `developmenttoolkit`.`activity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS activity(
  activityID INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  file VARCHAR(45) NULL DEFAULT NULL,
  description VARCHAR(250) NULL DEFAULT NULL,
  isOfficial BOOLEAN,
  PRIMARY KEY (activityID))
ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `developmenttoolkit`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS tag(
  tagID VARCHAR(11) NOT NULL,
  description VARCHAR(200) NULL DEFAULT NULL,
  isOfficial TINYINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`tagID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `developmenttoolkit`.`objective`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS objective(
  objectiveID INT(11) NOT NULL,
  Activity_activityID INT(11) NOT NULL,
  Tag_tagID VARCHAR(11) NOT NULL,
  PRIMARY KEY (objectiveID),
    FOREIGN KEY (Activity_activityID)
    REFERENCES activity (activityID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (`Tag_tagID`)
    REFERENCES tag (tagID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `developmenttoolkit`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS role(
--  `roleID` INT(11) NOT NULL AUTO_INCREMENT,
--   `type` VARCHAR(45) NOT NULL DEFAULT NULL,
  role VARCHAR(45) NOT NULL,
  description VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (role))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `developmenttoolkit`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS siteUser(
  userID INT(11) NOT NULL AUTO_INCREMENT,
  emailAddress VARCHAR(45) NOT NULL,
  password VARCHAR(100)NOT NULL,
  userName VARCHAR(20) NULL DEFAULT NULL,
  isActive BOOLEAN NULL DEFAULT NULL,
  permissions VARCHAR(30) NOT NULL,
  PRIMARY KEY (userID))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `developmenttoolkit`.`reflection`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS reflection(
   reflectionID INT(11) NOT NULL AUTO_INCREMENT,
   reflection VARCHAR(45) NULL DEFAULT NULL,
   Participation_participationID INT(11) NOT NULL,
   Tag_tagID VARCHAR(11) NOT NULL,
   PRIMARY KEY (reflectionID),
   FOREIGN KEY (Tag_tagID)
       REFERENCES tag (tagID)
       ON DELETE NO ACTION
       ON UPDATE NO ACTION)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `developmenttoolkit`.`participation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS participation(
  participationID INT(11) NOT NULL AUTO_INCREMENT,
  date DATETIME NULL DEFAULT NULL,
  siteUser_userID INT(11) NOT NULL,
  Activity_activityID INT(11) NOT NULL,
  Role_roleID VARCHAR(45) NOT NULL,
  PRIMARY KEY (participationID),
    FOREIGN KEY (Activity_activityID)
    REFERENCES activity (activityID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (siteUser_userID)
    REFERENCES siteUser (userID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (Role_roleID)
    REFERENCES role (role)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
