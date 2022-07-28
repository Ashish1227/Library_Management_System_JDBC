import java.sql.*;
import java.util.Scanner;

public class LMS{

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String USER = "ashishgatsy";
	static final String PASS = "fysv273Ash@g&";
	static final String DB_URL = "jdbc:mysql://localhost/lmsdb?useSSL=false";

	public static void main(String[] args)
	{
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			Scanner scan = new Scanner(System.in);

			clearScreen();
			lms_main(stmt,scan);

			stmt.close();
			scan.close();
			conn.close();
		} 
		catch (SQLException se) 
		{

		} 
		catch (Exception e) 
		{

		} 
		finally 
		{
			try {
				if (stmt!=null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if(conn!=null)
					conn.close(); 
			} catch (SQLException se) {
			}
		}
	}

	static void lms_main(Statement stmt, Scanner scan)
	{

		System.out.println("LIBRARY MANAGEMENT SYSTEM");
		System.out.println("-------------------------");
    	System.out.println("Login as: ");
      	System.out.println("1. Student");
      	System.out.println("2. Librarian");
      	System.out.println("3. Admin");
      	System.out.println("4. Exit");

      	System.out.print("\n\nEnter Option Number: ");
		int opt=Integer.parseInt(scan.nextLine());
		clearScreen();

		switch(opt)
		{
			case 1:
				auth_student(stmt,scan);
				break;
			case 2:
				auth_librarian(stmt,scan);
				break;
			case 3:
				auth_admin(stmt,scan);
				break;
			case 4:
				//print.
				System.exit(0);
			default:
				clearScreen();
				System.out.println("Kindly enter a valid option number...\n");
				break;
		}
		lms_main(stmt,scan);
	}

	static boolean auth_common(Statement stmt, Scanner scan, int type)
	{
		System.out.print("Enter your ID: ");
		String id = scan.nextLine();
		System.out.print("Enter your password: ");
		String password = scan.nextLine();

		clearScreen();
		boolean auth_status = false;

		if(type==1)
		{
			String sql = "SELECT student_id,student_password from student";
			ResultSet rs = executeSqlStmt(stmt,sql);

			try 
			{
				while(rs.next())
				{
					String temp_id = rs.getString("student_id");
					String temp_pass = rs.getString("student_password");
					
					if(temp_id.equals(id) && password.equals(temp_pass))
					{
						auth_status = true;
						break;
					}
				}	
			} catch (SQLException se) {
			}
		}
		if(type==2)
		{
			String sql = "SELECT librarian_id,librarian_password from librarian";
			ResultSet rs = executeSqlStmt(stmt,sql);

			try 
			{
				while(rs.next())
				{
					String temp_id = rs.getString("librarian_id");
					String temp_pass = rs.getString("librarian_password");
					
					if(temp_id.equals(id) && password.equals(temp_pass))
					{
						auth_status = true;
						break;
					}
				}	
			} catch (SQLException se) {
			}
		}
		else if(type==3)
		{
			String sql = "SELECT admin_id,admin_password from admin";
			ResultSet rs = executeSqlStmt(stmt,sql);

			try 
			{
				while(rs.next())
				{
					String temp_id = rs.getString("admin_id");
					String temp_pass = rs.getString("admin_password");
					
					if(temp_id.equals(id) && password.equals(temp_pass))
					{
						auth_status = true;
						break;
					}
				}	
			} catch (SQLException se) {
			}			
		}

		return auth_status;
	}

	static void auth_student(Statement stmt, Scanner scan)
	{
		if(auth_common(stmt,scan,1))
		{
			student_menu(stmt,scan);
		}
		else
		{
			System.out.print("Incorrect details entered. Would you like to enter again (Y/N)?....");
			String check=scan.nextLine();
			if(check.equals("Y"))
				auth_student(stmt,scan);
			else
				return;
		}
	}

	static void auth_librarian(Statement stmt, Scanner scan)
	{
		if(auth_common(stmt,scan,2))
		{
			librarian_menu(stmt,scan);
		}
		else
		{
			System.out.print("Incorrect details entered. Would you like to enter again (Y/N)?....");
			String check=scan.nextLine();
			if(check.equals("Y"))
				auth_librarian(stmt,scan);
			else
				return;
		}
	}

