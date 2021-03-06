--   Copyright 2009 TiTA Project, Vienna University of Technology
--   
--   Licensed under the Apache License, Version 2.0 (the "License");
--   you may not use this file except in compliance with the License.
--   You may obtain a copy of the License at
--   
--       http://www.apache.org/licenses/LICENSE\-2.0
--       
--   Unless required by applicable law or agreed to in writing, software
--   distributed under the License is distributed on an "AS IS" BASIS,
--   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
--   See the License for the specific language governing permissions and
--   limitations under the License.


--insert into CONV_ISSUE_TRACKER (ID, DESCRIPTION, URL) values (1, 'it 1', 'www.mantis.com');
insert into CONV_ISSUE_TRACKER (ID, DESCRIPTION, URL) values (1, 'it 1', 'http://localhost/mantisbt-1.1.8');

insert into CONV_ROLE (ID, DESCRIPTION) values (1, 'Administrator');
insert into CONV_ROLE (ID, DESCRIPTION) values (2, 'Time consumer');
insert into CONV_ROLE (ID, DESCRIPTION) values (3, 'Time controller');

insert into CONV_PROJECT_STATUS (ID, DESCRIPTION) values (1, 'stat 1');

INSERT INTO tita_user (id, deleted, email, firstname, lastname, modification_version, password, username, role_id)
 VALUES (5, false, NULL, 'admin', 'admin', 0, 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', 'admin', 1);
INSERT INTO tita_user (id, deleted, email, firstname, lastname, modification_version, password, username, role_id)
 VALUES (6, false, NULL, 'timeconsumer', 'timeconsumer', 0, '/FsJXhs6B+gZPRMpQKJiGQtu1lPCNfWlkhZntCoeico=', 'timeconsumer', 2);
INSERT INTO tita_user (id, deleted, email, firstname, lastname, modification_version, password, username, role_id)
 VALUES (7, false, NULL, 'timecontroller', 'timecontroller', 0, 'qVJE9TtwMPxRRMXFfINXke/8IKAjhy5NEVuplbR6MDU=', 'timecontroller', 3);

insert into TITA_PROJECT (ID, DESCRIPTION, NAME, DELETED, STATUS_ID, MODIFICATION_VERSION)
values (1, 'nix', 'tita_test', false, 1, 0);

insert into TITA_PROJECT (ID, DESCRIPTION, NAME, DELETED, STATUS_ID, MODIFICATION_VERSION)
values (2, 'nix', 'name2', false, 1, 0);

--insert into ISSUE_TRACKER_PROJECT (ID, TITA_PROJECT_ID, ISST_ID, ISST_PROJECT_ID, MODIFICATION_VERSION, PROJECT_NAME)
--values (1,1,1,358,0, 'tita_test');
insert into ISSUE_TRACKER_PROJECT (ID, TITA_PROJECT_ID, ISST_ID, ISST_PROJECT_ID, MODIFICATION_VERSION, PROJECT_NAME)
values (2,1,1,222,0, 'tita-business');

insert into TITA_TASK (ID, DESCRIPTION, USER_ID, TITA_PROJECT_ID, MODIFICATION_VERSION) values
(1, 'titaTask 1', 6, 1, 0);
insert into TITA_TASK (ID, DESCRIPTION, USER_ID, TITA_PROJECT_ID, MODIFICATION_VERSION) values
(2, 'titaTask 2', 6, 1, 0);

--insert into ISSUE_TRACKER_TASK (ID, ISSUE_TRACKER_PROJECT_ID, ISST_TASK_ID, MODIFICATION_VERSION) values
--(1,1,43, 0);
--insert into ISSUE_TRACKER_TASK (ID, ISSUE_TRACKER_PROJECT_ID, ISST_TASK_ID, MODIFICATION_VERSION) values
--(2,2,44, 0);

insert into EFFORT (ID, DESCRIPTION, TITA_TASK_ID, ISSUET_TASK_ID, DATE, DURATION, DELETED, USER_ID, START_TIME, END_TIME, COST_CENTER)
values (1, 'e1', 1, null, '2009-10-12', 2000000, false, 6, 2000000, 4000000, 'W580');
insert into EFFORT (ID, DESCRIPTION, TITA_TASK_ID, ISSUET_TASK_ID, DATE, DURATION, DELETED, USER_ID, START_TIME, END_TIME, COST_CENTER)
values (2, 'e2', 1, null, '2009-03-24', 2000000, false, 6, 2000000, 4000000, 'W580');

insert into EFFORT (ID, DESCRIPTION, TITA_TASK_ID, ISSUET_TASK_ID, DATE, DURATION, DELETED, USER_ID, START_TIME, END_TIME, COST_CENTER)
values (3, 'e3', 2, null, '2009-07-09', 2000000, false, 6, 2000000, 4000000, 'W530');
insert into EFFORT (ID, DESCRIPTION, TITA_TASK_ID, ISSUET_TASK_ID, DATE, DURATION, DELETED, USER_ID, START_TIME, END_TIME, COST_CENTER)
values (4, 'e4', 2, null, '2009-05-17', 2000000, false, 6, 2000000, 4000000, 'W530');
insert into EFFORT (ID, DESCRIPTION, TITA_TASK_ID, ISSUET_TASK_ID, DATE, DURATION, DELETED, USER_ID, START_TIME, END_TIME, COST_CENTER)
values (9, 'e9', 2, null, '2009-05-18', 3000000, false, 6, 2000000, 5000000, 'W530');

insert into EFFORT (ID, DESCRIPTION, TITA_TASK_ID, ISSUET_TASK_ID, DATE, DURATION, DELETED, USER_ID, START_TIME, END_TIME, COST_CENTER)
values (5, 'e5', 1, null, '2009-01-12', 2000000, false, 6, 2000000, 4000000, 'B3345');
insert into EFFORT (ID, DESCRIPTION, TITA_TASK_ID, ISSUET_TASK_ID, DATE, DURATION, DELETED, USER_ID, START_TIME, END_TIME, COST_CENTER)
values (6, 'e6', 1, null, '2009-11-18', 2000000, false, 6, 2000000, 4000000, 'B3345');

insert into EFFORT (ID, DESCRIPTION, TITA_TASK_ID, ISSUET_TASK_ID, DATE, DURATION, DELETED, USER_ID, START_TIME, END_TIME, COST_CENTER)
values (7, 'e7', 2, null,  '2009-06-29', 2000000, false, 6, 2000000, 4000000, 'I3345');
insert into EFFORT (ID, DESCRIPTION, TITA_TASK_ID, ISSUET_TASK_ID, DATE, DURATION, DELETED, USER_ID, START_TIME, END_TIME, COST_CENTER)
values (8, 'e8', 2, null, '2009-10-24', 2000000, false, 6, 2000000, 4000000, 'I3345');
insert into EFFORT (ID, DESCRIPTION, TITA_TASK_ID, ISSUET_TASK_ID, DATE, DURATION, DELETED, USER_ID, START_TIME, END_TIME, COST_CENTER)
values (10, 'e10', 2, null ,'2009-05-18', 3000000, false, 6, 2000000, 5000000, 'I3345');

insert into USER_PROJECT (ID, USER_ID, TITA_PROJECT_ID, TARGET_HOURS) values (1, 7, 1, 130);
insert into USER_PROJECT (ID, USER_ID, TITA_PROJECT_ID, TARGET_HOURS) values (2, 6, 1, 140);
insert into USER_PROJECT (ID, USER_ID, TITA_PROJECT_ID, TARGET_HOURS) values (4, 6, 2, 150);

-- for testing mantis
insert into isst_login (id, modification_version, password, name, isst_id, user_id)
values (1,0,'root','administrator',1,6);
insert into isst_login (id, modification_version, password, name, isst_id, user_id)
values (2,0,'root','administrator',1,7);
--insert into ISSUE_TRACKER_TASK (ID, ISSUE_TRACKER_PROJECT_ID, ISST_TASK_ID, MODIFICATION_VERSION) values
--(3,2,439, 0);
--insert into EFFORT (ID, DESCRIPTION, TITA_TASK_ID, ISSUET_TASK_ID, DATE, DURATION, DELETED, USER_ID, START_TIME, END_TIME)
--values (11, 'e11', null, 3, '2010-01-06', 2000000, false, 6, 2000000, 5000000);
--insert into ISSUE_TRACKER_TASK (ID, ISSUE_TRACKER_PROJECT_ID, ISST_TASK_ID, MODIFICATION_VERSION) values
--(4,2,440, 0);
--insert into EFFORT (ID, DESCRIPTION, TITA_TASK_ID, ISSUET_TASK_ID, DATE, DURATION, DELETED, USER_ID, START_TIME, END_TIME)
--values (13, 'e12', null, 4, '2010-01-07', 2000000, false, 6, 2000000, 5000000);
