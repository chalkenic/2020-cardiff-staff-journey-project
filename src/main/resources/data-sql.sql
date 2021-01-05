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

INSERT INTO tag (tagName, description, isOfficial) VALUES('worked well', 'I felt the activity''s content worked well.' , false);
INSERT INTO tag (tagName, description, isOfficial) VALUES('Motivational', 'I felt Motivated from participating.', false);
INSERT INTO tag (tagName, description, isOfficial) VALUES('Context', 'I felt this activity gave me more context.', false);
INSERT INTO tag (tagName, description, isOfficial) VALUES('Something new', 'I learnt something new on this activity.', false);

INSERT INTO tag (tagName, description, isOfficial) VALUES('Out of my comfort zone', 'I was out of my comfort zone during activity.', false);
INSERT INTO tag (tagName, description, isOfficial) VALUES('Had a go', 'I actively participated', false);
INSERT INTO tag (tagName, description, isOfficial) VALUES('Good response', 'I garned a good response from all involved.', false);
INSERT INTO tag (tagName, description, isOfficial) VALUES('Needs more work', 'I feel this activity could use improvement.', false);

INSERT INTO tag (tagName, description, isOfficial) VALUES('Back to the drawing board', 'I feel this activity was a failure and needs a rethink.', false);
INSERT INTO tag (tagName, description, isOfficial) VALUES('Phone a friend', 'I feel I need to speak to a friend after this activity.', false);
INSERT INTO tag (tagName, description, isOfficial) VALUES('Apprehensive', 'I was apprehensive about the Activity contents.', false);
INSERT INTO tag (tagName, description, isOfficial) VALUES('Cautious', 'I was Cautious about the Activity contents.', false);

INSERT INTO tag (tagName, description, isOfficial) VALUES('Confident', 'I was Confident in my knowledge of the Activity contents.', false);
INSERT INTO tag (tagName, description, isOfficial) VALUES('Excited', 'I was Excited about the Activity contents.', false);
INSERT INTO tag (tagName, description, isOfficial) VALUES('Depressed', 'I felt Depressed regarding the Activity contents.', false);
INSERT INTO tag (tagName, description, isOfficial) VALUES('Frustrated', 'I was Frustrated with the Activity contents.', false);


-- Dummy Users for database --


INSERT INTO `developmenttoolkit`.`siteUser`( emailAddress, password, userName, permissions, isActive)
VALUES('k@aren.com', '$2a$10$YhSaTI2UkEJmGmNzR..pouFZlKAHoQR1uvTyAUv5ffGYHTum.BUHy', 'karen', 'ROLE_USER', 1);

INSERT INTO `developmenttoolkit`.`siteUser`( emailAddress, password, userName, permissions,isActive)
VALUES('david112@outlook.com', '$2a$10$Es0Jp14TEF2wGQ3xBb8/g.l9PGwH.yBXKBsy4XBcE7tUfFT9Ou6D.', 'david112', 'ROLE_USER', 1);

INSERT INTO `developmenttoolkit`.`siteUser`(emailAddress, password, userName, permissions,isActive)
VALUES('admin@emailexample.com', '$2a$10$0MPUyNqDfnnqegokguyWwehXGQwfEot8GV5TWB7MDd8ztHHTuL4oG', 'admin', 'ROLE_ADMIN', 1);

INSERT INTO `developmenttoolkit`.`siteUser`(emailAddress, password, userName, permissions, isActive)
VALUES('user@name', '$2a$10$rnXtXnpvlvTKX8IBR7nBpupJRHGu37TLE5ICBisSH.XY26MN3s7rC', 'user', 'ROLE_USER', 1);

INSERT INTO `developmenttoolkit`.`siteUser`(emailAddress, password, userName, permissions, isActive)
VALUES('user@name2', '$2a$10$5aH9gZ0pjoTa7FZSMiD88OF.RtvYiVdBxCu49v8gmNwVY9JWixZgC', 'usernew', 'ROLE_USER', 1);

INSERT INTO `developmenttoolkit`.`siteUser`(emailAddress, password, userName, permissions, isActive)
VALUES('user@name3', '$2a$10$5aH9gZ0pjoTa7FZSMiD88OF.RtvYiVdBxCu49v8gmNwVY9JWixZgC', 'usernewer', 'ROLE_USER', 1);

