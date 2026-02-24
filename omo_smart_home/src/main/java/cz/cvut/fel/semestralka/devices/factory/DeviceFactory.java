package cz.cvut.fel.semestralka.devices.factory;

import cz.cvut.fel.semestralka.devices.Device;
import cz.cvut.fel.semestralka.home.Room;

/**
 * Factory interface for creating device instances.
 * <p>
 * Implementations of this interface are responsible for instantiating
 * concrete device types and assigning them to a specific room.
 * This interface follows the Factory design pattern.
 */
public interface DeviceFactory {

    /**
     * Creates a new device of the specified class.
     *
     * @param deviceClass class of the device to be created
     * @param id          unique identifier of the device
     * @param description textual description of the device
     * @param room        room where the device will be placed
     * @return newly created device instance
     */
    Device createDevice(Class<? extends Device> deviceClass,
                        String id,
                        String description,
                        Room room);
}
