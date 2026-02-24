package cz.cvut.fel.semestralka.reports.generators.activityusage;

import cz.cvut.fel.semestralka.home.House;
import cz.cvut.fel.semestralka.residents.Actions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ActivityReportGenerator {

    private static final String REPORTS_DIR =
            "src/main/java/cz/cvut/fel/semestralka/reports/reportsinfo";
    private static final String FILE_NAME = "ActivityReport.txt";

    public void generateAndSave(House house) {

        int deviceUsage = 0;
        int sportUsage = 0;

        Path reportsDir = Path.of(REPORTS_DIR);
        Path reportFile = reportsDir.resolve(FILE_NAME);

        try {
            Files.createDirectories(reportsDir);

            try (var writer = Files.newBufferedWriter(reportFile)) {

                writer.write("ACTIVITY REPORT\n\n");

                for (Activity a : house.getActivitiesUnmodifiable()) {

                    writer.write(
                            a.getPersonType() +
                                    " performed " +
                                    a.getAction() +
                                    " on " +
                                    a.getTarget() +
                                    "\n"
                    );

                    if (a.getAction() == Actions.USE) {
                        if (isSportItem(a.getTarget())) {
                            sportUsage++;
                        } else {
                            deviceUsage++;
                        }
                    }
                }

                writer.write("\n--- SUMMARY ---\n");
                writer.write("Device usage count: " + deviceUsage + "\n");
                writer.write("Sport usage count: " + sportUsage + "\n");

                writer.write(
                        "Device / Sport usage ratio: " +
                                (sportUsage > 0
                                        ? (double) deviceUsage / sportUsage
                                        : "N/A")
                                + "\n"
                );
            }

        } catch (IOException e) {
            throw new RuntimeException("Cannot write ActivityReport", e);
        }
    }

    private boolean isSportItem(String target) {
        return target.startsWith("BasketballHoop")
                || target.startsWith("ExerciseBike")
                || target.startsWith("Badminton")
                || target.startsWith("Skate")
                || target.startsWith("NikeJordan");
    }
}
