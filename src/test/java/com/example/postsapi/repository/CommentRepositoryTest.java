package com.example.postsapi.repository;

import com.example.postsapi.model.Comment;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class CommentRepositoryTest {

  @InjectMocks
  CommentRepository commentRepository;

  @Mock
  private JdbcTemplate jdbc;

  @Mock
  private ResultSet rs;

  @Captor
  private ArgumentCaptor<RowMapper<Comment>> rowMapperCaptor;

  private Comment comment;

  @Before
  public void test()throws SQLException {
    comment = new Comment(1L, "batman", 1l);
    when(rs.getLong(any())).thenReturn(comment.getUser_id(), comment.getId());
    when(rs.getString(any())).thenReturn(comment.getText());
  }

  @Test
  public void getCommentsByPostId_ListComments_Success() throws SQLException {
    commentRepository.getCommentsByPostId(1L);
    verify(jdbc).query(anyString(), (Object[]) any(), rowMapperCaptor.capture());
    RowMapper<Comment> rm = rowMapperCaptor.getValue();
    Comment testComment = rm.mapRow(rs, 1);
  }

}
