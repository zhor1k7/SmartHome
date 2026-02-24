package cz.cvut.fel.semestralka.devices.strategy;

import cz.cvut.fel.semestralka.devices.Device;
import cz.cvut.fel.semestralka.home.House;
import cz.cvut.fel.semestralka.residents.Actions;
import cz.cvut.fel.semestralka.residents.Resident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UseStrategy implements ActionPerformStrategy {

    private static final Logger log =
            LoggerFactory.getLogger(UseStrategy.class);

    @Override
    public boolean execute(Device device, Resident resident, House house) {

        //device busy
        if (!device.tryStartUsing()) {
            return false;
        }

        //ensure device is ON
        if (!device.isOn()) {
            device.turnOn();
        }


        device.run();

        //pro reporty
        house.logUsage(device, resident);
        house.logActivity(resident, Actions.USE, device.getId());

        log.info("{} used device {}", resident.getName(), device.getId());

        return true;
    }
}
