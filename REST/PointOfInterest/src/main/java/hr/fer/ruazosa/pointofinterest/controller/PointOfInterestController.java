package hr.fer.ruazosa.pointofinterest.controller;

import hr.fer.ruazosa.pointofinterest.JWT.JwtResponse;
import hr.fer.ruazosa.pointofinterest.JWT.JwtUtils;
import hr.fer.ruazosa.pointofinterest.entity.Place;
import hr.fer.ruazosa.pointofinterest.service.IPointOfInterestService;
import hr.fer.ruazosa.pointofinterest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IPointOfInterestService pointOfInterestService;

    @PostMapping("/registerUser")
    public ResponseEntity<Object> registerUser(@RequestBody User user, HttpServletRequest request) {
        // validation
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        Map<String, Object> body = new LinkedHashMap<>();
        for (ConstraintViolation<User> violation : violations)
            body.put(violation.getPropertyPath().toString(), violation.getMessage());
        if (!body.isEmpty())
            return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);

        if (!pointOfInterestService.checkUsernameUnique(user))
            return returnError("user with same username already exists");
        if (!pointOfInterestService.checkEmailUnique(user))
            return returnError("user with same email already exists");

        if (user.getPassword().length() > 60)
            return returnError("password can not be longer than 60 characters");

        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user = pointOfInterestService.registerUser(user);

        return createAuthenticationToken(new User(user.getUsername(), password));
    }

    @PostMapping("/loginUser")
    public ResponseEntity<Object> createAuthenticationToken(@RequestBody User user) {

        try {
            authenticate(user.getUsername(), user.getPassword());
        } catch (Exception e) {
            return returnError("authentication failed");
        }

        UserDetails userDetails = pointOfInterestService.loadUserByUsername(user.getUsername());
        final String token = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/addPlace")
    public ResponseEntity<Object> addPlace(@RequestBody Place place) {

        User user = getCurrentUser();

        Place returnPlace = pointOfInterestService.addPlaceToUser(place, user);
        if (returnPlace == null)
            return returnError("place can not be added");

        return new ResponseEntity<>(returnPlace, HttpStatus.OK);
    }

    @DeleteMapping("/removePlace")
    public ResponseEntity<Object> removePlace(@RequestParam String placeName) {

        User user = getCurrentUser();

        Place place = pointOfInterestService.getPlaceForUser(placeName, user);
        if (place == null)
            return returnError("place can not be reached");

        pointOfInterestService.deletePlace(place);

        return new ResponseEntity<>(place, HttpStatus.OK);
    }

    @GetMapping("/getPlaces")
    public ResponseEntity<Object> getPlacesByName(@RequestParam(defaultValue = "") String name,
                                                  @RequestParam(required = false) String type,
                                                  @RequestParam Integer fromIndex,
                                                  @RequestParam Integer toIndex) {

        User user = getCurrentUser();

        List<Place> resultPlaces = pointOfInterestService.getPlacesFromTo(user, name, type, fromIndex, toIndex);
        if (resultPlaces == null)
            return returnError("can not get places for given data");

        return new ResponseEntity<>(resultPlaces, HttpStatus.OK);
    }

    @GetMapping("/countPlaces")
    public ResponseEntity<Object> countPlaces() {

        User user = getCurrentUser();

        Long resultCountPlaces = pointOfInterestService.countPlaces(user);
        if (resultCountPlaces == null)
            return returnError("can not count places for this user");

        return new ResponseEntity<>(resultCountPlaces, HttpStatus.OK);
    }



    private ResponseEntity<Object> returnError(String message) {
        Map<String, Object> bodyError = new LinkedHashMap<>();
        bodyError.put("error", message);
        return new ResponseEntity<>(bodyError, HttpStatus.NOT_ACCEPTABLE);
    }

    private User getCurrentUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return pointOfInterestService.getUser(userDetails.getUsername());
    }
}
