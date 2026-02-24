package cz.cvut.fel.semestralka.devices;

import cz.cvut.fel.semestralka.devices.visitors.DeviceConsumptionVisitor;

public class OutdoorGate extends Device {
    private boolean open = false;
    private boolean locked = true;

    public OutdoorGate(String id, String description) {
        super(id, description, 20);
    }

    public void openGate() {
        if (!locked) open = true;
    }

    public void closeGate() {
        open = false;
    }

    public void lock() {
        locked = true;
    }

    public void unlock() {
        locked = false;
    }

    @Override
    public void accept(DeviceConsumptionVisitor visitor) {
        visitor.visit(this);
    }
}
