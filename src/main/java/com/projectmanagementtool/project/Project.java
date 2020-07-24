/* 
*  Project.java - this file implements the Project class, which contains details about 
*  a particular project, including the members and tasks within it.
*/

package com.rtb.projectmanagementtool.task;

import java.util.HashSet;

public class Project {
    private long ID;
    private String name;
    private String description;
    private HashSet<Long> adminUserIDs;
    private HashSet<Long> regularUserIDs;
    private HashSet<Long> taskIDs;

    /**
    * Class constructor with the minimum requirements for creating a project.
    */
    public void Project(long projectID, String name, String description)  {
        this.ID = projectID;
        this.name = name;
        this.description = description;
        this.adminUserIDs = new HashSet<Long>();
        this.regularUserIDs = new HashSet<Long>();
        this.taskIDs = new HashSet<Long>();
    }

    /**
    * @return project ID
    */
    public long getID() {
        return this.ID;
    }

    /**
    * @return project name
    */
    public String getName() {
        return this.name;
    }

    /**
    * @return project description
    */
    public String getDescription() {
        return this.description;
    }

    /**
    * Adds a user to the project
    * @param isAdmin    is this user an admin?
    * @param userID   ID of the user to add
    */
    public void addUser(boolean isAdmin, long userID) {
        if (isAdmin) {
            this.adminUserIDs.add(userID);
        } else {
            this.regularUserIDs.add(userID);
        }
    }

    /**
    * Removes a user from the project
    * @param isAdmin    is this user an admin?
    * @param userID   ID of the user to remove
    */
    public void removeUser(boolean isAdmin, long userID) {
        if (isAdmin) {
            this.adminUserIDs.remove(userID);
        } else {
            this.regularUserIDs.remove(userID);
        }
    }

    /**
    * Adds a task to the project
    * @param taskID   ID of the task to add
    */
    public void addTask(long taskID) {
        this.taskIDs.add(taskID);
    }

    /**
    * Removes a task from the project
    * @param taskID   ID of the task to remove 
    */
    public void removeTask(long taskID) {
        this.taskIDs.remove(taskID);
    }

    /**
    * @return the string representation of this project.
    * TODO: implement
    */
    @Override
    public String toString() { 
        return "";
    } 
}