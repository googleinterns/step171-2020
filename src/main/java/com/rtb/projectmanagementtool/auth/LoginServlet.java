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

    // Don't view login page if user is logged in
    if (auth.whichUserIsLoggedIn(request, response) != Long.parseLong(AuthOps.NO_LOGGED_IN_USER)) {
      response.sendRedirect("/home");
      return;
    }

    // if user successfully logged in but isn't in database, redirect to create user page
    // 'do' param indicates that /login was called after a user a user logged in with Google
    if (auth.loginUser(request, response) == Long.parseLong(AuthOps.NO_LOGGED_IN_USER)) {
      response.sendRedirect("/create-user");
      return;
    }

    // Get login URL
    request.setAttribute("loginUrl", auth.getLoginLink(/*Return URL*/ "/login?do"));

    // Forward to login page
    request.getRequestDispatcher("login.jsp").forward(request, response);
  }
}
