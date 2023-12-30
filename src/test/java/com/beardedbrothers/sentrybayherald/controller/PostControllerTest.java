package com.beardedbrothers.sentrybayherald.controller;

import com.beardedbrothers.sentrybayherald.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="admin", roles={"MODERATOR","ADMIN"})
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    public void whenCreatePost_thenReturnsCreated() throws Exception {
        // Setup your mock behavior here
        // For example, you might return a new post when createPost is called
        // ...

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Post\",\"content\":\"Content of the new post\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenShowAllPosts_thenReturnsOk() throws Exception {
        // Mock the behavior of postService.showAllPosts() if needed
        // ...

        mockMvc.perform(get("/api/posts/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetSinglePost_thenReturnsOk() throws Exception {
        // Setup your mock behavior here
        // For example, mock the postService.readSinglePost to return a specific post
        // ...

        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk());
    }

    // You can add more tests for different scenarios here
}
