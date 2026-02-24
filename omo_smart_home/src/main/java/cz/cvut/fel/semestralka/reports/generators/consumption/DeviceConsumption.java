package cz.cvut.fel.semestralka.reports.generators.consumption;

import lombok.Getter;

public class DeviceConsumption {
    @Getter
    private double water;
    @Getter
    private double electricity;

    public void addWater(double amount) {
        water += amount;
    }

    public void addElectricity(double amount) {
        electricity += amount;
    }

}
