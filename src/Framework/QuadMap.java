package Framework;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by khaled.abbas on 7/24/2017.
 */
public class QuadMap <K, V1, V2, V3 >{
    List<K> keys;
    List<V1> values1;
    List<V2> values2;
    List<V3> values3;

    public QuadMap(){

        keys = new LinkedList<K>();
        values1 = new LinkedList<V1>();
        values2 = new LinkedList<V2>();
        values3 = new LinkedList<V3>();
    }
    public int size() {
        return keys.size();
    }

    public List<?> listKeys(){
        return keys;
    }

    public boolean isEmpty() {
        return keys.size() == 0;
    }


    public boolean containsKey(Object key) {
        return keys.contains(key);
    }


    public boolean containsValue(Object value, IdentificationMethods.elementsMapTranslator valueIDX) {
        switch (valueIDX){
            case ELEMENT:
                return values1.contains(value);
            case IDENTIFICATION:
                return values2.contains(value);
            case ZONE:
                return values3.contains(value);
        }
        return false;
    }

    public Object get(Object key, IdentificationMethods.elementsMapTranslator valueIDX) {
        switch (valueIDX){
            case ELEMENT:
                return values1.get(keys.indexOf(key));
            case IDENTIFICATION:
                return values2.get(keys.indexOf(key));
            case ZONE:
                return values3.get(keys.indexOf(key));
        }
        return null;
    }

    public Object get(Object key, IdentificationMethods.devicesMapTranslator valueIDX) {
        switch (valueIDX){
            case OS:
                return values1.get(keys.indexOf(key));
            case VERSION:
                return values2.get(keys.indexOf(key));
            case STATUS:
                return values3.get(keys.indexOf(key));
        }
        return null;
    }
    public synchronized void put(K key, V1 value1, V2 value2, V3 value3) throws Exception {
        if (keys.contains(key)){
            throw new Exception("Element already exists");
        }
        keys.add(keys.size(), key);
        values1.add(values1.size(), value1);
        values2.add(values2.size(), value2);
        values3.add(values3.size(), value3);
    }



    public Object getKey(int idx){
        return keys.get(idx);
    }
}
