<%--Class Imports--%>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.rtb.projectmanagementtool.user.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.List"%>

<%--Get variables--%>
<%
    List<UserData> users = (List<UserData>)(List<?>) request.getAttribute("users");
%>

<%--HTML--%>
<ul>
  <%for (UserData user : users) {%>
    <li class="user inline">
      <a href="user-profile?userID=<%=user.getUserID()%>">
      <button type="button" class = "flat-button">
        <h3><%=user.getUserName()%></h3>
        <p>Major: <%=user.getUserMajors()%></p>
        <p>Graduation Year: <%=user.getUserYear()%></p>
      </button>
      </a>
    </li>
  <%}%>
</ul>