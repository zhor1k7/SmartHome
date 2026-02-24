package cz.cvut.fel.semestralka.residents;

import cz.cvut.fel.semestralka.devices.Device;
import cz.cvut.fel.semestralka.devices.DeviceApi;
import cz.cvut.fel.semestralka.home.House;
import cz.cvut.fel.semestralka.home.Room;
import cz.cvut.fel.semestralka.sensor.EventType;
import cz.cvut.fel.semestralka.sensor.SensorEventObserver;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Resident implements SensorEventObserver {

    private static final Logger log = LoggerFactory.getLogger(Resident.class);

    @Getter
    private final PersonType personType;

    @Getter
    private final String name;

    @Getter
    private Room currentRoom;

    private final Set<Class<? extends Device>> allowedDevices = new HashSet<>();
    private final EnumSet<Actions> allowedActions = EnumSet.noneOf(Actions.class);

    // reaction plan
    private Actions plannedAction;
    private Device targetDevice;
    private Room targetRoom;

    @Setter
    private House house;

    private final Random random = new Random();


    public Resident(PersonType personType, String name, Room startRoom) {
        this.personType = personType;
        this.name = name;
        this.currentRoom = startRoom;

        if (startRoom != null) {
            startRoom.addResident(this);
        }
    }

    // obs pattern
    private boolean isResponsible(EventType eventType) {

        return switch (eventType) {

            case FOOD_EMPTY -> personType == PersonType.MOTHER;

            case DEVICE_BROKEN -> personType == PersonType.FATHER;

            default -> false;
        };
    }

    @Override
    public void update(EventType eventType, String sensorName) {


        if (house == null) return;

        switch (eventType) {

            case DEVICE_BROKEN -> {

                if (!isResponsible(eventType)) {
                    return;
                }

                targetDevice = house.findBrokenDevice();
                if (targetDevice != null) {
                    plannedAction = Actions.REPAIR;
                    targetRoom = targetDevice.getLocation();
                    log.info("{} plans to REPAIR device {} in room {}",
                            name, targetDevice.getId(), targetRoom.getName());
                }
            }

            case FOOD_EMPTY -> {

                if (!isResponsible(eventType)) {
                    return;
                }

                targetDevice = house.findFridge();
                if (targetDevice != null) {
                    plannedAction = Actions.REFILL;
                    targetRoom = targetDevice.getLocation();

                    log.info("{} plans to REFILL fridge in room {}",
                            name, targetRoom.getName());
                }
            }

            default -> {
            }
        }
    }


    //std logic for 50/50

    private void idleBehaviour() {

        // 50 / 50
        if (random.nextBoolean()) {
            trySport();
        } else {
            tryUseDevice();
        }
    }


    private void trySport() {

        if (house == null) return;

        var item = house.findFreeSportItem();
        if (item == null) {
            log.debug("{} found no free sport item", name);
            return;
        }

        item.startUsing(this);
        log.info("{} uses sport item {}", name, item.getName());

        house.logActivity(this, Actions.USE, item.getName());
    }

    private void tryUseDevice() {

        if (house == null) return;

        Device device = house.findFreeUsableDevice(this);
        if (device == null) {
            log.debug("{} found no free usable device", name);
            return;
        }

        if (currentRoom != device.getLocation()) {
            moveTo(device.getLocation());
            return;
        }
        DeviceApi api = house.getDeviceApi();
        api.perform(Actions.USE, device, this);
    }

    public void simulateStep() {

        if (plannedAction == null || targetDevice == null || targetRoom == null) {
            idleBehaviour();
            return;
        }

        if (currentRoom != targetRoom) {
            moveTo(targetRoom);
            return;
        }

        DeviceApi api = house.getDeviceApi();
        api.perform(plannedAction, targetDevice, this);

        plannedAction = null;
        targetDevice = null;
        targetRoom = null;
    }

    // moving in house
    public void moveTo(Room newRoom) {
        if (currentRoom != null) {
            currentRoom.removeResident(this);
        }
        currentRoom = newRoom;
        newRoom.addResident(this);

        log.info("{} moved to room {}", name, newRoom.getName());
    }

    //permitions check
    public boolean canPerform(Actions action, Device device) {
        if (action == null || device == null) return false;
        if (!allowedActions.contains(action)) return false;

        if (allowedDevices.isEmpty()) return true;

        for (Class<? extends Device> cls : allowedDevices) {
            if (cls.isAssignableFrom(device.getClass())) {
                return true;
            }
        }
        return false;
    }

    public void allowAction(Actions action) {
        if (action != null) allowedActions.add(action);
    }

    public void allowDevice(Class<? extends Device> deviceClass) {
        if (deviceClass != null) allowedDevices.add(deviceClass);
    }

    @Override
    public String toString() {
        return "Resident{" +
                "type=" + personType +
                ", name='" + name + '\'' +
                ", room=" + (currentRoom != null ? currentRoom.getName() : "none") +
                '}';
    }
}
