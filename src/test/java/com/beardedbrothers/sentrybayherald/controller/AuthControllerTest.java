package com.beardedbrothers.sentrybayherald.controller;

import com.beardedbrothers.sentrybayherald.repository.RoleRepository;
import com.beardedbrothers.sentrybayherald.repository.UserRepository;
import com.beardedbrothers.sentrybayherald.security.jwt.JwtUtils;
import com.beardedbrothers.sentrybayherald.service.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PasswordEncoder encoder;

    @MockBean
    private JwtUtils jwtUtils;

    @Test
    @WithMockUser
    public void whenAuthenticateUser_thenReturnsJwt() throws Exception {
        // Create a dummy UserDetails object to return from authenticate call
        UserDetailsImpl dummyUserDetails = new UserDetailsImpl(
                1L, "user", "user@example.com", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(dummyUserDetails);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("dummy-jwt-token");

        mockMvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user\",\"password\":\"password\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void whenRegisterUserWithExistingUsername_thenReturnsBadRequest() throws Exception {
        // Mocking the user repository to simulate user existence check
        when(userRepository.existsByUsername("existingUser")).thenReturn(true);

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"existingUser\",\"password\":\"password\",\"email\":\"email@example.com\"}"))
                .andExpect(status().isBadRequest());
    }

    // Additional tests can be written to cover more scenarios like successful registration, different roles, etc.
}