INSERT INTO `developmenttoolkit`.`siteUser`(emailAddress, password, userName, permissions, isActive)
VALUES('user@name4', '$2a$10$5aH9gZ0pjoTa7FZSMiD88OF.RtvYiVdBxCu49v8gmNwVY9JWixZgC', 'usernewish', 'ROLE_USER', 1);

INSERT INTO `developmenttoolkit`.`siteUser`(emailAddress, password, userName, permissions, isActive)
VALUES('user@name5', '$2a$10$5aH9gZ0pjoTa7FZSMiD88OF.RtvYiVdBxCu49v8gmNwVY9JWixZgC', 'usernewing', 'ROLE_USER', 1);

INSERT INTO `developmenttoolkit`.`siteUser`(emailAddress, password, userName, permissions, isActive)
VALUES('user@name6', '$2a$10$5aH9gZ0pjoTa7FZSMiD88OF.RtvYiVdBxCu49v8gmNwVY9JWixZgC', 'usernework', 'ROLE_USER', 1);

INSERT INTO `developmenttoolkit`.`siteUser`(emailAddress, password, userName, permissions, isActive)
VALUES('user@name7', '$2a$10$5aH9gZ0pjoTa7FZSMiD88OF.RtvYiVdBxCu49v8gmNwVY9JWixZgC', 'usernewton', 'ROLE_USER', 1);

INSERT INTO `developmenttoolkit`.`siteUser`(emailAddress, password, userName, permissions, isActive)
VALUES('user@name8', '$2a$10$5aH9gZ0pjoTa7FZSMiD88OF.RtvYiVdBxCu49v8gmNwVY9JWixZgC', 'usernewbab', 'ROLE_USER', 1);

INSERT INTO `developmenttoolkit`.`siteUser`(emailAddress, password, userName, permissions, isActive)
VALUES('user@name9', '$2a$10$5aH9gZ0pjoTa7FZSMiD88OF.RtvYiVdBxCu49v8gmNwVY9JWixZgC', 'usernewsi', 'ROLE_USER', 1);

-- Dummy Activities with assigned objectives for database --

INSERT INTO `developmenttoolkit`.`activity` (name, description, isOfficial) VALUES ('Student Document Reflection Webinar', 'A short webinar regarding how to reflect on student submissions', true);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (1, 3);

INSERT INTO `developmenttoolkit`.`activity` (name, description, isOfficial) VALUES ('Peer review workshop', 'A group workshop where you can meet up with fellow lecturers and learn how to provide difficult constructive criticism', true);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (2, 2);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (2, 6);

INSERT INTO `developmenttoolkit`.`activity` (name, description, isOfficial) VALUES ('The Basics of Biology learning', 'An online group introduction to the process of instructing students on the basics of a Biology degree', true);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (3, 7);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (3, 8);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (3, 10);

INSERT INTO `developmenttoolkit`.`activity` (name, description, isOfficial) VALUES ('Student Representative course feedback', 'A group feedback session between yourself and the assigned student reps', true);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (4, 3);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (4, 9);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (4, 11);

INSERT INTO `developmenttoolkit`.`activity` (name, description, isOfficial) VALUES ('Is your workspace just bad?', 'Public talk from show speaker "Ryan David" about the dangers of your working environment, along with improvements to make. ', true);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (5, 2);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (5, 4);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (5, 8);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (5, 10);

INSERT INTO `developmenttoolkit`.`activity` (name, description, isOfficial) VALUES ('Agile Development Best practice', 'PDF download on following best practice on participating in an Agile project', true);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (6, 15);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (6, 16);

INSERT INTO `developmenttoolkit`.`activity` (name, description, isOfficial) VALUES ('Fostering a better online classroom', 'Zoom meeting on how to make best of the tools available for assisting students during remote learning', true);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (7, 4);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (7, 10);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (7, 11);

INSERT INTO `developmenttoolkit`.`activity` (name, description, isOfficial) VALUES ('Student - Master meet-up', 'Self-organised meeting for students to garner higher education information from MsC students', true);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (8, 3);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (8, 4);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (8, 9);

INSERT INTO `developmenttoolkit`.`activity` (name, description, isOfficial) VALUES ('How to use GIT remotely', 'Basic lecturer tutorial in applying git with remote work.', true);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (9, 2);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (9, 14);

INSERT INTO `developmenttoolkit`.`activity` (name, description, isOfficial) VALUES ('Agile Development Best Practice #2', 'PDF download on additional practices on participating in an Agile project', true);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (10, 15);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (10, 16);

