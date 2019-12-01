package com.example.postsapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.postsapi.model.Comment;
import com.example.postsapi.model.Post;
import com.example.postsapi.service.PostService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PostService postService;

  @Test
  public void listAll_PostList_Success() throws Exception {
    Post testPost = new Post();
    testPost.setTitle("test");
    List<Post> testPostList = new ArrayList<>();
    testPostList.add(testPost);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/list");

    when(postService.listPosts()).thenReturn(testPostList);

    MvcResult result = mockMvc.perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().json("[{\"title\": \"test\" }]"))
        .andReturn();

    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void createPost_Post_Success() throws Exception {
    Post testPost = new Post();
    testPost.setTitle("test");
    testPost.setDescription("test");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createPostInJson("test","test"))
        .header("userId", "1");

    when(postService.createPost(anyString(), any())).thenReturn(testPost);

    MvcResult result = mockMvc.perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().json("{\"title\":\"test\",\"description\":\"test\"}"))
        .andReturn();

    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void getCommentsByPostId_ListComment_Success() throws Exception {
    Comment testComment = new Comment();
    testComment.setText("test");
    List<Comment> commentList = new ArrayList<>();
    commentList.add(testComment);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/1/comment");

    when(postService.getCommentsByPostId(anyLong())).thenReturn(commentList);
    MvcResult result = mockMvc.perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().json("[{\"text\":\"test\"}]"))
        .andReturn();

    System.out.println(result.getResponse().getContentAsString());
}

  @Test
  public void deletePost_String_Success() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .delete("/1");

    when(postService.deletePost(anyLong())).thenReturn("success");

    MvcResult result = mockMvc.perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().string("success"))
        .andReturn();

    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void updatePost_Post_Success() throws Exception {
    Post testPost = new Post();
    testPost.setTitle("test");
    testPost.setDescription("test");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .put("/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createPostInJson("test","test"))
        .header("userId", "1");

    when(postService.updatePost(anyLong(), any())).thenReturn(testPost);

    MvcResult result = mockMvc.perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().json("{\"title\":\"test\",\"description\":\"test\"}"))
        .andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  private static String createPostInJson(String title, String description) {
    return "{ \"title\": \"" + title + "\", " +
        "\"description\":\"" + description + "\"}";
  }
}
