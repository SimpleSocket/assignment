package app.demo.controllers;

import app.demo.ApplicationContext;
import app.demo.utils.ObjectGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peper.blog.DemoApplication;
import com.peper.blog.authentication.AuthenticationRequest;
import com.peper.blog.authentication.AuthenticationResponse;
import com.peper.blog.authentication.UserExtended;
import com.peper.blog.controllers.Authentication;
import com.peper.blog.repositories.PersonRepository;
import com.peper.blog.services.UserAuth;
import com.peper.blog.services.UserService;
import com.peper.blog.utilities.JwtUtils;
import com.peper.blog.utilities.SecurityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(Authentication.class)
@ContextConfiguration(initializers={ApplicationContext.class}, classes={DemoApplication.class, JwtUtils.class, ObjectGenerator.class})
public class AuthenticationTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserAuth userAuth;

    @Autowired
    private JwtUtils jwtUtils;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ObjectGenerator objectGenerator;



    @Test
    public void login_Return401Unauthorized_RequestWithNonExistentUser() throws Exception{
        AuthenticationRequest authenticationRequest = objectGenerator.createDefaultAuthenticationRequest();

        String loginBody = objectMapper.writeValueAsString(authenticationRequest);

        mvc.perform( MockMvcRequestBuilders
                .post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginBody.getBytes())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }


    @Test
    public void login_Return200Ok_RequestWithValidCredentials() throws Exception{
        UserExtended userExtended = objectGenerator.createDefaultUserExtended();

        Mockito.when(userAuth.loadUserByUsername(userExtended.getUsername()))
                .thenReturn(userExtended);

        AuthenticationRequest authenticationRequest = objectGenerator.createDefaultAuthenticationRequest();
        String loginBody = objectMapper.writeValueAsString(authenticationRequest);

        MvcResult result =  mvc.perform( MockMvcRequestBuilders
                .post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginBody.getBytes())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        AuthenticationResponse actualResponse = objectMapper.readValue(content, AuthenticationResponse.class);

        assertFalse(jwtUtils.isTokenExpired(actualResponse.getJwt()));
    }

}
