package cz.cvut.fel.semestralka.devices;

import cz.cvut.fel.semestralka.devices.states.BrokenState;
import cz.cvut.fel.semestralka.devices.states.DeviceState;
import cz.cvut.fel.semestralka.devices.states.OffState;
import cz.cvut.fel.semestralka.devices.states.OnState;
import cz.cvut.fel.semestralka.devices.visitors.DeviceConsumptionVisitor;
import cz.cvut.fel.semestralka.home.Room;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Abstract base class representing a device in the smart home system.
 * <p>
 * Each device has an identifier, description, power consumption,
 * internal state (ON, OFF, BROKEN), and can be located in a room.
 * The class uses the State pattern to manage device behavior
 * and the Visitor pattern to support external operations.
 */
public abstract class Device {

    /**
     * Unique identifier of the device.
     */
    @Getter
    private final String id;

    /**
     * Human-readable description of the device.
     */
    @Getter
    private String description;

    /**
     * State representing the OFF mode of the device.
     */
    @Getter
    private final DeviceState offState;

    /**
     * State representing the ON mode of the device.
     */
    @Getter
    private final DeviceState onState;

    /**
     * State representing the BROKEN mode of the device.
     */
    @Getter
    private final DeviceState brokenState;

    /**
     * Current state of the device.
     */
    @Getter
    private DeviceState state;

    /**
     * Base power consumption of the device.
     */
    @Getter
    private final double basePower;

    /**
     * List of recorded resource consumptions.
     */
    private final List<Consumption> consumptions = new ArrayList<>();

    /**
     * Indicates whether the device is currently in use.
     */
    @Getter
    private boolean inUse = false;

    /**
     * Room in which the device is currently located.
     */
    @Getter
    private Room location;

    /**
     * Creates a new device with the given parameters.
     *
     * @param id          unique device identifier
     * @param description textual description of the device
     * @param basePower   base power consumption value
     */
    protected Device(String id, String description, double basePower) {
        this.id = id;
        this.description = description;

        this.offState = new OffState(this);
        this.onState = new OnState(this);
        this.brokenState = new BrokenState(this);

        this.basePower = basePower;
        this.state = offState;
    }

    /**
     * Turns the device on using its current state logic.
     */
    public void turnOn() {
        state.turnOn();
    }

    /**
     * Turns the device off using its current state logic.
     */
    public void turnOff() {
        state.turnOff();
    }

    /**
     * Marks the device as broken.
     */
    public void breakDevice() {
        state.breakDevice();
    }

    /**
     * Checks whether the device is currently turned on.
     *
     * @return true if the device is on, false otherwise
     */
    public boolean isOn() {
        return state.isOn();
    }

    /**
     * Checks whether the device is currently broken.
     *
     * @return true if the device is broken, false otherwise
     */
    public boolean isBroken() {
        return state.isBroken();
    }

    /**
     * Repairs the device if it is in the broken state.
     */
    public void repair() {
        if (state instanceof BrokenState) {
            ((BrokenState) state).repair();
        }
    }

    /**
     * Sets a new state for the device.
     *
     * @param newState new device state
     * @throws NullPointerException if the state is null
     */
    public void setState(DeviceState newState) {
        this.state = Objects.requireNonNull(newState, "State cant be null");
    }

    /**
     * Records resource consumption caused by the device.
     *
     * @param resourceType type of consumed resource
     * @param amount       amount of the resource
     * @param unit         unit of measurement
     */
    protected void recordConsumption(ResourceType resourceType, double amount, String unit) {
        if (resourceType == null) return;
        consumptions.add(new Consumption(resourceType, amount, unit, null));
    }

    /**
     * Returns an unmodifiable list of all recorded consumptions.
     *
     * @return list of consumptions
     */
    public List<Consumption> getConsumptions() {
        return Collections.unmodifiableList(consumptions);
    }

    /**
     * Accepts a visitor operating on this device.
     *
     * @param visitor visitor instance
     */
    public void accept(DeviceConsumptionVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Simulates device operation and records electricity consumption
     * if the device is turned on.
     */
    public void run() {
        if (!getState().isOn()) return;
        double kwh = (basePower / 10.0);
        recordConsumption(ResourceType.ELECTRICITY, kwh, "kWh");
    }

    /**
     * Returns a string representation of the device.
     *
     * @return device description string
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "{id='" + id + '\''
                + ", desc='" + description + '\''
                + ", state=" + state.getClass().getSimpleName()
                + ", location=" + (location != null ? location.getName() : "none")
                + '}';
    }

    /**
     * Sets the room in which the device is located.
     * Automatically updates room-device relationships.
     *
     * @param room target room
     */
    public void setRoom(Room room) {
        if (this.location != null && this.location != room) {
            this.location.removeDevice(this);
        }
        this.location = room;
        if (room != null && !room.getDevices().contains(this)) {
            room.addDevice(this);
        }
    }

    /**
     * Attempts to start using the device.
     *
     * @return true if usage was successfully started, false otherwise
     */
    public boolean tryStartUsing() {
        if (isBroken() || inUse) {
            return false;
        }
        inUse = true;
        return true;
    }

    /**
     * Stops using the device.
     */
    public void stopUsing() {
        inUse = false;
    }
}
