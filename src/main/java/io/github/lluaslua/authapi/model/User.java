package io.github.lluaslua.authapi.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotEmpty
    private String name;

    @NotEmpty
    @Column(unique = true, name = "email")
    private String email;

    @Column(name = "password")
    @NotEmpty
    private String password;




}
