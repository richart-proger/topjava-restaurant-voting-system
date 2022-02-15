package ru.javawebinar.restaurant_voting_system.to;

import ru.javawebinar.restaurant_voting_system.HasId;

public abstract class BaseTo implements HasId {
    protected Integer id;

    public BaseTo() {
    }

    protected BaseTo(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}