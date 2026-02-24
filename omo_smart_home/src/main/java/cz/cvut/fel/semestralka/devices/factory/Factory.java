package cz.cvut.fel.semestralka.devices.factory;

import cz.cvut.fel.semestralka.devices.Device;
import cz.cvut.fel.semestralka.home.Room;

/**
 * Concrete implementation of the {@link DeviceFactory} interface.
 * <p>
 * This factory creates device instances using reflection and assigns
 * them to a specified room. It assumes that each device class provides
 * a constructor with parameters {@code (String id, String description)}.
 */
public class Factory implements DeviceFactory {

    /**
     * Creates a new device instance of the given class and places it into a room.
     *
     * @param deviceClass class of the device to be created
     * @param id          unique identifier of the device
     * @param description textual description of the device
     * @param room        room where the device will be located
     * @return newly created device instance
     * @throws IllegalArgumentException if the device cannot be created
     */
    @Override
    public Device createDevice(Class<? extends Device> deviceClass,
                               String id,
                               String description,
                               Room room) {
        try {
            Device device = deviceClass
                    .getConstructor(String.class, String.class)
                    .newInstance(id, description);

            room.addDevice(device);
            return device;
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Error: cannot create device of type " + deviceClass.getSimpleName(), e);
        }
    }
}
