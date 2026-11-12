package org.transformice.database;

// Imports
import org.psframework.database.PSDatabase;
import org.psframework.database.providers.PSMongoDatabase;

public final class GameDatabaseManagerFactory {
    /**
     * Initializes the database manager factory.
     */
    public GameDatabaseManagerFactory() {

    }

    /**
     * Creates a new database manager instance.
     * @param database The database object.
     * @return Database manager instance.
     */
    public static GameDatabaseManager create(PSDatabase database, boolean isDebug) {
        if (database instanceof PSMongoDatabase mongoDatabase)
            return new MongoGameDatabaseManager(mongoDatabase, isDebug);

        throw new IllegalArgumentException("Unsupported database provider: " + database.getClass().getName());
    }
}