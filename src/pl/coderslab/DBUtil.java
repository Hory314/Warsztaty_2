package pl.coderslab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil
{
    private static String DB_URL = "jdbc:mysql://localhost:3306/";
    private static String DB_PARAMS = "?useSSL=false&useUnicode=yes&characterEncoding=UTF-8" + "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static String DB_USER = "root";
    private static String DB_PASS = "coderslab";

    public static Connection getConnection(String db) throws SQLException
    {
        return DriverManager.getConnection(DB_URL + db + DB_PARAMS, DB_USER, DB_PASS);
    }

    public static Connection getConnection() throws SQLException
    {
        return getConnection("programming_school");
    }
}
