package cz.cvut.fel.semestralka.devices;

import cz.cvut.fel.semestralka.devices.strategy.ActionPerformStrategy;
import cz.cvut.fel.semestralka.devices.strategy.RefillStrategy;
import cz.cvut.fel.semestralka.devices.strategy.RepairStrategy;
import cz.cvut.fel.semestralka.devices.strategy.UseStrategy;
import cz.cvut.fel.semestralka.home.House;
import cz.cvut.fel.semestralka.residents.Actions;
import cz.cvut.fel.semestralka.residents.Resident;
import cz.cvut.fel.semestralka.sensor.EventType;
import cz.cvut.fel.semestralka.sensor.SensorEventObserver;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Getter
public class DeviceApi implements SensorEventObserver {


    private static final Logger log = LoggerFactory.getLogger(DeviceApi.class);
    private final House house;
    private final Map<Actions, ActionPerformStrategy> strategies = Map.of(
            Actions.USE, new UseStrategy(),
            Actions.REFILL, new RefillStrategy(),
            Actions.REPAIR, new RepairStrategy()
    );

    public DeviceApi(House house) {
        if (house == null) {
            throw new IllegalArgumentException("House cannot be null");
        }
        this.house = house;
    }


    // obs pattern
    @Override
    public void update(EventType eventType, String sensorName) {

        log.info("SYSTEM EVENT: {} from {}", eventType, sensorName);

        switch (eventType) {

            case POWER_OUTAGE -> turnOffAllDevices();

            case WIND_ALERT -> closeAllBlinds();


            case DEVICE_BROKEN -> {
                Device d = house.getRandomNotBrokenDevice();
                if (d != null) {
                    d.breakDevice();
                }
            }


            default -> {
                // ignore
            }
        }
    }


    // user usage pres strategy
    public boolean perform(Actions action, Device device, Resident resident) {

        if (action == null || device == null || resident == null) return false;
        if (!resident.canPerform(action, device)) return false;
        if (device.getLocation() != resident.getCurrentRoom()) return false;

        ActionPerformStrategy strategy = strategies.get(action);
        return strategy != null && strategy.execute(device, resident, house);
    }


    // system actions
    public void turnOffAllDevices() {
        log.info("SYSTEM: turning off all devices");

        for (Device device : house.getAllDevices()) {
            if (device.isOn()) {
                device.turnOff();
                log.info(" - {} turned off", device.getId());
            }
        }
    }

    private void closeAllBlinds() {
        log.info("SYSTEM: closing all blinds");

        for (Device device : house.getAllDevices()) {
            if (device instanceof Blinds blinds && blinds.isOn()) {
                blinds.turnOff(); // or blinds.close()
                log.info(" - blinds closed: {}", blinds.getId());
            }
        }
    }
}
