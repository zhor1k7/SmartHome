package cz.cvut.fel.semestralka.home;

import cz.cvut.fel.semestralka.devices.Device;
import cz.cvut.fel.semestralka.residents.Actions;
import cz.cvut.fel.semestralka.residents.Resident;

public final class HousePermissions {

    private HousePermissions() {
        // now its utility class
    }

    public static void setupPermissions(House house) {

        for (Resident resident : house.getResidentsUnmodifiable()) {

            switch (resident.getPersonType()) {
                case FATHER -> setupFather(resident);
                case MOTHER -> setupMother(resident);
                case SON, DAUGHTER -> setupChildren(resident);
                case GRANDMA, GRANDPA -> setupGrandParents(resident);
            }
        }
    }

    private static void setupFather(Resident r) {
        r.allowAction(Actions.REPAIR);
        r.allowAction(Actions.USE);
        r.allowDevice(Device.class);
    }

    private static void setupMother(Resident r) {
        r.allowAction(Actions.USE);
        r.allowAction(Actions.REFILL);
        r.allowDevice(Device.class);
    }

    private static void setupChildren(Resident r) {
        r.allowAction(Actions.USE);
        r.allowDevice(Device.class);
    }

    private static void setupGrandParents(Resident r) {
        r.allowAction(Actions.USE);
        r.allowDevice(Device.class);
    }
}
