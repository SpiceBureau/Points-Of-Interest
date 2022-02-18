package hr.fer.ruazosa.pointofinterest.controller;

import hr.fer.ruazosa.pointofinterest.entity.User;
import hr.fer.ruazosa.pointofinterest.entity.VerificationToken;
import hr.fer.ruazosa.pointofinterest.service.IPointOfInterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@RestController
public class RegistrationController {
    @Autowired
    private IPointOfInterestService service;

    @GetMapping("/registrationConfirm")
    public ResponseEntity<Object> confirmRegistration
            (WebRequest request, Model model, @RequestParam("token") String token) {

        Locale locale = request.getLocale();

        VerificationToken verificationToken = service.getVerificationToken(token);
        if (verificationToken == null) {
            return returnError("wrong token");
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return returnError("token expired");
        }

        user.setEnabled(true);
        service.saveRegisteredUser(user);
        return new ResponseEntity<>("Successful authentication", HttpStatus.OK);
    }

    private ResponseEntity<Object> returnError(String message) {
        Map<String, Object> bodyError = new LinkedHashMap<>();
        bodyError.put("error", message);
        return new ResponseEntity<>(bodyError, HttpStatus.NOT_ACCEPTABLE);
    }
}
