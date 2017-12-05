package TestPlanSuite.cloudEntities;

import MyMain.Enums;
import TestPlanSuite.CloudServer;

import java.io.IOException;

/**
 * Created by khaled.abbas on 11/15/2017.
 */
public class User {
    String userName;
    String userId;
    String projId;

    public Enums.ROLES getRole() {
        return role;
    }

    Enums.ROLES role;
    RestAPIBuilder restAPIBuilder;

    public User (CloudServer cloudServer, String name, String id, String projectId) throws IOException {
        restAPIBuilder = new RestAPIBuilder(cloudServer);
        userName = name;
        userId = id;
        setRole(name);
        projId = projectId;
    }
    public void setRole(String name){
        if(name.contains("proj")){
            role = Enums.ROLES.PROJECT_ADMIN;
            return;
        }
        if(name.contains("admin")){
            role = Enums.ROLES.ADMIN;
        }
        else if(name.contains("khaleda")){
            role = Enums.ROLES.ADMIN;
        }
        else{
            role = Enums.ROLES.USER;
        }

    }
    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getProjId() {
        return projId;
    }

    @Override
    public String toString(){
        return "testPlanUser role is " + role + " Username is " + getUserName() + " Project ID " + getProjId();
    }
}
