package danielrichtersz.controllers.impl;

import danielrichtersz.models.Redditor;
import danielrichtersz.controllers.interfaces.RedditorController;
import danielrichtersz.services.interfaces.RedditorService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.Map;

//Controller is same as Bean, endpoint layer
@Controller
@RequestMapping("/api")
@Api(value = "Redditor Controller Implementation", description = "Operations pertaining to redditors through Spring Boot REST API")
public class RedditorControllerImpl implements RedditorController {

    @Autowired
    private RedditorService redditorService;

    public RedditorControllerImpl() {

    }

    public RedditorControllerImpl(RedditorService redditorService) {
        this.redditorService = redditorService;
    }

    @PostMapping("/redditors")
    @Override
    public ResponseEntity createRedditor(@ApiParam(value = "The username for the to-be created redditor", required = true) @RequestParam(value = "name") String username,
                                         @ApiParam(value = "The password for the to-be created redditor", required = true) @RequestParam(value = "password") String password) {
        if (redditorService.findByUsername(username) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This username is already in use, please try another username");
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(redditorService.createRedditor(username, password));
    }

    @GetMapping("/redditors/{name}")
    @ApiOperation(value = "Find a specific redditor by full username", response = Redditor.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "The given username did not match an existing user's username")
    })
    @Override
    public ResponseEntity getRedditorByUsername(@PathVariable(value = "name") String redditorUsername) {
        Redditor found = redditorService.findByUsername(redditorUsername);
        if (found != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body(found);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The given username did not match an existing user's username");
    }

    @PutMapping("/redditors")
    @ApiOperation(value = "Edit a specific redditor by username", response = Redditor.class)
    @Override
    public ResponseEntity editRedditor(
            @ApiParam(value = "The username for the to-be edited redditor", required = true) @RequestParam(value = "username") String username,
            @ApiParam(value = "The old password of the redditor, for verification") @RequestParam(value = "oldpassword") String oldpassword,
            @ApiParam(value = "The new password of the redditor") @RequestParam(value = "newpassword") String newpassword) {
        Redditor redditor = redditorService.findByUsername(username);

        if (redditor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Redditor not found");
        }

        if (!redditor.passwordIsValid(oldpassword)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The provided current password did not match the password for this user");
        }

        redditor.setPassword(newpassword);

        Redditor editedRedditor = redditorService.editRedditor(redditor);

        if (editedRedditor == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong, please try again or contact customer support");
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(editedRedditor);
    }

    @DeleteMapping("/redditors")
    @ApiOperation(value = "Delete a redditor by username")
    @Override
    public ResponseEntity deleteRedditor(@ApiParam(value = "The id of the to-be deleted redditor", required = true)
                                         @RequestParam(value = "username") String username,
                                         @ApiParam(value = "The password of the to-be deleted redditor", required = true)
                                         @RequestParam(value = "password") String password) {
        Redditor redditor = redditorService.findByUsername(username);
        if (redditor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Redditor not found");
        }

        if (!redditor.passwordIsValid(password)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Given password did not match the password for this Redditor");
        }

        redditorService.deleteRedditor(username);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Redditor succesfully deleted");
    }
}
