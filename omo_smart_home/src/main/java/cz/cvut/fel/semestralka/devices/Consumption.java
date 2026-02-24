package cz.cvut.fel.semestralka.devices;

import lombok.Getter;

import java.time.Instant;
import java.util.Objects;

public final class Consumption {


    @Getter
    private final ResourceType resource;
    @Getter
    private final double amount;
    @Getter
    private final String unit;
    @Getter
    private final Instant timestamp;

    public Consumption(ResourceType resource, double amount, String unit, Instant timestamp) {
        this.resource = Objects.requireNonNull(resource);
        this.amount = amount;
        this.unit = unit == null ? "" : unit;
        this.timestamp = timestamp == null ? Instant.now() : timestamp;
    }

    public Consumption(ResourceType resource, double amount, String unit) {
        this(resource, amount, unit, Instant.now());
    }

    @Override
    public String toString() {
        return "Consumption{" +
                "resource=" + resource +
                ", amount=" + amount +
                ", unit='" + unit + '\'' +
                ", ts=" + timestamp +
                '}';
    }
}
