import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Map;

public class Database {

    private static String url = "jdbc:postgresql://localhost/";
    private static final String user = "postgres";
    private static final String password = "";
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    public static void saveIntoDatabase(Map<String, Integer> wordCount) throws SQLException, IOException {
        Connection connection = connect();
        createTable(connection);
        insertData(wordCount, connection);
        connection.close();
    }

    public static Connection connect() throws IOException {
        Connection connection = null;
        String tempUrl = url;
        String tempUser = "";
        String tempPassword = "";
        while (connection == null){
            try {
                System.out.println("Type the Database Name: ");
                tempUrl += reader.readLine();
                System.out.println("\nThe Database user by default is \"postgres\"");
                tempUser += reader.readLine();
                System.out.println("\nType the Database password: ");
                tempPassword += reader.readLine();
                connection = DriverManager.getConnection(tempUrl, tempUser, tempPassword);
                System.out.println();
            }catch (SQLException exception){
                System.out.println("Failed to connect to the PostgreSQL!");
                tempUrl =url;
                tempUser = "";
                tempPassword = "";
            }
        }
        return connection;
    }


    public static void createTable(Connection connection) throws SQLException {
        String sql = "  create table if not exists wordCount(\n" +
                "  \t\tid serial Primary key, word text, count integer);\n";
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.close();
    }

    public static void insertData(Map<String, Integer>map, Connection connection) throws SQLException {
        PreparedStatement statement = null;
        String sql = "Insert into wordCount(word, count) values(?, ?)";
        for (Map.Entry<String, Integer> entry : map.entrySet()){
            statement = connection.prepareStatement(sql);
            statement.setString(1, entry.getKey());
            statement.setInt(2, entry.getValue());
            statement.executeUpdate();
            statement.close();
        }
        System.out.println("The queries have been successfully executed!:)");
    }

}
