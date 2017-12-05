package TestPlanSuite.cloudEntities;

import MyMain.Enums;
import Utils.Utilities;

import java.util.ArrayList;

/**
 * Created by khaled.abbas on 11/23/2017.
 */
public class TestPlan {
//    int id;
    Enums.OS OS;
    String projID;
    ArrayList<String> deviceQueries;
    String project;
    String testPlanName;

    public TestPlan(Enums.OS os, ArrayList<String> deviceQueries, String project, String testPlanName, String projectID) {
//        this.id = id;
        this.OS = os;
        this.deviceQueries = deviceQueries;
        this.testPlanName = testPlanName;
        this.project = project;
        this.projID = projectID;
        Utilities.log("created a new test plan object " + toString());
    }

//    public int getId() {
//        return id;
//    }

    public Enums.OS getOS() {
        return OS;
    }

    public String getProjID() {
        return projID;
    }

    public ArrayList<String> getDeviceQueries() {
        return deviceQueries;
    }

    public String getProject() {
        return project;
    }

    public String getTestPlanName() {
        return testPlanName;
    }

    public String toString(){
        return "test Plan name " + testPlanName + " for project " + projID;
    }

    public void setDeviceQueries(int numOfQueries){
        this.deviceQueries.clear();
        for (int i = 0; i < numOfQueries; i++){
            this.deviceQueries.add("@os='" + this.OS.toString().toLowerCase() + "'");
            Utilities.log("Adding query @os='" + this.OS.toString().toLowerCase() + "', to testPlan " + this.testPlanName);
        }
    }
}
