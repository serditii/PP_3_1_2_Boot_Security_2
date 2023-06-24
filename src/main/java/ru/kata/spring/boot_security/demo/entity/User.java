package ru.kata.spring.boot_security.demo.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Data
@Table(name = "users")
@DynamicUpdate
public class User implements UserDetails {

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    private String username;

    @Size(min = 2, max = 30, message = "Lastname must be between 2 and 30 characters")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "The field must not be empty")
    @Email(message = "Email must be valid")
    private String email;

    @Size(min = 2, max = 30, message = "Sity must be between 2 and 30 characters")
    private String sity;

    @Min(value = 0, message = "Age must be between 0 and 100 years old")
    @Max(value = 100, message = "Age must be between 0 and 100 years old")
    private byte age;

    @Size(min = 8, message = "Password must be minimum 8 characters")
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

