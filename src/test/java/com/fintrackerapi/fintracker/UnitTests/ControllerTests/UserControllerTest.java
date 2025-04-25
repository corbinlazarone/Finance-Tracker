package com.fintrackerapi.fintracker.UnitTests.ControllerTests;


import com.fintrackerapi.fintracker.configs.JwtAuthenticationFilter;
import com.fintrackerapi.fintracker.controllers.UserController;
import com.fintrackerapi.fintracker.services.JwtService;
import com.fintrackerapi.fintracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * TEST CASES
 *  - Test successful authenticated user
 *  - Test successful all users
 */

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

//    @Test
//    @WithMockUser(username = "test@example.com")
//    public void authenticatedUserSuccess() throws Exception {
//        UUID mockUserId = UUID.randomUUID();
//        Date mockCreatedAt = new Date();
//
//        // Setup Mock user
//        UserResponse mockUserResponse = new UserResponse(
//                mockUserId,
//                "Test test",
//                "test@example.com",
//                mockCreatedAt
//        );
//
//        // Mock UserService findByEmail to return mock user
//        when(userService.findByEmail(mockUserResponse.getEmail())).thenReturn(mockUserResponse);
//
//
//        // Perform GET request to /users/me
//        mockMvc.perform(MockMvcRequestBuilders.get("/users/me")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(mockUserId.toString()))
//                .andExpect(jsonPath("$.fullName").value(mockUserResponse.getFullName()))
//                .andExpect(jsonPath("$.email").value(mockUserResponse.getEmail()))
//                .andExpect(jsonPath("$.createdAt").exists());
//
//    }
}
