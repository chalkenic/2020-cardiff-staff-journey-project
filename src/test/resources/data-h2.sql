-- Preset data for database --
USE developmenttoolkit;

INSERT INTO `developmenttoolkit`.`role` VALUES ( 'Participant', 'participating in activity');
INSERT INTO `developmenttoolkit`.`role` VALUES ( 'Review', 'Reviewing activity');
INSERT INTO `developmenttoolkit`.`role` VALUES ( 'Lead', 'Leading activity');


INSERT INTO tag (tagName, description, isOfficial) VALUES ('A1', 'Design and plan learning activities and/or programmes of study', 1);
INSERT INTO tag (tagName, description, isOfficial) VALUES('A2', 'Teach and/or support learning.', true);
INSERT INTO tag (tagName, description, isOfficial) VALUES('A3', 'Assess and give feedback to learners.', true);
INSERT INTO tag (tagName, description, isOfficial) VALUES('A4', 'Develop effective learning environments and approaches to student support and guidance.', true);

INSERT INTO tag (tagName, description, isOfficial) VALUES('A5', 'Engage in continuing professional development in subjects/disciples and their pedagogy, incorporating research, scholarship and the evaluation of professional practices.', true);
INSERT INTO tag (tagName, description, isOfficial) VALUES('D3.7', 'Supporting colleagues.', true);
INSERT INTO tag (tagName, description, isOfficial) VALUES('K1', 'The subject material', true);
INSERT INTO tag (tagName, description, isOfficial) VALUES('K2', 'Appropriate methods for reaching learning and assessing in the subject area and at the level of the academic programme.', true);

INSERT INTO tag (tagName, description, isOfficial) VALUES('K3', 'How students learn, both generally and within their subject/disciplinary area(s)', true);
INSERT INTO tag (tagName, description, isOfficial) VALUES('K4', 'The use and value of appropriate learning technologies.', true);
INSERT INTO tag (tagName, description, isOfficial) VALUES('K5', 'Methods for evaluating the effectiveness of teaching.', true);
INSERT INTO tag (tagName, description, isOfficial) VALUES('K6', 'The implications of quality assurance and quality enhancement for academic and professional practice with a particular focus on teaching.', true);

INSERT INTO tag (tagName, description, isOfficial) VALUES('V1', 'Respect individual learners and diverse learning communities.', true);
INSERT INTO tag (tagName, description, isOfficial) VALUES('V2', 'Promote participation in higher education and equality of opportunity for learners.', true);
INSERT INTO tag (tagName, description, isOfficial) VALUES('V3', 'Use evidence-informed approaches and the outcomes from research, scholarship and continuing professional development.', true);
INSERT INTO tag (tagName, description, isOfficial) VALUES('V4', 'Acknowledge the wider context in which higher education operates recognising the implications for professional practice.', true);

-- Dummy Users for database --


INSERT INTO `developmenttoolkit`.`siteUser`( emailAddress, password, userName, permissions, isActive)
VALUES('k@aren.com', '$2a$10$YhSaTI2UkEJmGmNzR..pouFZlKAHoQR1uvTyAUv5ffGYHTum.BUHy', 'karen', 'ROLE_USER', 1);

INSERT INTO `developmenttoolkit`.`siteUser`( emailAddress, password, userName, permissions,isActive)
VALUES('david112@outlook.com', '$2a$10$Es0Jp14TEF2wGQ3xBb8/g.l9PGwH.yBXKBsy4XBcE7tUfFT9Ou6D.', 'david112', 'ROLE_USER', 1);

INSERT INTO `developmenttoolkit`.`siteUser`(emailAddress, password, userName, permissions,isActive)
VALUES('admin@emailexample.com', '$2a$10$0MPUyNqDfnnqegokguyWwehXGQwfEot8GV5TWB7MDd8ztHHTuL4oG', 'admin', 'ROLE_ADMIN', 1);

INSERT INTO `developmenttoolkit`.`siteUser`(emailAddress, password, userName, permissions, isActive)
VALUES('user@name', '$2a$10$rnXtXnpvlvTKX8IBR7nBpupJRHGu37TLE5ICBisSH.XY26MN3s7rC', 'user', 'ROLE_USER', 1);


