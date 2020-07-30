import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.rtb.projectmanagementtool.project.ProjectController;
import com.rtb.projectmanagementtool.project.ProjectData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProjectControllerTest {
  private final LocalServiceTestHelper helper =
      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

  @Before
  public void setUp() {
    helper.setUp();
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

  @Test
  public void noProjects() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    ProjectController projectController = new ProjectController(datastore);
    HashSet<ProjectData> projects = projectController.getProjects(Collections.emptyList());
    Assert.assertTrue(projects.size() == 0);
  }

  @Test
  public void addAndSaveProjects() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    ProjectController projectController = new ProjectController(datastore);

    ProjectData project1 = new ProjectData("Project1", "Project1 Desc", 1l);
    projectController.saveProject(project1);

    ProjectData project2 = new ProjectData("Project2", "Project2 Desc", 1l);
    projectController.saveProject(project2);

    Assert.assertTrue(projectController.getProjects(Collections.emptyList()).size() == 2);
  }

  @Test
  public void removeProject() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    ProjectController projectController = new ProjectController(datastore);

    ProjectData project1 = new ProjectData("Project1", "Project1 Desc", 1l);
    projectController.saveProject(project1);

    ProjectData project2 = new ProjectData("Project2", "Project2 Desc", 1l);
    projectController.saveProject(project2);

    HashSet<ProjectData> projects = projectController.getProjects(Collections.emptyList());
    int projectsSize = projects.size();
    Assert.assertTrue(projectsSize == 2);

    for (ProjectData project : projects) {
      projectController.removeProject(project);
      Assert.assertTrue(
          projectController.getProjects(Collections.emptyList()).size() == projectsSize - 1);
      projectsSize--;
    }
  }

  @Test
  public void getAllProjects() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    ProjectController projectController = new ProjectController(datastore);

    HashSet<ProjectData> expectedProjects = new HashSet<ProjectData>();

    ProjectData project1 = new ProjectData("Project1", "Project1 Desc", 1l);
    projectController.saveProject(project1);
    expectedProjects.add(project1);

    ProjectData project2 = new ProjectData("Project2", "Project2 Desc", 1l);
    projectController.saveProject(project2);
    expectedProjects.add(project2);

    ProjectData project3 = new ProjectData("Project3", "Project3 Desc", 2l);
    projectController.saveProject(project3);
    expectedProjects.add(project3);

    ProjectData project4 = new ProjectData("Project4", "Project4 Desc", 3l);
    projectController.saveProject(project4);
    expectedProjects.add(project4);

    HashSet<ProjectData> actualProjects = projectController.getProjects(Collections.emptyList());

    // Can't do assertEquals on the actual arrays because of ordering in respective sets
    Assert.assertEquals(actualProjects.size(), expectedProjects.size());
  }

  // get projects that a particular user is in
  @Test
  public void getProjectsWithUser() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    ProjectController projectController = new ProjectController(datastore);

    final Long desiredUser = 5l;

    HashSet<ProjectData> expectedProjects = new HashSet<ProjectData>();

    ProjectData project1 = new ProjectData("Project1", "Project1 Desc", 1l);
    project1.addRegularUser(desiredUser);
    projectController.saveProject(project1);
    expectedProjects.add(project1);

    ProjectData project2 = new ProjectData("Project2", "Project2 Desc", 1l);
    project2.addAdminUser(desiredUser);
    projectController.saveProject(project2);
    expectedProjects.add(project2);

    ProjectData project3 = new ProjectData("Project3", "Project3 Desc", 2l);
    projectController.saveProject(project3);

    ProjectData project4 = new ProjectData("Project4", "Project4 Desc", 3l);
    projectController.saveProject(project4);

    HashSet<ProjectData> actualProjects =
        projectController.getProjects(new ArrayList<Long>(Arrays.asList(desiredUser)));

    Assert.assertEquals(actualProjects.size(), expectedProjects.size());
  }
}
