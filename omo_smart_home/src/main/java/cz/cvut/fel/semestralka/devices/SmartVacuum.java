package cz.cvut.fel.semestralka.devices;

import cz.cvut.fel.semestralka.devices.visitors.DeviceConsumptionVisitor;

public class SmartVacuum extends Device {
    private int battery = 100;


    public SmartVacuum(String id, String description) {
        super(id, description, 10);
    }


    @Override
    public void accept(DeviceConsumptionVisitor visitor) {
        visitor.visit(this);
    }
}
