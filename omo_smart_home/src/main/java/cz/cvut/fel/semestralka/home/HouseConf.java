package cz.cvut.fel.semestralka.home;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.InputStream;
import java.util.List;

@Getter
public class HouseConf {

    private List<FloorConf> floors;
    private GardenConf garden;
    private List<ResidentConf> residents;
    private List<SportItemConf> sportItems;

    public static HouseConf load(String resourcePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = HouseConf.class
                    .getClassLoader()
                    .getResourceAsStream(resourcePath);

            if (is == null) {
                throw new IllegalArgumentException("Cannot find resource: " + resourcePath);
            }

            return mapper.readValue(is, HouseConf.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load HouseConf from JSON", e);
        }
    }

    @Getter
    public static class FloorConf {
        private int level;
        private List<RoomConf> rooms;
    }

    @Getter
    public static class RoomConf {
        private String name;
        private String type;
        private List<DeviceConf> devices;
    }

    @Getter
    public static class DeviceConf {
        private String id;
        private String type;
        private String description;
    }


    @Getter
    public static class GardenConf {
        private List<DeviceConf> devices;
    }


    @Getter
    public static class ResidentConf {
        private String name;
        private String personType;
        private String startRoom;
    }


    @Getter
    public static class SportItemConf {
        private String name;
        private String type;
    }
}
