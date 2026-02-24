import cz.cvut.fel.semestralka.devices.factory.Factory;
import cz.cvut.fel.semestralka.home.House;
import cz.cvut.fel.semestralka.home.HouseBuilder;
import cz.cvut.fel.semestralka.home.HouseConf;
import cz.cvut.fel.semestralka.home.HousePermissions;
import cz.cvut.fel.semestralka.reports.generators.HouseConfigurationReportGenerator;
import cz.cvut.fel.semestralka.reports.generators.activityusage.ActivityReportGenerator;
import cz.cvut.fel.semestralka.reports.generators.consumption.ConsumptionReportGenerator;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


public class Simulation {
    private static final Logger log =
            LoggerFactory.getLogger(Simulation.class);
    private static final int STEPS = 10;

    private final House house;

    public Simulation() {

        HouseConf conf = HouseConf.load("house.json");

        Factory deviceFactory = new Factory();
        HouseBuilder builder = new HouseBuilder(conf, deviceFactory);

        this.house = builder.build();

        HousePermissions.setupPermissions(house);
    }

    public void run() {
        log.info("=== SIMULATION START ===");
        runSteps(STEPS);
        log.info("=== SIMULATION END ===");
        generateReports();
    }

    private void runSteps(int steps) {
        for (int i = 1; i <= steps; i++) {
            house.tick();
        }
    }

    private void generateReports() {
        new HouseConfigurationReportGenerator().generateAndSave(house);
        new ActivityReportGenerator().generateAndSave(house);
        new ConsumptionReportGenerator().generateAndSave(house);
    }
}
