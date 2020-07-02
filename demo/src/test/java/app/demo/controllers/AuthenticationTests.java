package app.demo.controllers;

import app.demo.ApplicationContextInitializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peper.blog.DemoApplication;
import com.peper.blog.authentication.AuthenticationRequest;
import com.peper.blog.authentication.AuthenticationResponse;
import com.peper.blog.authentication.UserExtended;
import com.peper.blog.controllers.Authentication;
import com.peper.blog.controllers.Blog;
import com.peper.blog.repositories.EntryRepository;
import com.peper.blog.repositories.PersonRepository;
import com.peper.blog.services.BlogService;
import com.peper.blog.services.UserAuth;
import com.peper.blog.services.UserService;
import com.peper.blog.utilities.JwtUtils;
import com.peper.blog.utilities.SecurityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(Authentication.class)
@ContextConfiguration(initializers={ApplicationContextInitializer.class}, classes={DemoApplication.class, JwtUtils.class})
public class AuthenticationTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserAuth userAuth;

    @Autowired
    private JwtUtils jwtUtils;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private SecurityUtils securityUtils;

    @Autowired
    private ObjectMapper objectMapper;


    /*
    @Test
    public void login_ReturnValidJwtToken_RequestWithValidCredentials() throws Exception{
        Integer id = 5;
        String email = "test@mail.com";
        String password = "password";

        UserExtended userExtended = new UserExtended(id, email, password);

        Mockito.when(userAuth.loadUserByUsername(email))
                .thenReturn(userExtended);

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, password);
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

        String token = jwtUtils.generateToken(userExtended);
        AuthenticationResponse expectedResponse = new AuthenticationResponse(token);

        assertEquals(expectedResponse.getJwt(), actualResponse.getJwt());
    }
     */

    @Test
    public void login_Return401Unauthorized_RequestWithNonExistentUser() throws Exception{

        Integer id = 5;
        String email = "test@mail.com";
        String password = "password";
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, password);
        String loginBody = objectMapper.writeValueAsString(authenticationRequest);

        MvcResult result =  mvc.perform( MockMvcRequestBuilders
                .post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginBody.getBytes())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn();
    }


    @Test
    public void register_Return200OK_RequestWithValidCredentials() throws Exception{
        Integer id = 5;
        String email = "test@mail.com";
        String password = "password";

        UserExtended userExtended = new UserExtended(id, email, password);

        Mockito.when(userAuth.loadUserByUsername(email))
                .thenReturn(userExtended);

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, password);
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

        String token = jwtUtils.generateToken(userExtended);
        AuthenticationResponse expectedResponse = new AuthenticationResponse(token);


        assertEquals(expectedResponse.getJwt(), actualResponse.getJwt());
    }

}
