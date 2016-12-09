package authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;


@RestController
public class LoginController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity login(@RequestBody Map<String, Object> data) {
        // Setting internal variables and initial query
        String email = ((String) data.get("Email"));
        String firstName = ((String) data.get("FirstName"));
        String lastName = ((String) data.get("LastName"));
        String token = ((String) data.get("Token"));
        long currEpoch = System.currentTimeMillis() / 1000;
        long expireEpoch = currEpoch + (30 * 60 * 1000); // Token expiration set to 30 minutes

        String sql = "Select * FROM user where email = '" + email + "'";
        List<User> result = query(sql);

        // If return code == 0 then error
        int returnCode = 0;

        // If the user already exists give them a token and expiration
        if (result.size() == 1) {
            sql = "UPDATE user SET token = '" + token + "', token_expiration = '" + expireEpoch + "' WHERE email = '" + email + "'";
            update(sql);
            returnCode = 1;
        }
        // If no user
        else if (result.size() == 0) {
            sql = "INSERT INTO user (LastName, FirstName, email, token, token_expiration) " +
                    "VALUES ('" + lastName + "', '" + firstName + "', '" + email + "', '" + token + "', '" + expireEpoch + "')";
            update(sql);
            returnCode = 1;
        }

        return new ResponseEntity<>(returnCode, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/isAuthenticated", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity isAuthenticated(@RequestBody Map<String, Object> data) {
        long currEpoch = System.currentTimeMillis() / 1000;
        long expireEpoch = currEpoch + (30 * 60 * 1000); // Token expiration set to 30 minutes
        int returnCode = 0;
        String token = ((String) data.get("Token"));
        long expire = Long.valueOf((String) data.get("Token_Expiration"));
        String sql = "Select * FROM user where token = '" + token + "'";
        List<User> result = query(sql);

        if (result.size() == 1){
            if (expire > currEpoch ){
                returnCode = 1;
                sql = "UPDATE user SET token_expiration = '" + expireEpoch + "' WHERE token = '" + token + "'";
                update(sql);
            }
        }

        return new ResponseEntity<>(returnCode, HttpStatus.OK);
    }


    private List<User> query(String sql) {
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
    }

    private void update(String sql) {
        jdbcTemplate.update(sql);
    }
}