package com.example.postsapi.service;

import com.example.postsapi.exception.EntityNotFoundException;
import com.example.postsapi.model.Comment;
import com.example.postsapi.model.Post;
import com.example.postsapi.model.User;
import com.example.postsapi.mq.Sender;
import com.example.postsapi.repository.CommentRepository;
import com.example.postsapi.repository.PostRepository;
import com.example.postsapi.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    Sender sender;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Iterable<Post> listPosts() {
        Iterable foundPosts = postRepository.findAll();

//        TODO: iterate over posts and add userbean to each post;
        for (Object post : foundPosts) {
            Post casted = (Post) post;
            Long user_id = casted.getUser_id();

            User user = userRepository.getUserById(user_id);
            casted.setUser(user);
        }

        return foundPosts;
    }

    @Override
    public Post createPost(String userId, Post post) {
        Long user_id = Long.parseLong(userId, 10);
        System.out.println(user_id);

        post.setUser_id(user_id);
        return postRepository.save(post);
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        List <Comment> comments = commentRepository.getCommentsByPostId(postId);

        for (Object comment : comments) {
           Comment castedComment = (Comment) comment;
           User user = userRepository.getUserById(castedComment.getUser_id());
           castedComment.setUser(user);
        }

        return comments;
    }

    @Override
    public String deletePost(Long postId) throws EntityNotFoundException {
        try {
            postRepository.deleteById(postId);
            sender.send(Long.toString(postId));
        } catch (Exception e){
            throw new EntityNotFoundException("Post does not exist");
        }
        return "success";
    }

    @Override
    public Post updatePost(Long postId, Post post) throws EntityNotFoundException{
        Post existingPost = null;
        try {
            existingPost = postRepository.findById(postId).get();
        } catch (Exception e){
            throw new EntityNotFoundException("Post does not exist!");
        }
        existingPost.setTitle(post.getTitle());
        existingPost.setDescription(post.getDescription());
        return postRepository.save(existingPost);
    }
}
