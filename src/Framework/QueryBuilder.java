package Framework;

import static Framework.IdentificationMethods.identificationsZone.INSTRUMENTED;
import static Framework.IdentificationMethods.identificationsZone.NON_INSTRUMENTED;
import static Framework.IdentificationMethods.identificationsZone.TEXT_ZONE;

public class QueryBuilder {

    static String getSeeTestFriendlyQuery(QuadMap map, String element ) throws Exception {
        if(map.get(element, IdentificationMethods.elementsMapTranslator.ZONE).equals(IdentificationMethods.identificationsZone.TEXT_ZONE)){
            return element;
        }
        String identificationMethod = getIdMethod(map, element);
        String elem = (String) map.get(element, IdentificationMethods.elementsMapTranslator.ELEMENT);
        String query = "";
        query += identificationMethod;
        query += "=";
        query += elem;
        return query;
    }
    static String getSeeTestZone(QuadMap map, String element ){
        Object id = map.get(element, IdentificationMethods.elementsMapTranslator.ZONE);
        if(id.equals(TEXT_ZONE)){
            return "TEXT";
        }
        return (id.equals(NON_INSTRUMENTED) || id.equals(INSTRUMENTED)) ? "NATIVE" : "WEB";
    }
    static String getIdMethod(QuadMap map, String element) throws Exception {
        switch ((IdentificationMethods.identifications)map.get(element, IdentificationMethods.elementsMapTranslator.IDENTIFICATION)){
            case XPATH:
                return "xpath";
            case TEXT:
                return "text";
            case PLACE_HOLDER:
                return "placeholder";
            case ACCESSIBLITY_LABEL:
                return "accessibilityLabel";
            case ID:
                return "id";
            case HINT:
                return "hint";
        }
        throw new Exception("Missing an identification method");
    }
}
