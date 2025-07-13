package org.transformice.properties.configs;

// Imports
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.transformice.libraries.JsonLoader;
import org.transformice.properties.Property;

public final class VillageNpcsConfig implements Property {
    private Map<String, VillageNPC> villageNpcsInstance;

    @Override
    public Object getInstance() {
        return this.villageNpcsInstance;
    }

    @Override
    public void loadFile() {
        this.villageNpcsInstance = JsonLoader.loadJson("server/village_npcs.json", new TypeToken<Map<String, VillageNPC>>() {}.getType());
        if(this.villageNpcsInstance == null) {
            this.villageNpcsInstance = new HashMap<>();
        }
    }

    @Override
    public void saveFile() {

    }

    public static class VillageNPC {
        public int title_id;
        public boolean feiminine;
        public String look;
        public int x;
        public int y;
        public int emote;
        public boolean facing_right;
        public boolean face_player;
        public int npc_interface;
        public String message;
        public int isVillage;
        public ArrayList<VillageNPCShopInfo> items;
    }


    public static class VillageNPCShopInfo {
        public int type;
        public int item_id;
        public int quantity;
        public int cost_type;
        public int cost_id;
        public int cost_quantity;
        public String hover_text_template;
        public String hover_text_args;
    }
}