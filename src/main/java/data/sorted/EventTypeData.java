package data.sorted;

public enum EventTypeData {
    OPEN("Open webinar");

    private String name;

    EventTypeData(String name) {
        this.name= name;
    }

    public String getName() {
        return name;
    }
}