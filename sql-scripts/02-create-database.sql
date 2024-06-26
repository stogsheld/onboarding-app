-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema onboarding_app
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema onboarding_app
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `onboarding_app` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `onboarding_app` ;

-- -----------------------------------------------------
-- Table `onboarding_app`.`course_completion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `onboarding_app`.`course_completion` (
  `attempt_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `course_id` INT NOT NULL,
  `attempt_score` INT NOT NULL,
  `attempt_date` DATE NULL DEFAULT NULL,
  `completion_date` DATE NULL DEFAULT NULL,
  `refresher_date` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`attempt_no`))
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `onboarding_app`.`course_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `onboarding_app`.`course_info` (
  `course_id` INT NOT NULL,
  `course_name` VARCHAR(45) NULL DEFAULT NULL,
  `course_description` VARCHAR(1000) NULL DEFAULT NULL,
  `course_content` VARCHAR(5000) NULL DEFAULT NULL,
  PRIMARY KEY (`course_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `onboarding_app`.`course_info` VALUES 
	(1,'Introduction to Data Security','Example Description','Example Course Content'),
	(2,'Intro to Ethics','Example','Example');


-- -----------------------------------------------------
-- Table `onboarding_app`.`course_question`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `onboarding_app`.`course_question` (
  `question_id` INT NOT NULL AUTO_INCREMENT,
  `question_number` INT NOT NULL,
  `question_content` VARCHAR(45) NOT NULL,
  `course_id` INT NOT NULL,
  `answer_one` VARCHAR(45) NOT NULL,
  `answer_two` VARCHAR(45) NOT NULL,
  `answer_three` VARCHAR(45) NOT NULL,
  `answer_four` VARCHAR(45) NOT NULL,
  `correct_answer` INT NOT NULL,
  PRIMARY KEY (`question_id`),
  INDEX `course_id_idx` (`course_id` ASC) VISIBLE,
  CONSTRAINT `course_id`
    FOREIGN KEY (`course_id`)
    REFERENCES `onboarding_app`.`course_info` (`course_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `onboarding_app`.`course_question` VALUES 
	(1, 1, 'Question 1', 1,'Answer 1','Answer 2 (Correct)', 'Answer 3', 'Answer 4', 2),
	(2, 2, 'Question 2', 1,'Answer 1','Answer 2', 'Answer 3 (Correct)', 'Answer 4', 3),
	(3, 3, 'Question 3', 1,'Answer 1 (Correct)','Answer 2', 'Answer 3', 'Answer 4', 1),
	(4, 1, 'Question 1', 2,'Answer 1','Answer 2 (Correct)', 'Answer 3', 'Answer 4', 2),
	(5, 2, 'Question 2', 2,'Answer 1','Answer 2 (Correct)', 'Answer 3', 'Answer 4', 2),
	(6, 3, 'Question 3', 2,'Answer 1','Answer 2 (Correct)', 'Answer 3', 'Answer 4', 2);



-- -----------------------------------------------------
-- Table `onboarding_app`.`onboarding_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `onboarding_app`.`onboarding_info` (
  `user_id` INT NOT NULL,
  `welcome_completion` INT NOT NULL,
  `data_ethics_completion` INT NOT NULL,
  `setup_completion` INT NOT NULL,
  `meet_team_completion` INT NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `onboarding_app`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `onboarding_app`.`roles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `role` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `authorities5_idx_1` (`user_id` ASC, `role` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = latin1;

INSERT INTO `onboarding_app`.`roles` VALUES 
	(1,11111,'ROLE_ADMIN'),
	(2,11111,'ROLE_EMPLOYEE'),
	(3,22222,'ROLE_EMPLOYEE');
    
    
    
-- -----------------------------------------------------
-- Table `onboarding_app`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `onboarding_app`.`user` (
  `user_id` INT NOT NULL,
  `pw` CHAR(68) NOT NULL,
  `active` TINYINT NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `career_level` INT NOT NULL,
  `team_id` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

INSERT INTO `onboarding_app`.`user` VALUES 
	(11111, '{bcrypt}$2a$12$vYZLC7ubVYrbP/UH.dreROKA.wKQ/dKWS70gXIVgV5Af4B0Qgc1z2', 1, 'TESTADMIN', 'LASTNAME', 4, 'Scrum Team 5', 'testadmin@accenture.com'),
	(22222, '{bcrypt}$2a$12$vYZLC7ubVYrbP/UH.dreROKA.wKQ/dKWS70gXIVgV5Af4B0Qgc1z2', 1, 'TESTNONADMIN', 'LASTNAME', 10, 'Scrum Team 2', 'testnonadmin@accenture.com');



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
