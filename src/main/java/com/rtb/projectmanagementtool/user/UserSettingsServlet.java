/** Servlet responsible for user_settings */
package com.rtb.projectmanagementtool.user;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.*;
import com.rtb.projectmanagementtool.task.*;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user-settings")
public class UserSettingsServlet extends HttpServlet {
  DatastoreService datastore;

  public UserSettingsServlet() {
    datastore = DatastoreServiceFactory.getDatastoreService();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // taken from @Godsfavour. Will implement once auth is ready
    // Authentication goes here
    // if (something something something) {
    //   // Redirect to /login servlet if authentication fails
    //   request.getRequestDispatcher("/login").forward(request, response);
    //   return;
    // }

    // HARDCODE FOR TESTING

    // initialize/fill hardcoded userMajors
    ArrayList<String> majors = new ArrayList<>();
    majors.add("Chemistry");
    majors.add("Studio Art");

    // Create hardcoded user
    long userID = Long.parseLong(request.getParameter("userID"));
    String AuthID = "abc";
    String userName = "Name1";
    long userYear = 2023;
    ArrayList<String> userMajors = majors;
    Skills skills = Skills.OOP;
    long userTotalCompTasks = 3;
    UserData user =
        new UserData(AuthID, userName, userYear, userMajors, skills, userTotalCompTasks);
    user.setUserID(userID);

    // NON TESTING: ONCE EVERYTHING IS SET UP

    /**
     * // new UserController UserController userController = new UserController(datastore);
     *
     * <p>// get user by ID long userID = Long.parseLong(request.getParameter("userID")); user =
     * userController.getUserByID(userID);
     */
    // make a string of majors
    String majorsString = "";
    for (String major : user.getUserMajors()) {
      if (majorsString == "") {
        majorsString = major;
      } else {
        majorsString = majorsString + "," + major;
      }
    }
    majorsString = majorsString.replaceAll("\\s", "");

    // make enum ArrayList<String>
    Skills userSkills = user.getUserSkills();
    Skills[] skillsArray = userSkills.values();
    ArrayList<String> skillsStringList = new ArrayList<>();
    for (Skills skill : skillsArray) {
      skillsStringList.add(skill.name());
    }

    // Set attributes of request; retrieve in jsp with
    request.setAttribute("settings", user);
    request.setAttribute("skillsSettings", skillsStringList);
    request.setAttribute("majorsSettings", majorsString);

    // Load jsp for user page
    request.getRequestDispatcher("user-settings.jsp").forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // taken from @Godsfavour. Will implement once auth is ready
    // Authentication goes here
    // if (something something something) {
    //   // Redirect to /login servlet if authentication fails
    //   request.getRequestDispatcher("/login").forward(request, response);
    //   return;
    // }

    // get stuff from form update
    String userName = request.getParameter("userName");
    long userYear = Long.parseLong(request.getParameter("userYear"));
    String userMajorsString = request.getParameter("userMajors");
    String skillsString = request.getParameter("skills");
    long userTotalCompTasks = Long.parseLong(request.getParameter("total"));

    // convert back to ArrayList
    String[] majorsSA = userMajorsString.split(",");
    ArrayList<String> userMajors = new ArrayList(Arrays.asList(majorsSA));

    // convert back to enum (?)

  }
}
