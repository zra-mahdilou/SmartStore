package com.example.smartstore.service;

import com.example.smartstore.component.MessageComponent;
import com.example.smartstore.dto.AuthenticationResponse;
import com.example.smartstore.dto.LoginRequest;
import com.example.smartstore.dto.RegisterRequestDto;
import com.example.smartstore.entity.Users;
import com.example.smartstore.enums.Role;
import com.example.smartstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final jwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MessageComponent messageComponent;

    public Object register(RegisterRequestDto request) {
        String validationUser = validationUser(request);
        if (validationUser.equals("ok")) {
            var users = Users.builder().
                    firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .userName(request.getUserName())
                    .password(request.getPassword())
                    .role(Role.valueOf(request.getRole()))
                    .build();
            userRepository.save(users);
            var jwtToken = jwtService.generateToken(users);
            return AuthenticationResponse.builder().token(jwtToken).build();
        } else {
            return validationUser;
        }
    }


    private String validationUser(RegisterRequestDto request) {
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String email = request.getEmail();
        String userName = request.getUserName();
        String password = request.getPassword();
        String role = request.getRole();

        if (firstName.equals("")) {
            return messageComponent.getMessage("MANDATORY.firstName");
        }
        if (lastName.equals("")) {
            return messageComponent.getMessage("MANDATORY.lastName");
        }
        if (email.equals("")) {
            return messageComponent.getMessage("VALIDATION.email", new Object[]{email});
        }
        if (userName.equals("")) {
            return messageComponent.getMessage("VALIDATION.userName");
        }
        if (password.length() < 8) {
            return messageComponent.getMessage("VALIDATION.password");
        }
        List roles = List.of("ADMIN", "Seller", "Customer");
        if (!roles.contains(role)) {
            return messageComponent.getMessage("VALIDATION.role", roles.toArray(new Object[0]));
        }
        if (userRepository.existsAllByUserName(userName)) {
            return messageComponent.getMessage("VALIDATION.exist.userName");
        }
        if (userRepository.existsAllByEmail(email)) {
            return messageComponent.getMessage("VALIDATION.exist.email");
        }
        return "ok";
    }

    public Object login(LoginRequest request) {
        var users = userRepository.findAllByUserNameAndPassword(request.getUserName(),request.getPassword());
        if (users.isAccountNonExpired()) {
            var jwtToken = jwtService.generateToken(users);
            return AuthenticationResponse.builder().token(jwtToken).build();
        } else {
            return "Username or password is wrong";
        }
    }

}
