package com.web.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.jwt.JwtUtils;
import com.web.mode.Author;
import com.web.mode.ERole;
import com.web.mode.Role;
import com.web.mode.User;
import com.web.repository.RoleRepository;
import com.web.repository.UserRepository;
import com.web.repository.AuthorRepository;
import com.web.request.LoginRequest;
import com.web.request.SignupRequest;
import com.web.response.JwtResponse;
import com.web.response.MessageResponse;
import com.web.security.UserDetailsImpl;
import com.web.utils.AjaxResponseBody;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  AuthorRepository authorRepository;
  
  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, 
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
	  AjaxResponseBody result = new AjaxResponseBody();
	  
	  
	if(signUpRequest.getName().trim().isEmpty() || signUpRequest.getName() == null)  {
		result.setCode("500");
		result.setMsg("Name field is required");
		result.setResult(null);
		return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
	}
	
	if(signUpRequest.getUsername().trim().isEmpty() || signUpRequest.getUsername() == null)  {
		result.setCode("500");
		result.setMsg("Username field is required");
		result.setResult(null);
		return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
	}
	
	if(signUpRequest.getEmail().trim().isEmpty() || signUpRequest.getEmail() == null)  {
		result.setCode("500");
		result.setMsg("Email field is required");
		result.setResult(null);
		return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
	}
	
	if(signUpRequest.getPassword().trim().isEmpty() || signUpRequest.getPassword() == null)  {
		result.setCode("500");
		result.setMsg("Password field is required");
		result.setResult(null);
		return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
	}
	
	if(signUpRequest.getPhoto().trim().isEmpty() || signUpRequest.getPhoto() == null)  {
		result.setCode("500");
		result.setMsg("Photo field is required");
		result.setResult(null);
		return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
	}
	
	if(signUpRequest.getAge().trim().isEmpty() || signUpRequest.getAge() == null)  {
		result.setCode("500");
		result.setMsg("Age field is required");
		result.setResult(null);
		return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
	}
	  
	
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
    	result.setCode("500");
		result.setMsg("Error: Username is already taken!");
		result.setResult(null);
		return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
    	result.setCode("500");
		result.setMsg("Error: Email is already in use!");
		result.setResult(null);
		return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(), 
               signUpRequest.getEmail(),
               encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "author":
          Role modRole = roleRepository.findByName(ERole.ROLE_AUTHOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);
    
    Author author = new Author();
    author.setName(signUpRequest.getName());
    author.setAge(signUpRequest.getAge());
    author.setStatus("Active");
    author.setPhoto(signUpRequest.getPhoto());
    author.setDate(new Date());
    author.setUser(user);
    authorRepository.save(author);
    
    List<Author> authorList = new ArrayList<Author>();
    authorList.add(author);
    
    result.setCode("200");
	result.setMsg("Auther registered successfully!");
	result.setResult(authorList);
	return new ResponseEntity<>(result,HttpStatus.OK);
  }
}
