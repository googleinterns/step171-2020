package com.rtb.projectmanagementtool.notification;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NotificationDataTest {
  private final LocalServiceTestHelper helper =
      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

  private final String PROPERTY_MESSAGE = "message";
  private final String PROPERTY_TIME_STAMP = "timeStamp";
  private final String PROPERTY_USERS = "users";

  private final String MESSAGE = "Notification message";
  private final HashSet<Long> USERS = new HashSet<Long>(Arrays.asList(1l, 2l, 3l));

  @Before
  public void setUp() {
    helper.setUp();
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

  @Test
  public void createNotification() {
    NotificationData notification = new NotificationData(MESSAGE, USERS);

    Assert.assertEquals("notification message is correct", notification.getMessage(), MESSAGE);
    Assert.assertEquals("notification users is correct", notification.getUsers(), USERS);
  }

  @Test
  public void createNotificationFromEntity() {
    final Date TIME_STAMP = new Date();
    // Create entity
    Entity entity = new Entity("Notification");
    entity.setProperty(PROPERTY_MESSAGE, MESSAGE);
    entity.setProperty(PROPERTY_TIME_STAMP, TIME_STAMP);
    entity.setProperty(PROPERTY_USERS, new ArrayList<Long>(Arrays.asList(1l, 2l)));

    // Create notification
    NotificationData notification = new NotificationData(entity);

    Assert.assertEquals(
        "notification messages match",
        entity.getProperty(PROPERTY_MESSAGE),
        notification.getMessage());
    Assert.assertEquals(
        "notification times match",
        entity.getProperty(PROPERTY_TIME_STAMP),
        notification.getTimeStamp());
    Assert.assertEquals(
        "notification users match",
        new HashSet<Long>((ArrayList<Long>) entity.getProperty(PROPERTY_USERS)),
        notification.getUsers());
  }

  @Test
  public void createEntityFromNotification() {
    // Create notification object
    NotificationData notification = new NotificationData(MESSAGE, USERS);

    // Create entity from notification
    Entity entity = notification.toEntity();

    // Assert fields are correct
    Assert.assertEquals(
        "message is correct in entity", MESSAGE, (String) entity.getProperty(PROPERTY_MESSAGE));
    Assert.assertEquals(
        "timestamp is correct in entity",
        notification.getTimeStamp(),
        (Date) entity.getProperty(PROPERTY_TIME_STAMP));
    Assert.assertEquals(
        "users container is accurate in entity",
        notification.getUsers(),
        (HashSet<Long>) entity.getProperty(PROPERTY_USERS));
  }

  @Test
  public void addUser() {
    NotificationData notification = new NotificationData(MESSAGE, USERS);
    notification.addUsers(USERS);

    Assert.assertTrue(notification.hasUser(1l));
    Assert.assertTrue(notification.hasUser(2l));
  }

  @Test
  public void removeUser() {
    NotificationData notification = new NotificationData(MESSAGE, USERS);
    notification.addUsers(USERS);
    notification.removeUser(2l);

    Assert.assertTrue(notification.hasUser(1l));
    Assert.assertFalse(notification.hasUser(2l));
  }
}
