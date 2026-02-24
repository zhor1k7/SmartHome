package cz.cvut.fel.semestralka.reports.generators.consumption;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConsumptionReport {
    @Getter
    private double totalWater;
    @Getter
    private double totalElectricity;
    @Getter
    private final Map<String, DeviceConsumption> deviceStats = new LinkedHashMap<>();

    public void addTotalWater(double amount) {
        totalWater += amount;
    }

    public void addTotalElectricity(double amount) {
        totalElectricity += amount;
    }


}
