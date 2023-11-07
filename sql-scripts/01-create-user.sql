-- This script is used to create a user with the username 'onboarding' and password 'onboarding' for testing purposes. If using another user, there is no need to execute this script.

-- Dropping user if they already exist
DROP USER if exists 'onboarding'@'%' ;

-- Creating fresh user
CREATE USER 'onboarding'@'%' IDENTIFIED BY 'onboarding';

-- Granting privileges to user
GRANT ALL PRIVILEGES ON * . * TO 'onboarding'@'%';