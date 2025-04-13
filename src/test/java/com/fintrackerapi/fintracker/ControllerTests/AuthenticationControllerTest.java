package com.fintrackerapi.fintracker.ControllerTests;


import com.fintrackerapi.fintracker.controllers.AuthenticationController;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

/**
 * TEST CASES
 * - Test register method returns 200 status user response
 * - Test login method return 200 login response
 */

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

}
