package app.demo.controllers;

import app.demo.ApplicationContextInitializer;
import com.peper.blog.DemoApplication;
import com.peper.blog.controllers.Blog;
import com.peper.blog.entities.EntryEntity;
import com.peper.blog.filters.JwtFilter;
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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(Blog.class)
@ContextConfiguration(initializers={ApplicationContextInitializer.class},
        classes={
                DemoApplication.class,
                JwtUtils.class,
                UserAuth.class,
                BlogService.class,
                SecurityUtils.class,
                EntryRepository.class,
                })
public class BlogControllerSecurityTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BlogService blogService;

  /*
    @MockBean
    private EntryRepository entryRepository;

    @MockBean
    private UserAuth userAuth;

    @MockBean
   private JwtUtils jwtUtils;

     @MockBean
    private SecurityUtils securityUtils;
   */

    @MockBean
    private PersonRepository personRepository;

    private static final String INVALID_JWT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @Test
    public void getAllBlogEntries_Return401Unauthorized_RequestWithNoJwtToken() throws Exception {
       // Mockito.when(blogService.getBlogEntries(0))
       //         .thenReturn(Arrays.asList(new EntryEntity()));

        mvc.perform( MockMvcRequestBuilders
                .get("/entries")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void saveBlogEntry_Return401Unauthorized_RequestWithNoJwtToken() throws Exception {
     //   Mockito.when(blogService.getBlogEntries(0))
     //           .thenReturn(Arrays.asList(new EntryEntity()));

        mvc.perform( MockMvcRequestBuilders
                .post("/entries")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void updateBlogEntry_Return401Unauthorized_RequestWithNoJwtToken() throws Exception {
      //  Mockito.when(blogService.getBlogEntries(0))
      //          .thenReturn(Arrays.asList(new EntryEntity()));

        mvc.perform( MockMvcRequestBuilders
                .put("/entries/2")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }


    @Test
    public void deleteBlogEntry_Return401Unauthorized_RequestWithNoJwtToken() throws Exception {
      //  Mockito.when(blogService.getBlogEntries(0))
       //         .thenReturn(Arrays.asList(new EntryEntity()));

        mvc.perform( MockMvcRequestBuilders
                .delete("/entries/2")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void getAllBlogEntries_Return401Unauthorized_RequestWithBadJwtToken() throws Exception {
      //  Mockito.when(blogService.getBlogEntries(0))
     //          .thenReturn(Arrays.asList(new EntryEntity()));

        mvc.perform( MockMvcRequestBuilders
                .get("/entries")
                .header("Authorization", "Bearer " + INVALID_JWT_TOKEN)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void saveBlogEntry_Return401Unauthorized_RequestWithBadJwtToken() throws Exception {
      //  Mockito.when(blogService.getBlogEntries(0))
      //          .thenReturn(Arrays.asList(new EntryEntity()));

        mvc.perform( MockMvcRequestBuilders
                .post("/entries")
                .header("Authorization", "Bearer " + INVALID_JWT_TOKEN)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void updateBlogEntry_Return401Unauthorized_RequestWithBadJwtToken() throws Exception {
      //  Mockito.when(blogService.getBlogEntries(0))
      //          .thenReturn(Arrays.asList(new EntryEntity()));

        mvc.perform( MockMvcRequestBuilders
                .put("/entries/2")
                .header("Authorization", "Bearer " + INVALID_JWT_TOKEN)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }


    @Test
    public void deleteBlogEntry_Return401Unauthorized_RequestWithBadJwtToken() throws Exception {
      //  Mockito.when(blogService.getBlogEntries(0))
      //          .thenReturn(Arrays.asList(new EntryEntity()));

        mvc.perform( MockMvcRequestBuilders
                .delete("/entries/2")
                .header("Authorization", "Bearer " + INVALID_JWT_TOKEN)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

}
