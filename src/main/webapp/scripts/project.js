/**
 * This file contains javascript code for the project page
 */

// Get some page elements
var addUserModal = document.getElementById('add-user-to-project-modal');
var messageModal = document.getElementById('message-modal');
var messageModalMessage = document.getElementById('message-modal-message');
var taskSection = document.querySelector('.project-section.tasks');
var usersSection = document.querySelector('.project-section.users');
var projectActions = document.querySelector('.page-header-actions');
// Hides the modal when clicked
document.getElementById('modal-close').addEventListener('click',
  function () {
    addUserModal.style.display = 'none';
  })

// Opens the modal when clicked
document.getElementById('add-user-button').addEventListener('click',
  function () {
    document.getElementById('user-email').value = "";
    addUserModal.style.display = 'flex';
  })

// Displays the message modal
function showMessage(message) {
  messageModalMessage.innerHTML = message;
  messageModal.style.display = 'flex';
}

// Closes the message modal
document.getElementById('message-modal-close').addEventListener('click',
  function () {
    messageModal.style.display = 'none';
  })

// Adds a user to the project; called from addUserModal
function addUserToProject(projectId) {
  // Get user input for userName
  var userEmail = document.getElementById('user-email').value;

  // If userEmail is empty, show error message
  if (!validateEmail(userEmail)) {
    addUserModal.style.display = 'none';
    showMessage('Invalid email');
    return;
  }

  // Get user input for userRole
  var userRole = document.getElementById('user-role').value;

  // Generate query string & use it t call the servlet
  var queryString = '/add-user-to-project?project=' + projectId + '&userEmail=' + userEmail + '&userRole=' + userRole;
  fetch(queryString, {method: "POST"}).then(response => response.json()).then((response) => {
    if (response.hasOwnProperty('userId') && response.hasOwnProperty('userName')) {
      addUserToProjectPage(response.userName, userRole, response.userId);
    }

    // After call to servlet, show message on page describing outcome
    addUserModal.style.display = 'none';
    showMessage(response.message);
  })
}

function validateEmail(email) {
  const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return re.test(String(email).toLowerCase());
}

// Adds the user to the page so that a page reload isn't necessary
function addUserToProjectPage(userName, userRole, userId) {
  var a = document.createElement('a');
  var p = document.createElement('p');
  p.innerHTML = userRole + ': ' + userName;
  a.appendChild(p);
  a.href = '/user-profile?userID=' + userId;
  usersSection.appendChild(a);
}

const tabs = document.querySelectorAll('.project-header-tab');
tabs.forEach(clickedTab => {
  // add onClick event for each tab
  clickedTab.addEventListener('click', () => {
    // remove the active class from all tabs
    tabs.forEach(tab => {
      tab.classList.remove('active');
    });
    var clickedClassList = clickedTab.classList;
    // Add the active class on the clicked tab
    clickedClassList.add('active');
    if (clickedClassList.contains('tasks')) {
      showTaskSection();
    }
    if (clickedClassList.contains('users')) {
      showUsersSection();
    }
  })
})

function showTaskSection() {
  hideUsersSection();
  taskSection.style.display = "block";
}

function hideTaskSection() {
  taskSection.style.display = "none";
}

function showUsersSection() {
  hideTaskSection();
  usersSection.style.display = "block";
}

function hideUsersSection() {
  usersSection.style.display = "none";
}

function showActions() {
  projectActions.style.display = 'block';
}

function hideActions() {
  projectActions.style.display = 'none';
}