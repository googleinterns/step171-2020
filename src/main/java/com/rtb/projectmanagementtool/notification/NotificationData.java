package com.rtb.projectmanagementtool.notification;

import com.google.appengine.api.datastore.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class NotificationData {
  private static final String PROPERTY_MESSAGE = "message";
  private static final String PROPERTY_TIME_STAMP = "timeStamp";
  private static final String PROPERTY_USERS = "users";

  private long id;
  private String message;
  private Date timeStamp;
  private HashSet<Long> users; // users to display this notification for

  public NotificationData(String message, HashSet<Long> users) {
    this.message = message;
    this.users = users;
    this.timeStamp = new Date();
  }

  public NotificationData(Entity entity) {
    this.id = (Long) entity.getKey().getId();
    this.message = (String) entity.getProperty(PROPERTY_MESSAGE);
    this.timeStamp = (Date) entity.getProperty(PROPERTY_TIME_STAMP);

    Collection entityProperty = (Collection) entity.getProperty(PROPERTY_USERS);
    if (entityProperty != null) {
      this.users = new HashSet<Long>((ArrayList<Long>) entityProperty);
    } else {
      this.users = new HashSet<Long>();
    }
  }

  /** @return the entity representation of this class */
  public Entity toEntity() {
    Entity entity;
    if (this.id != 0) {
      entity = new Entity("Notification", this.id);
    } else {
      entity = new Entity("Notification");
    }
    entity.setProperty(PROPERTY_MESSAGE, this.message);
    entity.setProperty(PROPERTY_TIME_STAMP, this.timeStamp);
    entity.setProperty(PROPERTY_USERS, this.users);
    return entity;
  }

  /** @return notification id */
  public long getId() {
    return this.id;
  }

  /** @return notification message */
  public String getMessage() {
    return this.message;
  }

  /** @return notification time stamp */
  public Date getTimeStamp() {
    return this.timeStamp;
  }

  /** @return users receiving notification */
  public HashSet<Long> getUsers() {
    return this.users;
  }

  /**
   * Set the id of this notification
   *
   * @param id the new id of the notification
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Set the message of notification
   *
   * @param message the new message of the project
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Set the timeStamp of this project
   *
   * @param timeStamp Date object representing new timeStamp
   */
  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }

  /**
   * Add a user to receive the notification
   *
   * @return true if operation is successful
   */
  public boolean addUser(Long userId) {
    return this.users.add(userId);
  }

  // ProjectData returns users with HashSet
  public void addUsers(HashSet<Long> userIds) {
    for (Long userId : userIds) {
      addUser(userId);
    }
  }

  // TaskData returns users with ArrayList
  public void addUsers(ArrayList<Long> userIds) {
    addUsers(new HashSet<Long>(userIds));
  }

  /**
   * Removes a user from notification
   *
   * @return true if operation successful
   */
  public boolean removeUser(Long userId) {
    return this.users.remove(userId);
  }

  /**
   * @param userId the userId
   * @return true if the user is in notification
   */
  public boolean hasUser(long userId) {
    return this.users.contains(userId);
  }

  public static boolean equals(NotificationData a, NotificationData b) {
    return a.getId() == b.getId()
        && a.getMessage().equals(b.getMessage())
        && a.getTimeStamp().equals(b.getTimeStamp())
        && a.getUsers().equals(b.getUsers());
  }

  @Override
  public boolean equals(Object other) {
    return other instanceof NotificationData && equals(this, (NotificationData) other);
  }

  /** @return the string representation of this class. */
  @Override
  public String toString() {
    String returnString = "{\n";
    returnString += "\tNotification id: " + this.id + "\n";
    returnString += "\tMessage: " + this.message + "\n";
    returnString += "\tTime stamp: " + this.timeStamp + "\n";
    returnString += "\tUsers: " + this.users + "\n";
    returnString += "}";
    return returnString;
  }
}
