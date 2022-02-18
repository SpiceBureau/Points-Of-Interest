package hr.fer.ruazosa.pointofinterest.service;

import hr.fer.ruazosa.pointofinterest.entity.Place;
import hr.fer.ruazosa.pointofinterest.entity.User;
import hr.fer.ruazosa.pointofinterest.entity.VerificationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IPointOfInterestService extends UserDetailsService{
    Place addPlaceToUser(Place place, User user);
    void deletePlace(Place place);
    Place getPlaceForUser(String placeName, User user);
    List<Place> getPlacesFromTo(User user, String name, String type, Integer fromIndex, Integer toIndex);
    Long countPlaces(User user);

    User registerUser(User user);
    User getUser(String username);
    boolean checkUsernameUnique(User user);
    boolean checkEmailUnique(User user);

    UserDetails loadUserByUsername(String username);
    VerificationToken getVerificationToken(String VerificationToken);
    void saveRegisteredUser(User user);
    void createVerificationToken(User user, String token);
}
