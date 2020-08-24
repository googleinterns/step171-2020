/**
 * This file contains javascript code for the project page
 */
// Get some page elements
const addUserModal = document.querySelector(
  '.modal.add-user-to-project');
const messageModal = document.querySelector('.modal.message');
const messageModalMessage = document.getElementById(
  'message-modal-message');
const taskSection = document.querySelector('.project-section.tasks');
const usersSection = document.querySelector('.project-section.users');
const projectActionsSelector = document.querySelector(
  '.page-header-actions-selector');
const projectActions = document.querySelector('.page-header-actions');
const projectDescription = document.querySelector(
  '.page-header-description');
const editProjectDetailsModal = document.querySelector(
  '.modal.edit-project-details');
// Hides the modal when clicked
document.querySelector('.modal-close.add-user-to-project')
  .addEventListener('click',
    function () {
      addUserModal.style.display = 'none';
    });
// Opens the modal when clicked
document.getElementById('add-user-button').addEventListener('click',
  function () {
    document.getElementById('user-email').value = '';
    addUserModal.style.display = 'flex';
  });
/**
 * Displays the message modal
 *@param {String} message the message to display
 */
function showMessage(message) {
  messageModalMessage.innerHTML = message;
  messageModal.style.display = 'flex';
}
// Closes the message modal
document.getElementById('message-modal-close').addEventListener(
  'click',
  function () {
    messageModal.style.display = 'none';
  });
/**
 * Adds a user to the project; called from addUserModal
 *@param {String} projectId id of the project
 */
function addUserToProject(projectId) {
  // Get user input for userName
  const userEmail = document.getElementById('user-email').value;
  // If userEmail is empty, show error message
  if (userEmail === '') {
    addUserModal.style.display = 'none';
    showMessage('Invalid email');
    return;
  }
  // Get user input for userRole
  const userRole = document.getElementById('user-role').value;
  // Generate query string & use it t call the servlet
  const queryString = '/add-user-to-project?project=' + projectId +
    '&userEmail=' + userEmail + '&userRole=' + userRole;
  fetch(queryString, {
    method: 'POST'
  }).then((response) =>
    response.json()).then((response) => {
    if (response.hasOwnProperty('userId') &&
      response.hasOwnProperty('userName')) {
      addUserToProjectPage(response.userName, userRole, response
        .userId);
    }
    // After call to servlet, show message on page describing outcome
    addUserModal.style.display = 'none';
    showMessage(response.message);
  });
}
/**
 * Adds the user to the page so that a page reload isn't necessary
 *@param {String} userName name of the user
 *@param {String} userRole role of the user
 *@param {String} userId id of user to generate link to their page
 */
function addUserToProjectPage(userName, userRole, userId) {
  const a = document.createElement('a');
  const p = document.createElement('p');
  p.innerHTML = userRole + ': ' + userName;
  a.appendChild(p);
  a.href = '/user-profile?userID=' + userId;
  usersSection.appendChild(a);
}
// get all tabs
const tabs = document.querySelectorAll('.project-header-tab');
tabs.forEach((clickedTab) => {
  // add onClick event for each tab
  clickedTab.addEventListener('click', (e) => {
    // remove the active class from all tabs
    tabs.forEach((tab) => {
      tab.classList.remove('active');
    });
    const clickedClassList = clickedTab.classList;
    // Add the active class on the clicked tab
    clickedClassList.add('active');
    if (clickedClassList.contains('tasks')) {
      showTaskSection();
    }
    if (clickedClassList.contains('users')) {
      showUsersSection();
    }
  });
});
/**
 * Displays the task section
 */
function showTaskSection() {
  hideUsersSection();
  taskSection.style.display = 'block';
}
/**
 * Hides the task section
 */
function hideTaskSection() {
  taskSection.style.display = 'none';
}
/**
 * Displays the users section
 */
function showUsersSection() {
  hideTaskSection();
  usersSection.style.display = 'block';
}
/**
 * Hides the users section
 */
function hideUsersSection() {
  usersSection.style.display = 'none';
}
/**
 * Displays user actions in header of project page
 */
function showActions() {
  if (projectActions.style.display === 'block') {
    hideActions();
    return;
  }
  projectActions.style.display = 'block';
}
// Close the actions menu when page is clicked
document.addEventListener('click', function (event) {
  if (!document.querySelector('.page-header-actions-selector')
    .contains(event.target)) {
    projectActions.style.display = 'none';
  }
});
/**
 * Hides user actions
 */
function hideActions() {
  projectActions.style.display = 'none';
}
/**
 * Toggles the state of the description text of a project to shown or hidden
 */
function toggleDescription() {
  if (projectDescription.style.display === 'none' ||
    projectDescription.style.display === '') {
    projectDescription.style.display = 'block';
  } else {
    projectDescription.style.display = 'none';
  }
  hideActions();
}
// Hides the modal when clicked
document.querySelector('.modal-close.edit-project-details')
  .addEventListener('click',
    function () {
      editProjectDetailsModal.style.display = 'none';
    });
// Opens the modal when clicked
function showEditProjectModal() {
  hideActions();
  document.getElementById('project-name').value =
    document.getElementById('main-project-name').innerHTML;
  document.getElementById('project-desc').value =
    document.getElementById('main-project-description').innerHTML;
  editProjectDetailsModal.style.display = 'flex';
}
/**
 * Edits the project details
 *@param {String} projectId id of the project
 */
function editProjectDetails(projectId) {
  // Get user inputs
  const projectName = document.getElementById('project-name').value;
  const projectDesc = document.getElementById('project-desc').value;
  // If userEmail is empty, show error message
  if (projectDesc === '' || projectName === '') {
    editProjectDetailsModal.style.display = 'none';
    if (projectName === '' && projectDesc === '') {
      showMessage('Invalid inputs.');
    } else if (projectName === '') {
      showMessage('Invalid email.');
    } else if (projectDesc === '') {
      showMessage('Invalid description.');
    }
    return;
  }
  // Generate query string & use it to call the servlet
  const queryString = '/edit-project-details?project=' + projectId +
    '&projectName=' + projectName + '&projectDesc=' + projectDesc;
  fetch(queryString, {
    method: 'POST'
  }).then((response) =>
    response.json()).then((response) => {
    if (response.message ===
      'Updated project name and description.') {
      updateProjectName(projectName);
      updateProjectDescription(projectDesc);
    } else if (response.message === 'Updated project name.') {
      updateProjectName(projectName);
    } else if (response.message ===
      'Updated project description.') {
      updateProjectDescription(projectDesc);
    }
    // After call to servlet, show message on page describing outcome
    editProjectDetailsModal.style.display = 'none';
    showMessage(response.message);
  });
}
/**
 * Updates the project name on project page
 *@param {String} newName the new name
 */
function updateProjectName(newName) {
  document.getElementById('main-project-name').innerHTML = newName;
}
/**
 * Updates the project desc on project page
 *@param {String} newDesc the new description
 */
function updateProjectDescription(newDesc) {
  document.getElementById('main-project-description').innerHTML =
    newDesc;
}