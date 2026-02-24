# 🏠 OMO Smart Home Simulation

## 📌 Project Description
This project simulates a **Smart Home system** where residents interact with devices, sport items, and sensors over time.

The simulation runs in discrete time steps (**ticks**). During each tick, residents decide what action to perform  
(use device, repair device, refill, or use sport item).  
After the simulation ends, several reports are generated.

---

## UML & Use-Case Diagrams
UML diagram and Use-Case diagram are available here:  
https://miro.com/welcomeonboard/anI5L0VMeGFZOW9yczBZKzJIVjBTWXhvQzdkM21aUXJkdkZ5RlZTb2NKUHRkWDBySjhCMGpzNmxUcVhuVHE2WTVRNU16dFJWdktTL3IrQXNtaDJKMlpMM0xjQzBDNk5kNjN4KzBPZVVYdlVOeFlZNmdHM2x1aW5QZGFjcU5iNDhnbHpza3F6REdEcmNpNEFOMmJXWXBBPT0hdjE=?share_link_id=276736196146

---

## ✅ Functional Requirements (FRQ)

### FRQ1 – House configuration
The house structure (floors, rooms, residents, devices, garden, sport items) is loaded from a JSON configuration file.

**Implementation:**
- `HouseConf` loads data from `house.json`
- `HouseBuilder` constructs the house structure
- `House`, `Floor`, and `Room` store the final structure

---

### FRQ2 – Multiple device types
The system supports multiple device types (TV, Fridge, Blinds, SmartKettle, Multicooker, OutdoorGate, etc.).

**Implementation:**
- All devices inherit from abstract class `Device`
- Each device defines its own behavior and consumption
- Devices are created using the `Factory` pattern

---

### FRQ3 – Residents and roles
Each resident has a predefined role (Father, Mother, Son, Daughter, Grandpa, Grandma).

**Implementation:**
- `Resident` represents a person in the house
- `PersonType` enum defines roles
- `simulateStep()` controls resident behavior
- Different roles react differently to events (e.g. only Father repairs devices)

---

### FRQ4 – Simulation with ticks
The simulation runs in discrete time steps.

**Implementation:**
- `Simulation.run(int ticks)` controls the simulation loop
- Each tick calls `House.tick()`
- Each resident executes `simulateStep()`

---

### FRQ5 – Device interaction
Residents can use, repair, or refill devices.

**Implementation:**
- Actions are defined in `Actions` enum
- `DeviceApi` receives action requests
- Action execution is delegated to strategy classes:
  - `UseStrategy`
  - `RepairStrategy`
  - `RefillStrategy`

---

### FRQ6 – Events and alerts
The system reacts to events such as device breakdowns.

**Implementation:**
- `Sensor` generates events of type `EventType`
- Observers are notified and react accordingly
- Implemented using the Observer pattern

---

### FRQ7 – Resource consumption
Devices consume resources such as electricity and water.

**Implementation:**
- Each device stores a list of `Consumption` records
- Resource types are defined in `ResourceType`
- Consumption data is used for reports

---

### FRQ8 – Reports
Text reports are generated after the simulation ends.

**Reports:**
- `ConsumptionReportGenerator`
- `ActivityReportGenerator`
- `HouseConfigurationReportGenerator`

---

## 🧠 Design Patterns Used

### Observer Pattern
**Where:**  
`SensorEventObservable`, `SensorEventObserver`  
(Observable implemented by `Sensor`, observers implemented by `DeviceApi` and `Resident`)

**Purpose:**  
Allows the system to react to sensor-generated events without tight coupling between event producers and consumers.

**Main responsibilities and methods:**
- `notifyObservers(EventType eventType)`  
  Called by `Sensor` when an event is detected.  
  Notifies all registered observers about the event.

- `update(EventType eventType, String sensorName)`  
  Implemented by `DeviceApi` and `Resident`.  
  Defines how each observer reacts to a specific event or decides to ignore it.

**Benefit:**  
New observers can be added without modifying sensor logic.

---

### Strategy Pattern
**Where:**  
`ActionPerformStrategy` and its implementations  
(`UseStrategy`, `RepairStrategy`, `RefillStrategy`)

**Purpose:**  
Encapsulates different device-related actions and allows selecting the action behavior at runtime.

**Main responsibility and method:**
- `perform(Resident resident, Device device)`  
  Executes a specific action (use, repair, refill) on a device.

**Benefit:**  
Separates action logic from residents and devices, improving maintainability.

---

### Factory Pattern
**Where:**  
`Factory`

**Purpose:**  
Centralizes and simplifies the creation of device objects.

**Main responsibility and method:**
- `createDevice(DeviceType type, ...)`  
  Creates and returns a concrete `Device` instance based on configuration data.

**Benefit:**  
New device types can be added without changing existing creation logic.

---

### Builder Pattern
**Where:**  
`HouseBuilder`

**Purpose:**  
Handles complex construction of the house structure from configuration data.

**Main responsibility and method:**
- `build()`  
  Constructs and returns a fully initialized `House` object.

**Benefit:**  
Separates construction logic from domain classes and supports configuration-based setup.

---

### Visitor Pattern
**Where:**  
`DeviceConsumptionVisitor`, `EnergyConsumptionVisitor`

**Purpose:**  
Performs operations on devices (e.g. resource consumption calculation) without modifying device classes.

**Main responsibilities and methods:**
- `visit(Device device)`  
  Processes a device and extracts its consumption data.

- `getTotalEnergy()`  
  Returns the accumulated energy consumption after visiting all devices.

**Benefit:**  
New reporting or analytical operations can be added without changing the device hierarchy.

---

## Java Streams Usage
Java Streams are used in the project to simplify data processing and improve code readability.

**Streams are used in the following places:**

### House class
- `findBrokenDevice()` – finds the first broken device
- `findFridge()` – finds a fridge device
- `getRandomNotBrokenDevice()` – filters usable devices and selects a random one
- `getAllDevices()` – collects all devices from all floors and rooms

### EnergyConsumptionVisitor
- `sum(Device device)` – filters electricity consumption records and sums their values using streams

Streams are used only for data processing and reporting logic, while simulation behavior (ticks, actions) is implemented using classic loops to avoid side effects.

---

## ▶️ How to Run
1. Load configuration from `house.json`
2. Build house using `HouseBuilder`
3. Run simulation using `Simulation.run(ticks)`
4. Generate reports

---

## 📄 Output Files
All generated reports are stored in the directory: reports/reportsinfo
Files:
- `ConsumptionReport.txt`
- `ActivityReport.txt`
- `HouseConfigurationReport.txt`

