package cz.cvut.fel.semestralka.sensor;

import cz.cvut.fel.semestralka.devices.Fridge;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sensor implements SensorEventObservable {

    @Getter
    private final String name;

    private final List<SensorEventObserver> observers = new ArrayList<>();
    private final Random random = new Random();
    private Fridge fridge;

    public Sensor(String name) {
        this.name = name;
    }


    public void addObserver(SensorEventObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(SensorEventObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(EventType eventType) {
        for (SensorEventObserver observer : observers) {
            observer.update(eventType, name);
        }
    }

    public void detect() {

        //20procentu nic ne stane
        if (random.nextDouble() < 0.20) {
            return;
        }

        EventType randomEvent =
                EventType.values()[random.nextInt(EventType.values().length)];


        notifyObservers(randomEvent);
    }
}
