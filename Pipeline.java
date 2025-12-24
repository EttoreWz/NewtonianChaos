import java.sql.*;
import java.util.ArrayList;

public class Pipeline {
    public void uploadData(ArrayList<Integer> prngValues, ArrayList<Long> evenBits, ArrayList<Long> oddBits) {
        String url = "jdbc:mysql://localhost:3306/testDB";
        String user = "root";
        String password = "password";
        
        String query = "INSERT INTO newtonianData (PRNG) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            for (Integer value : prngValues) {
                pstmt.setInt(1, value);
                pstmt.addBatch();       
            }

            pstmt.executeBatch();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
