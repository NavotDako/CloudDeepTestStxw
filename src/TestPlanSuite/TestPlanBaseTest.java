package TestPlanSuite;

import MyMain.*;
import Utils.Utilities;
import TestPlanSuite.cloudEntities.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by khaled.abbas on 11/27/2017.
 */
public abstract class TestPlanBaseTest extends BaseBaseTest {

    protected RestAPIBuilder restAPIBuilder;

    protected TestPlan testPlanToRun;
    protected boolean changeQueries;

    protected String url;
    protected CloseableHttpClient httpClient;
    protected HttpPost uploadFile;
    protected MultipartEntityBuilder builder;
    protected SeleniumHelper seleniumHelper;
    protected Project testPlanProject;
    protected User user;
    @Override
    protected boolean testingOnADevice() {
        return false;
    }
    @Before
    public void setup() {
        runner = (TestPlanRunner) Thread.currentThread();


        //Update project test plans
        testPlanProject = new Project(runner.project);
        user = runner.testPlanUser;
        seleniumHelper = new SeleniumHelper(this.user, testPlanProject, (BaseRunner) Thread.currentThread());
        this.testPlanProject.readAndUpdateTestPlansFromProject(runner.testPlanUser, this.seleniumHelper);
//        this.runTestPlanFromUI = fromAPI;


        Utilities.log(runner, "building rest api request");
        restAPIBuilder = new RestAPIBuilder(Main.cs);
        url = restAPIBuilder.getPrefix() + restAPIBuilder.getCloudIP() + ":" + restAPIBuilder.getCloudPort() + "/api/v1/execution-plan/execute-test-plan";
        httpClient = HttpClients.createDefault();
        uploadFile = new HttpPost(url.toString());
        builder = MultipartEntityBuilder.create();
    }


    protected String runAPITest() throws IOException {
        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);
        uploadFile.addHeader("Authorization", "Basic " + restAPIBuilder.getAuthStringEnc());
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(uploadFile);
        } catch (IOException e) {
            Utilities.log(runner, "got exception while executing API CMD");
            Utilities.log(e);
            throw e;
        }
        HttpEntity responseEntity = response.getEntity();
        java.io.InputStream stream = null;
        try {
            stream = responseEntity.getContent();
        } catch (IOException e) {
            Utilities.log(runner, "got exception while reading from response entity resources");
            Utilities.log(e);
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String inputLine;
        StringBuffer responseBuffer = new StringBuffer();
        try {
            while ((inputLine = in.readLine()) != null) {
                responseBuffer.append(inputLine);
            }
        } catch (IOException e) {
            Utilities.log(runner, "got exception while reading from input");
            Utilities.log(e);
            throw e;
        }
        try {
            in.close();
        } catch (IOException e) {
            Utilities.log(runner, "got exception while closing resources");
            Utilities.log(e);
            throw e;
        }
        String finalString = responseBuffer.toString();
        Utilities.log(runner, finalString);

        Utilities.log(runner, String.format("Got response buffer: %s", responseBuffer.toString()));
        Assert.assertTrue("Did not get Success Status", responseBuffer.toString().contains("\"status\":\"SUCCESS\""));
        return finalString;
    }


    protected void createTestRequest() {
        this.testPlanProject.createTestPlansForProject(this.user, this.seleniumHelper);
    }


    /**
     * This function will verify the project has a test plan with the correct os, Other wise it will create one
     */

    protected boolean isThereTestPlanInProject() {
        return this.testPlanProject.doesProjectHaveTestPlans(this.user, this.seleniumHelper);
    }

    public void setTestPlanToRun(TestPlan testPlan) {
        this.testPlanToRun = testPlan;
    }

    @After
    public void tearDown() {
        Utilities.log(runner, "Finishing");
        this.seleniumHelper.perish();
    }
}
