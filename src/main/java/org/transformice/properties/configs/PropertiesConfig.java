package org.transformice.properties.configs;

// Imports
import org.transformice.libraries.JsonLoader;
import org.transformice.properties.Property;

public final class PropertiesConfig implements Property {
    private PropertiesClass propertiesInstance;

    @Override
    public Object getInstance() {
        return this.propertiesInstance;
    }

    @Override
    public void loadFile() {
        this.propertiesInstance = JsonLoader.loadJson("properties.json", PropertiesClass.class);
        if(this.propertiesInstance == null) {
            System.exit(1);
        }
    }

    @Override
    public void saveFile() {

    }

    public static class PropertiesClass {
        public String database_url = "mongodb://localhost:27017";
        public String collection_name = "transformice";
        public boolean is_debug = true;
        public boolean twitchStreaming;
        public String flyerName;
        public boolean allow_email = true;
        public boolean legacy_login = false;
        public boolean beta_login = false;
        public boolean use_tag_system = true;
        public int login_attempts;
        public EventInfo event;
        public int map_editor_cheese_amount = 40;
        public Timers timers;

        public static class TimerObject {
            public boolean enable = false;
            public int delay = 0;
        }

        public static class Timers {
            public TimerObject keep_alive = new TimerObject();
            public TimerObject create_account = new TimerObject();
        }

        public static class EventInfo {
            public int banner_id;
            public String banner_bg_img_legacy;
            public String banner_fg_img_legacy;
        }
    }
}