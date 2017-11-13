--
-- Database: `Resonate`
--

-- DROP DATABASE `Resonate`;
-- CREATE DATABASE IF NOT EXISTS `Resonate`;
USE `sql3204487`;

-- --------------------------------------------------------

--
-- Table structure for table `AdminUsers`
--

CREATE TABLE IF NOT EXISTS `AdminUsers` (
  `_id` int(10) PRIMARY KEY AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `name` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `email` varchar(40) NOT NULL,
  `photo` varchar(200) NULL,
  `bio` TEXT NULL
);


-- --------------------------------------------------------

--
-- Table structure for table `NonAdminUsers`
--

CREATE TABLE IF NOT EXISTS `NonAdminUsers` (
  `_id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `name` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `email` varchar(40) NOT NULL
);

-- --------------------------------------------------------

--
-- Table structure for table `Projects`
--

CREATE TABLE IF NOT EXISTS `Projects` (
  `_id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `description` text NOT NULL,
  `upvoteCount` int(6) NOT NULL DEFAULT '0',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- --------------------------------------------------------

--
-- Table structure for table `Roles`
--

CREATE TABLE IF NOT EXISTS `Roles` (
  `_id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `description` text NOT NULL,
  FOREIGN KEY fk1(project_id) REFERENCES Projects(_id)
);

-- --------------------------------------------------------

--
-- Table structure for table `Contributors`
--

CREATE TABLE IF NOT EXISTS `Contributors` (
  `_id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  FOREIGN KEY fk1(project_id) REFERENCES Projects(_id),
  FOREIGN KEY fk2(user_id) REFERENCES NonAdminUsers(_id),
  FOREIGN KEY fk3(role_id) REFERENCES Roles(_id)
);

-- --------------------------------------------------------

--
-- Table structure for table `Editors`
--

CREATE TABLE IF NOT EXISTS `Editors` (
  `_id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  FOREIGN KEY fk1(project_id) REFERENCES Projects(_id),
  FOREIGN KEY fk2(user_id) REFERENCES NonAdminUsers(_id)
);

-- --------------------------------------------------------

--
-- Table structure for table `LikedProjects`
--

CREATE TABLE IF NOT EXISTS `LikedProjects` (
  `_id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  FOREIGN KEY fk1(project_id) REFERENCES Projects(_id),
  FOREIGN KEY fk2(user_id) REFERENCES NonAdminUsers(_id)
);

-- --------------------------------------------------------

--
-- Table structure for table `MyProjects`
--

CREATE TABLE IF NOT EXISTS `MyProjects` (
  `_id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  FOREIGN KEY fk1(project_id) REFERENCES Projects(_id),
  FOREIGN KEY fk2(user_id) REFERENCES NonAdminUsers(_id)
);

-- --------------------------------------------------------

--
-- Table structure for table `Tracks`
--

CREATE TABLE IF NOT EXISTS `Tracks` (
  `_id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `project_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `fileLocation` varchar(100) NOT NULL,
  `fileName` varchar(30) NOT NULL,
  `delay` int(7) NOT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY fk1(project_id) REFERENCES Projects(_id),
  FOREIGN KEY fk2(role_id) REFERENCES Roles(_id),
  FOREIGN KEY fk3(user_id) REFERENCES NonAdminUsers(_id)
);

-- --------------------------------------------------------

--
-- Table structure for table `UserToRoleMap`
--

CREATE TABLE IF NOT EXISTS `UserToRoleMap` (
  `_id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  FOREIGN KEY fk1(user_id) REFERENCES NonAdminUsers(_id),
  FOREIGN KEY fk2(role_id) REFERENCES Roles(_id)
);

-- --------------------------------------------------------

--
-- Table structure for table `ProjectResources`
--

CREATE TABLE IF NOT EXISTS `ProjectResources` (
  `_id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `fileLocation` varchar(100) NOT NULL,
  `fileName` varchar(30) NOT NULL,
  `fileDescription` text,
  FOREIGN KEY fk1(project_id) REFERENCES Projects(_id)
);
