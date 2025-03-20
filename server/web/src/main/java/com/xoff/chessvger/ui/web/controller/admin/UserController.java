package com.xoff.chessvger.ui.web.controller.admin;

import com.xoff.chessvger.service.UserService;
import com.xoff.chessvger.config.JwtUtil;
import com.xoff.chessvger.ui.web.controller.tools.ResponseList;
import com.xoff.chessvger.ui.web.controller.tools.UserDTO;
import com.xoff.chessvger.ui.form.LoginForm;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(path = "/apiadmin/users/login")
    public UserDTO login(@RequestBody LoginForm form) {
        log.info("login " + form);
        UserDTO user = userService.findByLoginAndPassword(form.getLogin(), form.getPassword());
        if (user == null) {
            log.info("not found " + form);
            return null;
        } else {
            log.info("ok " + user);
            user.setToken(jwtUtil.generateToken(user.getLogin()));
            log.info("login ok " + user);
            // tenantName allows the application to know which database we work with
            return user;
        }
    }

    @GetMapping("/apiadmin/users/all")
    public ResponseEntity<ResponseList<UserDTO>> all(@RequestHeader("Authorization") String token) {


        return new ResponseEntity<>(new ResponseList(userService.findAll(), userService.count()),
                HttpStatus.OK);
    }

    @GetMapping("/apiadmin/users/user")
    public ResponseEntity<UserDTO> getUser(@RequestHeader("Authorization") String token) {
        try {

            String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));

            UserDTO user = userService.getUserByUsername(username);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }


            return ResponseEntity.ok(user);

        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}




