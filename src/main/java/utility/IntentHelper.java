package utility;

import java.util.Hashtable;

public class IntentHelper {

    private static IntentHelper myInstance;
    private Hashtable<String, Object> myHashTable;

    private IntentHelper() {
        myHashTable = new Hashtable<>();
    }

    public static IntentHelper getInstance() {
        if(myInstance == null) myInstance = new IntentHelper();
        return myInstance;
    }

    public void addObjectForKey(Object object, String key) {
        this.myHashTable.put(key, object);
    }

    public Object getObjectForKey(String key) {
        Object data = this.myHashTable.get(key);
        this.myHashTable.remove(key);
        return data;
    }
}