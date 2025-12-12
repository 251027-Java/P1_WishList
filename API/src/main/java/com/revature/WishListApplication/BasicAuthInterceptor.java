package com.revature.WishListApplication;

import com.revature.WishListApplication.Model.User;
import com.revature.WishListApplication.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Component
public class BasicAuthInterceptor implements HandlerInterceptor {
    // Fields
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    // Constructor
    public BasicAuthInterceptor (UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    // Methods

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");
        String error = "";

        // is the header there, is it the right kind?
        if (authHeader != null && authHeader.startsWith("Basic ")){
            // decode the header to a base 64 string
            String b64c = authHeader.substring(6);
            byte[] decoded = Base64.getDecoder().decode(b64c);
            String creds = new String( decoded, StandardCharsets.UTF_8);

            // split the "username:password"
            String[] parts = creds.split(":", 2);
            if (parts.length == 2) {
                String username = parts[0];
                String password = parts[1];
                error += username;
                error += " ";
                error += password;

                // check if the user is in the db
                Optional<User> user = repo.findByUserUsername(username);
                System.out.println("THE USER: " + user.isPresent());

//                // check if the password is correct
//                if (user.isPresent() && user.get().getPassword().equals(password)) {
//                    return true;
//                }
                // updated check with hashing check.
                if(user.isPresent() && passwordEncoder.matches(password, user.get().getUserPassword())){
                    return true;
                }
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized: invalid credentials" + error);
        return false;
    }
}
