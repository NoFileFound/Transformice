package org.transformice.properties.configs;

// Imports
import java.util.ArrayList;
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
        public boolean beta_login = false;
        public boolean use_tag_system = true;
        public int login_attempts;
        public String yt_key;
        public int map_editor_cheese_amount = 40;
        public int max_players = -1;
        public ArrayList<Integer> shop_sprites = new ArrayList<>();
        public ArrayList<Integer> shaman_sprites = new ArrayList<>();
        public EventInfo event;
        public Timers timers;
        public GameDefaultConstants constants;
        public SMTP email_info;

        public static class Timers {
            public TimerObject keep_alive = new TimerObject();
            public TimerObject create_account = new TimerObject();
            public TimerObject reload_cafe = new TimerObject();
            public TimerObject create_cafe_topic = new TimerObject();
            public TimerObject create_cafe_post = new TimerObject();
            public TimerObject marriage = new TimerObject();
            public TimerObject chat_message = new TimerObject();
            public TimerObject skip_music = new TimerObject();
        }

        public static class GameDefaultConstants {
            public int default_shop_cheeses;
            public int default_shop_fraises;
            public int default_normal_saves;
            public int default_hard_saves;
            public int default_divine_saves;
            public int default_normal_saves_noskill;
            public int default_hard_saves_noskill;
            public int default_divine_saves_noskill;
            public int default_firsts;
            public int default_cheeses;
            public int default_shaman_cheeses;
            public int default_bootcamps;
        }

        public static class TimerObject {
            public boolean enable = false;
            public int delay = 0;
        }

        public static class EventInfo {
            public int adventure_id;
            public String event_name;
            public String event_cheese_suffix;
            public String event_shop_news_file_id;
            public String decoration_list_left_image;
            public int decoration_list_left_color;
            public String decoration_list_right_image;
            public int decoration_list_right_color;
            public int banner_id;
            public String banner_bg_img_legacy;
            public String banner_fg_img_legacy;
            public int event_delay;
            public int minimum_players;
            public int event_points;
            public ArrayList<EventAdventureTasks> adventure_tasks;
            public ArrayList<Integer> adventure_progress;
        }

        public static class EventAdventureTasks {
            public int task_consumable_id;
            public int task_finish_points;
            public int task_progess_type;
            public int task_progess_type2_minimum;
            public int task_progess_type1_tooltip_id;
        }

        public static class SMTP {
            public String smtpHost;
            public Integer smtpPort;
            public String smtpUsername;
            public String smtpPassword;
        }
    }
}