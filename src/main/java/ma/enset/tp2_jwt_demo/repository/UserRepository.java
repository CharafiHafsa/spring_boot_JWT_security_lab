package ma.enset.tp2_jwt_demo.repository;

import ma.enset.tp2_jwt_demo.entity.AppUser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface UserRepository extends JpaRepository<AppUser, Long>{
    Optional<AppUser> findByEmail(String email);
    boolean existsByEmail(String email);

}
