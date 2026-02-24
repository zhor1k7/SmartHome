package cz.cvut.fel.semestralka.sensor;

public interface SensorEventObserver {

    void update(EventType eventType, String sensorName);

}
