package ru.javawebinar.restaurant_voting_system.model;

import org.hibernate.Hibernate;

import javax.persistence.*;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBaseEntity {
    public static final int START_SEQ = 100000;

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
//  See https://hibernate.atlassian.net/browse/HHH-3718 and https://hibernate.atlassian.net/browse/HHH-12034
//  Proxy initialization when accessing its identifier managed now by JPA_PROXY_COMPLIANCE setting
    protected Integer id;

    public AbstractBaseEntity() {
    }

    protected AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }

    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        AbstractBaseEntity that = (AbstractBaseEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }
}