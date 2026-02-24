package cz.cvut.fel.semestralka.devices.strategy;

import cz.cvut.fel.semestralka.devices.Device;
import cz.cvut.fel.semestralka.devices.Fridge;
import cz.cvut.fel.semestralka.home.House;
import cz.cvut.fel.semestralka.residents.Actions;
import cz.cvut.fel.semestralka.residents.Resident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RefillStrategy implements ActionPerformStrategy {

    private static final Logger log =
            LoggerFactory.getLogger(RefillStrategy.class);

    @Override
    public boolean execute(Device device, Resident resident, House house) {

        if (!(device instanceof Fridge fridge)) return false;

        fridge.refill();
        log.info("{} refilled the fridge", resident.getName());

        house.logActivity(resident, Actions.REFILL, device.getId());
        return true;
    }
}

