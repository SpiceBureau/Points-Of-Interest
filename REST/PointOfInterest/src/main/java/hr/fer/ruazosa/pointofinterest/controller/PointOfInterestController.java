package hr.fer.ruazosa.pointofinterest.controller;


import hr.fer.ruazosa.pointofinterest.DTO.UserFromIndexToIndexDTO;
import hr.fer.ruazosa.pointofinterest.DTO.UserPlaceDTO;
import hr.fer.ruazosa.pointofinterest.entity.Place;
import hr.fer.ruazosa.pointofinterest.service.IPointOfInterestService;
import hr.fer.ruazosa.pointofinterest.entity.User;
import jdk.nashorn.internal.ir.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PostRemove;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class PointOfInterestController {

    @Autowired
    private IPointOfInterestService pointOfInterestService;

    @PostMapping("/registerUser")
    public ResponseEntity<Object> registerUser(@RequestBody User user) {
        // validation
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        Map<String, Object> body = new LinkedHashMap<>();
        for (ConstraintViolation<User> violation : violations) {
            body.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        if (!body.isEmpty()) {
            return new ResponseEntity<Object>(body, HttpStatus.NOT_ACCEPTABLE);
        }

        if (!pointOfInterestService.checkUsernameUnique(user)) {
            Map<String, Object> bodyError = new LinkedHashMap<>();
            bodyError.put("error", "user with same username already exists");
            return new ResponseEntity<Object>(bodyError, HttpStatus.NOT_ACCEPTABLE);
        }
        if (!pointOfInterestService.checkEmailUnique(user)) {
            Map<String, Object> bodyError = new LinkedHashMap<>();
            bodyError.put("error", "user with same email already exists");
            return new ResponseEntity<Object>(bodyError, HttpStatus.NOT_ACCEPTABLE);
        }

        pointOfInterestService.registerUser(user);
        return new ResponseEntity<Object>(user, HttpStatus.OK);
    }

    @PostMapping("/loginUser")
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
        // validation
        if (user == null) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("error", "no user JSON object in body");
            return new ResponseEntity<Object>(body, HttpStatus.NOT_ACCEPTABLE);
        }
        else if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("error", "username or password parameters are empty");
            return new ResponseEntity<Object>(body, HttpStatus.NOT_ACCEPTABLE);
        }
        else {
            User loggedUser = pointOfInterestService.loginUser(user);
            if (loggedUser != null) {
                return new ResponseEntity<Object>(loggedUser, HttpStatus.OK);
            }
            else {
                Map<String, Object> body = new LinkedHashMap<>();
                body.put("error", "no user found");
                return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
            }
        }
    }



    @PostMapping("/addPlace")
    public ResponseEntity<Object> addPlace(@RequestBody UserPlaceDTO userPlaceDTO) {
        ResponseEntity<Object> loginResponse = this.loginUser(userPlaceDTO.getUser());
        if (loginResponse.getStatusCode() != HttpStatus.OK)
            return loginResponse;

        Place returnPlace = pointOfInterestService.addPlaceToUser(userPlaceDTO.getPlace(), (User) loginResponse.getBody());
        if (returnPlace == null){
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("error", "place can not be added");
            return new ResponseEntity<Object>(body, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<Object>(returnPlace, HttpStatus.OK);
    }

    @DeleteMapping("/removePlace")
    public ResponseEntity<Object> removePlace(@RequestBody User user, @RequestParam String placeName) {
        ResponseEntity<Object> loginResponse = this.loginUser(user);
        if (loginResponse.getStatusCode() != HttpStatus.OK)
            return loginResponse;

        user = (User) loginResponse.getBody();
        Place place = pointOfInterestService.getPlaceForUser(placeName, user);

        if (place == null) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("error", "place can not be reached");
            return new ResponseEntity<Object>(body, HttpStatus.NOT_ACCEPTABLE);
        }

        pointOfInterestService.deletePlace(place);

        return new ResponseEntity<Object>(place, HttpStatus.OK);
    }


    @PostMapping("/getPlaces")
    public ResponseEntity<Object> getPlacesByName(@RequestBody User user, @RequestParam(defaultValue = "") String name, @RequestParam(required = false) String type,
                                            @RequestParam Integer fromIndex, @RequestParam Integer toIndex) {
        ResponseEntity<Object> loginResponse = this.loginUser(user);
        if (loginResponse.getStatusCode() != HttpStatus.OK)
            return loginResponse;

        List<Place> resultPlaces = pointOfInterestService.getPlacesFromTo((User) loginResponse.getBody(), name, type, fromIndex, toIndex);

        if (resultPlaces == null){
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("error", "can not get places for given data");
            return new ResponseEntity<Object>(body, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<Object>(resultPlaces, HttpStatus.OK);
    }

    @GetMapping("/countPlaces")
    public ResponseEntity<Object> countPlaces(@RequestBody User user) {
        ResponseEntity<Object> loginResponse = this.loginUser(user);
        if (loginResponse.getStatusCode() != HttpStatus.OK)
            return loginResponse;

        Long resultCountPlaces = pointOfInterestService.countPlaces((User) loginResponse.getBody());
        if (resultCountPlaces == null){
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("error", "can not count places for this user");
            return new ResponseEntity<Object>(body, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<Object>(resultCountPlaces, HttpStatus.OK);
    }
}
