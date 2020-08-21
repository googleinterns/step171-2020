<%@ page import="com.rtb.projectmanagementtool.project.*" %>

<%
    Long userId = (Long) request.getAttribute("userId");
    ProjectData project = (ProjectData) request.getAttribute("project");
%>

<div id="add-user-to-project-modal">
  <div class="modal-content">
    <div id="modal-close">X</div>
      <h3>Add user to project</h3>
      <input type="hidden" name="projectId" value=<%=project.getId()%>>
      <div>
        <label>Email</label>
        <input type="text" id="user-name" name="user-name" placeholder="User email" /> 
      </div>
      <div>
        <label>Role</label>
        <select id="user-role" name="user-role" class="field-select">
          <% if (project.isCreator(userId)) { %>
          <option value="admin">admin</option>
          <% } %>
        <option value="member">member</option>
        </select>
      </div>
    <button onclick="addUserToProject(<%=project.getId()%>)">Add user</button>
  </div>
</div>