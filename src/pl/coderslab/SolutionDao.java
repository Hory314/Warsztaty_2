package pl.coderslab;

import java.nio.channels.SelectionKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//todo
//Utwórz implementację dodatkowych metod realizujących zadania:
//
//        pobranie wszystkich rozwiązań danego użytkownika (dopisz metodę loadAllByUserId do klasy Solution),
//        pobranie wszystkich rozwiązań danego zadania, posortowanych od najnowszego do najstarszego (dopisz metodę loadAllByExerciseId do klasy Solution),
public class SolutionDao
{
    private static final String CREATE_USER_GROUP_QUERY = "INSERT INTO solution (created, updated, description, exercise_id, users_id) VALUES (?, ?, ?, ?. ?);";
    private static final String DELETE_USER_GROUP_QUERY = "DELETE FROM solution WHERE id = ?;";
    private static final String SELECT_ALL_USER_GROUP_QUERY = "SELECT * FROM solution;";
    private static final String SELECT_USER_GROUP_QUERY = "SELECT * FROM solution WHERE id = ?;";
    private static final String UPDATE_USER_GROUP_QUERY = "UPDATE solution SET created = ?,updated = ?, description = ?, exercise_id = ?, users_id = ? WHERE id = ?;";


    public static Solution select(int id)
    {
        Solution solution = new Solution();
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(SELECT_USER_GROUP_QUERY);)
        {
            stmt.setInt(1, id); // rs w try() bo ustawiam to stmt.set...
            try(ResultSet rs = stmt.executeQuery();)
            {
                while(rs.next())
                {
                    solution.setId(rs.getInt("id"));
                    solution.setCreated(rs.getString("created"));
                    solution.setUpdated(rs.getString("updated"));
                    solution.setDescription(rs.getString("description"));
                    solution.setExercise_id(rs.getInt("exercise_id"));
                    solution.setUsers_id(rs.getInt("users_id"));
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return solution;
    }

    public static List<Solution> selectAll()
    {
        List<Solution> userList = new ArrayList<>();
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_USER_GROUP_QUERY); ResultSet rs = stmt.executeQuery();)
        {
            while(rs.next())
            {
                Solution solution = new Solution();
                solution.setId(rs.getInt("id"));
                solution.setCreated(rs.getString("created"));
                solution.setUpdated(rs.getString("updated"));
                solution.setDescription(rs.getString("description"));
                solution.setExercise_id(rs.getInt("exercise_id"));
                solution.setUsers_id(rs.getInt("users_id"));
                userList.add(solution);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return userList;
    }

    public static Solution create(Solution solution)
    {
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(CREATE_USER_GROUP_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);)
        {
            //przygotowuje sety
            stmt.setString(1, solution.getCreated());
            stmt.setString(2, solution.getUpdated());
            stmt.setString(3, solution.getDescription());
            stmt.setInt(4, solution.getExercise_id());
            stmt.setInt(5, solution.getUsers_id());

            int result = stmt.executeUpdate();

            if(result != 1) // czyli jak jest błąd
            {
                throw new RuntimeException("Update zwrócił kod: " + result);
            }

            try(ResultSet generatedKeys = stmt.getGeneratedKeys();)
            {
                if(generatedKeys.first())
                {
                    solution.setId(generatedKeys.getInt(1));
                    System.out.println("Zapisano pomyślnie.");
                    return solution;
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

    public static void delete(Solution solution)
    {
        delete(solution.getId());
    }

    public static void update(Solution solution)
    {
        try(Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(UPDATE_USER_GROUP_QUERY);)
        {

            stmt.setInt(6, solution.getId());
            //przygotowuje sety
            stmt.setString(1, solution.getCreated());
            stmt.setString(2, solution.getUpdated());
            stmt.setString(3, solution.getDescription());
            stmt.setInt(4, solution.getExercise_id());
            stmt.setInt(5, solution.getUsers_id());

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
