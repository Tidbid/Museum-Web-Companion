package com.romanov.rksp.museum.model;

import com.romanov.rksp.museum.dto.UserRegistrationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints=@UniqueConstraint(columnNames="username"))
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private String username;
    private String password;

    @ManyToMany(fetch= FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    public AppUser(UserRegistrationDto userRegistrationDto) {
        this.name = userRegistrationDto.getName();
        this.surname = userRegistrationDto.getSurname();
        this.patronymic = userRegistrationDto.getPatronymic();
        this.username = userRegistrationDto.getUsername();
        this.password = userRegistrationDto.getPassword();
    }
}
