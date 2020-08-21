/**
 * This file contains javascript code for the project page
 */

// Get some page elements
var addUserModal = document.getElementById('add-user-to-project-modal');
var messageModal = document.getElementById('message-modal');
var messageModalMessage = document.getElementById('message-modal-message');

// Hides the modal when clicked
document.getElementById('modal-close').addEventListener('click', 
function() {
  addUserModal.style.display = 'none';
})

// Opens the modal when clicked
document.getElementById('add-user-button').addEventListener('click',
function () {
  addUserModal.style.display = 'flex';
})

// Displays the message modal
function showMessage(message) {
  messageModalMessage.innerHTML = message;
  messageModal.style.display = 'flex';
}

// Closes the message modal
document.getElementById('message-modal-close').addEventListener('click',
function() {
  messageModal.style.display = 'none';
})

// Adds a user to the project; called from addUserModal
function addUserToProject(projectId) {
  // Get user input for userName
  var userName = document.getElementById('user-name').value;

  // If userName is empty, show error message
  if (userName === '') {
    addUserModal.style.display = 'none';
    showMessage('Invalid name');
    return;
  }

  // Get user input for userRole
  var userRole = document.getElementById('user-role').value;

  // Generate query string & use it t call the servlet
  var queryString = '/add-user-to-project?project=' + projectId + '&userName=' + userName + '&userRole=' + userRole;
  fetch(queryString).then(response => response.json()).then((response) => {
    if (response.hasOwnProperty('userId')) {
        addUserToProjectPage(userName, userRole, response.userId);
    }

    // After call to servlet, show message on page describing outcome
    addUserModal.style.display = 'none';
    showMessage(response.message);
  })
}

// Adds the user to the page so that a page reload isn't necessary
function addUserToProjectPage(userName, userRole, userId) {
  var usersContainer = document.getElementById('project-users-container');
  var a = document.createElement('a');
  var p = document.createElement('p');
  p.innerHTML = userRole + ': ' + userName;
  a.appendChild(p);
  a.href= '/user-profile?userID=' + userId;
  usersContainer.appendChild(a);
}