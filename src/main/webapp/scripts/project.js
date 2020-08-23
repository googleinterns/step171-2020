/**
 * This file contains javascript code for the project page
 */

// Get some page elements
const addUserModal = document.getElementById('add-user-to-project-modal');
const messageModal = document.getElementById('message-modal');
const messageModalMessage = document.getElementById('message-modal-message');
const taskSection = document.querySelector('.project-section.tasks');
const usersSection = document.querySelector('.project-section.users');
const projectActions = document.querySelector('.page-header-actions');
// Hides the modal when clicked
document.getElementById('modal-close').addEventListener('click',
    function() {
      addUserModal.style.display = 'none';
    });

// Opens the modal when clicked
document.getElementById('add-user-button').addEventListener('click',
    function() {
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
document.getElementById('message-modal-close').addEventListener('click',
    function() {
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
  fetch(queryString, {method: 'POST'}).then((response) =>
    response.json()).then((response) => {
    if (response.hasOwnProperty('userId') &&
    response.hasOwnProperty('userName')) {
      addUserToProjectPage(response.userName, userRole, response.userId);
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
  projectActions.style.display = 'block';
}

/**
 * Hides user actions
 */
function hideActions() {
  projectActions.style.display = 'none';
}