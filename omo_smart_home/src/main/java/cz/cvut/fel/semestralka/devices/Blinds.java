package cz.cvut.fel.semestralka.devices;

import cz.cvut.fel.semestralka.devices.visitors.DeviceConsumptionVisitor;

public class Blinds extends Device {

    public Blinds(String id, String description) {
        super(id, description, 10);
    }


    @Override
    public void run() {
        System.out.println("Blinds closing..");
    }

    @Override
    public void accept(DeviceConsumptionVisitor visitor) {
        visitor.visit(this);
    }

}
