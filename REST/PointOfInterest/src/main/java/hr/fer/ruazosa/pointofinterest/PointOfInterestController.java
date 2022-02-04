package hr.fer.ruazosa.pointofinterest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.LinkedHashMap;
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

}
