package hr.fer.ruazosa.pointofinterest.DTO;

import hr.fer.ruazosa.pointofinterest.entity.Place;
import hr.fer.ruazosa.pointofinterest.entity.User;

public class UserPlaceDTO {
    private User user;
    private Place place;

    public UserPlaceDTO(User user, Place place) {
        this.user = user;
        this.place = place;
    }

    public User getUser() {
        return user;
    }

    public Place getPlace() {
        return place;
    }
}
