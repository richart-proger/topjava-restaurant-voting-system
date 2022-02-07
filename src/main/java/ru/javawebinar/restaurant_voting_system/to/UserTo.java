package ru.javawebinar.restaurant_voting_system.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.javawebinar.restaurant_voting_system.model.Role;

import java.beans.ConstructorProperties;
import java.util.Date;
import java.util.Set;

public class UserTo {

    @JsonProperty(value = "user_id")
    private Integer id;

    @JsonProperty(value = "user_name")
    private String name;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "password")
    private String password;

    @JsonProperty(value = "enabled")
    private boolean enabled;

    @JsonProperty(value = "registered")
    private Date registered;

    @JsonProperty(value = "roles")
    private Set<Role> roles;

    public UserTo() {
    }

    @ConstructorProperties({"user_id", "user_name", "email", "password", "enabled", "registered", "roles"})
    public UserTo(Integer id, String name, String email, String password, boolean enabled, Date registered, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.registered = registered;
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}