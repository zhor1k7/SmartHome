package cz.cvut.fel.semestralka.devices;

import cz.cvut.fel.semestralka.devices.visitors.DeviceConsumptionVisitor;

public class SaunaStove extends Device {

    private static double temperature = 70;

    public SaunaStove(String id, String description) {
        super(id, description, 250);
    }

    public void setTemperature(int t) {
        temperature = t;
    }

    @Override
    public void accept(DeviceConsumptionVisitor visitor) {
        visitor.visit(this);
    }
}
