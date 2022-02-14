package hr.fer.ruazosa.pointofinterest.service;

import hr.fer.ruazosa.pointofinterest.entity.Place;
import hr.fer.ruazosa.pointofinterest.entity.User;

import java.util.List;

public interface IPointOfInterestService {
    Place addPlaceToUser(Place place, User user);
    void deletePlace(Place place);
    Place getPlaceForUser(Place place, User user);
    List<Place> getPlacesFromTo(User user, Integer fromIndex, Integer toIndex);
    Long countPlaces(User user);
    User registerUser(User user);
    boolean checkUsernameUnique(User user);
    boolean checkEmailUnique(User user);
    User loginUser(User user);
}
