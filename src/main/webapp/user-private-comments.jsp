<%--Class Imports--%>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.rtb.projectmanagementtool.task.*"%>
<%@ page import="com.rtb.projectmanagementtool.privatecomment.*"%>
<%@ page import="java.util.*"%>


<%--Get variables--%>
<%
    List<TaskData> tasks = (List<TaskData>)(List<?>) request.getAttribute("tasks");
    HashMap<Long, PrivateCommentData> privateCommentsMap = (HashMap<Long, PrivateCommentData>) request.getAttribute("privateCommentsMap");
    boolean currUser = (boolean) request.getAttribute("currentUser");
%>

<%--HTML--%>
<head>
  <script src="https://cdn.jsdelivr.net/npm/@editorjs/editorjs@latest"></script>
  <script src="https://cdn.jsdelivr.net/npm/@editorjs/header@latest"></script>
</head>   
  <ul>
    <%if (currUser) {%>
      <h1 id="pc-header">Your Private Task Comments:</h1>
      <%for (TaskData task : tasks) {%>
        
        <script>
          function init() {
            const editor = new EditorJS({
            holderId : 'box',
            autofocus: true,
            data: '<%=(privateCommentsMap.get(task.getTaskID())).getMessage().trim()%>',
            tools: {
              header: Header,
             },
            });
          }
          function save() {
            editor.save().then((outputData) => {
              console.log('Article data: ', outputData)
            }).catch((error) => {
              console.log('Saving failed: ', error)
            });  
          }
        </script>
        
        <li class="task-with-pc">
          <button class="inline deep-button" type="button" onclick="location.href='task?taskID=<%=task.getTaskID()%>'">
            <%=task.getName()%>
          </button>
          <%request.setAttribute("task", task);%>
          <%request.setAttribute("clickable", false);%>
          <jsp:include page="task-status-checkmark.jsp"/>
          <p class="inline"><%=task.getDescription()%></p>
          <form onload="init()" action="/user-profile" method="POST">
            <input type="hidden" name="taskID" value="<%=task.getTaskID()%>">
            <br>
            <div id='box' style="width:300px; height:100px;" type="text" name="message-<%=task.getTaskID()%>"></div>
            <br>
            <button onlclick="save()" class="deep-button submit-button" type="submit" class="deep-button">Update Comment</button>
          </form>
          <br><br>
        </li>
      <%}%>
    <%}%>
  </ul>
</body>