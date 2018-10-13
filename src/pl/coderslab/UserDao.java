package pl.coderslab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao
{
    private static final String CREATE_USER_GROUP_QUERY = "INSERT INTO users (username, email, password, user_group_id) VALUES (?, ?, ?, ?);";
    private static final String DELETE_USER_GROUP_QUERY = "DELETE FROM users WHERE id = ?;";
    private static final String SELECT_ALL_USER_GROUP_QUERY = "SELECT * FROM users;";
    private static final String SELECT_USER_GROUP_QUERY = "SELECT * FROM users WHERE id = ?;";
    private static final String UPDATE_USER_GROUP_QUERY = "UPDATE users SET username = ?,email = ?, password = ?, user_group_id = ? WHERE id = ?;";

    /**
     * @param id ID of checked user
     * @return <code>true</code> if user with given ID exists otherwise return <code>false</code>
     */
    public static boolean exist(int id)
    {
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(SELECT_USER_GROUP_QUERY);)
        {
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery();)
            {
                if(rs.next()) // false jesli nie ma juz nic dalej
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param user <code>User</code> object of checked user
     * @return <code>true</code> if user with given ID exists otherwise return <code>false</code>
     */
    public static boolean exist(User user)
    {
        return exist(user.getId());
    }

    public static User select(int id)
    {
        User user = new User();
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(SELECT_USER_GROUP_QUERY);)
        {
            stmt.setInt(1, id); // rs w try() bo ustawiam to stmt.set...
            try(ResultSet rs = stmt.executeQuery();)
            {
                while(rs.next())
                {
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setUserGroupId(rs.getInt("user_group_id"));
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return user;
    }

    public static List<User> selectAll()
    {
        List<User> userList = new ArrayList<>();
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_USER_GROUP_QUERY); ResultSet rs = stmt.executeQuery();)
        {
            while(rs.next())
            {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setUserGroupId(rs.getInt("user_group_id"));
                userList.add(user);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return userList;
    }

    public static User create(User user)
    {
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(CREATE_USER_GROUP_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);)
        {
            //przygotowuje sety
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            if(user.getUserGroupId() == null)
            {
                stmt.setNull(4, 4); //sqlType 4 == INTEGER (dla mysql) i to ustawi null
            }
            else
            {
                stmt.setInt(4, user.getUserGroupId());
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
                    user.setId(generatedKeys.getInt(1));
                    System.out.println("Zapisano pomyślnie.");
                    return user;
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
    public static void delete(int id)
    {
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(DELETE_USER_GROUP_QUERY);)
        {
            stmt.setInt(1, id);
            int result = stmt.executeUpdate();
            if(result != 1)
            {
                System.out.println("Nie ma nic do usunięcia.");
            }
            else
            {
                System.out.println("Usunięto pomyślnie.");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void delete(User user)
    {
        delete(user.getId());
    }

    public static void update(User user)
    {
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(UPDATE_USER_GROUP_QUERY);)
        {

            stmt.setInt(5, user.getId());
            //przygotowuje sety
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            if(user.getUserGroupId() == null)
            {
                stmt.setNull(4, 4); //sqlType 4 == INTEGER (dla mysql) i to ustawi null
            }
            else
            {
                stmt.setInt(4, user.getUserGroupId());
            }
            int result = stmt.executeUpdate();

            if(result != 1)
            {
                System.out.println("Nie ma nic do edycji.");
            }
            else
            {
                System.out.println("Zaktualizowano pomyślnie.");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
