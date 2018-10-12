package pl.coderslab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserGroupDao
{
    private static final String CREATE_USER_GROUP_QUERY = "INSERT INTO user_group (name) VALUES (?);";
    private static final String DELETE_USER_GROUP_QUERY = "DELETE FROM user_group WHERE id = ?;";
    private static final String SELECT_ALL_USER_GROUP_QUERY = "SELECT * FROM user_group;";
    private static final String SELECT_USER_GROUP_QUERY = "SELECT * FROM user_group WHERE id = ?;";
    private static final String UPDATE_USER_GROUP_QUERY = "UPDATE user_group SET name = ? WHERE id = ?;";

    public UserGroup select(int id)
    {
        UserGroup userGroup = new UserGroup();
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(SELECT_USER_GROUP_QUERY);)
        {
            stmt.setInt(1, id); // rs w try() bo ustawiam to stmt.set...
            try(ResultSet rs = stmt.executeQuery();)
            {
                while(rs.next())
                {
                    userGroup.setId(rs.getInt("id"));
                    userGroup.setName(rs.getString("name"));
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return userGroup;
    }

    public static List<UserGroup> selectAll()
    {
        List<UserGroup> userGroupList = new ArrayList<>();
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_USER_GROUP_QUERY); ResultSet rs = stmt.executeQuery();)
        {
            while(rs.next())
            {
                UserGroup userGroup = new UserGroup();
                userGroup.setId(rs.getInt("id"));
                userGroup.setName(rs.getString("name"));
                userGroupList.add(userGroup);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return userGroupList;
    }

    public static UserGroup create(UserGroup userGroup)
    {
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(CREATE_USER_GROUP_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);)
        {
            //przygotowuje sety
            stmt.setString(1, userGroup.getName());
            int result = stmt.executeUpdate();

            if(result != 1) // czyli jak jest błąd
            {
                throw new RuntimeException("Update zwrócił kod: " + result);
            }

            try(ResultSet generatedKeys = stmt.getGeneratedKeys();)
            {
                if(generatedKeys.first())
                {
                    userGroup.setId(generatedKeys.getInt(1));
                    return userGroup;
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
    public void delete(int id) // potem przeciążyć na (UserGroup userGroup)
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

    public void update(UserGroup userGroup)
    {
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(UPDATE_USER_GROUP_QUERY);)
        {
            stmt.setInt(2, userGroup.getId());
            stmt.setString(1, userGroup.getName());
            stmt.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
