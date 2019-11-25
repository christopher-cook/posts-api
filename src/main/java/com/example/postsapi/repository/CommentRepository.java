package com.example.postsapi.repository;

import com.example.postsapi.model.Comment;
import com.example.postsapi.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<Comment> getCommentsByPostId(Long post_id){
    String sql = "SELECT * FROM comments WHERE post_id = ?";
    List<Comment> comments = jdbcTemplate.query(sql, new Object[]{post_id}, (rs, rowNum) ->
        new Comment(
            rs.getLong("id"),
            rs.getString("text"),
            rs.getLong("user_id")
        ));

    return comments;
  }



//  public List<Long> getSongsByUser(long userId){
//    List<Long> songIds = new ArrayList<>();
//    jdbcTemplate.query("select song_id from user_song where " +
//            "user_id = ?", new Object[]{userId},
//        (rs, num) -> songIds.add(rs.getLong("song_id")));
//    return songIds;
//  }
}
