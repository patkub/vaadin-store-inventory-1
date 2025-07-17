package dev.patkub.app.itemmanagement.ui.view;

public class Stat {

    private String name;
    private String value;

    public Stat(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void getValue(String value) {
        this.value = value;
    }
}
