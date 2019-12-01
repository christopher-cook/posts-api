package com.example.postsapi.service;

import com.example.postsapi.exception.EntityNotFoundException;
import com.example.postsapi.model.Comment;
import com.example.postsapi.model.Post;
import com.example.postsapi.model.User;
import com.example.postsapi.mq.Sender;
import com.example.postsapi.repository.CommentRepository;
import com.example.postsapi.repository.PostRepository;
import com.example.postsapi.repository.UserRepository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class PostServiceTest {

  @InjectMocks
  PostServiceImpl postService;

  @Mock
  CommentRepository commentRepository;

  @Mock
  PostRepository postRepository;

  @Mock
  UserRepository userRepository;

  @Mock
  Sender sender;

  private User testUser;
  private Post testPost;
  private Comment testComment;

  @Before
  public void initData() {
    testUser = new User("test");
    testPost = new Post();
    testPost.setTitle("test");
    testPost.setDescription("test");
    testPost.setUser(testUser);

    testComment = new Comment(1L, "test", 1L);
  }

  @Test
  public void listPosts_Posts_Success() {
    List<Post> listPosts = new ArrayList<>();
    listPosts.add(testPost);

    when(postRepository.findAll()).thenReturn(listPosts);
    when(userRepository.getUserById(anyLong())).thenReturn(testUser);

    Iterable<Post> posts = postService.listPosts();

    assertEquals(listPosts, posts);
  }

  @Test
  public void createPost_Post_Success() {
    when(postRepository.save(any())).thenReturn(testPost);
    Post post = postService.createPost("1", testPost);
    assertEquals(post, testPost);
  }

  @Test
  public void getCommentsByPostId_Comments_Success() {
    Comment testComment = new Comment();
    testComment.setText("test");
    testComment.setId(1L);
    testComment.setUser_id(1L);

    List<Comment> listComments = new ArrayList<>();
    listComments.add(testComment);

    when(commentRepository.getCommentsByPostId(anyLong())).thenReturn(listComments);

    List<Comment> fetchedComments = postService.getCommentsByPostId(1L);
    assertEquals(fetchedComments, listComments);
  }

  @Test
  public void deletePost_String_Success() throws EntityNotFoundException {
    doNothing().when(postRepository).deleteById(anyLong());
    doNothing().when(sender).send(anyString());
    String deleteSuccess = postService.deletePost(1L);
    assertEquals(deleteSuccess, "success");
  }

//  @Test(expected = EntityNotFoundException.class)
//  public void deletePost_Error_Fail() throws EntityNotFoundException {
//    doNothing().when(sender).send(anyString());
//    postService.deletePost(1L);
//  }

  @Test
  public void updatePost_Post_Success() throws EntityNotFoundException {
    when(postRepository.findById(anyLong())).thenReturn(Optional.of(testPost));
    when(postRepository.save(any())).thenReturn(testPost);
    Post updatedPost = postService.updatePost(1L, testPost);
    assertEquals(updatedPost, testPost);
  }

  @Test(expected = EntityNotFoundException.class)
  public void updatePost_Error_Fail() throws EntityNotFoundException {
    postService.updatePost(1L, testPost);
  }

}
