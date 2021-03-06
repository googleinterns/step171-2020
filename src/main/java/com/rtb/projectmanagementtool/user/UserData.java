package com.rtb.projectmanagementtool.user;

import com.google.appengine.api.datastore.Entity;
import java.util.*;
import java.util.UUID;

/**
 * Enum containing skills for user. enum Skills { NONE, LEADERSHIP, ORGANIZATION, WRITING, ART,
 * WEBDEV, OOP; }
 */

// Class for User data.
public class UserData {

  /** Enum containing skills for user. */
  public enum Skills {
    NONE,
    LEADERSHIP,
    ORGANIZATION,
    WRITING,
    ART,
    WEBDEV,
    OOP;
  }

  private long userID;
  private String AuthID; // this will be the ID from API
  private String inviteCode; // code used to add user to a project
  private String userName;
  private long userYear;
  private ArrayList<String> userMajors;
  private Skills skills;
  private long userTotalCompTasks;

  public UserData(
      long userID,
      String AuthID,
      String userName,
      long userYear,
      ArrayList<String> userMajors,
      Skills skills,
      long userTotalCompTasks) {
    this.userID = userID;
    this.AuthID = AuthID;
    this.userName = userName;
    this.userYear = userYear;
    this.userMajors = userMajors;
    this.skills = skills;
    this.userTotalCompTasks = userTotalCompTasks;
    setInviteCode();
  }

  public UserData(
      String AuthID,
      String userName,
      long userYear,
      ArrayList<String> userMajors,
      Skills skills,
      long userTotalCompTasks) {
    this.userID = 0;
    this.AuthID = AuthID;
    this.userName = userName;
    this.userYear = userYear;
    this.userMajors = userMajors;
    this.skills = skills;
    this.userTotalCompTasks = userTotalCompTasks;
    setInviteCode();
  }

  public UserData(long userID, String AuthID) {
    this.userID = userID;
    this.AuthID = AuthID;
    setInviteCode();
  }

  public UserData(Entity entity) {
    userID = (long) entity.getKey().getId();
    AuthID = (String) entity.getProperty("AuthID");
    inviteCode = (String) entity.getProperty("inviteCode");
    userName = (String) entity.getProperty("userName");
    userYear = (long) entity.getProperty("userYear");
    userMajors = (ArrayList<String>) entity.getProperty("userMajors");
    skills = Skills.valueOf((String) entity.getProperty("skills"));
    userTotalCompTasks = (long) entity.getProperty("userTotalCompTasks");
  }

  public Entity toEntity() {
    Entity entity;
    if (userID != 0) {
      entity = new Entity("User", userID);
    } else {
      entity = new Entity("User");
    }
    entity.setProperty("userID", entity.getKey().getId());
    entity.setProperty("AuthID", AuthID);
    entity.setProperty("inviteCode", inviteCode);
    entity.setProperty("userName", userName);
    entity.setProperty("userYear", userYear);
    entity.setProperty("userMajors", userMajors);
    entity.setProperty("skills", skills.name());
    entity.setProperty("userTotalCompTasks", userTotalCompTasks);
    return entity;
  }

  public long getUserID() {
    return userID;
  }

  public String getAuthID() {
    return AuthID;
  }

  public String getUserName() {
    return userName;
  }

  public long getUserYear() {
    return userYear;
  }

  public String getInviteCode() {
    return inviteCode;
  }

  public ArrayList<String> getUserMajors() {
    return userMajors;
  }

  public Skills getUserSkills() {
    return skills;
  }

  public long getUserTotal() {
    return userTotalCompTasks;
  }

  public void
      setInviteCode() { // can allow user to randomly update their invite code w/ this method
    this.inviteCode = UUID.randomUUID().toString().replaceAll("-", "");
  }

  public void setUserID(long userID) {
    this.userID = userID;
  }

  public void setAuthID(String AuthID) {
    this.AuthID = AuthID;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setUserYear(long userYear) {
    this.userYear = userYear;
  }

  public void setUserMajors(ArrayList<String> userMajors) {
    this.userMajors = userMajors;
  }

  public void setUserSkills(Skills skills) {
    this.skills = skills;
  }

  public void setUserTotal(long userTotalCompTasks) {
    this.userTotalCompTasks = userTotalCompTasks;
  }

  @Override
  public String toString() {
    String returnString = "{\n";
    returnString += "User ID: " + userID + "\n";
    returnString += "Auth ID: " + AuthID + "\n";
    returnString += "inviteCode: " + inviteCode + "\n";
    returnString += "User Name: " + userName + "\n";
    returnString += "Year: " + userYear + "\n";
    returnString += "Majors: " + userMajors.toString() + "\n";
    returnString += "Skills: " + skills.name() + "\n";
    returnString += "Completed Tasks: " + userTotalCompTasks + "\n}";
    return returnString;
  }

  public boolean equals(UserData a, UserData b) {
    return a.userID == b.userID
        && a.AuthID.equals(b.AuthID)
        && a.userName.equals(b.userName)
        && a.userYear == b.userYear
        && a.userMajors.equals(b.userMajors)
        && a.skills == b.skills
        && a.userTotalCompTasks == b.userTotalCompTasks;
  }

  @Override
  public boolean equals(Object other) {
    return other instanceof UserData && equals(this, (UserData) other);
  }
}
