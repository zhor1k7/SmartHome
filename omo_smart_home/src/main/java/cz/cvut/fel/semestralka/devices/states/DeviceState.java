package cz.cvut.fel.semestralka.devices.states;

/**
 * DeviceState interface
 */
public interface DeviceState {


    void turnOn();

    void turnOff();

    void breakDevice();

    boolean isOn();

    boolean isBroken();

}
