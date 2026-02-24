package cz.cvut.fel.semestralka.home;

import cz.cvut.fel.semestralka.residents.Resident;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
public class SportItem {

    private final String name;
    private final SportItemType type;

    private boolean inUse = false;


    @Setter
    private Resident currentUser;

    public SportItem(String name, SportItemType type) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("SportItem ID cannot be null or empty");
        }
        if (type == null) {
            throw new IllegalArgumentException("SportItemType cannot be null");
        }

        this.name = name;
        this.type = type;
    }


    public boolean startUsing(Resident resident) {
        if (inUse) {
            return false;
        }

        this.inUse = true;
        this.currentUser = resident;
        return true;
    }


    public void stopUsing() {
        this.inUse = false;
        this.currentUser = null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SportItem sportItem = (SportItem) o;
        return Objects.equals(name, sportItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}