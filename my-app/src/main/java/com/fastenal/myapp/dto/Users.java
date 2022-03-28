package com.fastenal.myapp.dto;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Customers")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String email;
    private String password;
    @ManyToMany()
    @JoinTable(
            name = "users_course",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Courses> course;

    public Users() {
    }

    public Users(int id, String username, String email, String password, Set<Courses> course) {
        this.id = id;
        this.username = username;
        this. email = email;
        this.password = password;
        this.course = course;
    }

    public Set<Courses> getCourse() {
        return course;
    }

    public void setCourse(Courses c) {
        this.course.add(c);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
