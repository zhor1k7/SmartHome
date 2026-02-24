package cz.cvut.fel.semestralka.home;

import cz.cvut.fel.semestralka.devices.Device;
import cz.cvut.fel.semestralka.devices.DeviceApi;
import cz.cvut.fel.semestralka.devices.Fridge;
import cz.cvut.fel.semestralka.reports.generators.activityusage.Activity;
import cz.cvut.fel.semestralka.reports.generators.activityusage.Usage;
import cz.cvut.fel.semestralka.residents.Actions;
import cz.cvut.fel.semestralka.residents.Resident;
import cz.cvut.fel.semestralka.sensor.Sensor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Getter
public class House {
    protected Garden garden;
    protected DeviceApi deviceApi;
    protected final List<Floor> floors = new ArrayList<>();
    protected final List<Resident> residents = new ArrayList<>();

    private final List<SportItem> sportItems = new ArrayList<>();

    private final List<Usage> usages = new ArrayList<>();
    private final List<Activity> activities = new ArrayList<>();

    private static final Logger log =
            LoggerFactory.getLogger(House.class);

    protected Sensor sensor;

    public House() {
        this.deviceApi = new DeviceApi(this);

    }

    public void addFloor(Floor floor) {
        if (floor == null) {
            throw new IllegalArgumentException("Floor cannot be null");
        }
        floor.setHouse(this);
        floors.add(floor);
    }

    public void addResident(Resident resident) {
        if (resident == null) {
            throw new IllegalArgumentException("Resident cannot be null");
        }
        resident.setHouse(this);
        residents.add(resident);
    }

    public void setSensor(Sensor sensor) {
        if (this.sensor != null) {
            throw new IllegalStateException("Sensor already exists");
        }
        this.sensor = sensor;
    }


    // i want control making garden in house class, 1 house = 1 garden control
    public void createGarden() {
        if (garden != null) {
            throw new IllegalArgumentException("Already have garden");
        }
        this.garden = new Garden(this);
    }

    public void addSportItem(SportItem item) {
        sportItems.add(item);
    }

    public Device findBrokenDevice() {

        return getAllDevices().stream()
                .filter(Device::isBroken)
                .findFirst()
                .orElse(null);
    }

    public Device findFridge() {

        return getAllDevices().stream()
                .filter(Fridge.class::isInstance)
                .findFirst()
                .orElse(null);
    }


    public SportItem findFreeSportItem() {

        for (SportItem item : sportItems) {
            if (!item.isInUse()) {
                return item;
            }
        }
        return null;
    }

    public Device findFreeUsableDevice(Resident resident) {

        if (resident == null) return null;

        for (Floor floor : floors) {
            for (Room room : floor.getRooms()) {
                for (Device device : room.getDevices()) {


                    if (device.isBroken()) continue;
                    if (!resident.canPerform(Actions.USE, device)) continue;
                    if (device.isInUse()) continue;

                    return device;
                }
            }
        }
        return null;
    }


    public void tick() {
        log.info("Tick...");

        if (sensor != null) {
            sensor.detect();
        }


        for (Resident resident : residents) {
            resident.simulateStep();
        }
        releaseAllDevicesAndSportItems();
    }

    private void releaseAllDevicesAndSportItems() {


        for (Floor floor : floors) {
            for (Room room : floor.getRooms()) {
                for (Device device : room.getDevices()) {
                    device.stopUsing();
                }
            }
        }


        for (SportItem item : sportItems) {
            item.stopUsing();
        }
    }

    public Device getRandomNotBrokenDevice() {

        List<Device> available = getAllDevices().stream()
                .filter(d -> !d.isBroken())
                .toList();

        if (available.isEmpty()) return null;

        return available.get(new Random().nextInt(available.size()));
    }

    public void logUsage(Device device, Resident resident) {
        if (device == null || resident == null) return;
        usages.add(new Usage(device, resident));
    }


    public void logActivity(Resident resident, Actions action, String target) {
        if (resident == null || action == null) return;
        activities.add(new Activity(resident.getPersonType(), action, target));
    }

    public List<Activity> getActivitiesUnmodifiable() {
        return Collections.unmodifiableList(activities);
    }

    public List<Device> getAllDevices() {
        return floors.stream()
                .flatMap(floor -> floor.getAllDevices().stream())
                .collect(Collectors.toList());
    }

    public List<Resident> getResidentsUnmodifiable() {
        return Collections.unmodifiableList(residents);
    }
}