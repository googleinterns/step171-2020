<%@ page import="com.rtb.projectmanagementtool.project.*" %>
<%@ page import="com.rtb.projectmanagementtool.user.*" %>
<%@ page import="com.rtb.projectmanagementtool.task.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashSet" %>

<%
    Long userId = (Long) request.getAttribute("userId");
    ProjectData project = (ProjectData) request.getAttribute("project");
    UserData creator = (UserData) request.getAttribute("creator");
    HashSet<UserData> admins =  (HashSet<UserData>) request.getAttribute("admins");
    HashSet<UserData> members =  (HashSet<UserData>) request.getAttribute("members");
    ArrayList<TaskData> tasks = (ArrayList<TaskData>) request.getAttribute("tasks");
%>

<html>
  <head>
    <meta charset="UTF-8">
    <title><%=project.getName()%></title>
    <link rel="stylesheet" href="style.css">
    <script defer src="scripts/project.js"></script>
  </head>
  <body>
    <div class="content">
    <!-- Include navigation bar -->
    <jsp:include page="navigation-bar.jsp" />

    <!-- Include modals -->
    <jsp:include page="add-user-to-project-modal.jsp" />
    <jsp:include page="message-modal.jsp" />

    <!-- Page content -->
      <div id="project-title-container"><h1><%=project.getName()%></h1></div>
      <div id="project-description-container">
        <p><%=project.getDescription()%></p>
      </div>
      
      <div id="project-users-container">
        <h2>Users</h2>
        <a href="user-profile?userID=<%=creator.getUserID()%>">
        <p>creator: 
            <%if (userId == creator.getUserID()) { %> 
              <mark>
            <% } %>
            <%=creator.getUserName() %>
            <%if (userId == creator.getUserID()) { %> 
              </mark>
            <% } %>
        </p>
        </a>
        <%for (UserData user : admins) {%>
        <a href="user-profile?userID=<%=user.getUserID()%>">
          <p>admin: 
            <%if (userId == user.getUserID()) { %> 
              <mark>
            <% } %>
              <%=user.getUserName() %>
            <%if (userId == user.getUserID()) { %> 
              </mark>
            <% } %>
          </p>
        </a>
        <%}%>
        <%for (UserData user : members) {%>
        <a href="user-profile?userID=<%=user.getUserID()%>">
          <p>member: 
            <%if (userId == user.getUserID()) { %> 
              <mark>
            <% } %>
              <%=user.getUserName() %>
            <%if (userId == user.getUserID()) { %> 
              </mark>
            <% } %>
          </p>
        </a>
        <%}%>
      </div>
      <% if (project.isCreator(userId) || project.hasAdmin(userId)) { %>
          <button id="add-user-button">Add user</button>
        <% } %>
      <div id="project-tasks-container">
        <h2>Tasks</h2>
        <%request.setAttribute("tasks", tasks);%>
        <jsp:include page="list-tasks.jsp"/>
      </div>
      <div id="project-addtask-container">
        <button type="button" class="deep-button" onclick="location.href='add-task.jsp?projectID=<%=project.getId()%>&projectName=<%=project.getName()%>&taskID=0&taskName=null'">
          Add Task
        </button>
      </div>
    </div>
  </body>
</html>
