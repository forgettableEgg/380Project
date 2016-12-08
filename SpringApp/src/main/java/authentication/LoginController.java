package authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class LoginController {

    @RequestMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity login(@RequestBody Map<String, Object> data) {
        // Check if user is already registered
        // If so return regular sign in & set token expiration
        // Else add user to DB then return sign in and set token expiration

        String email = ((String) data.get("Email"));

        int returnCode = 1;
        return new ResponseEntity<>(returnCode, HttpStatus.OK);
    }
}

//ResponseEntity<User>