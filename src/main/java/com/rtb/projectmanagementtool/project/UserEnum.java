/** UserEnum.java - this contains the User enum used in ProjectData */
package com.rtb.projectmanagementtool.project;

public enum UserEnum {
  CREATOR, // has admin capabilities and more, like deleting the project and add & remove admins
  ADMIN, // can add users to the project/perform other administrative tasks
  REGULAR;
}
