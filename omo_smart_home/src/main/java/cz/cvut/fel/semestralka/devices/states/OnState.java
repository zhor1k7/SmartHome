package cz.cvut.fel.semestralka.devices.states;

import cz.cvut.fel.semestralka.devices.Device;

public class OnState implements DeviceState {

    private final Device device;

    public OnState(Device device) {
        this.device = device;
    }

    @Override
    public void turnOn() {
        //already on
    }

    @Override
    public void turnOff() {
        device.setState(device.getOffState());
    }

    @Override
    public void breakDevice() {
        device.setState(device.getBrokenState());
    }

    @Override
    public boolean isOn() {
        return true;
    }

    @Override
    public boolean isBroken() {
        return false;
    }
}