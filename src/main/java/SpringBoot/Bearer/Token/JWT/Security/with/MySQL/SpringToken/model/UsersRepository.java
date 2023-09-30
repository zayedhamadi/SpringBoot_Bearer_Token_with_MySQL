package SpringBoot.Bearer.Token.JWT.Security.with.MySQL.SpringToken.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}