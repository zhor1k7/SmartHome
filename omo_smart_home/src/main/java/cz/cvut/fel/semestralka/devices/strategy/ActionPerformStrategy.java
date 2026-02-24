package cz.cvut.fel.semestralka.devices.strategy;

import cz.cvut.fel.semestralka.devices.Device;
import cz.cvut.fel.semestralka.home.House;
import cz.cvut.fel.semestralka.residents.Resident;

public interface ActionPerformStrategy {
    boolean execute(Device device, Resident resident, House house);
}
