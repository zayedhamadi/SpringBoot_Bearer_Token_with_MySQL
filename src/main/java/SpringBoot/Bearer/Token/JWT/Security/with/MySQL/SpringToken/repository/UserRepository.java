package SpringBoot.Bearer.Token.JWT.Security.with.MySQL.SpringToken.repository;

import SpringBoot.Bearer.Token.JWT.Security.with.MySQL.SpringToken.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<Users,Long> {
Users findByUsername(String username);
}
