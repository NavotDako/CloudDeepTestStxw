package Framework;

public class IdentificationMethods {
    public enum identificationsZone{
        INSTRUMENTED, NON_INSTRUMENTED, WEB, TEXT_ZONE
    }
    public enum environment{
        SEETEST,APPIUM
    }
    public enum elementsMapTranslator {
        ELEMENT,IDENTIFICATION,ZONE
    }
    public enum devicesMapTranslator{
        OS, VERSION, STATUS
    }
    public enum identifications{
        ID, XPATH, TEXT, HINT, PLACE_HOLDER, ACCESSIBLITY_LABEL
    }
    public enum appSource{
        CLOUD, PATH
    }
}
