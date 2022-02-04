package hr.fer.ruazosa.pointofinterest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u where u.email = ?1")
    List<User> findByEmail(String email);

    @Query("SELECT u FROM User u where u.username = ?1")
    List<User> findByUserName(String userName);

    @Query("SELECT u FROM User u where u.username = ?1 and u.password = ?2")
    List<User> findByUserNameAndPassword(String userName, String password);

}
