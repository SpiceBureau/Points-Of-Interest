package hr.fer.ruazosa.pointofinterest;

public interface IPointOfInterestService {
    User registerUser(User user);
    boolean checkUsernameUnique(User user);
    boolean checkEmailUnique(User user);
    User loginUser(User user);
}
