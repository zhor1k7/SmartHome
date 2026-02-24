package cz.cvut.fel.semestralka.reports.generators.activityusage;


import cz.cvut.fel.semestralka.residents.Actions;
import cz.cvut.fel.semestralka.residents.PersonType;
import lombok.Getter;

public class Activity {

    @Getter
    private final PersonType personType;
    @Getter
    private final Actions action;
    @Getter
    private final String target;

    public Activity(PersonType personType, Actions action, String target) {
        this.personType = personType;
        this.action = action;
        this.target = target;
    }


}
