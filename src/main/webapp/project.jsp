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
  <body onload="initEventListeners(); loadPageElements(<%=project.isComplete()%>);">
    <!-- Include navigation bar -->
    <jsp:include page="navigation-bar.jsp" />

    <!-- Page content -->
    <div class="project-page-top-bar">
    <div class="project-page-header">
        <div class="page-header-title-and-actions">
        <h1 id="main-project-name"><%=project.getName()%></h1>
        <div class="page-header-actions-selector" >
            <a href="javascript:showActions();"><i id="angle-down" class="fas fa-angle-down"></i></a>
            <div class="page-header-actions">
            <ul>
                <li class="action-list-item"><a href="javascript:hideActions();toggleDescription();"><i class="fas fa-info"></i><p>Toggle description</p></a></li>
                <% if (project.isCreator(userId) || project.hasAdmin(userId)) { %>
                <li class="action-list-item"><a href="javascript:hideActions();showEditProjectModal();"><i class="fas fa-edit"></i><p>Edit project details</p></a></li>
                <% } %>
                <% if (project.isCreator(userId)) { %>
                <li class="action-list-item"><a href="javascript:hideActions();completeProject(<%=project.getId()%>);"><i class="fas fa-check"></i><p id="set-project"><%=project.isComplete() ? "Set Project Incomplete" : "Set Project Complete"%></p></a></li>
                <li class="action-list-item"><a href="/delete-project?project=<%=project.getId()%>"><i class="far fa-trash-alt"></i><p>Delete project</p></a></li>
                <% } %>
            </ul>
            </div>
        </div>
        </div>
        <div class="page-header-description">
        <p id="main-project-description"><%=project.getDescription()%></p>
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
    
    <!-- Task Section -->
    <div class="project-section tasks active">
      <%request.setAttribute("tasks", tasks);%>
      <div id="project-addtask-container" class="inline">
      <button type="button" class="deep-button" onclick="location.href='add-task.jsp?projectID=<%=project.getId()%>&projectName=<%=project.getName()%>&taskID=0&taskName=null'">
        Add Task
      </button>
      </div>
      <div id="project-tasktree-button-container" class="inline">
      <button id="tasktree-button" class="deep-button">View Task Tree</button>
      </div>
      <div id="project-tasktree-container" class="popup">
      <div class="popup-content">
        <span class="close">&times;</span>
        <h2>Task Tree</h2>
        <%request.setAttribute("projectID", project.getId());%>
        <%request.setAttribute("select", false);%>
        <jsp:include page="/task-tree"/>
      </div>
      </div>
      <div id="project-addtaskblocker-container" class="inline">
      <button id="addtaskblocker-button" class="deep-button" onclick="location.href='add-task-blocker.jsp?projectID=<%=project.getId()%>&projectName=<%=project.getName()%>'">
          Add Task Blocker
      </button>
      </div>
      <jsp:include page="list-tasks.jsp"/>
    </div>

    <!-- User Section -->
    <div class="project-section users">
      <% if (project.hasAdmin(userId) || project.isCreator(userId)) { %>
      <button id="add-user-button">add user</button>
      <% } %>
       <a href="user-profile?userID=<%=creator.getUserID()%>">
        <p>creator: <%=creator.getUserName()%></p>
      </a>
      <% for (UserData user : admins) { %>
      <a href="user-profile?userID=<%=user.getUserID()%>">
        <p>admin: <%=user.getUserName()%></p>
      </a>
      <% } %>
      <% for (UserData user : members) { %>
      <a href="user-profile?userID=<%=user.getUserID()%>">
        <p>member: <%=user.getUserName()%></p>
      </a>
      <% } %>
    </div>

    <!-- Include modals -->
    <jsp:include page="add-user-to-project-modal.jsp" />
    <jsp:include page="message-modal.jsp" />
    <jsp:include page="edit-project-details-modal.jsp" />
  </body>
</html>
