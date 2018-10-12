package pl.coderslab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDao
{
    private static final String CREATE_USER_GROUP_QUERY = "INSERT INTO users (username, email, password, user_group_id) VALUES (?, ?, ?, ?);";
    private static final String DELETE_USER_GROUP_QUERY = "DELETE FROM users WHERE id = ?;";
    private static final String SELECT_ALL_USER_GROUP_QUERY = "SELECT * FROM users;";
    private static final String SELECT_USER_GROUP_QUERY = "SELECT * FROM users WHERE id = ?;";
    private static final String UPDATE_USER_GROUP_QUERY = "UPDATE users SET username = ?,email = ?, password = ?, user_group_id = ? WHERE id = ?;";

    public Users select(int id)
    {
        Users users = new Users();
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(SELECT_USER_GROUP_QUERY);)
        {
            stmt.setInt(1, id); // rs w try() bo ustawiam to stmt.set...
            try(ResultSet rs = stmt.executeQuery();)
            {
                while(rs.next())
                {
                    users.setId(rs.getInt("id"));
                    users.setUsername(rs.getString("username"));
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return users;
    }

    public static List<Users> selectAll()
    {
        List<Users> usersList = new ArrayList<>();
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_USER_GROUP_QUERY); ResultSet rs = stmt.executeQuery();)
        {
            while(rs.next())
            {
                Users users = new Users();
                users.setId(rs.getInt("id"));
                users.setUsername(rs.getString("username"));
                users.setEmail(rs.getString("email"));
                users.setPassword(rs.getString("password"));
                users.setUserGroupId(rs.getInt("user_group_id"));
                usersList.add(users);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return usersList;
    }

    public static Users create(Users users)
    {
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(CREATE_USER_GROUP_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);)
        {
            //przygotowuje sety
            stmt.setString(1, users.getUsername());
            stmt.setString(2, users.getEmail());
            stmt.setString(3, users.getPassword());
            if(!(users.getUserGroupId() == null))
            {
                stmt.setNull(4, 4);
            }
            else
            {
                stmt.setObject(4,null);
            }

            int result = stmt.executeUpdate();

            if(result != 1) // czyli jak jest błąd
            {
                throw new RuntimeException("Update zwrócił kod: " + result);
            }

            try(ResultSet generatedKeys = stmt.getGeneratedKeys();)
            {
                if(generatedKeys.first())
                {
                    users.setId(generatedKeys.getInt(1));
                    return users;
                }
                else
                {
                    throw new RuntimeException("Wygenerowany klucz nie znaleziony!");
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    // usuwanie po id
    public void delete(int id) // potem przeciążyć na (Users users)
    {
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(DELETE_USER_GROUP_QUERY);)
        {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void update(Users users)
    {
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(UPDATE_USER_GROUP_QUERY);)
        {
            stmt.setInt(2, users.getId());
            stmt.setString(1, users.getUsername());
            stmt.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
