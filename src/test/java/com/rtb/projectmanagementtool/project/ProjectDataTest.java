import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.rtb.projectmanagementtool.project.ProjectData;
import java.util.HashSet;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProjectDataTest {
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
  public void createProject() {
    String projectName = "Project";
    String projectDesc = "(Description of project)";
    Long projectCreator = Long.parseLong("4535635645");

    ProjectData testProject = new ProjectData(projectName, projectDesc, projectCreator);
    Assert.assertEquals(
        testProject.getId(), 0l); // should be 0 because project hasn't been added to database
    Assert.assertEquals(testProject.getName(), projectName);
    Assert.assertEquals(testProject.getDescription(), projectDesc);
    Assert.assertTrue(testProject.getUsers().keySet().contains(projectCreator));
    Assert.assertTrue(testProject.getTaskIds().size() == 0);
  }

  @Test
  public void testToEntity() {
    String projectName = "Project";
    String projectDesc = "(Description of project)";
    Long projectCreator = Long.parseLong("4535635645");

    ProjectData testProject = new ProjectData(projectName, projectDesc, projectCreator);
    Entity entity = testProject.toEntity();
    Assert.assertEquals(entity.getProperty("name"), projectName);
    Assert.assertEquals(entity.getProperty("description"), projectDesc);
  }

  @Test
  public void changeProjectMembers() {
    String projectName = "Project";
    String projectDesc = "(Description of project)";
    Long projectCreator = Long.parseLong("4535635645");

    ProjectData testProject = new ProjectData(projectName, projectDesc, projectCreator);

    Long newId = 5l;
    String newName = "New project name";
    String newDesc = "New project desc";

    testProject.setId(newId);
    Assert.assertEquals((Long) testProject.getId(), newId);

    testProject.setName(newName);
    Assert.assertEquals(testProject.getName(), newName);

    testProject.setDescription(newDesc);
    Assert.assertEquals(testProject.getDescription(), newDesc);
  }

  @Test
  public void addNewTask() {
    Long taskId = 6l;

    ProjectData testProject = new ProjectData("", "", 0l);

    testProject.addTask(taskId);
    Assert.assertTrue(testProject.getTaskIds().contains(taskId));
  }

  @Test
  public void addMultipleTasks() {
    HashSet<Long> taskIds = new HashSet<Long>();
    taskIds.add(1l);
    taskIds.add(2l);
    taskIds.add(3l);
    taskIds.add(4l);

    ProjectData testProject = new ProjectData("", "", 0l);

    for (Long taskId : taskIds) {
      testProject.addTask(taskId);
    }

    Assert.assertEquals(taskIds, testProject.getTaskIds());
  }

  @Test
  public void addUserByType() {
    ProjectData testProject = new ProjectData("", "", 0l);

    String regularType = "REGULAR";
    String adminType = "ADMIN";

    testProject.addRegularUser(1l);
    Assert.assertEquals(regularType, testProject.getUsers().get(1l).name());
    testProject.addAdminUser(2l);
    Assert.assertEquals(adminType, testProject.getUsers().get(2l).name());
  }
}
