package capstone.csc8429.onboardingapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "role")
    private String role;


    // Constructors
    public Role() {
    }

    public Role(int userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    public Role(int id, int userId, String role) {
        this.id = id;
        this.userId = userId;
        this.role = role;
    }


    // Getters/Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    // toString()
    @Override
    public String toString() {
        return "Roles{" +
                "id=" + id +
                ", userId=" + userId +
                ", role='" + role + '\'' +
                '}';
    }
}
