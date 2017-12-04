package MyMain;

import cloudEntities.CloudServer;
import cloudEntities.Project;
import cloudEntities.ProjectsManager;
import cloudEntities.User;
import org.junit.Assert;

import java.io.IOException;
import java.util.*;

/**
 * Created by khaled.abbas on 11/15/2017.
 */
public class SetupTestPlanRunner {
    public static CloudServer cloudServer = new CloudServer(CloudServer.CloudServerName.RELEASE);
    private static ProjectsManager projectsMGR = null;

    public static Map<User, Project> getRunningCandidates() {
        return runningCandidates;
    }

    private static Map<User, Project> runningCandidates = null;
    private static Logger _log;

    public static void setupRun() {
        _log.createDir();
        _log.setLoggingLevel(Logger.LogLevel.DEBUG);
        Assert.assertTrue(createProjectsInstance());
        _log.debug("Completed createProjectsInstace method");
        Assert.assertTrue(pickRunningCandidates());
        _log.debug("Completed picking the candidates");
//        runTests();
    }


    /**
     * Randomly chooses a set of 5 users to run the suites, those users may be from different projects
     * @return
     */
    private static boolean pickRunningCandidates(){
        User currUser;
        runningCandidates = new HashMap<>();
        ArrayList<Project> projects = projectsMGR.getProjects();
        int iter = 0;
        try {
            runningCandidates.put(new User(cloudServer, "khaleda", "15", "2"), projectsMGR.getProject("2"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (runningCandidates.size() < 5 && iter < 5000){
            Project prj = projects.get((int)(Math.random() * projects.size()));
            if(prj.getProjId().equals("2")){
                continue;
            }
            ArrayList<User> users = prj.getProjUsers();
            if(users.size() == 0) continue;
            currUser = users.get((int)(Math.random() * users.size()));
            runningCandidates.put(currUser, prj);
//            _log.debug("Added the candidate " + currUser.toString());
            iter++;
        }
        return true;
    }

    /**
     * get all projects in cloud
     * @return
     */
    private static boolean createProjectsInstance() {
        try {
            projectsMGR = new ProjectsManager(cloudServer);
//            projectsMGR.createProjectByAPI();

        } catch (Exception e) {
            System.out.println("Had an issue while creating the projectsMGR");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static User getRandomUser(){
        List<User> keysAsArray = new ArrayList<User>(getRunningCandidates().keySet());
        Random r = new Random();
        return keysAsArray.get(r.nextInt(keysAsArray.size()));
    }
}
