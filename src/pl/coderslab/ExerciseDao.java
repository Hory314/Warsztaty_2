package pl.coderslab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDao
{
    private static final String CREATE_USER_GROUP_QUERY = "INSERT INTO exercise (title, description) VALUES (?, ?);";
    private static final String DELETE_USER_GROUP_QUERY = "DELETE FROM exercise WHERE id = ?;";
    private static final String SELECT_ALL_USER_GROUP_QUERY = "SELECT * FROM exercise;";
    private static final String SELECT_USER_GROUP_QUERY = "SELECT * FROM exercise WHERE id = ?;";
    private static final String UPDATE_USER_GROUP_QUERY = "UPDATE exercise SET title = ?,description = ? WHERE id = ?;";

    public static Exercise select(int id)
    {
        Exercise exercise = new Exercise();
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(SELECT_USER_GROUP_QUERY);)
        {
            stmt.setInt(1, id); // rs w try() bo ustawiam to stmt.set...
            try(ResultSet rs = stmt.executeQuery();)
            {
                while(rs.next())
                {
                    exercise.setId(rs.getInt("id"));
                    exercise.setTitle(rs.getString("title"));
                    exercise.setDescription(rs.getString("description"));
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return exercise;
    }

    public static List<Exercise> selectAll()
    {
        List<Exercise> exerciseList = new ArrayList<>();
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_USER_GROUP_QUERY); ResultSet rs = stmt.executeQuery();)
        {
            while(rs.next())
            {
                Exercise exercise = new Exercise();
                exercise.setId(rs.getInt("id"));
                exercise.setTitle(rs.getString("title"));
                exercise.setDescription(rs.getString("description"));
                exerciseList.add(exercise);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return exerciseList;
    }

    public static Exercise create(Exercise exercise)
    {
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(CREATE_USER_GROUP_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);)
        {
            //przygotowuje sety
            stmt.setString(1, exercise.getTitle());
            stmt.setString(2, exercise.getDescription());

            int result = stmt.executeUpdate();

            if(result != 1) // czyli jak jest błąd
            {
                throw new RuntimeException("Update zwrócił kod: " + result);
            }

            try(ResultSet generatedKeys = stmt.getGeneratedKeys();)
            {
                if(generatedKeys.first())
                {
                    exercise.setId(generatedKeys.getInt(1));
                    System.out.println("Zapisano pomyślnie.");
                    return exercise;
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

    public static void delete(Exercise exercise)
    {
        delete(exercise.getId());
    }

    public static void update(Exercise exercise)
    {
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(UPDATE_USER_GROUP_QUERY);)
        {

            stmt.setInt(3, exercise.getId());
            //przygotowuje sety
            stmt.setString(1, exercise.getTitle());
            stmt.setString(2, exercise.getDescription());

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
