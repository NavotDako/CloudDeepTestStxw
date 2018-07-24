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

public class EriBankIOS {
    STGridRunner runner;
    Client client;
    CommandTranslator commandTranslator;
    QuadMap<String, String, IdentificationMethods.identifications, IdentificationMethods.identificationsZone> eriElements = new QuadMap();
    public EriBankIOS(STGridRunner runner, Client client, AppiumDriver driver){
        this.client = client;
        commandTranslator = new CommandTranslator(runner, client, driver);
        this.runner = runner;

        try {
            setupNonInst();
            setupInst();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void installAndLaunch(boolean instrument) {
        if(instrument){
            client.install("cloud:com.experitest.ExperiBank", instrument, false);
            client.launch("cloud:com.experitest.ExperiBank", instrument, true);
        }
        else {
            client.install("cloud:com.experitest.ExperiBankO", instrument, false);
            client.launch("cloud:com.experitest.ExperiBankO", instrument, true);
        }

    }


    public void setupNonInst() throws Exception {
        //Build map - ElementName, element identifictation, element identification method, element zone

        //Non Instrumented elements
        eriElements.put("username_non", "//*[@placeholder='Username']", XPATH, NON_INSTRUMENTED);
        eriElements.put("password_non", "//*[@value='Password']", XPATH, NON_INSTRUMENTED);
        eriElements.put("login_non", "xpath=//*[@value='loginButton']", XPATH, NON_INSTRUMENTED);

        // logged in  page
        eriElements.put("makePayment_non", "//*[@text='makePaymentButton']", XPATH, NON_INSTRUMENTED);
        eriElements.put("mortgageReques_non", "//*[@text='Mortgage Request']", XPATH, NON_INSTRUMENTED);
        eriElements.put("expenseReport_non", "//*[@text='Expense Report']", XPATH, NON_INSTRUMENTED);
        eriElements.put("logout_non", "xpath=//*[@id='logoutButton']", XPATH, NON_INSTRUMENTED);

        //Non Instrumented elements - Make Payment Page
        eriElements.put("phone_non", "xpath=//*[@placeholder='Phone']", XPATH, NON_INSTRUMENTED);
        eriElements.put("name_non", "//*[@placeholder='Name']", XPATH, NON_INSTRUMENTED);
        eriElements.put("amount_non", "xpath=//*[@placeholder='Amount']", XPATH, NON_INSTRUMENTED);
        eriElements.put("select_non", "xpath=//*[@name='countryButton']", XPATH, NON_INSTRUMENTED);
        eriElements.put("norway_non", "//*[@text='Norway']", XPATH , NON_INSTRUMENTED);
        eriElements.put("send_payment_non", "xpath=//*[@text='sendPaymentButton']", XPATH, NON_INSTRUMENTED);
        eriElements.put("cancel_non", "xpath=//*[@text='cancelButton']", XPATH, NON_INSTRUMENTED);
        eriElements.put("yes_non", "//*[@text='Yes']", XPATH, NON_INSTRUMENTED);

        //Non Instrumented elements - Mortgage request Page
        eriElements.put("first_name_non", "firstNameTextField", XPATH, NON_INSTRUMENTED);
        eriElements.put("last_name_non", "//*[@id='lastNameTextField']", XPATH, NON_INSTRUMENTED);
        eriElements.put("age_non", "xpath=//*[@id='ageTextField']", XPATH, NON_INSTRUMENTED);
        eriElements.put("address_one_non", "xpath=//*[@id='addressOneTextField']", XPATH, NON_INSTRUMENTED);
        eriElements.put("address_two_non", "xpath=//*[@id='addressTwoTextField']]", XPATH, NON_INSTRUMENTED);
        eriElements.put("next_non", "//*[@text='nextButton']", XPATH, NON_INSTRUMENTED);

        eriElements.put("home_non", "xpath=//*[@text='Home']", XPATH, NON_INSTRUMENTED);
        eriElements.put("car_non", "xpath=//*[@text='Car']", XPATH, NON_INSTRUMENTED);
        eriElements.put("personal_non", "xpath=//*[@text='Personal']", XPATH, NON_INSTRUMENTED);
        eriElements.put("education_non", "xpath=//*[@text='Education']",XPATH, NON_INSTRUMENTED);
        eriElements.put("professional_non", "xpath=//*[@text='Professional']", XPATH, NON_INSTRUMENTED);
        eriElements.put("five_years_non", "xpath=//*[@text='5']", XPATH, NON_INSTRUMENTED);
        eriElements.put("agricultural_non", "xpath=//*[@label='Agricultural']", XPATH, NON_INSTRUMENTED);
        eriElements.put("yearly_income_non", "//*[@text='2,000,000']", XPATH, NON_INSTRUMENTED);
        eriElements.put("back_non", "xpath=//*[@value='backButton']", XPATH, NON_INSTRUMENTED);
        eriElements.put("save_non", "xpath=//*[@label='saveButton']", XPATH, NON_INSTRUMENTED);

        //Non Instrumented elements - Expense report Page
        eriElements.put("add_non", "xpath=//*[@text='addButton']", XPATH, NON_INSTRUMENTED);
        eriElements.put("expense_non", "//*[@text='Expense 0']", XPATH, NON_INSTRUMENTED);


    }
    public void setupInst() throws Exception {
        //Build map - ElementName, element identifictation, element identification method, element zone

        //Non Instrumented elements
        eriElements.put("username", "Username", TEXT, INSTRUMENTED);
        eriElements.put("password", "Password", PLACE_HOLDER, NON_INSTRUMENTED);
        eriElements.put("login", "Login", ACCESSIBLITY_LABEL, INSTRUMENTED);

        // logged in  page
        eriElements.put("makePayment", "//*[@text='Make Payment']", XPATH, INSTRUMENTED);
        eriElements.put("mortgageReques", "//*[@text='Mortgage Request']", XPATH, INSTRUMENTED);
        eriElements.put("expenseReport", "//*[@text='Expense Report']", XPATH, INSTRUMENTED);
        eriElements.put("logout", "xpath=//*[@accessibilityLabel='Logout']", XPATH, INSTRUMENTED);
        eriElements.put("balance", "//*[@nodeName='H1']", XPATH, WEB);

        // Instrumented elements - Make Payment Page
        eriElements.put("phone", "Phone", ACCESSIBLITY_LABEL, INSTRUMENTED);
        eriElements.put("name", "xpath=//*[@accessibilityLabel='Name']", XPATH, INSTRUMENTED);
        eriElements.put("amount", "Amount", PLACE_HOLDER, INSTRUMENTED);
        eriElements.put("select", "Select", TEXT, INSTRUMENTED);
        eriElements.put("norway", "//*[@text='Norway']", XPATH , INSTRUMENTED);
        eriElements.put("send_payment", "//*[@text='Send Payment']", XPATH, INSTRUMENTED);
        eriElements.put("cancel", "Cancel", TEXT, INSTRUMENTED);
        eriElements.put("yes", "//*[@text='Yes']", XPATH, INSTRUMENTED);

        // Instrumented elements - Mortgage request Page
        eriElements.put("first_name", "//*[@text='First Name']", XPATH, INSTRUMENTED);
        eriElements.put("last_name", "//*[@text='Last Name']", XPATH, INSTRUMENTED);
        eriElements.put("age", "//*[@accessibilityLabel='Age']", XPATH, INSTRUMENTED);
        eriElements.put("address_one", "xpath=//*[@accessibilityLabel='Address 1']", XPATH, INSTRUMENTED);
        eriElements.put("address_two", "xpath=//*[@accessibilityLabel='Address 2']", XPATH, INSTRUMENTED);
        eriElements.put("next", "Next", ACCESSIBLITY_LABEL, INSTRUMENTED);

        eriElements.put("home", "Home", TEXT, INSTRUMENTED);
        eriElements.put("car", "Car", TEXT, INSTRUMENTED);
        eriElements.put("personal", "Personal", TEXT, INSTRUMENTED);
        eriElements.put("education", "Education",TEXT, INSTRUMENTED);
        eriElements.put("professional", "Professional", TEXT, INSTRUMENTED);
        eriElements.put("five_years", "5", TEXT, INSTRUMENTED);
        eriElements.put("agricultural", "Agricultural", TEXT, INSTRUMENTED);
        eriElements.put("yearly_income", "xpath=//*[@accessibilityLabel='2,000,000']", XPATH, INSTRUMENTED);
        eriElements.put("Back", "backButton", TEXT, INSTRUMENTED);
        eriElements.put("Save", "Save", TEXT, INSTRUMENTED);

        //Instrumented elements - Expense report Page
        eriElements.put("add", "xpath=//*[@text='Add']", XPATH, INSTRUMENTED);
        eriElements.put("expense", "=//*[@text='Type goes here']", XPATH, INSTRUMENTED);

        eriElements.put("EribankO_Warning", "//*[@text='“EriBankO” May Slow Down Your iPad']", XPATH, NON_INSTRUMENTED);
    }

    public boolean doLogin(IdentificationMethods.environment environment, boolean instrumented) throws Exception {
        String prefix;
        prefix = instrumented ? "" : "_non";
        this.commandTranslator.setOrientation(environment, ScreenOrientation.PORTRAIT);
        if (this.commandTranslator.isElementFound(eriElements, "EribankO_Warning", environment)) {
            this.commandTranslator.click(eriElements, "EribankO_Warning", environment) ;
        }
        this.commandTranslator.waitForElement(eriElements, "username" + prefix, environment, 10);
        this.commandTranslator.elementSendText(eriElements, "username" + prefix, environment, "company");
        this.commandTranslator.elementSendText(eriElements, "password" + prefix, environment, "company");
        this.commandTranslator.closeKeyboard(environment);
        this.commandTranslator.click(eriElements,"login" + prefix, environment);
        return this.commandTranslator.waitForElement(eriElements, "makePayment" + prefix, environment, 10);
    }

    public boolean doPayment(IdentificationMethods.environment environment, boolean instrumented) throws Exception {
        String prefix;
        prefix = instrumented ? "" : "_non";
        this.commandTranslator.setOrientation(environment, ScreenOrientation.LANDSCAPE);
        Thread.sleep(2000);

        this.commandTranslator.click(eriElements,"makePayment" + prefix, environment);
        this.commandTranslator.verifyElementFound(eriElements, "phone" + prefix, environment);
        this.commandTranslator.elementSendText(eriElements, "phone" + prefix, environment, "050-7937021");
        this.commandTranslator.closeKeyboard(environment);
        this.commandTranslator.verifyElementFound(eriElements, "name" + prefix, environment);
        this.commandTranslator.elementSendText(eriElements, "name" + prefix, environment, "Long Run");
        this.commandTranslator.closeKeyboard(environment);
        this.commandTranslator.verifyElementFound(eriElements, "amount" + prefix, environment);
        this.commandTranslator.elementSendText(eriElements, "amount" + prefix, environment, "100");
        this.commandTranslator.closeKeyboard(environment);

        this.commandTranslator.verifyElementFound(eriElements, "select" + prefix, environment);
        this.commandTranslator.click(eriElements,"select" + prefix, environment);

        this.commandTranslator.waitForElement(eriElements, "norway" + prefix, environment, 10);
        this.commandTranslator.click(eriElements,"norway" + prefix, environment);

        this.commandTranslator.setOrientation(environment, ScreenOrientation.PORTRAIT);
        this.commandTranslator.verifyElementFound(eriElements, "send_payment" + prefix, environment);
        this.commandTranslator.click(eriElements, "send_payment" + prefix, environment);
        this.commandTranslator.click(eriElements, "yes" + prefix, environment);

        return this.commandTranslator.waitForElement(eriElements, "makePayment" + prefix, environment, 10);
    }
    public boolean doLogout(IdentificationMethods.environment environment, boolean instrumented) throws Exception {
        String prefix;
        prefix = instrumented ? "" : "_non";
        this.commandTranslator.click(eriElements, "logout" + prefix, environment);
        return this.commandTranslator.waitForElement(eriElements, "login" + prefix, environment, 10);
    }
    public void doUninstall(){
        client.uninstall("com.experitest.ExperiBank");
    }
}
