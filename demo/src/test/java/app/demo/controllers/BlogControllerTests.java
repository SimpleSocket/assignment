package app.demo.controllers;

import app.demo.ApplicationContextInitializer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peper.blog.DemoApplication;
import com.peper.blog.authentication.UserExtended;
import com.peper.blog.controllers.Blog;
import com.peper.blog.entities.EntryEntity;
import com.peper.blog.repositories.EntryRepository;
import com.peper.blog.repositories.PersonRepository;
import com.peper.blog.services.BlogService;
import com.peper.blog.services.UserAuth;
import com.peper.blog.utilities.JwtUtils;
import com.peper.blog.utilities.SecurityUtils;
import org.junit.After;
import org.junit.Before;
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

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(Blog.class)
@ContextConfiguration(initializers={ApplicationContextInitializer.class}, classes={DemoApplication.class, BlogService.class})
public class BlogControllerTests {

    @Autowired
    private MockMvc mvc;

  //  @MockBean
  //  private BlogService blogService;

    @MockBean
    private EntryRepository entryRepository;

    @MockBean
    private UserAuth userAuth;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private SecurityUtils securityUtils;


    @Autowired
    private ObjectMapper objectMapper;

    private static final String JWT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    private static final String JWT_TOKEN_BEARER = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    private static final String JWT_TOKEN_SUBJECT = "1234567890";

    private EntryEntity createEntryEntity(Integer id, Integer userId, String tittle, String text){
        EntryEntity entryEntity = new EntryEntity();
        entryEntity.setIdEntry(id);
        entryEntity.setFkPersonidPerson(userId);
        entryEntity.setText(tittle);
        entryEntity.setTittle(text);
        return entryEntity;
    }

    private UserExtended createUserExtended(Integer id, String username, String password){
        UserExtended userExtended = new UserExtended(id, username, password);
        return userExtended;
    }

    private EntryEntity createDefaultEntryEntity(){
        EntryEntity entryEntity = createEntryEntity(0,0, "Tittle", "Text");
        return entryEntity;
    }

    private UserExtended createDefaultUserExtended(){
        UserExtended userExtended = createUserExtended(0, "a", "b");
        return userExtended;
    }


    @Test
    public void getAllBlogEntries_ReturnListWithReturn200OK_RequestWithValidCredentials() throws Exception{

        UserExtended user = createDefaultUserExtended();


        Mockito.when(jwtUtils.validateToken(JWT_TOKEN, user)).thenReturn(true);
        Mockito.when(jwtUtils.extractUsername(JWT_TOKEN)).thenReturn(JWT_TOKEN_SUBJECT);
        Mockito.when(userAuth.loadUserByUsername(JWT_TOKEN_SUBJECT)).thenReturn(user);

        List<EntryEntity> expectedEntityList = Arrays.asList(
                createEntryEntity(0,null, "a", "b"),
                createEntryEntity(1,null, "a", "b")
        );

        Mockito.when(entryRepository.findAllByFkPersonidPerson(user.getId())).thenReturn(expectedEntityList);

        MvcResult result = mvc.perform( MockMvcRequestBuilders
                .get("/entries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JWT_TOKEN_BEARER)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", JWT_TOKEN_BEARER)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String entriesList = result.getResponse().getContentAsString();

        List<EntryEntity> actualEntryEntityList = objectMapper.readValue(entriesList, new TypeReference<List<EntryEntity>>(){});

        Collections.sort(actualEntryEntityList, Comparator.comparing(EntryEntity::getTittle));
        Collections.sort(expectedEntityList, Comparator.comparing(EntryEntity::getTittle));

        assertEquals(expectedEntityList.get(0), actualEntryEntityList.get(0));
        assertEquals(expectedEntityList.get(1), actualEntryEntityList.get(1));
    }


    @Test
    public void saveBlogEntry_Return201Created_RequestWithValidCredentials() throws Exception {


        UserExtended user = createDefaultUserExtended();
        Mockito.when(jwtUtils.validateToken(JWT_TOKEN, user)).thenReturn(true);
        Mockito.when(jwtUtils.extractUsername(JWT_TOKEN)).thenReturn(JWT_TOKEN_SUBJECT);
        Mockito.when(userAuth.loadUserByUsername(JWT_TOKEN_SUBJECT)).thenReturn(user);


        EntryEntity entryEntity = createDefaultEntryEntity();
        String content = objectMapper.writeValueAsString(entryEntity);
        mvc.perform( MockMvcRequestBuilders
                .post("/entries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", JWT_TOKEN_BEARER)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    public void updateBlogEntry_Return404NotFound_UpdateNonExistentEntry() throws Exception {


        UserExtended user = createDefaultUserExtended();
        Mockito.when(jwtUtils.validateToken(JWT_TOKEN, user)).thenReturn(true);
        Mockito.when(jwtUtils.extractUsername(JWT_TOKEN)).thenReturn(JWT_TOKEN_SUBJECT);
        Mockito.when(userAuth.loadUserByUsername(JWT_TOKEN_SUBJECT)).thenReturn(user);


        EntryEntity entryEntity = createDefaultEntryEntity();
        String content = objectMapper.writeValueAsString(entryEntity);
        mvc.perform( MockMvcRequestBuilders
                .put("/entries/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", JWT_TOKEN_BEARER)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateBlogEntry_Return200Ok_UpdateExistingtEntry() throws Exception {

        UserExtended user = createDefaultUserExtended();
        Mockito.when(jwtUtils.validateToken(JWT_TOKEN, user)).thenReturn(true);
        Mockito.when(jwtUtils.extractUsername(JWT_TOKEN)).thenReturn(JWT_TOKEN_SUBJECT);
        Mockito.when(userAuth.loadUserByUsername(JWT_TOKEN_SUBJECT)).thenReturn(user);

        EntryEntity entryEntity = createDefaultEntryEntity();
        Mockito.when(entryRepository.existsById(entryEntity.getIdEntry())).thenReturn(true);


        String content = objectMapper.writeValueAsString(entryEntity);
        mvc.perform( MockMvcRequestBuilders
                .put("/entries/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", JWT_TOKEN_BEARER)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    public void deleteBlogEntry_Return404NotFound_DeleteNonExistentEntry() throws Exception {


        UserExtended user = createDefaultUserExtended();
        Mockito.when(jwtUtils.validateToken(JWT_TOKEN, user)).thenReturn(true);
        Mockito.when(jwtUtils.extractUsername(JWT_TOKEN)).thenReturn(JWT_TOKEN_SUBJECT);
        Mockito.when(userAuth.loadUserByUsername(JWT_TOKEN_SUBJECT)).thenReturn(user);

        EntryEntity entryEntity = createDefaultEntryEntity();
        String content = objectMapper.writeValueAsString(entryEntity);
        mvc.perform( MockMvcRequestBuilders
                .delete("/entries/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", JWT_TOKEN_BEARER)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

}
