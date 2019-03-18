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
        //TODO: Auto error message when duplicate names are found, and throw with Try/Catch
        if (redditorService.findByUsername(username) == null) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(redditorService.createRedditor(username, password));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("This username is already in use, please try another username");
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



    /*@PutMapping("/redditors/{id}")
    @ApiOperation(value = "Update a redditor by id")
    public ResponseEntity<Redditor> updateRedditor(@ApiParam(value = "The id of the to-be updated redditor", required = true)
                                                       @PathVariable(value = "id") Long userID,
                                                   @ApiParam(value = "The new username of the to-be updated redditor", required = true)
                                                        @PathVariable(value = "name") String username) {


    }*/

    @DeleteMapping("/redditors/{id}")
    @ApiOperation(value = "Delete a redditor by id")
    @Override
    public Map<String, Boolean> deleteRedditor(@ApiParam(value = "The id of the to-be deleted redditor", required = true)
                                               @PathVariable(value = "id") Long userID) {
        redditorService.deleteRedditor(userID);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
