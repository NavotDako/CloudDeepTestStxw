package Applications;

import Framework.CommandTranslator;
import Framework.IdentificationMethods;
import Framework.QuadMap;
import STGridSuite.STGridRunner;
import com.experitest.client.Client;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.ScreenOrientation;

import static Framework.IdentificationMethods.identifications.*;
import static Framework.IdentificationMethods.identificationsZone.*;
public class EriBankAndroid{

    Client client;
    CommandTranslator commandTranslator;
    STGridRunner runner;
    QuadMap<String, String, IdentificationMethods.identifications, IdentificationMethods.identificationsZone> eriElements = new QuadMap();
    public EriBankAndroid(STGridRunner runner, Client client, AppiumDriver driver){
        this.client = client;
        this.runner = runner;
        commandTranslator = new CommandTranslator(runner, client, driver);
        try {
            this.runner = runner;
            setupNonInst();
            setupInst();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public String getAppToInstall(){
//        if(Math.random() > Math.random()) {
//            return "http://192.168.2.72:8181/AndroidApps/eribank.apk";
//        }else{
//            return "cloud:com.experitest.eribank/com.experitest.ExperiBank.LoginActivity";
//        }
//    }
    public void installAndLaunch(boolean instrument) {
//        if(Math.random() > Math.random()) {//TODO remove
        if(false) {
            client.install("http://192.168.2.72:8181/AndroidApps/eribank.apk", instrument, false);
            client.launch("com.experitest.eribank/com.experitest.ExperiBank.LoginActivity", instrument, true);
        }
        else{
            client.install("cloud:com.experitest.eribank/com.experitest.ExperiBank.LoginActivity", instrument, false);
            client.launch("cloud:com.experitest.eribank/com.experitest.ExperiBank.LoginActivity", instrument, true);
        }
    }

    public void setupNonInst() throws Exception {
        //Build map - ElementName, element identifictation, element identification method, element zone

        //Non Instrumented elements
        eriElements.put("username_non", "usernameTextField", ID, NON_INSTRUMENTED);
        eriElements.put("password_non", "passwordTextField", ID, NON_INSTRUMENTED);
        eriElements.put("login_non", "loginButton", ID, NON_INSTRUMENTED);

        // logged in  page
        eriElements.put("makePayment_non", "//*[@text='Make Payment']", XPATH, NON_INSTRUMENTED);
        eriElements.put("mortgageReques_non", "//*[@text='Mortgage Request']", XPATH, NON_INSTRUMENTED);
        eriElements.put("expenseReport_non", "//*[@text='Expense Report']", XPATH, NON_INSTRUMENTED);
        eriElements.put("logout_non", "xpath=//*[@id='logoutButton']", XPATH, NON_INSTRUMENTED);

        //Non Instrumented elements - Make Payment Page
        eriElements.put("phone_non", "phoneTextField", ID, NON_INSTRUMENTED);
        eriElements.put("name_non", "xpath=//*[@resource-id='com.experitest.eribank:id/nameTextField']", XPATH, NON_INSTRUMENTED);
        eriElements.put("amount_non", "Amount", TEXT, NON_INSTRUMENTED);
        eriElements.put("select_non", "Select", TEXT, NON_INSTRUMENTED);
        eriElements.put("norway_non", "//*[@text='Norway']", XPATH , NON_INSTRUMENTED);
        eriElements.put("send_payment_non", "sendPaymentButton", ID, NON_INSTRUMENTED);
        eriElements.put("cancel_non", "Cancel", TEXT, NON_INSTRUMENTED);
        eriElements.put("yes_non", "//*[@text='Yes']", XPATH, NON_INSTRUMENTED);

        //Non Instrumented elements - Mortgage request Page
        eriElements.put("first_name_non", "nameTextField", ID, NON_INSTRUMENTED);
        eriElements.put("last_name_non", "xpath=//*[@id='lastNameTextField']", XPATH, NON_INSTRUMENTED);
        eriElements.put("age_non", "xpath=//*[@id='ageTextField']", XPATH, NON_INSTRUMENTED);
        eriElements.put("address_one_non", "xpath=//*[@id='addressOneTextField']", XPATH, NON_INSTRUMENTED);
        eriElements.put("address_two_non", "xpath=//*[@id='addressTwoTextField']]", XPATH, NON_INSTRUMENTED);
        eriElements.put("next_non", "nextButton", ID, NON_INSTRUMENTED);

        eriElements.put("home_non", "Home", TEXT, NON_INSTRUMENTED);
        eriElements.put("car_non", "Car", TEXT, NON_INSTRUMENTED);
        eriElements.put("personal_non", "Personal", TEXT, NON_INSTRUMENTED);
        eriElements.put("education_non", "Education",TEXT, NON_INSTRUMENTED);
        eriElements.put("professional_non", "Professional", TEXT, NON_INSTRUMENTED);
        eriElements.put("five_years_non", "5", TEXT, NON_INSTRUMENTED);
        eriElements.put("agricultural_non", "Agricultural", TEXT, NON_INSTRUMENTED);
        eriElements.put("checkedOccupation_non", "//*[@id='typeOfOccupationListView']/*[@checked='true']", XPATH, NON_INSTRUMENTED);
        eriElements.put("yearly_income_non", "//*[@text='2,000,000']", XPATH, NON_INSTRUMENTED);
        eriElements.put("back_non", "backButton", ID, NON_INSTRUMENTED);
        eriElements.put("save_non", "Save", TEXT, NON_INSTRUMENTED);

        //Non Instrumented elements - Expense report Page
        eriElements.put("add_non", "addButton", ID, NON_INSTRUMENTED);
        eriElements.put("expense_non", "//*[@text=concat('Press ', \"'\", 'Add', \"'\", ' to add row')]", XPATH, NON_INSTRUMENTED);
    }
    public void setupInst() throws Exception {
        //Build map - ElementName, element identifictation, element identification method, element zone

        //Non Instrumented elements
        eriElements.put("username", "usernameTextField", ID, INSTRUMENTED);
        eriElements.put("password", "passwordTextField", ID, NON_INSTRUMENTED);
        eriElements.put("login", "loginButton", ID, INSTRUMENTED);

        // logged in  page
        eriElements.put("makePayment", "//*[@text='Make Payment']", XPATH, INSTRUMENTED);
        eriElements.put("mortgageReques", "//*[@text='Mortgage Request']", XPATH, INSTRUMENTED);
        eriElements.put("expenseReport", "//*[@text='Expense Report']", XPATH, INSTRUMENTED);
        eriElements.put("logout", "logout", IdentificationMethods.identifications.TEXT, TEXT_ZONE);
        eriElements.put("balance", "//*[@nodeName='H1']", XPATH, WEB);

        //Non Instrumented elements - Make Payment Page
        eriElements.put("phone", "phoneTextField", ID, INSTRUMENTED);
        eriElements.put("name", "//*[@id='nameTextField']", XPATH, INSTRUMENTED);
        eriElements.put("amount", "Amount", HINT, INSTRUMENTED);
        eriElements.put("select", "Select", TEXT, INSTRUMENTED);
        eriElements.put("norway", "//*[@text='Norway']", XPATH , INSTRUMENTED);
        eriElements.put("send_payment", "sendPaymentButton", ID, INSTRUMENTED);
        eriElements.put("cancel", "Cancel", TEXT, INSTRUMENTED);
        eriElements.put("yes", "//*[@text='Yes']", XPATH, INSTRUMENTED);

        //Non Instrumented elements - Mortgage request Page
        eriElements.put("first_name", "nameTextField", ID, INSTRUMENTED);
        eriElements.put("last_name", "//*[@id='lastNameTextField']", XPATH, INSTRUMENTED);
        eriElements.put("age", "//*[@id='ageTextField']", XPATH, INSTRUMENTED);
        eriElements.put("address_one", "//*[@id='addressOneTextField']", XPATH, INSTRUMENTED);
        eriElements.put("address_two", "//*[@id='addressTwoTextField']]", XPATH, INSTRUMENTED);
        eriElements.put("next", "nextButton", ID, INSTRUMENTED);

        eriElements.put("home", "Home", TEXT, INSTRUMENTED);
        eriElements.put("car", "Car", TEXT, INSTRUMENTED);
        eriElements.put("personal", "Personal", TEXT, INSTRUMENTED);
        eriElements.put("education", "Education",TEXT, INSTRUMENTED);
        eriElements.put("professional", "Professional", TEXT, INSTRUMENTED);
        eriElements.put("five_years", "5", TEXT, INSTRUMENTED);
        eriElements.put("agricultural", "Agricultural", TEXT, INSTRUMENTED);
        eriElements.put("checkedOccupation", "//*[@id='typeOfOccupationListView']/*[@checked='true']", XPATH, INSTRUMENTED);
        eriElements.put("yearly_income", "//*[@text='2,000,000']", XPATH, INSTRUMENTED);
        eriElements.put("back", "backButton", ID, INSTRUMENTED);
        eriElements.put("save", "Save", TEXT, INSTRUMENTED);

        //Non Instrumented elements - Expense report Page
        eriElements.put("add", "addButton", ID, INSTRUMENTED);
        eriElements.put("expense", "//*[@text=concat('Press ', \"'\", 'Add', \"'\", ' to add row')]", XPATH, INSTRUMENTED);
    }

    public boolean doLogin(IdentificationMethods.environment environment, boolean instrumented) throws Exception {
        this.commandTranslator.setOrientation(environment, ScreenOrientation.PORTRAIT);
        String prefix;
        prefix = instrumented ? "" : "_non";
        this.commandTranslator.waitForElement(eriElements, "username" + prefix, environment, 10);
        this.commandTranslator.elementSendText(eriElements, "username" + prefix, environment, "company");
        this.commandTranslator.elementSendText(eriElements, "password" + prefix, environment, "company");
        this.commandTranslator.click(eriElements,"login" + prefix, environment);
        return this.commandTranslator.waitForElement(eriElements, "makePayment" + prefix, environment, 10);
    }

    public boolean doPayment(IdentificationMethods.environment environment, boolean instrumented) throws Exception {
        String prefix;
        prefix = instrumented ? "" : "_non";
        this.commandTranslator.click(eriElements,"makePayment" + prefix, environment);
        this.commandTranslator.verifyElementFound(eriElements, "phone" + prefix, environment);
        this.commandTranslator.elementSendText(eriElements, "phone" + prefix, environment, "050-7937021");
        this.commandTranslator.verifyElementFound(eriElements, "name" + prefix, environment);
        this.commandTranslator.elementSendText(eriElements, "name" + prefix, environment, "Long Run");
        this.commandTranslator.verifyElementFound(eriElements, "amount" + prefix, environment);
        this.commandTranslator.elementSendText(eriElements, "amount" + prefix, environment, "100");

        this.commandTranslator.verifyElementFound(eriElements, "select" + prefix, environment);
        this.commandTranslator.click(eriElements,"select" + prefix, environment);

        this.commandTranslator.waitForElement(eriElements, "norway" + prefix, environment, 10);
        this.commandTranslator.click(eriElements,"norway" + prefix, environment);

        this.commandTranslator.verifyElementFound(eriElements, "send_payment" + prefix, environment);
        this.commandTranslator.click(eriElements, "send_payment" + prefix, environment);
        this.commandTranslator.click(eriElements, "yes" + prefix, environment);

        return this.commandTranslator.waitForElement(eriElements, "makePayment" + prefix, environment, 10);
    }

    public boolean doLogout(IdentificationMethods.environment environment, boolean instrumented) throws Exception {
        String prefix;
        prefix = instrumented ? "" : "_non";
        this.commandTranslator.click(eriElements, "logout" + prefix, environment);
        return this.commandTranslator.waitForElement(eriElements, "username" + prefix, environment, 10);
    }
    public void doUninstall(){
        client.uninstall("com.experitest.ExperiBank");
    }

}
