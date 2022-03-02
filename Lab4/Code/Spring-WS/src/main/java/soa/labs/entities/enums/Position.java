package soa.labs.entities.enums;

public enum Position {
    MANAGER("manager"),
    ENGINEER("engineer"),
    BAKER("baker"),
    MANAGER_OF_CLEANING("manager_of_cleaning");

    String position;

    Position(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

}