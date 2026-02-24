package cz.cvut.fel.semestralka.devices;

import cz.cvut.fel.semestralka.devices.visitors.DeviceConsumptionVisitor;
import lombok.Setter;

public class Television extends Device {
    @Setter
    private int volume = 10;

    public Television(String id, String description) {
        super(id, description, 5);
    }

    @Override
    public void accept(DeviceConsumptionVisitor visitor) {
        visitor.visit(this);
    }
}
