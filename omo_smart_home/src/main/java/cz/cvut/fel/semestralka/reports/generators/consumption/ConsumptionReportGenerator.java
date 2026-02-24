package cz.cvut.fel.semestralka.reports.generators.consumption;

import cz.cvut.fel.semestralka.devices.Consumption;
import cz.cvut.fel.semestralka.devices.Device;
import cz.cvut.fel.semestralka.devices.ResourceType;
import cz.cvut.fel.semestralka.devices.visitors.EnergyConsumptionVisitor;
import cz.cvut.fel.semestralka.home.House;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class ConsumptionReportGenerator {

    private static final String REPORTS_DIR =
            "src/main/java/cz/cvut/fel/semestralka/reports/reportsinfo";

    private static final String FILE_NAME = "ConsumptionReport.txt";

    public ConsumptionReport generateAndSave(House house) {

        ConsumptionReport report = new ConsumptionReport();
        EnergyConsumptionVisitor energyVisitor = new EnergyConsumptionVisitor();

        for (Device device : house.getAllDevices()) {
            device.accept(energyVisitor);

            String deviceName = device.getClass().getSimpleName();
            DeviceConsumption dc =
                    report.getDeviceStats()
                            .computeIfAbsent(deviceName, k -> new DeviceConsumption());

            for (Consumption c : device.getConsumptions()) {

                if (c.getResource() == ResourceType.WATER) {
                    report.addTotalWater(c.getAmount());
                    dc.addWater(c.getAmount());
                }

                if (c.getResource() == ResourceType.ELECTRICITY) {
                    dc.addElectricity(c.getAmount());
                }
            }
        }

        report.addTotalElectricity(energyVisitor.getTotalEnergy());

        writeToFile(report);
        return report;
    }

    private void writeToFile(ConsumptionReport report) {

        Path reportsDir = Path.of(REPORTS_DIR);
        Path reportFile = reportsDir.resolve(FILE_NAME);

        try {
            Files.createDirectories(reportsDir);

            try (var writer = Files.newBufferedWriter(reportFile)) {

                writer.write("CONSUMPTION REPORT\n\n");

                writer.write("GENERAL CONSUMPTION:\n");
                writer.write("Total water consumption: " + report.getTotalWater() + "\n");
                writer.write("Total electricity consumption: " + report.getTotalElectricity() + "\n");
                writer.write("---\n");

                writer.write("DEVICES CONSUMPTION:\n");

                for (Map.Entry<String, DeviceConsumption> entry : report.getDeviceStats().entrySet()) {

                    DeviceConsumption dc = entry.getValue();

                    writer.write("Device '" + entry.getKey() + "' consumption:\n");
                    writer.write("Water: " + dc.getWater() + "\n");
                    writer.write("Electricity: " + dc.getElectricity() + "\n\n");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Cannot write ConsumptionReport.txt", e);
        }
    }
}