	static void auth_admin(Statement stmt, Scanner scan)
	{
		if(auth_common(stmt,scan,3))
		{
			admin_menu(stmt,scan);
		}
		else
		{
			System.out.print("Incorrect details entered. Would you like to enter again (Y/N)?....");
			String check=scan.nextLine();
			if(check.equals("Y"))
				auth_admin(stmt,scan);
			else
				return;
		}
	}

	static void student_menu(Statement stmt, Scanner scan)
	{
		System.out.println("Select one of the options below: ");
		System.out.println("1. List of books available");
		System.out.println("2. Exit");

		System.out.print("Enter the option number: ");
		int choice=Integer.parseInt(scan.nextLine());
		clearScreen();

		switch(choice)
		{
			case 1: 
				books_list(stmt,scan,1);
				break;
			case 2:
				return;
			default:
				clearScreen();
				System.out.println("Kindly enter a valid option number...\n");
				break;
		}
		student_menu(stmt,scan);
	}

	static void librarian_menu(Statement stmt, Scanner scan) 
	{
    	System.out.println("Select one of the options below: ");
      	System.out.println("1. List of all books");
    	System.out.println("2. List of books available");
		System.out.println("3. Add a book");
		System.out.println("4. Delete a book");
		System.out.println("5. Issue book to student");
		System.out.println("6. Book returned from student");
		System.out.println("7. Exit");


    	System.out.print("Enter the option number: ");
      	int choice=Integer.parseInt(scan.nextLine());
      	clearScreen();

      	switch (choice) 
      	{
      		case 1:
            	books_list(stmt, scan, 2);
            	break;
         	case 2:
            	books_list(stmt, scan, 1);
            	break;
         	case 3:
            	add_book(stmt, scan);
            	break;
         	case 4:
            	delete_book(stmt, scan);
            	break;
         	case 5:
            	issue_book(stmt, scan);
            	break;
         	case 6:
            	return_book(stmt, scan);
            	break;
            case 7:
            	return;
         	default:
            	clearScreen();
            	System.out.println("Kindly enter a valid option number...\n");
            	break;
      	}
      	librarian_menu(stmt, scan);
   	}

   	static void admin_menu(Statement stmt, Scanner scan)
   	{
   		System.out.println("Select one of the options below: ");
   		System.out.println("1. List of students");
      	System.out.println("2. List of librarians");
      	System.out.println("3. Add a librarian");
      	System.out.println("4. Delete a librarian");
      	System.out.println("5. Add a student");
      	System.out.println("6. Delete a student");
      	System.out.println("7. Exit");

    	System.out.print("Enter the option number: ");
      	int choice=Integer.parseInt(scan.nextLine());
      	clearScreen();

      	switch(choice)
      	{
      		case 1:
      			student_list(stmt,scan);
      			break;
      		case 2:
      			librarian_list(stmt,scan);
      			break;
      		case 3:
      			add_librarian(stmt,scan);
      			break;
      		case 4:
      			delete_librarian(stmt,scan);
      			break;
      		case 5:
      			add_student(stmt,scan);
      			break;
      		case 6:
      			delete_student(stmt,scan);
      			break;
      		case 7:
      			return;
      		default:
            	clearScreen();
            	System.out.println("Kindly enter a valid option number...\n");
            	break;
      	}
      	admin_menu(stmt,scan);
   	}

