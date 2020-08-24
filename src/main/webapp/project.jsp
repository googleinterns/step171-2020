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
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.14.0/css/all.css">
    <script defer src="scripts/project.js"></script>
  </head>
  <body>
    <!-- Include navigation bar -->
    <jsp:include page="navigation-bar.jsp" />

    <!-- Include modals -->
    <jsp:include page="add-user-to-project-modal.jsp" />
    <jsp:include page="message-modal.jsp" />

    <div class="content">
      <div class="project-page-top-bar">
        <div class="project-page-header">
          <div class="page-header-title-and-actions">
            <h1><%=project.getName()%></h1>
            <div class="page-header-actions-selector" >
              <button onclick="showActions()"><i id="angle-down" class="fas fa-angle-down"></i></button>
              <div class="page-header-actions">
                <ul>
                  <li class="action-list-item"><a href="javascript:toggleDescription()"><i class="fas fa-info"></i><p>Toggle description</p></a></li>
                  <li class="action-list-item"><a href="#"><i class="fas fa-edit"></i><p>Edit project details</p></a></li>
                  <li class="action-list-item"><a href="#"><i class="fas fa-check"></i><p>Complete project</p></a></li>
                  <li class="action-list-item"><a href="#"><i class="far fa-trash-alt"></i><p>Delete project</p></a></li>
                </ul>
              </div>
            </div>
          </div>
          <div class="page-header-description">
            <p><%=project.getDescription()%></p>
          </div>
          <div class="page-header-nav">
            <div class="project-header-tab tasks active">
              <i class="fas fa-tasks"></i>
              <p>Tasks</p>
            </div>
            <div class="project-header-tab users">
              <i class="fas fa-users"></i>
              <p>Users</p>
            </div>
          </div>
        </div>
      </div>
    

    <!-- Display tasks -->
      <div class="project-section tasks active">
        <%request.setAttribute("tasks", tasks);%>
      <div class="project-addtask-container">
        <button type="button" class="deep-button" onclick="location.href='add-task.jsp?projectID=<%=project.getId()%>&projectName=<%=project.getName()%>&taskID=0&taskName=null'">
          Add Task
        </button>
      </div>
      <jsp:include page="list-tasks.jsp"/>
      </div>
      
      <!-- Display users -->
      <div class="project-section users">
        <% if (project.hasAdmin(userId) || project.isCreator(userId)) { %>
        <button id="add-user-button">add user</button>
        <% } %>
        <p>creator: <%=creator.getUserName()%></p>
        <% for (UserData user : admins) { %>
        <p>admin: <%=user.getUserName()%></p>
        <% } %>
        <% for (UserData user : members) { %>
        <p>member: <%=user.getUserName()%></p>
        <% } %>
      </div>
    </div>
  </body>
</html>
