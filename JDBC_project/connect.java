import java.sql.*;
import java.util.*;

public class connect
{
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String USER = "ashishgatsy";
	static final String PASS = "fysv273Ash@g&";
	static final String DB_URL = "jdbc:mysql://localhost/companydb?useSSL=false";

	public static void main(String[] args)
	{
		Connection conn = null;
   		Statement stmt = null;

  	// STEP 2. Connecting to the Database
   try{
      //STEP 2a: Register JDBC driver
      Class.forName(JDBC_DRIVER);
      //STEP 2b: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);
      //STEP 2c: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();

//STEP 3: Query to database
      String sql;
      sql = "SELECT fname, lname from employee";
      ResultSet rs = stmt.executeQuery(sql);

//STEP 4: Extract data from result set
      while(rs.next()){
         //Retrieve by column name
         String fname  = rs.getString("fname");
         String lname = rs.getString("lname");

         //Display values
         System.out.print("fname: " + fname);
         System.out.println(", lname: " + lname);
      			}

//STEP 5: Clean-up environment
      rs.close();
      stmt.close();
      conn.close();
	}catch(SQLException se){    	 //Handle errors for JDBC
      	se.printStackTrace();
   	}catch(Exception e){        	//Handle errors for Class.forName
      e.printStackTrace();
   }finally{				//finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      	}
      	try{
         		if(conn!=null)
            		conn.close();
      			}catch(SQLException se){
        		se.printStackTrace();
      		}					//end finally try
   		}					//end try
   		System.out.println("End of Code");
	}					//end main
}					//end clas

