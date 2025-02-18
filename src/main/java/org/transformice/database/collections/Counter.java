package org.transformice.database.collections;

// Imports
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(value = "counters", useDiscriminator = false)
@Getter
public final class Counter {
    private @Id String id;
    @Setter private Long value;
}