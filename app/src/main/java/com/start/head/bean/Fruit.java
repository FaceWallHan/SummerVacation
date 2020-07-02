package com.start.head.bean;

public class Fruit {
    private int typeId;
    private String name;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Fruit(int typeId, String name) {
        this.typeId = typeId;
        this.name = name;
    }
}
