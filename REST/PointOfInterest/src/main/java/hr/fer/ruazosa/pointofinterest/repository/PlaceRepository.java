package hr.fer.ruazosa.pointofinterest.repository;

import hr.fer.ruazosa.pointofinterest.entity.Place;
import hr.fer.ruazosa.pointofinterest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {

    @Query("SELECT p FROM User u JOIN Place p ON u.id = p.user.id WHERE u.id = ?1 ORDER BY p.name")
    List<Place> findUserPlaces(Long userId);

    @Query("SELECT p FROM User u JOIN Place p ON u.id = p.user.id WHERE u.id = ?1 AND p.name = ?2")
    List<Place> getUserPlace(Long userId, String placeName);

    @Query("SELECT COUNT(p) FROM User u JOIN Place p ON u.id = p.user.id WHERE u.id = ?1")
    Long countUserPlaces(Long userId);
}
