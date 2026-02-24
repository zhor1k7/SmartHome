package cz.cvut.fel.semestralka.home;

import cz.cvut.fel.semestralka.devices.Device;
import cz.cvut.fel.semestralka.residents.Resident;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class Room {
    private final String name;
    private final RoomType type;
    private final Floor floor;

    private final List<Device> devices = new ArrayList<>();

    private final List<Resident> currentResidents = new CopyOnWriteArrayList<>();

    public Room(String name, Floor floor, RoomType type) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Room name cannot be null or empty");
        }
        if (floor == null) {
            throw new IllegalArgumentException("Floor cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("RoomType cannot be null");
        }

        this.name = name;
        this.floor = floor;
        this.type = type;
    }

    public void addDevice(Device device) {
        if (device == null) {
            throw new IllegalArgumentException("Device cannot be null");
        }
        devices.add(device);
        device.setRoom(this);
    }

    public void removeDevice(Device device) {
        devices.remove(device);
        if (device != null) {
            device.setRoom(null);
        }
    }


    public void addResident(Resident resident) {
        if (resident == null) {
            throw new IllegalArgumentException("Resident cannot be null");
        }
        if (!currentResidents.contains(resident)) {
            currentResidents.add(resident);
        }
    }

    public void removeResident(Resident resident) {
        currentResidents.remove(resident);
    }


}