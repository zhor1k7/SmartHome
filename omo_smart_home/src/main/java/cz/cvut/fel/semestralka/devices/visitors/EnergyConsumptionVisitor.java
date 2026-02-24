package cz.cvut.fel.semestralka.devices.visitors;

import cz.cvut.fel.semestralka.devices.*;

/**
 * Visitor implementation used to calculate total electricity consumption
 * of devices in the smart home system.
 * <p>
 * This class follows the Visitor design pattern and traverses devices
 * to sum up all recorded electricity consumption values.
 */
public class EnergyConsumptionVisitor implements DeviceConsumptionVisitor {

    /**
     * Accumulated total electricity consumption.
     */
    private double totalEnergy = 0.0;

    /**
     * Sums electricity consumption of the given device.
     *
     * @param device device whose consumption is processed
     */
    private void sum(Device device) {
        totalEnergy += device.getConsumptions().stream()
                .filter(c -> c.getResource() == ResourceType.ELECTRICITY)
                .mapToDouble(Consumption::getAmount)
                .sum();
    }

    /**
     * Visits a generic device and processes its energy consumption.
     *
     * @param device visited device
     */
    @Override
    public void visit(Device device) {
        sum(device);
    }

    /**
     * Visits a fridge device.
     *
     * @param fridge visited fridge
     */
    @Override
    public void visit(Fridge fridge) {
        sum(fridge);
    }

    /**
     * Visits blinds device.
     *
     * @param blinds visited blinds
     */
    @Override
    public void visit(Blinds blinds) {
        sum(blinds);
    }

    /**
     * Visits a multicooker device.
     *
     * @param multicooker visited multicooker
     */
    @Override
    public void visit(Multicooker multicooker) {
        sum(multicooker);
    }

    /**
     * Visits an outdoor gate device.
     *
     * @param outdoorGate visited outdoor gate
     */
    @Override
    public void visit(OutdoorGate outdoorGate) {
        sum(outdoorGate);
    }

    /**
     * Visits a smart kettle device.
     *
     * @param smartKettle visited smart kettle
     */
    @Override
    public void visit(SmartKettle smartKettle) {
        sum(smartKettle);
    }

    /**
     * Visits a smart light device.
     *
     * @param smartLight visited smart light
     */
    @Override
    public void visit(SmartLight smartLight) {
        sum(smartLight);
    }

    /**
     * Visits a smart speaker device.
     *
     * @param smartSpeaker visited smart speaker
     */
    @Override
    public void visit(SmartSpeaker smartSpeaker) {
        sum(smartSpeaker);
    }

    /**
     * Visits a smart vacuum device.
     *
     * @param smartVacuum visited smart vacuum
     */
    @Override
    public void visit(SmartVacuum smartVacuum) {
        sum(smartVacuum);
    }

    /**
     * Visits a television device.
     *
     * @param television visited television
     */
    @Override
    public void visit(Television television) {
        sum(television);
    }

    /**
     * Returns the total accumulated electricity consumption.
     *
     * @return total energy consumption value
     */
    public double getTotalEnergy() {
        return totalEnergy;
    }
}
