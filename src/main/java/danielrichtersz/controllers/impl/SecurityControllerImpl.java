package danielrichtersz.controllers.impl;

import danielrichtersz.models.JwtUser;
import danielrichtersz.models.Redditor;
import danielrichtersz.repositories.interfaces.RedditorRepository;
import danielrichtersz.security.JwtGenerator;
import danielrichtersz.services.interfaces.RedditorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(value = "Security Controller Implementation", description = "Operations pertaining to security through Spring Boot REST API")
public class SecurityControllerImpl {

    @Autowired
    private RedditorService redditorService;

    private JwtGenerator jwtGenerator;

    public SecurityControllerImpl(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/token")
    public ResponseEntity generate(@ApiParam(value = "The username for the redditor", required = true) @RequestParam(value = "username") String username,
                           @ApiParam(value = "The password for the redditor", required = true) @RequestParam(value = "password") String password) {

        Redditor redditor = redditorService.findByUsername(username);

        if (redditor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Redditor with given username not found");
        }

        if (!redditor.passwordIsValid(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password not valid");
        }

        return ResponseEntity.status(HttpStatus.OK).body(jwtGenerator.generate(redditor));
    }
}
