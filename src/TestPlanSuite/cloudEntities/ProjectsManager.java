package TestPlanSuite.cloudEntities;

import TestPlanSuite.CloudServer;
import Utils.Utilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import TestPlanSuite.cloudEntities.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by khaled.abbas on 11/15/2017.
 */
public class ProjectsManager {

    private ArrayList<Project> projects = new ArrayList<>();
    private ArrayList<Project> projectsToDeleteOnExit;
    RestAPIBuilder restAPIBuilder;


    public ProjectsManager(CloudServer cloudServer) throws IOException, JSONException {
        restAPIBuilder = new RestAPIBuilder(cloudServer);
        populateProjectsMap();
        projectsToDeleteOnExit = new ArrayList<Project>();

    }

    public Project getProject(String id) {
        for (Project proj :
                projects) {
            if (proj.getProjId().equals(id)) {
                return proj;
            }
        }
        Utilities.log("Did not find testPlanProject " + id + "in projMGR");
        return null;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    private void populateProjectsMap() throws IOException, JSONException {
        JSONObject obj;

        obj = new JSONObject(getProjectsByAPI());
        JSONArray arr = obj.getJSONArray("data");
        for (int i = 0; i < arr.length(); i++) {
            String name = arr.getJSONObject(i).getString("name");
            int id = arr.getJSONObject(i).getInt("id");
            if (name.startsWith("TestPlanProjects")) {
                projects.add(new Project(restAPIBuilder.getCloudServerProperties(), name, Integer.toString(id)));
            } else if (name.equalsIgnoreCase("default")) {
                projects.add(new Project(restAPIBuilder.getCloudServerProperties(), name, Integer.toString(id)));
            }

        }
    }

    private String getProjectsByAPI() throws IOException {
        String APIResponse = "";
        try {
            APIResponse = restAPIBuilder.doGet("/projects", restAPIBuilder.getPrefix() + restAPIBuilder.getCloudIP() + ":" + restAPIBuilder.getCloudPort()
                    + "/api/v1", restAPIBuilder.getAuthStringEnc());
        } catch (IOException e) {

            Utilities.log("API FAILED to get projects");
            Utilities.log(e);
            throw e;
        }
        return APIResponse;
    }
}
