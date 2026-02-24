package cz.cvut.fel.semestralka.home;

import cz.cvut.fel.semestralka.devices.Device;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Floor {
    private final int level;
    private final List<Room> rooms = new ArrayList<>();

    @Setter(AccessLevel.PACKAGE) //we cant change it from other class
    private House house;

    public Floor(int level) {
        this.level = level;
    }

    public void addRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        rooms.add(room);
    }

    public List<Device> getAllDevices() {
        return rooms.stream()
                .flatMap(room -> room.getDevices().stream())
                .collect(Collectors.toList());
    }


}