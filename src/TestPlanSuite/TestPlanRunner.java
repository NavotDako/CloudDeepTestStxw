package TestPlanSuite;

import MyMain.BaseRunner;
import MyMain.Main;
import TestPlanSuite.cloudEntities.Project;
import TestPlanSuite.cloudEntities.ProjectsManager;
import TestPlanSuite.cloudEntities.User;
import Utils.Utilities;
import org.junit.Assert;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import java.io.PrintWriter;
import java.util.*;

public class TestPlanRunner extends BaseRunner {

    private Map<User, Project> runningCandidates = null;
    private ProjectsManager projectsMGR = null;

    public TestPlanRunner(int i, PrintWriter overallSummaryWriter, PrintWriter overallWriter) {

        super("TestPlanRunner", i, overallSummaryWriter, overallWriter);

    }

    @Override
    public void run() {
        pw = Utilities.CreateReportFile(this, iteration);
        Utilities.log(this,"Starting Thread Num - " + iteration + " - Thread Name is - " + Thread.currentThread().getName());
        setupRun();
        while (true) {
            this.testClass = getTestPlanAction(rand.nextInt(enums.TestPlanActions.length));
            Utilities.log(this, testClass.getName());

            testName = testClass.getSimpleName();
            testPlanUser = getRandomUser();
            Utilities.log(this, testPlanUser.toString());
            project = getRunningCandidates().get(testPlanUser);

            Long currTime = System.currentTimeMillis();
            testName = testName + " " + currTime;

            Result r = JUnitCore.runClasses(testClass);

            GoToSleep(20);
        }
    }

    public Class getTestPlanAction(int test) {
        return Main.enums.TestPlanActions[test];
    }



    public Map<User, Project> getRunningCandidates() {
        return runningCandidates;
    }

       public void setupRun() {

        Assert.assertTrue(createProjectsInstance());
        Utilities.log(currentThread().getName() + " Completed createProjectsInstace method");
        Assert.assertTrue(pickRunningCandidates());
        Utilities.log(currentThread().getName() + "Completed picking the candidates");


//        runTests();
    }

    /**
     * Randomly chooses a set of 5 users to run the suites, those users may be from different projects
     *
     * @return
     */
    private boolean pickRunningCandidates() {
        User currUser;
        runningCandidates = new HashMap<>();
        ArrayList<Project> projects = projectsMGR.getProjects();
        int iter = 0;
//        try {
//            runningCandidates.put(new User(Main.cloudServer, "khaleda", "15", "2"), projectsMGR.getProject("2"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        while (runningCandidates.size() < 5 && iter < 5000) {
            Project prj = projects.get((int) (Math.random() * projects.size()));
            if (prj.getProjId().equals("2")) {
                continue;
            }
            if(runningCandidates.values().contains(prj.getProjId())){
                continue;
            }
            ArrayList<User> users = prj.getProjUsers();
            if (users.size() == 0) continue;
            currUser = users.get((int) (Math.random() * users.size()));
            runningCandidates.put(currUser, prj);
//            _log.debug("Added the candidate " + currUser.toString());
            iter++;
        }
        return true;
    }

    /**
     * get all projects in cloud
     *
     * @return
     */
    private boolean createProjectsInstance() {
        try {
            projectsMGR = new ProjectsManager(Main.cs);
//            projectsMGR.createProjectByAPI();

        } catch (Exception e) {
            Utilities.log(this, "Had an issue while creating the projectsMGR");
            Utilities.log(e);
            return false;
        }
        return true;
    }

    public User getRandomUser() {
        List<User> keysAsArray = new ArrayList<User>(getRunningCandidates().keySet());
        Random r = new Random();
        return keysAsArray.get(r.nextInt(keysAsArray.size()));
    }
}


