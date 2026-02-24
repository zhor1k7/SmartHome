package cz.cvut.fel.semestralka.reports.generators.activityusage;


import cz.cvut.fel.semestralka.devices.Device;
import cz.cvut.fel.semestralka.residents.PersonType;
import cz.cvut.fel.semestralka.residents.Resident;
import lombok.Getter;

public class Usage {

    @Getter
    private final String deviceId;
    @Getter
    private final String deviceType;
    @Getter
    private final PersonType residentType;

    public Usage(Device device, Resident resident) {
        this.deviceId = device.getId();
        this.deviceType = device.getClass().getSimpleName();
        this.residentType = resident.getPersonType();
    }

}
