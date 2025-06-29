package org.transformice.properties.configs;

// Imports
import org.transformice.libraries.JsonLoader;
import org.transformice.properties.Property;

public final class SwfConfig implements Property {
    private SWFClass swfClassInstance;

    @Override
    public Object getInstance() {
        return this.swfClassInstance;
    }

    @Override
    public void loadFile() {
        this.swfClassInstance = JsonLoader.loadJson("server/swf.json", SWFClass.class);
        if(this.swfClassInstance == null) {
            System.exit(1);
        }
    }

    @Override
    public void saveFile() {

    }

    public static class SWFClass {
        public int version;
        public java.util.ArrayList<Integer> ports;
        public String connection_key;
        public java.util.List<Integer> packet_keys;
        public java.util.List<Long> login_keys;
        public int authorization_key;
        public String swf_url = "";
    }
}