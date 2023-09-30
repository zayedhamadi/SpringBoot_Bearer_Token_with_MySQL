package SpringBoot.Bearer.Token.JWT.Security.with.MySQL.SpringToken.controller;

import SpringBoot.Bearer.Token.JWT.Security.with.MySQL.SpringToken.model.TokenReqRes;
import SpringBoot.Bearer.Token.JWT.Security.with.MySQL.SpringToken.model.Users;
import SpringBoot.Bearer.Token.JWT.Security.with.MySQL.SpringToken.repository.UserRepository;
import SpringBoot.Bearer.Token.JWT.Security.with.MySQL.SpringToken.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class OurController {

    @Autowired
    private JwtTokenUtil JwtTokenUtil;

    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private BCryptPasswordEncoder BCryptPasswordEncoder;


    @PostMapping("/register")
    public ResponseEntity<Object> Register(@RequestBody Users u) {
        String HashCodePassword = this.BCryptPasswordEncoder.encode(u.getPassword());
        u.setPassword(HashCodePassword);
        if (UserRepository.save(u).getId() > 0) {
            return ResponseEntity.ok("User Was Saved");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while registration ");
    }


    @PostMapping("/generateToken")
    public ResponseEntity<Object> generateToken(@RequestBody TokenReqRes t) {


        Users databaseUser = this.UserRepository.findByUsername(t.getUsername());

        if (databaseUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exist to generate another token");
        }
        if (new BCryptPasswordEncoder().matches(t.getPassword(), databaseUser.getPassword())) {
            String token = JwtTokenUtil.generateToken(t.getUsername());
            t.setToken(token);
            t.setExpirationTime("60sec");
            return ResponseEntity.ok("generated token successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password does not match ,verify ur password");
        }
    }

    @PostMapping("/validateToken")
    public ResponseEntity<Object> validateToken(@RequestBody TokenReqRes tr) {
        return ResponseEntity.ok(JwtTokenUtil.validateToken(tr.getToken()));
    }

    @GetMapping("/getFruits")
    public ResponseEntity<Object> getFruits(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is required to proceed ");
        } else {
            String realToken = token.substring(7);
            String tokenCheckResult = JwtTokenUtil.generateToken(realToken);
            if (tokenCheckResult.equalsIgnoreCase("valid")) {
                List<String> fruits = List.of("Mango", "Banana", "Orange", "Watermellon", "Grapes", "Appple", "Berries");
                return new ResponseEntity<>(fruits, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorixed dur to: " + tokenCheckResult);
            }
        }
    }
}
