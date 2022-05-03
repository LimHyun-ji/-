package DB2021Team01;

import java.awt.*;
import java.sql.*;

public class DB2021Team01_DBManager {

	static final String JDBC_Driver="com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/DB2021Team01?&useSSL=false"; //접속할 DB 서버	
	static final String userName="DB2021Team01";
	static final String password="DB2021Team01";
	List column;
	List row;
	public String temp = "";
	public String errorMessage;
	
	public DB2021Team01_DBManager(String sql) {
		Connection conn=null;
		Statement state=null;
		
		try {
			Class.forName(JDBC_Driver);
			conn=DriverManager.getConnection(DB_URL,userName,password);
			System.out.println("[MySQL Connection]\n");
			state= conn.createStatement();
			
			ResultSet rs= state.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			while(rs.next()) {
				/*
				if(i == 0) {
					String actor= rs.getString("actor");
					String gender= rs.getString("gender");
					Date birthdate= rs.getDate("birthdate");
				
					System.out.println("Actor: "+ actor
							+"\nGender: "+gender
							+"\nBirthdate: "+birthdate
							+"\n-------------\n");
					
					System.out.println("정상");
				}*/

				for(int k =1; k<=columnCount; k++) {
					String str =rs.getString(k);
					if(str==null)
						break;
						temp +=(str+"\t");
						System.out.println(temp);
				} temp+=("\n");

			}
			
			rs.close();
			state.close();
			conn.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			errorMessage="error";
			
		}finally {
			
			try {if(state != null)
				state.close();
			}catch(SQLException e) {
				System.out.println("SQL Exception");
			}
			try {
				if(conn != null)
					conn.close();
			}catch(SQLException e) {
				System.out.println("SQL Exeption");
			}
		}
		System.out.println("MySQL Close");
		
	}

}
