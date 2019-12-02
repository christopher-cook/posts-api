package com.example.postsapi.service;

import com.example.postsapi.exception.EntityNotFoundException;
import com.example.postsapi.model.Comment;
import com.example.postsapi.model.Post;
import java.util.List;

public interface PostService {
    public Iterable<Post> listPosts();

    public Post createPost(String userId, Post post);

    public List<Comment> getCommentsByPostId(Long postId);

    /**
     *
     * @param postId String
     * @return "success"
     * @throws EntityNotFoundException
     */
    public String deletePost(Long postId) throws EntityNotFoundException;

    public Post updatePost(Long postId, Post post) throws EntityNotFoundException;
}

