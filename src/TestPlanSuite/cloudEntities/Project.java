package TestPlanSuite.cloudEntities;

import MyMain.CloudServer;
import MyMain.Enums;
import MyMain.Main;
import Utils.Utilities;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by khaled.abbas on 11/15/2017.
 */
public class Project {

    String projName;
    String projId;
    ArrayList<User> projUsers;

    public ArrayList<TestPlan> getProjectTestPlans() {
        return projectTestPlans;
    }

    ArrayList<TestPlan> projectTestPlans;
    JSONParser parser;
    RestAPIBuilder restAPIBuilder;

    public Project(CloudServer cloudServer, String name, String id) throws IOException {
        this.restAPIBuilder = new RestAPIBuilder(cloudServer);
        this.projName = name;
        this.projId = id;
        this.projUsers = new ArrayList<>();
        try {
            this.populateProjUsersArray();
        } catch (Exception e) {
            Utilities.log(e);
        }
        this.projectTestPlans = new ArrayList<TestPlan>();
    }

    public Project(Project proj) {
        this.restAPIBuilder = new RestAPIBuilder(Main.cs);
        this.projName = proj.getProjName();
        this.projId = proj.getProjId();
        this.projUsers = proj.getProjUsers();
        this.projectTestPlans = proj.getProjectTestPlans();
    }

    public String getProjName() {
        return projName;
    }

    public String getProjId() {
        return projId;
    }

    public ArrayList<User> getProjUsers() {
        return projUsers;
    }

    //Add users to project instance
    public void populateProjUsersArray() throws Exception {
        String APIResponse = restAPIBuilder.doGet("/projects/" + projId + "/users", restAPIBuilder.getPrefix() + restAPIBuilder.getCloudIP() + ":" + restAPIBuilder.getCloudPort()
                + "/api/v1", restAPIBuilder.getAuthStringEnc());
        JSONObject obj = new JSONObject(APIResponse);
        JSONArray arr = obj.getJSONArray("data");
        for (int i = 0; i < arr.length(); i++) {
            projUsers.add(new User(restAPIBuilder.getCloudServerProperties(), arr.getJSONObject(i).getString("username"), Integer.toString(arr.getJSONObject(i).getInt("id")),
                    projId));
        }
    }

    public void addNewTestPlan(TestPlan newTestPlan) {
        this.projectTestPlans.add(newTestPlan);
        Utilities.log("added test plan " + newTestPlan + "to testPlanProject" + this.projName);
    }

    public List<TestPlan> getTestPlanByOS(Enums.OS os) {
        List<TestPlan> testplansList = new ArrayList<>();
        List<TestPlan> projTestPlans = getProjectTestPlans();
        for (TestPlan testPlan : projTestPlans) {
            if (testPlan.getOS().equals(os)) {
                testplansList.add(testPlan);
            }
        }
        if (testplansList.size() > 0) {
            return testplansList;
        }
        Utilities.log("Did not find test plan with matching os to: " + os);
        return null;
    }

    public void readAndUpdateTestPlansFromProject(User user, SeleniumHelper sel) {

        Utilities.log("reading the test plans using selenium from testPlanProject " + this.projName);
        sel.getProjectTestPlans().stream().forEach(p -> addNewTestPlan(p));
        Utilities.log("added the test plans using selenium from testPlanProject " + this.projName);

    }

    public boolean doesProjectHaveTestPlans(User user, SeleniumHelper sel) {
        boolean result = sel.checkIfProjectHasTestPlans();
        return result;

    }

    public void createTestPlansForProject(User user, SeleniumHelper sel) {

        Utilities.log("reading the test plans using selenium from testPlanProject " + this.projName);
        sel.createTestPlans().stream().forEach(p -> addNewTestPlan(p));
        Utilities.log("added the test plans using selenium from testPlanProject " + this.projName);


    }

}

