package com.example.postsapi.repository;

import com.example.postsapi.model.Comment;
import com.example.postsapi.model.User;
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
public class UserRepositoryTest {

  @InjectMocks
  UserRepository userRepository;

  @Mock
  private JdbcTemplate jdbc;

  @Mock
  private ResultSet rs;

  @Captor
  private ArgumentCaptor<RowMapper<User>> rowMapperCaptor;

  private User testUser;

  @Before
  public void test()throws SQLException {
    testUser = new User("test");
    testUser.setUsername("test");
    when(rs.getString(any())).thenReturn(testUser.getUsername());
  }

  @Test
  public void getUserById_User_Success() throws SQLException {
    userRepository.getUserById(1L);
    verify(jdbc).queryForObject(anyString(), any(), rowMapperCaptor.capture());
    RowMapper<User> rm = rowMapperCaptor.getValue();
    User testUser = rm.mapRow(rs, 1);
  }

}

