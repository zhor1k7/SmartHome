package cz.cvut.fel.semestralka.home;

import cz.cvut.fel.semestralka.devices.*;
import cz.cvut.fel.semestralka.devices.factory.Factory;
import cz.cvut.fel.semestralka.residents.PersonType;
import cz.cvut.fel.semestralka.residents.Resident;
import cz.cvut.fel.semestralka.sensor.Sensor;

public class HouseBuilder {

    private final HouseConf conf;
    private final Factory factory;

    public HouseBuilder(HouseConf conf, Factory factory) {
        this.conf = conf;
        this.factory = factory;
    }

    public House build() {

        if (conf.getFloors() == null || conf.getFloors().isEmpty()) {
            throw new IllegalStateException("House must have at least one floor");
        }

        House house = new House();


        Sensor houseSensor = new Sensor("HouseSensor");
        house.setSensor(houseSensor);
        houseSensor.addObserver(house.getDeviceApi());

        // flors,rooms,device
        for (HouseConf.FloorConf floorConf : conf.getFloors()) {

            Floor floor = new Floor(floorConf.getLevel());
            house.addFloor(floor);

            for (HouseConf.RoomConf roomConf : floorConf.getRooms()) {

                Room room = new Room(
                        roomConf.getName(),
                        floor,
                        RoomType.valueOf(roomConf.getType())
                );
                floor.addRoom(room);

                if (roomConf.getDevices() != null) {
                    for (HouseConf.DeviceConf devConf : roomConf.getDevices()) {

                        Device device = factory.createDevice(
                                resolveDeviceClass(devConf.getType()),
                                devConf.getId(),
                                devConf.getDescription(),
                                room
                        );

                    }
                }
            }
        }


        // garden
        if (conf.getGarden() != null) {
            house.createGarden();
        }

        //residents
        if (conf.getResidents() != null) {
            for (HouseConf.ResidentConf resConf : conf.getResidents()) {

                Room startRoom = findRoomByName(
                        house,
                        resConf.getStartRoom()
                );

                Resident resident = new Resident(
                        PersonType.valueOf(resConf.getPersonType()),
                        resConf.getName(),
                        startRoom
                );

                resident.setHouse(house);
                house.addResident(resident);

                // register observer immediately
                if (houseSensor != null) {
                    houseSensor.addObserver(resident);
                }
            }
        }
        //sport items
        if (conf.getSportItems() != null) {
            for (HouseConf.SportItemConf sportConf : conf.getSportItems()) {

                SportItemType type = SportItemType.valueOf(
                        sportConf.getType()
                );

                SportItem item = new SportItem(
                        sportConf.getName(),
                        type
                );

                house.addSportItem(item);
            }
        }

        return house;
    }

    //helpers
    private Room findRoomByName(House house, String name) {
        for (Floor floor : house.getFloors()) {
            for (Room room : floor.getRooms()) {
                if (room.getName().equals(name)) {
                    return room;
                }
            }
        }
        throw new IllegalArgumentException("Room not found: " + name);
    }

    private Class<? extends Device> resolveDeviceClass(String type) {
        return switch (type) {
            case "Television" -> Television.class;
            case "SmartSpeaker" -> SmartSpeaker.class;
            case "SmartLight" -> SmartLight.class;
            case "Fridge" -> Fridge.class;
            case "Multicooker" -> Multicooker.class;
            case "SmartKettle" -> SmartKettle.class;
            case "Heater" -> SaunaStove.class;
            case "Blinds" -> Blinds.class;
            case "SmartVacuum" -> SmartVacuum.class;
            case "OutdoorGate" -> OutdoorGate.class;
            default -> throw new IllegalArgumentException("Unknown device type: " + type);
        };
    }
}
