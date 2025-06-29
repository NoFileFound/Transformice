package org.transformice.properties.configs;

// Imports
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.transformice.libraries.JsonLoader;
import org.transformice.properties.Property;

public final class InventoryConfig implements Property {
    private final Int2ObjectMap<ConsumableInfo> inventoryInstance = new Int2ObjectOpenHashMap<>();

    @Override
    public Object getInstance() {
        return this.inventoryInstance;
    }

    @Override
    public void loadFile() {
        Map<Integer, ConsumableInfo> tmp = JsonLoader.loadJson("server/inventory.json", new TypeToken<Int2ObjectMap<ConsumableInfo>>() {}.getType());
        if(tmp != null) {
            this.inventoryInstance.putAll(tmp);
        }
    }

    @Override
    public void saveFile() {

    }

    public static class ConsumableInfo {
        public int sort;
        public int priority;
        public int limit;
        public int countdown;
        public boolean fromEvent;
        public boolean canTrade;
        public boolean canUse;
        public boolean canEquip;
        public boolean canUseWhenDead;
        public int category;
        public String images;

        // special
        public List<String> prize; // for chests
    }
}