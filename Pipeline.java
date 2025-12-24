import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class Pipeline {
	private static int index=1; 
    public Pipeline(ArrayList<Integer> prngValues, Map<Integer, Long> evenBits, Map<Integer, Long> oddBits) {
        String url = "jdbc:mysql://localhost:3306/newton";
        String user = "root";
        String password = "password";
        
        String query = "INSERT INTO newtonianData VALUES (?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        	
        	int size = prngValues.size();
            for (int i=0; i<size; i++) {
            	pstmt.setInt(1,index);
                pstmt.setInt(2, prngValues.get(i));
                if (evenBits.get(i) != null) {
                    pstmt.setFloat(3, evenBits.get(i));
                    pstmt.setFloat(4, oddBits.get(i));
                } else {
                    pstmt.setNull(3, java.sql.Types.FLOAT);;
                    pstmt.setNull(4, java.sql.Types.FLOAT);;
                }
                pstmt.addBatch();
                index++;
            }

            pstmt.executeBatch();
            System.out.println("Pipeline Transfer Complete.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
