package cz.cvut.fel.semestralka.devices.states;

import cz.cvut.fel.semestralka.devices.Device;

public class OffState implements DeviceState {

    private final Device device;

    public OffState(Device device) {
        this.device = device;
    }

    @Override
    public void turnOn() {
        device.setState(device.getOnState());
    }

    @Override
    public void turnOff() {
        // already off
    }

    @Override
    public void breakDevice() {
        device.setState(device.getBrokenState());
    }

    @Override
    public boolean isOn() {
        return false;
    }

    @Override
    public boolean isBroken() {
        return false;
    }
}