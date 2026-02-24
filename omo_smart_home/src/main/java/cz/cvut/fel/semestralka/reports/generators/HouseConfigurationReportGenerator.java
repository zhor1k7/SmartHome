package cz.cvut.fel.semestralka.reports.generators;

import cz.cvut.fel.semestralka.devices.Device;
import cz.cvut.fel.semestralka.home.Floor;
import cz.cvut.fel.semestralka.home.House;
import cz.cvut.fel.semestralka.home.Room;
import cz.cvut.fel.semestralka.residents.Resident;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HouseConfigurationReportGenerator {

    private static final String REPORTS_DIR =
            "src/main/java/cz/cvut/fel/semestralka/reports/reportsinfo";
    private static final String FILE_NAME = "HouseConfReport.txt";

    public void generateAndSave(House house) {
        if (house == null) {
            throw new IllegalArgumentException("House cannot be null");
        }

        String content = generate(house);

        Path reportsDir = Path.of(REPORTS_DIR);
        Path reportFile = reportsDir.resolve(FILE_NAME);

        try {
            Files.createDirectories(reportsDir);
            Files.writeString(reportFile, content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write HouseConfigurationReport", e);
        }
    }

    private String generate(House house) {
        StringBuilder sb = new StringBuilder();

        appendHouseInfo(sb, house);
        appendFloorsInfo(sb, house);
        appendRoomsInfo(sb, house);
        appendDevicesInfo(sb, house);
        appendPeopleInfo(sb, house);

        return sb.toString();
    }

    private void appendHouseInfo(StringBuilder sb, House house) {
        sb.append("House configuration report.\n\n");

        sb.append("HOUSE INFO:\n");
        sb.append("Amount of residents: ")
                .append(house.getResidents().size())
                .append("\n");

        sb.append("Amount of floors: ")
                .append(house.getFloors().size())
                .append("\n\n");
    }

    private void appendFloorsInfo(StringBuilder sb, House house) {
        sb.append("FLOORS INFO:\n");

        for (Floor floor : house.getFloors()) {
            sb.append("Floor ")
                    .append(floor.getLevel())
                    .append(" contains ")
                    .append(floor.getRooms().size())
                    .append(" rooms\n");
        }
        sb.append("\n");
    }

    private void appendRoomsInfo(StringBuilder sb, House house) {
        sb.append("ROOMS INFO:\n");

        for (Floor floor : house.getFloors()) {
            sb.append("ROOMS ON FLOOR ")
                    .append(floor.getLevel())
                    .append(":\n");

            for (Room room : floor.getRooms()) {
                sb.append("Room ")
                        .append(room.getType())
                        .append(" has ")
                        .append(room.getDevices().size())
                        .append(" devices\n");
            }
            sb.append("---\n");
        }
        sb.append("\n");
    }

    private void appendDevicesInfo(StringBuilder sb, House house) {
        sb.append("DEVICES INFO:\n");

        for (Floor floor : house.getFloors()) {
            sb.append("ON FLOOR ")
                    .append(floor.getLevel())
                    .append("\n");

            for (Room room : floor.getRooms()) {
                sb.append("IN ROOM: ")
                        .append(room.getType())
                        .append("\n");

                int i = 1;
                for (Device device : room.getDevices()) {
                    sb.append(i++)
                            .append(") ")
                            .append(device.getClass().getSimpleName())
                            .append("\n");
                }
                sb.append("\n");
            }
            sb.append("---\n");
        }
        sb.append("\n");
    }

    private void appendPeopleInfo(StringBuilder sb, House house) {
        sb.append("PEOPLE INFO:\n");

        int i = 1;
        for (Resident resident : house.getResidents()) {
            sb.append(i++)
                    .append(") ")
                    .append(resident.getPersonType())
                    .append("\n");
        }
    }
}
