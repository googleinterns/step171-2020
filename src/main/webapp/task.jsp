<%--Class Imports--%>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.rtb.projectmanagementtool.project.*"%>
<%@ page import="com.rtb.projectmanagementtool.task.*"%>
<%@ page import="java.util.ArrayList" %>

<%--Get variables--%>
<%
    TaskData task = (TaskData) request.getAttribute("task");
    TaskData parentTask = (TaskData) request.getAttribute("parentTask");
    ProjectData project = (ProjectData) request.getAttribute("project");
    ArrayList<TaskData> subtasks = (ArrayList<TaskData>) request.getAttribute("subtasks");
%>

    <!-- ArrayList<UserData> users = (ArrayList<UserData>) request.getAttribute("users"); -->
    <!-- ArrayList<CommentData> comments = (ArrayList<CommentData>) request.getAttribute("comments"); -->


<%--HTML--%>
<html>
  <head>
    <meta charset="UTF-8">
    <title><%=task.getName()%></title>
    <link rel="stylesheet" href="style.css">
    <!-- <script src="scripts/main.js"></script> -->
    <!-- <script src="scripts/task.js"></script> -->
  </head>

  <body>
    <jsp:include page="navigation-bar.jsp"/>

    <div id="content">
      <div id="task-title-container"><h1><%=task.getName()%></h1></div>
      <div id="task-description-container"><%=task.getDescription()%></div>
      <div id="task-status-container">Status: <%=task.getStatus()%></div>
      <div id="task-project-container">
        <%if (project != null) {%>
          <p class="inline">Return to Project: </p>
          <button type="button" class="inline" onclick="location.href='project?id=<%=project.getId()%>'">
            <%=project.getName()%>
          </button>
        <%}%>
      </div>
      <div id="task-parenttask-container">
        <%if (parentTask != null) {%>
          <p class="inline">Return to Parent Task: </p>
          <button type="button" class="inline" onclick="location.href='task?taskID=<%=parentTask.getTaskID()%>'">
            <%=parentTask.getName()%>
          </button>
        <%}%>
      </div>

      <h2>Subtasks</h2>
      <div id="task-subtasks-container">
        <%request.setAttribute("tasks", subtasks);%>
        <jsp:include page="list-tasks.jsp"/>
      </div>
      <div id="task-addsubtask-container">
        <%
            // Default values
            long projectID = 1;
            String projectName = "Default Project Name";
        %>
        <button type="button" onclick="location.href='add-task.jsp?projectID=<%=projectID%>&projectName=<%=projectName%>&taskID=<%=task.getTaskID()%>&taskName=<%=task.getName()%>'">
          Add Subtask
        </button>
      </div>

      <h2>Members</h2>
      <div id="task-assignuser-container"></div>
        <%
            long userID = 1; // Default value
            String servletPage;
            String buttonText;
            if (!task.getUsers().contains(userID)) {
                servletPage = "/task-add-user";
                buttonText = "Assign me to this task";
            } else {
                servletPage = "/task-remove-user";
                buttonText = "Remove me from this task";
            }
        %>
        <form id="toggle-user-assignment-post-form" action="<%=servletPage%>" method="POST">
          <input type="hidden" name="taskID" value="<%=task.getTaskID()%>"/>
          <input type="hidden" name="userID" value="<%=userID%>"/>
          <button type="submit" id="toggle-user-assignment"><%=buttonText%></button>
        </form>
      <div id="task-users-container"></div>

      <h2>Comments</h2>
      <ul id="task-comments-container">
        <li class="comment">
          <p>Comment 1</p>
        </li>
        <li class="comment">
          <p>Comment 2</p>
        </li>
        <li class="comment">
          <p>Comment 3</p>
        </li>
      </ul>
    </div>
  </body>
</html>