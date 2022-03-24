package fr.af.test.offer.api;

import fr.af.test.offer.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * User API
 */
@RequestMapping("services/v1/users")
@Api(tags = "User management API")
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
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @ResponseBody
    ResponseEntity getUser(@ApiParam(name = "id", value = "User id", example="1", required = true) @PathVariable(value = "id")  Integer id);

    /**
     * Insert or update user
     */
    @PostMapping(consumes = "application/json", produces="application/json")
    @ApiOperation(value = "Register a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @ResponseBody
    ResponseEntity postUser(@ApiParam("User")  @Valid @RequestBody UserDto userDTO, BindingResult result);

}
