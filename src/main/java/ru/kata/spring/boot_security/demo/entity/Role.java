package ru.kata.spring.boot_security.demo.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Set;

@Entity
@Data
@Table(name = "roles")
@DynamicUpdate
public class Role implements GrantedAuthority {

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    private String name;

    public Role() {
    }

    public Role(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return null;
    }
}
