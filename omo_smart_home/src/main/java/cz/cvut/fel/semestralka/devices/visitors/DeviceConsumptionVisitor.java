package cz.cvut.fel.semestralka.devices.visitors;


import cz.cvut.fel.semestralka.devices.*;

public interface DeviceConsumptionVisitor {
    void visit(Device device);

    void visit(Fridge fridge);

    void visit(Blinds blinds);

    void visit(Multicooker multicooker);

    void visit(OutdoorGate outdoorGate);

    void visit(SmartKettle smartKettle);

    void visit(SmartLight smartLight);

    void visit(SmartSpeaker smartSpeaker);

    void visit(SmartVacuum smartVacuum);

    void visit(Television television);
}
