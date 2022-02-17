package hr.fer.ruazosa.pointofinterest.service;

import hr.fer.ruazosa.pointofinterest.entity.Place;
import hr.fer.ruazosa.pointofinterest.entity.User;
import hr.fer.ruazosa.pointofinterest.repository.PlaceRepository;
import hr.fer.ruazosa.pointofinterest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointOfInterestService implements IPointOfInterestService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public Place addPlaceToUser(Place place, User user) {
        if (place == null || user == null || getPlaceForUser(place.getName(), user) != null
                || place.getName() == null || place.getType() == null
                || place.getLocationLatitude() == null || place.getLocationLongitude() == null )
            return null;

        place.setUser(user);
        user.addPlace(place);
        userRepository.save(user);

        return getPlaceForUser(place.getName(), user);
    }

    @Override
    public void deletePlace(Place place) {
        if (place == null)
            return;

        placeRepository.delete(place);
    }

    @Override
    public Place getPlaceForUser(String placeName, User user) {
        if (placeName == null || user == null)
            return null;

        List<Place> places = placeRepository.getUserPlace(user.getId(), placeName);
        if (places.size() != 1)
            return null;

        return places.get(0);
    }

    @Override
    public List<Place> getPlacesFromTo(User user, String name, String type, Integer fromIndex, Integer toIndex) {

        String stringRegex = "%" + name + "%";

        List<Place> allPlaces = type != null ?
                placeRepository.findUserPlacesByType(user.getId(), stringRegex, type) :
                placeRepository.findUserPlaces(user.getId(), stringRegex);


        if (toIndex < fromIndex || toIndex < 0 || fromIndex < 0) return null;
        if (toIndex > allPlaces.size()) toIndex = allPlaces.size();
        if (fromIndex > allPlaces.size()) return null;

        return allPlaces.subList(fromIndex, toIndex);
    }

    @Override
    public Long countPlaces(User user) {
        if (loginUser(user) == null)
            return null;
        return placeRepository.countUserPlaces(user.getId());
    }


    @Override
    public User registerUser(User user) {
        if (!checkUsernameUnique(user) || !checkEmailUnique(user))
            return null;

        return userRepository.save(user);
    }

    @Override
    public boolean checkUsernameUnique(User user) {
        List<User> sameUserNameUserList = userRepository.findByUserName(user.getUsername());

        return sameUserNameUserList.size() == 0;
    }

    @Override
    public boolean checkEmailUnique(User user) {
        List<User> sameEmailUserList = userRepository.findByEmail(user.getEmail());

        return sameEmailUserList.size() == 0;
    }

    @Override
    public User loginUser(User user) {
        List<User> loggedUserList = userRepository.findByUserNameAndPassword(user.getUsername(), user.getPassword());
        if (loggedUserList.isEmpty()) {
            return null;
        }
        return userRepository.findByUserNameAndPassword(user.getUsername(), user.getPassword()).get(0);
    }
}
