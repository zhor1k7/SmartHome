package cz.cvut.fel.semestralka.devices;

import cz.cvut.fel.semestralka.devices.visitors.DeviceConsumptionVisitor;

public class SmartKettle extends Device {
    private double waterLiters = 0.0;
    private final double capacity = 1.5;


    public SmartKettle(String id, String description) {
        super(id, description, 10);
    }


    @Override
    public void run() {

        double cup = 0.25; // одна чашка

        if (waterLiters < cup) {
            refill(capacity);
        }

        double kwh = (getBasePower() / 10.0);
        recordConsumption(ResourceType.ELECTRICITY, kwh, "kWh");

        waterLiters -= cup;
    }

    public void refill(double liters) {
        if (liters <= 0) return;

        double added = Math.min(capacity - waterLiters, liters);
        if (added <= 0) return;

        waterLiters += added;

        recordConsumption(ResourceType.WATER, added, "Liters");
    }


    @Override
    public void accept(DeviceConsumptionVisitor visitor) {
        visitor.visit(this);
    }
}
