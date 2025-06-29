package org.transformice.properties.configs;

// Imports
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import org.transformice.libraries.JsonLoader;
import org.transformice.properties.Property;

public final class VillageNpcsConfig implements Property {
    private ArrayList<VillageNPC> villageNpcsInstance;

    @Override
    public Object getInstance() {
        return this.villageNpcsInstance;
    }

    @Override
    public void loadFile() {
        this.villageNpcsInstance = JsonLoader.loadJson("server/village_npcs.json", new TypeToken<ArrayList<VillageNPC>>() {}.getType());
        if(this.villageNpcsInstance == null) {
            this.villageNpcsInstance = new ArrayList<>();
        }
    }

    @Override
    public void saveFile() {

    }

    public static class VillageNPC {
        public String name;
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
    }
}