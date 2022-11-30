package com.twitter.twitter_app.controllers;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.validation.Valid;

import com.twitter.twitter_app.email.context.AccountVerificationEmailContext;
import com.twitter.twitter_app.email.context.ForgotPasswordEmailContext;
import com.twitter.twitter_app.email.services.EmailService;
import com.twitter.twitter_app.exceptions.InvalidTokenException;
import com.twitter.twitter_app.models.ERole;
import com.twitter.twitter_app.models.Role;
import com.twitter.twitter_app.models.User;
import com.twitter.twitter_app.payload.request.*;
import com.twitter.twitter_app.payload.response.JwtResponse;
import com.twitter.twitter_app.payload.response.MessageResponse;
import com.twitter.twitter_app.repository.RoleRepository;
import com.twitter.twitter_app.repository.SecureTokenRepository;
import com.twitter.twitter_app.security.jwt.JwtUtils;
import com.twitter.twitter_app.models.SecureToken;
import com.twitter.twitter_app.security.services.AuthHelperService;
import com.twitter.twitter_app.security.services.SecureTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.twitter.twitter_app.repository.UserRepository;
import com.twitter.twitter_app.security.services.UserDetailsImpl;

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
	JwtUtils jwtUtils;

	@Autowired
	EmailService emailService;

	@Resource
	private AuthHelperService authHelperService;

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

		Optional<User> optUser = userRepository.findById(userDetails.getId());
		if (!optUser.get().isAccountVerified()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		User user = new User(signUpRequest.getUsername(),
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		user.setName(signUpRequest.getName());
		user.setLastName(signUpRequest.getLastName());

		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(ERole.ROLE_REGULAR_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);

		user.setRoles(roles);
		userRepository.save(user);

		authHelperService.sendRegistrationConfirmationEmail(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PostMapping("/signup-business")
	public ResponseEntity<?> registerBusinessUser(@Valid @RequestBody SignupBusinessRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		User user = new User(signUpRequest.getUsername(),
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		user.setName(signUpRequest.getName());
		user.setWebsite(signUpRequest.getUsername());

		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(ERole.ROLE_BUSINESS_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);

		user.setRoles(roles);
		userRepository.save(user);

		authHelperService.sendRegistrationConfirmationEmail(user);

		return ResponseEntity.ok(new MessageResponse("Business user registered successfully!"));
	}

	@PostMapping("/forgotten-password")
	public ResponseEntity<?> registerBusinessUser(@Valid @RequestBody ForgottenPasswordRequest request) {
		Optional<User> user= userRepository.findByEmail(request.getEmail());
		user.ifPresent(authHelperService::sendResetPasswordEmail);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/change-password")
	public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request) {

		try {
			authHelperService.changePassword(request.getToken(), request.getPassword());
		} catch (InvalidTokenException e) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok().build();
	}
}
