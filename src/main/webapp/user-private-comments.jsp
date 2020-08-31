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
<html>
  <head>
    <script src="https://cdn.jsdelivr.net/npm/@editorjs/editorjs@latest"></script>
    <script src="https://cdn.jsdelivr.net/npm/@editorjs/header@latest"></script>
  </head>
  <script>
    function init(privateCommentID, jsData) {
      const editor = new EditorJS({
        holderId : privateCommentID,
        autofocus: true,
        data: jsData,
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
  <body>  
    <ul>
      <%if (currUser) {%>
        <h1 id="pc-header">Your Private Task Comments:</h1>
        <%for (TaskData task : tasks) {%>
          <%System.out.println(task);
          <li class="task-with-pc">
            <button class="inline deep-button" type="button" onclick="location.href='task?taskID=<%=task.getTaskID()%>'">
              <%=task.getName()%>
            </button>
            <%request.setAttribute("task", task);%>
            <%request.setAttribute("clickable", false);%>
            <jsp:include page="task-status-checkmark.jsp"/>
            <p class="inline"><%=task.getDescription()%></p>
            <form onload="init(box-<%=task.getTaskID()%>, <%=(privateCommentsMap.get(task.getTaskID())).getMessage().trim());" action="/user-profile" method="POST">
              <input type="hidden" name="taskID" value="<%=task.getTaskID()%>"> 
              <br>
              <div id="box-<%=task.getTaskID()%>" type="text"></div>
              <br>
              <input type="hidden" name="message-<%=task.getTaskID()%>" value="document.getElementById('box-<%=task.getTaskID()%>').value(outputData);"</input>
              <button onclick="save()" class="deep-button submit-button" type="submit" class="deep-button">Update Comment</button>
            </form>
            <br><br>
          </li>
        <%}%>
      <%}%>
    </ul>
  </body>
</html>