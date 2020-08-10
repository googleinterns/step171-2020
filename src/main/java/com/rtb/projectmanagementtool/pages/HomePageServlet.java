/** Servlet responsible getting data for loading home page */
package com.rtb.projectmanagementtool.pages;

import com.google.appengine.api.datastore.*;
import com.google.gson.Gson;
import com.rtb.projectmanagementtool.auth.*;
import com.rtb.projectmanagementtool.project.*;
import com.rtb.projectmanagementtool.task.*;
import com.rtb.projectmanagementtool.user.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomePageServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    // Initialize controllers
    ProjectController projectController = new ProjectController(datastore);
    TaskController taskController = new TaskController(datastore);
    UserController userController = new UserController(datastore);
    AuthOps auth = new AuthOps(userController);

    // Get user
    Long userId = 2l;
    /**
     * TODO: change to Long userId = auth.whichUserLoggedIn(request, response); I'm not entirely
     * sure how authentication works yet
     */
    UserData user = userController.getUserByID(userId);

    // Get user projects
    ArrayList<ProjectData> userProjects = projectController.getProjectsWithUser(userId);

    // Get user tasks
    ArrayList<TaskData> userTasks = taskController.getTasksByUserID(userId);

    response.setContentType("application/json;");
    response.getWriter().println(getJsonForResponse(user, userProjects, userTasks));
  }

  public String getJsonForResponse(
      UserData user, ArrayList<ProjectData> userProjects, ArrayList<TaskData> userTasks) {
    Gson gson = new Gson();
    String json = "{";
    json += "\"user\":";
    json += "\"" + gson.toJson(user) + "\",";
    json += "\"userProjects\":";
    json += "\"" + gson.toJson(userProjects) + "\",";
    json += "\"userTasks\":";
    json += "\"" + gson.toJson(userTasks) + "\"";
    json += "}";
    return json;
  }
}
