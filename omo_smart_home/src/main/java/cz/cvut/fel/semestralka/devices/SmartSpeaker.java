package cz.cvut.fel.semestralka.devices;

import cz.cvut.fel.semestralka.devices.visitors.DeviceConsumptionVisitor;

public class SmartSpeaker extends Device {

    public SmartSpeaker(String id, String description) {
        super(id, description, 5);
    }


    @Override
    public void accept(DeviceConsumptionVisitor visitor) {
        visitor.visit(this);
    }
}
