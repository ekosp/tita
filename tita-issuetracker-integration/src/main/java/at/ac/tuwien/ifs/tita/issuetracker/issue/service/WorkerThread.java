/**
   Copyright 2009 TiTA Project, Vienna University of Technology
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE\-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package at.ac.tuwien.ifs.tita.issuetracker.issue.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.ifs.tita.entity.IssueTrackerLogin;
import at.ac.tuwien.ifs.tita.issuetracker.interfaces.IIssueTrackerDao;
import at.ac.tuwien.ifs.tita.issuetracker.interfaces.IProjectTrackable;
import at.ac.tuwien.ifs.tita.issuetracker.interfaces.ITaskTrackable;
import at.ac.tuwien.ifs.tita.issuetracker.mantis.dao.IssueTrackerMantisDao;

/**
 * The worker thread updates the tasks from a single project using the
 * MantisDao.
 *
 * @author Christoph
 *
 */
public class WorkerThread extends Thread {

    private final IProjectTrackable project;
    private final IIssueTrackerDao dao;

    private final Logger log = LoggerFactory.getLogger(WorkerThread.class);

    /**
     * Constructs a worker thread with a project.
     *
     * @param project - project, from where the tasks should be fetched from
     *        mantis.
     */
    public WorkerThread(IProjectTrackable project, IssueTrackerLogin login) {
        super("Worker");
        this.dao = new IssueTrackerMantisDao(login);
        this.project = project;
    }

    public WorkerThread(Long projectId, IssueTrackerLogin login) {
        super("Worker");
        this.dao = new IssueTrackerMantisDao(login);
        this.project = this.dao.findProject(projectId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        this.log.info("Fetching all Tasks for the chosen project.");
        Map<Long, ITaskTrackable> tasklist = this.dao.findAllTasksForProject(this.project.getId());

        for (ITaskTrackable task : tasklist.values()) {
            task.setProject(this.project);
        }

        this.project.setTasks(tasklist);
        this.log.info("Stopping the worker thread.");
        this.stop();
    }

}
