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
    var privateCommentsDataJSON = <%=new Gson().toJson(privateCommentsMap)%>;
    function init(privateCommentTaskID) {
        console.log("here!");
        const editor = new EditorJS({
        holderId : "box-" + privateCommentTaskID,
        autofocus: true,
        data: privateCommentsDataJson[privateCommentTaskID],
        tools: {
          header: Header,
        },
      });
      return editor;
    }
    function save(editor) {
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
          <li class="task-with-pc">
            <button class="inline deep-button" type="button" onclick="location.href='task?taskID=<%=task.getTaskID()%>'">
              <%=task.getName()%>
            </button>
            <%request.setAttribute("task", task);%>
            <%request.setAttribute("clickable", false);%>
            <jsp:include page="task-status-checkmark.jsp"/>
            <p class="inline"><%=task.getDescription()%></p>
            <form action="/user-profile" method="POST">
              <input type="hidden" name="taskID" value="<%=task.getTaskID()%>"> 
              <br>
              <div id="box-<%=task.getTaskID()%>" onload="init(<%=task.getTaskID()%>)" type="text">Here</div>
              <button onclick="save(init(<%=task.getTaskID()%>))" class="deep-button submit-button" type="submit" class="deep-button">Update Comment</button>
              <input type="hidden" name="message-<%=task.getTaskID()%>" value="save()"</input>
            </form>
            <br><br>
          </li>
        <%}%>
      <%}%>
    </ul>
  </body>
</html>