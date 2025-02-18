package org.transformice.properties;

public interface Property {
    Object getInstance();

    /**
     * Loads the config file.
     */
    void loadFile();

    /**
     * Saves the config file.
     */
    void saveFile();
}