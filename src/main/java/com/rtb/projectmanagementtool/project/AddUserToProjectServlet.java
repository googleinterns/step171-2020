/** Servlet responsible for adding users to a project */
package com.rtb.projectmanagementtool.project;

import com.google.appengine.api.datastore.*;
import com.rtb.projectmanagementtool.user.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/add-user-to-project")
public class AddUserToProjectServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");

    // initialize controllers
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    ProjectController projectController = new ProjectController(datastore);
    UserController userController = new UserController(datastore);

    // get request parameters
    Long projectId = Long.parseLong(request.getParameter("project"));
    String userName = request.getParameter("userName");
    String userRole = request.getParameter("userRole");

    // create objects
    ProjectData project = projectController.getProjectById(projectId);
    UserData user = userController.getUserByName(userName);

    // if user is not in database or they are already in the project,
    // redirect back to project page
    if (user == null) {
      response
          .getWriter()
          .println(generateResponseForFailedOperation(/*message*/ "User not found."));
      return;
    }

    if (project.hasUser(user.getUserID())) {
      response
          .getWriter()
          .println(generateResponseForFailedOperation(/*message*/ "User already in project."));
      return;
    }

    // user found and not in the project; add them
    switch (userRole) {
      case "admin":
        project.addAdminUser(user.getUserID());
        break;
      case "member":
        project.addMemberUser(user.getUserID());
        break;
    }

    // save project to database
    projectController.addProject(project);

    // Redirect to project page
    response
        .getWriter()
        .println(
            generateResponse(
                /*message*/ user.getUserName() + " successfully added to project",
                user.getUserID()));
  }

  // Method generates a json for servlet response
  public String generateResponse(String message, Long userId) {
    String response = "{";
    response += "\"message\": ";
    response += "\"" + message + "\"";
    if (userId != null) {
      response += ", ";
      response += "\"userId\": ";
      response += "\"" + userId + "\"";
    }
    response += "}";
    return response;
  }

  // Method generates a json for servlet response
  public String generateResponseForFailedOperation(String message) {
    return generateResponse(message, /*userId*/ null);
  }
}
