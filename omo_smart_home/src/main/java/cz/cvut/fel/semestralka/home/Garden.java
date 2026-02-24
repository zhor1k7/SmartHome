package cz.cvut.fel.semestralka.home;

import cz.cvut.fel.semestralka.residents.Resident;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class Garden {

    private final List<SportItem> sportItems = new ArrayList<>();


    private final List<Resident> currentResidents = new CopyOnWriteArrayList<>();

    private final House house;

    public Garden(House house) {
        if (house == null) {
            throw new IllegalArgumentException("House cannot be null");
        }
        this.house = house;
    }
}