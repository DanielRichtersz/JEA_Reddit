package danielrichtersz.controllers.interfaces;

import danielrichtersz.models.Redditor;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public interface RedditorController {

    ResponseEntity getRedditorByUsername(String redditorUsername);

    ResponseEntity createRedditor(String username, String password);

    Map<String, Boolean> deleteRedditor(Long userID);
}