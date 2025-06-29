package org.transformice.database.collections;

// Imports
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(value = "counters", useDiscriminator = false)
@Getter
public final class Counter {
    private final @Id String id;
    @Setter private Long value;

    /**
     * Creates a new counter.
     * @param id The counter id.
     * @param value The counter value.
     */
    public Counter(final String id, final Long value) {
        this.id = id;
        this.value = value;
    }
}