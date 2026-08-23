package it.unimore.fum.iot.utils.types.simulation.passenger;

import java.util.UUID;

public class Passenger {

    private final String name;
    private final String surname;

    private final int age;
    private final long boardingTimestamp;
    private final UUID uuid;
    private PassengerTasksEnum selectedTask;
    private PassengerTasksEnum previousTask;

    public Passenger() {
        this.name = "John";
        this.surname = "Smith";
        this.age = 20;
        this.boardingTimestamp = System.currentTimeMillis();
        this.uuid = UUID.randomUUID();
        this.selectedTask = PassengerTasksEnum.ENTER_CARRIAGE;
        this.previousTask = PassengerTasksEnum.ENTER_CARRIAGE;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public PassengerTasksEnum getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(PassengerTasksEnum selectedTask) {
        this.selectedTask = selectedTask;
    }

    public PassengerTasksEnum getPreviousTask() {
        return previousTask;
    }

    public void setPreviousTask(PassengerTasksEnum previousTask) {
        this.previousTask = previousTask;
    }

    public long getBoardingTimestamp() {
        return boardingTimestamp;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", selectedTask=" + selectedTask +
                '}';
    }
}
