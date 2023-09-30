package SpringBoot.Bearer.Token.JWT.Security.with.MySQL.SpringToken.model;

import jakarta.persistence.*;
import lombok.*;
@Table(name="Users")
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username",unique = true)
    private String username;
    @Column(name = "password",nullable = false)
    
    private String password;
}
