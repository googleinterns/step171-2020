<%--Class Imports--%>
<%@ page import="com.rtb.projectmanagementtool.user.*"%>
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
        <% if (user.getUserMajors().size() == 1) { %>
        <p>Major: <%=user.getUserMajors().get(0)%></p>
        <% } else { %>
        <p>Majors: <%=user.getUserMajors().toString().substring(1, user.getUserMajors().toString().length()-1)%></p>
        <% } %>
        <p>Class of <%=user.getUserYear()%></p>
      </button>
      </a>
    </li>
  <%}%>
</ul>