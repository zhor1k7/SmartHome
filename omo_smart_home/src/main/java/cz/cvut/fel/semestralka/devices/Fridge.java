package cz.cvut.fel.semestralka.devices;

import cz.cvut.fel.semestralka.devices.visitors.DeviceConsumptionVisitor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Fridge extends Device {

    @Getter
    private final List<String> foodlist = new ArrayList<>();

    public Fridge(String id, String description) {
        super(id, description, 100);
        turnOn();
    }


    public boolean isEmpty() {
        return foodlist.isEmpty();
    }


    public void refill() {
        foodlist.add("Milk");
        foodlist.add("Eggs");
        foodlist.add("Meat");
        foodlist.add("Cheese");
    }

    @Override
    public void accept(DeviceConsumptionVisitor visitor) {
        visitor.visit(this);
    }
}
