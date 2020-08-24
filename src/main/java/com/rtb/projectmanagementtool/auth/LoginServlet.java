// Servlet for loading login page

package com.rtb.projectmanagementtool.auth;

import com.google.appengine.api.datastore.*;
import com.rtb.projectmanagementtool.user.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    AuthOps auth = new AuthOps(datastore);
    
    String authID = auth.getAuthID();
    if (authID != null) {
      auth.loginUser(request, response);
      UserController userController = new UserController(datastore);
      UserData user = userController.getUserByAuthID(authID);

      if (userController.getUserByAuthID(authID) == null) {
        response.sendRedirect("/create-user");
      } else {
        response.sendRedirect("/home");
      }
    }

    // Get login URL
    request.setAttribute("loginUrl", auth.getLoginLink(/*Return URL*/ "/login"));

    // Forward to login page
    request.getRequestDispatcher("login.jsp").forward(request, response);
    return;
  }
}