   	static boolean books_list(Statement stmt, Scanner scan, int type)
   	{
   		String sql="SELECT * from book";
   		ResultSet rs=executeSqlStmt(stmt,sql);
   		boolean empty=true;

   		try
   		{
   			while(rs.next())
   			{
   				String id=rs.getString("book_id");
   				Integer year=rs.getInt("book_pbyear");
   				String name=rs.getString("book_name");
   				String author=rs.getString("book_author");
   				String issued=rs.getString("issued_by");

   				if(type==1)
   				{
   					if(issued==null)
   					{
   	                  	System.out.println("ID : " + id);
   	                  	System.out.println("Publication year : " + year);
                  		System.out.println("Book Name: " + name);
                  		System.out.println("Author : " + author);
                  		System.out.println("");
                  		empty = false;
   					}
   				}
   				else if(type==2)
   				{
	                System.out.println("ID : " + id);
	                System.out.println("Publication year : " + year);
              		System.out.println("Book Name: " + name);
              		System.out.println("Author : " + author);
              		System.out.println("Issued by: "+issued);
              		System.out.println("");
              		empty=false;   					
   				}
   			}
   			if(empty)
   				System.out.println("No Books are available to display currently, please check again later....\n");

   			rs.close();
   		}
   		catch (SQLException e){
   		}
  		return empty;
   	}

   	static void add_book(Statement stmt, Scanner scan)
   	{
   		try
   		{
   			System.out.print("Enter book ID: ");
   			String id=scan.nextLine();
   			System.out.print("Enter book publication year: ");
   			Integer pbyear=Integer.parseInt(scan.nextLine());
   			System.out.print("Enter book name: ");
   			String name=scan.nextLine();
   			System.out.print("Enter author name: ");
   			String author=scan.nextLine();

   			clearScreen();

   			String sql= String.format(
   					"INSERT INTO book VALUES('%s', '%d', '%s', '%s', NULL);",
   					id, pbyear, name, author);
   			int check=updateSqlStmt(stmt,sql);

   			if(check!=0)
   				System.out.println("Successfully added book!\n");
   			else
   				System.out.println("Error, something went wrong!\n");
   		}
   		catch(Exception e)
   		{
   		}
   	}
   	static void delete_book(Statement stmt, Scanner scan)
   	{
   		try
   		{
   			System.out.print("Enter book ID: ");
   			String id=scan.nextLine();

   			clearScreen();

   			String sql=String.format(
   					"DELETE FROM book WHERE book_id = '%s'",id);
   			int check=updateSqlStmt(stmt,sql);

   			if(check!=0)
   				System.out.println("Successfully deleted book!\n");
   			else
   				System.out.println("Error, something went wrong\n");	
   		}
   		catch (Exception e)
   		{

   		}
   	}

   	static void issue_book(Statement stmt, Scanner scan)
   	{
   		try
   		{
   			boolean empty=books_list(stmt,scan,1);
   			if(!empty)
   			{
   	            System.out.print("Enter book ID : ");
            	String id = scan.nextLine();

            	System.out.print("Enter student ID : ");
            	String student_id = scan.nextLine();

            	clearScreen();

            	String sql = String.format("UPDATE book SET issued_by = '%s' WHERE book_id = '%s'", student_id, id);
            	int check = updateSqlStmt(stmt, sql);

            	if (check!= 0)
               		System.out.println("Book has been issued successfully!\n");
            	else
               		System.out.println("Error,something went wrong\n");
   			}
   		}
   		catch (Exception e)
   		{

   		}
   	}

   	static void return_book(Statement stmt, Scanner scan)
   	{
      	try 
    	{
        	System.out.print("Enter book ID : ");
        	String id = scan.nextLine();

        	clearScreen();

        	String sql = String.format("UPDATE book SET issued_by = NULL WHERE book_id = '%s'", id);
        	int check = updateSqlStmt(stmt, sql);

        	if (check != 0)
            	System.out.println("Book has been returned successfully!\n");
        	else
            	System.out.println("Error,something went wrong\n");
      	} 
      	catch (Exception e) 
      	{

      	}
   	}

   	static void student_list(Statement stmt, Scanner scan) 
   	{
    	String sql = "SELECT * FROM student";
      	ResultSet rs = executeSqlStmt(stmt, sql);

    	try 
    	{
        	System.out.println("List of students:\n");
         	while (rs.next()) 
         	{
            	String id = rs.getString("student_id");
            	String name = rs.getString("student_name");

            	System.out.println("Student ID : " + id);
            	System.out.println("Student Name: " + name);
            	System.out.println("");
         	}
         	rs.close();
      	} 
      	catch (SQLException e) 
      	{

      	}
   	}

