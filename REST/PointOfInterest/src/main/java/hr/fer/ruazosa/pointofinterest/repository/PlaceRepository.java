package hr.fer.ruazosa.pointofinterest.repository;

import hr.fer.ruazosa.pointofinterest.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {

    @Query("SELECT p FROM User u JOIN Place p ON u.id = p.user.id WHERE u.id = ?1 AND p.name LIKE ?2 ORDER BY p.name")
    List<Place> findUserPlaces(Long userId, String regex);

    @Query("SELECT p FROM User u JOIN Place p ON u.id = p.user.id WHERE u.id = ?1 AND p.name LIKE ?2 AND p.type = ?3 ORDER BY p.name")
    List<Place> findUserPlacesByType(Long userId, String regex, String type);

    @Query("SELECT p FROM User u JOIN Place p ON u.id = p.user.id WHERE u.id = ?1 AND p.name = ?2")
    List<Place> getUserPlace(Long userId, String placeName);

    @Query("SELECT COUNT(p) FROM User u JOIN Place p ON u.id = p.user.id WHERE u.id = ?1")
    Long countUserPlaces(Long userId);
}
