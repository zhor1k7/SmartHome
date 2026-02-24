package cz.cvut.fel.semestralka.devices;

import cz.cvut.fel.semestralka.devices.visitors.DeviceConsumptionVisitor;
import lombok.Getter;
import lombok.Setter;

public class SmartLight extends Device {


    @Getter
    @Setter
    private int brightness = 100;


    public SmartLight(String id, String description) {
        super(id, description, 5);
    }


    @Override
    public void accept(DeviceConsumptionVisitor visitor) {
        visitor.visit(this);
    }

}