   	static void librarian_list(Statement stmt, Scanner scan) 
   	{
    	String sql = "SELECT * FROM librarian";
      	ResultSet rs = executeSqlStmt(stmt, sql);

    	try 
    	{
        	System.out.println("List of librarians:\n");
         	while (rs.next()) 
         	{
            	String id = rs.getString("librarian_id");
            	String name = rs.getString("librarian_name");

            	System.out.println("Librarian ID : " + id);
            	System.out.println("Librarian Name: " + name);
            	System.out.println("");
         	}
         	rs.close();
      	} 
      	catch (SQLException e) 
      	{

      	}
   	}

   	static void add_librarian(Statement stmt, Scanner scan) 
   	{
    	try 
    	{
        	System.out.print("Enter Librarian ID: ");
        	String id = scan.nextLine();
         	System.out.print("Enter Librarian name: ");
         	String name = scan.nextLine();
         	System.out.print("Enter Librarian password: ");
         	String password = scan.nextLine();

        	clearScreen();

        	String sql = String.format("INSERT INTO librarian VALUES('%s', '%s', '%s')", id, name, password);
        	int check = updateSqlStmt(stmt, sql);

        	if (check!= 0)
            	System.out.println("Successfully added Librarian!\n");
         	else
            	System.out.println("Error,something went wrong\n");
      	} 
      	catch (Exception e) 
      	{
         
      	}
   	}

   	static void delete_librarian(Statement stmt, Scanner scan) 
   	{
    	try 
    	{
        	System.out.print("Enter Librarian ID: ");
        	String id = scan.nextLine();

        	clearScreen();

        	String sql = String.format("DELETE FROM librarian where librarian_id = '%s'", id);
        	int check = updateSqlStmt(stmt, sql);

        	if (check!= 0)
            	System.out.println("Successfully deleted Librarian\n");
         	else
            	System.out.println("Error,something went wrong\n");
      	} 
      	catch (Exception e) 
      	{
         
      	}
   	}


   	static void add_student(Statement stmt, Scanner scan) 
   	{
    	try 
    	{
        	System.out.print("Enter student ID: ");
        	String id = scan.nextLine();
         	System.out.print("Enter student name : ");
         	String name = scan.nextLine();
         	System.out.print("Enter student password: ");
         	String password = scan.nextLine();

        	clearScreen();

        	String sql = String.format("INSERT INTO student VALUES('%s', '%s', '%s', NULL)", id, name, password);
        	int check = updateSqlStmt(stmt, sql);

        	if (check!= 0)
            	System.out.println("Successfully added Student!\n");
         	else
            	System.out.println("Error,something went wrong\n");
      	} 
      	catch (Exception e) 
      	{
         
      	}
   	}

   	static void delete_student(Statement stmt, Scanner scan) 
   	{
    	try 
    	{
        	System.out.print("Enter student ID: ");
        	String id = scan.nextLine();

        	clearScreen();

        	String sql = String.format("DELETE FROM student where student_id = '%s'", id);
        	int check = updateSqlStmt(stmt, sql);

        	if (check!= 0)
            	System.out.println("Successfully deleted Student\n");
         	else
            	System.out.println("Error,something went wrong\n");
      	} 
      	catch (Exception e) 
      	{
         
      	}
   	}

   	static ResultSet executeSqlStmt(Statement stmt, String sql) 
   	{
    	try 
    	{
         	ResultSet rs = stmt.executeQuery(sql);
         	return rs;
      	} 
      	catch (SQLException se) 
      	{

      	} 
      	catch (Exception e)
      	{

	    }
      	return null;
   	}

   	static int updateSqlStmt(Statement stmt, String sql) 
   	{
    	try 
    	{
        	int rs = stmt.executeUpdate(sql);
        	return rs;
      	}
      	catch (SQLException se) 
      	{
         
      	} 
      	catch (Exception e) 
      	{
        
      	}
      	return 0;
   	}

   	static void clearScreen() 
   	{
     	System.out.println("\033[H\033[J");
      	System.out.flush();
   	}
}