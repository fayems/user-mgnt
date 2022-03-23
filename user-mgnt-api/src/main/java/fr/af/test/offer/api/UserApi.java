package fr.af.test.offer.api;

import fr.af.test.offer.dto.UserDto;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * User API
 */
@RequestMapping("services/v1/users")
@Api(tags = "User management API", protocols = "http, https")
public interface UserApi {

    /**
     * Get user's infos if exist in database
     * @param id user id
     * @return UserDto
     */
    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation(value = "Display the details of a registered user",
            response = UserDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @ResponseBody
    ResponseEntity getUser(@ApiParam(name = "id", value = "User id", example="1", required = true) @PathVariable(value = "id")  Integer id);

    /**
     * Insert or update user
     */
    @PostMapping(consumes = "application/json", produces="application/json")
    @ApiOperation(value = "Register a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @ResponseBody
    ResponseEntity postUser(@ApiParam("User")  @Valid @RequestBody UserDto userDTO, BindingResult result);

}
