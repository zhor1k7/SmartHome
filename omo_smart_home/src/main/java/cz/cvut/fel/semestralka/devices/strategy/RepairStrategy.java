package cz.cvut.fel.semestralka.devices.strategy;

import cz.cvut.fel.semestralka.devices.Device;
import cz.cvut.fel.semestralka.home.House;
import cz.cvut.fel.semestralka.residents.Actions;
import cz.cvut.fel.semestralka.residents.Resident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepairStrategy implements ActionPerformStrategy {

    private static final Logger log =
            LoggerFactory.getLogger(RepairStrategy.class);

    @Override
    public boolean execute(Device device, Resident resident, House house) {

        if (!device.isBroken()) return false;

        device.repair();
        log.info("{} repaired device {}", resident.getName(), device.getId());

        house.logActivity(resident, Actions.REPAIR, device.getId());
        return true;
    }
}
