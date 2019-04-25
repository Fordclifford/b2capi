package com.mpesapush.controller;

import com.mpesapush.exception.ResourceNotFoundException;
import com.mpesapush.model.User;
import com.mpesapush.payload.*;
import com.mpesapush.repository.UserRepository;

import com.mpesapush.security.UserPrincipal;
import com.mpesapush.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;


//    @Autowired
//    private AgentService agentService;

    @GetMapping("/user/me")
   // @PreAuthorize("hasRole('DDR')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }



//    @GetMapping("/users/{username}/agents")
//    public PagedResponse<AgentResponse> getAgentsCreatedBy(@PathVariable(value = "username") String username,
//                                                         @CurrentUser UserPrincipal currentUser,
//                                                         @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//                                                         @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
//        return agentService.getAgentsCreatedBy(username, currentUser, page, size);
//    }


   
}