INSERT INTO `developmenttoolkit`.`activity` (name, description, isOfficial) VALUES ('Scrum Master Meeting', 'A meeting between like-minded group members about our project', false );
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (11, 18);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (11, 29);

INSERT INTO `developmenttoolkit`.`activity` (name, description, isOfficial) VALUES ('Lecturer Coffee Group', 'Meeting of all lecturers about how we find training is going', false );
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (12, 19);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (12, 20);

INSERT INTO `developmenttoolkit`.`activity` (name, description, isOfficial) VALUES ('How to teach for Dummies', 'A book I found in the Library about teaching basics', false );
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (13, 25);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (13, 32);

INSERT INTO `developmenttoolkit`.`activity` (name, description, isOfficial) VALUES ('Spoke to Dave', 'Conversation with dave about our work ethics', false );
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (14, 20);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (14, 22);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (14, 29);

INSERT INTO `developmenttoolkit`.`activity` (name, description, isOfficial) VALUES ('Spoke to upper management', 'Meeting my boss to discuss where I went wrong in latest lecture', false );
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (15, 21);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (15, 26);
INSERT INTO `developmenttoolkit`.`objective` (Activity_activityID, Tag_tagID) VALUES (15, 31);

-- Add david112 to 8 official activities--
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201210',2, 1, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201210',2, 2, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201211',2, 3, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201211',2, 4, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201213',2, 5, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201215',2, 6, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201215',2, 7, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201216',2, 8, 'Participant');

INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201215',2, 11, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201216',2, 12, 'Participant');

-- add random users to activity 1 --
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201211',5, 1, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201211',6, 1, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201211',7, 1, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201211',8, 1, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201211',9, 1, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201211',10, 1, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201211',11, 1, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201211',12, 1, 'Participant');

-- add user to 3 activities and 2 unofficial activities--

INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201211',4, 2, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201211',4, 5, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201211',4, 9, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201211',4, 14, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201211',4, 15, 'Participant');

-- Add karen to a few activities --

INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201211',1, 1, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201211',1, 7, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201211',1, 8, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201211',1, 9, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201211',1, 10, 'Participant');
INSERT INTO `developmenttoolkit`.`participation` (date, siteUser_userID, Activity_activityID, Role_roleID) VALUES ('20201211',1, 13, 'Participant');

-- Add some reflections that David has made --

INSERT INTO `developmenttoolkit`.`reflection`(Participation_participationID, Tag_tagID, Reflect_what, Reflect_prompt, Reflect_happen, Reflect_eval,Reflect_diff, Reflect_lp,isPublic, rating )
VALUES (1, 3, 'Attended a webinar regarding how to reflect on student submissions',
'I was pondering about my lack of knowledge in this skill, and activity attempted to find an activity that bettered my improvement',
'The lecture happened, the speaker was amazing in their delivery and gave really helpful points. A powerpoint breakdown was also sent afterwards containing details',
'I can use the pointers given to take a step back on my reflection skills and think "what comments actually benefit student learning journeys?"',
'Nothing, this lecture was incredibly good!',
'1: take a step back. 2: read thoroughly through criteria 3: provide constructive comments that directly link to criteria, rather than pick holes in writing.',
true, 5);

INSERT INTO `developmenttoolkit`.`reflection`(Participation_participationID, Tag_tagID, Reflect_what, Reflect_prompt, Reflect_happen, Reflect_eval,Reflect_diff, Reflect_lp,isPublic, rating )
VALUES (24, 3, 'Attended a webinar regarding how to reflect on student submissions',
'My colleague asked me to attend since he saw I was struggling at this',
'prompt lecture, delivered fast and done within a 1/2 hour timeframe!',
'A few good points here and there, but a lot of generalised knowledge that I had already. Still, was nice to hear some confirmation of my methods being used are in the right ballpark.',
'Maybe some more specific material that I could use?',
'That i should just keep doing what I already am doing.',
true, 3);

INSERT INTO `developmenttoolkit`.`reflection`(Participation_participationID, Tag_tagID, Reflect_what, Reflect_prompt, Reflect_happen, Reflect_eval,Reflect_diff, Reflect_lp,isPublic, rating )
VALUES (11, 3, 'Attended a webinar regarding how to reflect on student submissions',
'Sounded interesting',
'Lecturer went a bit fast, I was struggling to keep up',
'Tons of information to take away, and I am happy we were provided the powerpoint notes after so I will not need to write up',
'Listen rather than write notes!',
'Lots of things, too many to list here...',
true, 4);
