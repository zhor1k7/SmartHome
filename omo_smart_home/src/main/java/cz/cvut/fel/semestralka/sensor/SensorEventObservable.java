package cz.cvut.fel.semestralka.sensor;

public interface SensorEventObservable {
    void notifyObservers(EventType eventType);
}