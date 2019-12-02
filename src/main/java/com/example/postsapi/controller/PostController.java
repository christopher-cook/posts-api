package com.example.postsapi.controller;

import com.example.postsapi.exception.EntityNotFoundException;
import com.example.postsapi.model.Comment;
import com.example.postsapi.model.Post;
import com.example.postsapi.service.PostService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/list")
    public Iterable<Post> listAll(){
        return postService.listPosts();
    }

    /**
     *
     * @param userId String
     * @param post Object
     * @return new Post
     */
    @PostMapping("/")
    public Post createPost(@RequestHeader("userId") String userId, @RequestBody Post post) {
        return postService.createPost(userId, post);
    }

    @GetMapping("/{postId}/comment")
    public List<Comment> getCommentsByPostId(@PathVariable Long postId) {
        return postService.getCommentsByPostId(postId);
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable Long postId) throws EntityNotFoundException {
        return postService.deletePost(postId);
    }

    @PutMapping("/{postId}")
    public Post updatePost(@PathVariable Long postId, @RequestBody Post post) throws EntityNotFoundException {
        return postService.updatePost(postId, post);
    }
}
