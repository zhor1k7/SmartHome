package cz.cvut.fel.semestralka.devices.states;

import cz.cvut.fel.semestralka.devices.Device;

public class BrokenState implements DeviceState {

    private final Device device;

    public BrokenState(Device device) {
        this.device = device;
    }

    @Override
    public void turnOn() {
        //ignore
    }

    @Override
    public void turnOff() {
        //ignore
    }

    @Override
    public void breakDevice() {
        // nothing to do
    }

    @Override
    public boolean isOn() {
        return false;
    }

    @Override
    public boolean isBroken() {
        return true;
    }

    public void repair() {
        device.setState(device.getOffState());
    }
}