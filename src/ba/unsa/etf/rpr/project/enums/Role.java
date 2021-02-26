package ba.unsa.etf.rpr.project.enums;

public enum Role {
    //full view can only be accessed by these roles
    ADMIN(1),
    USER(2);

    private int value;

    Role(int value) {
        this.value = value;
    }

    Role() {
        setValue(2);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}