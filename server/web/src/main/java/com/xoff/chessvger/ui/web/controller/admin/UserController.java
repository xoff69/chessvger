package com.xoff.chessvger.ui.web.controller.admin;

import com.xoff.chessvger.config.JwtUtil;
import com.xoff.chessvger.database.DataSourceContextHolder;
import com.xoff.chessvger.database.DynamicDataSourceService;
import com.xoff.chessvger.service.UserService;
import com.xoff.chessvger.ui.form.LoginForm;
import com.xoff.chessvger.ui.web.controller.tools.ResponseList;
import com.xoff.chessvger.ui.web.controller.tools.UserDTO;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private DynamicDataSourceService dynamicDataSourceService;

    private void setDatasource() {
        // FIXME  a pousser dans le controller
        dynamicDataSourceService.addNewDataSource("common",
                "jdbc:postgresql://db_chessvger/chessvger",
                "chessvger",
                "chessvger", "common");
        DataSourceContextHolder.setDataSource("common");
    }


    @PostMapping(path = "/apiadmin/users/login")
    public UserDTO login(@RequestBody LoginForm form) {
        log.info("login " + form);
        setDatasource();
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
    public ResponseEntity<ResponseList<UserDTO>> all(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        //TODO @RequestHeader("Authorization") String token,
        Pageable pageable = PageRequest.of(page, size);
        setDatasource();
        return new ResponseEntity<>(new ResponseList(userService.findAll(pageable), userService.count()),
                HttpStatus.OK);
    }

    @GetMapping("/apiadmin/users/user")
    public ResponseEntity<UserDTO> getUser(@RequestHeader("Authorization") String token) {
        try {
            setDatasource();
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




