package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Reseacher {
	
	
		
		//A common method to connect to the DB
	//A common method to connect to the DB
	private Connection connect(){
		Connection con = null;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");

			//Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3311/research", "root", "");
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return con;
	}
			
		
		
		//Insert Project Details
		public String insertResearch(String researchID, String researchTopic, String description, String researcherID,String cost ){
			String output = "" ;
			try{
				Connection con = connect();
					if (con == null){
						return "Error while connecting to the database for inserting."; 
				}
				
					
					// create a prepared statement
					String query = "INSERT INTO `research`(`researchID`, `researchTopic`, `description`, `researcherID`,  `cost`) VALUES (?,?,?,?,?)";
					PreparedStatement preparedStmt = con.prepareStatement(query);
					
					
					 // binding values
					 preparedStmt.setInt(1, 0);
					 preparedStmt.setString(2, researchTopic);
					 preparedStmt.setString(3, description);
					 preparedStmt.setString(4, researcherID);					 
					 preparedStmt.setDouble(5, Float.parseFloat(cost)); 
					
					 
					 // execute the statement
					 preparedStmt.execute();
					 con.close();
					 
					 String newResearch = readResearch(); 
					 output = "{\"status\":\"success\", \"data\": \"" + newResearch + "\"}";
					 
					 }catch (Exception e)
					 {
						 
						 output = "{\"status\":\"error\", \"data\":\"Error while inserting the Research.\"}"; 
						 System.err.println(e.getMessage());
					 }
			 return output;
		 }
		
		
		
		
		public String readResearch(){
			String output = "";
			try{
				Connection con = connect();
					if (con == null){
						return "Error while connecting to the database for reading."; 
			}
					
				// Prepare the html table to be displayed
				output = 
						"<table border='1' >"+ 
						"<tr >" +
						     	
							 "<th >Research Topic</th>" +
							 "<th>Description</th>" +
							 "<th>Researcher ID</th>" +							 
							 "<th>Cost</th>" +							 
							 "<th>Update</th>" +
							 "<th>Remove</th>" +
						
						 "</tr>";
	
				String query = "select * from `research`";
				 Statement stmt = con.createStatement();
				 ResultSet rs = stmt.executeQuery(query);
				 
				 
				 // iterate through the rows in the result set
				 while (rs.next()){
					 
					 
					 //String researchID =  Integer.toString(rs.getInt("researchID"));
					 int researchID = rs.getInt("researchID");
					 String researchTopic = rs.getString("researchTopic");
					 String description = rs.getString("description");
					 String researcherID = rs.getString("researcherID");					 
					 String cost = Float.toString(rs.getFloat("cost"));
					 
	
					 
					 // Add into the html table
					 
					 //output += "<tr><td>" + researchID + "</td>";
					 output += "<tr><td>" + researchTopic + "</td>";
					 output += "<td>" + description + "</td>";
					 output += "<td>" + researcherID + "</td>";					 
					 output += "<td>" + cost + "</td>";
					
		
					 
					 
					 // buttons
					
					 output += "<td><input name='btnUpdate' type='button' value='Update' "
								+ "class='btnUpdate btn btn-secondary' data-userid='" + researchID + "'></td>"
								+ "<td><input name='btnRemove' type='button' value='Remove' "
								+ "class='btnRemove btn btn-danger' data-userid='" + researchID + "'></td></tr>"; 
				 }
			 con.close();
			 
			 // Complete the html table
			 output += "</table>";
			 
			 
			 }catch (Exception e){
				 
				 output = "Error while reading the researches.";
				 System.err.println(e.getMessage());
			 }
			 return output;
			 
		}
		
		
		
		public String updateResearch(String researchID, String researchTopic, String description, String researcherID, String cost){ 
			String output = ""; 
			try{
				Connection con = connect();
				if (con == null){
					return "Error while connecting to the database for updating."; 
				} 
				
				 // create a prepared statement
				String query = "UPDATE `research` SET `researchTopic`=?,`description`=?,`researcherID`=?,`cost`=? WHERE `researchID`=?";
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				  
				 preparedStmt.setString(1, researchTopic);
				 preparedStmt.setString(2, description);
				 preparedStmt.setString(3, researcherID);				 
				 preparedStmt.setDouble(4, Float.parseFloat(cost)); 
				 preparedStmt.setString(5, researchID);
				 
				// preparedStmt.setString(4, sector);
				
				 
 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 
				 String newResearch = readResearch(); 
				 output = "{\"status\":\"success\", \"data\": \"" + newResearch + "\"}";
				 
		
				 } catch (Exception e) {
					 
					 output = "{\"status\":\"error\", \"data\": \"Error while updating the research.\"}";
					 System.err.println(e.getMessage()); 
				 } 
				 return output; 
		 }
		
		

		public String deleteResearch(String researchID) { 
			String output = ""; 
			try{ 
				Connection con = connect();
				if (con == null) { 
					return "Error while connecting to the database for deleting."; 
				} 
					// create a prepared statement
				    String query ="DELETE FROM `research` WHERE researchID=?";
					PreparedStatement preparedStmt = con.prepareStatement(query); 
					
					// binding values
					preparedStmt.setInt(1, Integer.parseInt(researchID)); 
					
					// execute the statement
					preparedStmt.execute(); 
					con.close(); 
					
					  
					String newResearch = readResearch(); 
					output = "{\"status\":\"success\", \"data\": \"" + newResearch + "\"}"; 
					
			} catch (Exception e) { 
				output = "{\"status\":\"error\", \"data\": \"Error while deleting the research.\"}"; 
				System.err.println(e.getMessage()); 
			} 
			return output; 
		}
		
}