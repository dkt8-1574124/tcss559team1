package tcss559.controllers;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Map.Entry;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONArray;
import org.json.JSONObject;

// relative URI path which will serve as the base class to host REST API
// http://localhost:port_number/upload
@Path("/upload")
public class Upload {
	// Google SQL server!
	public String mysql_ip = "34.133.84.229";
	public String username = "tcss559";
	public String password = "tcss559";
	public String connectStr ="jdbc:mysql://" + mysql_ip + ":3306/garbage?user=" + username + "&password=" + password ;

	//convert csv file into mysql
	@GET  
    @Path("/file")  
    @Consumes(MediaType.MULTIPART_FORM_DATA)  
    public Response uploadFile() {
		Scanner scanner = new Scanner("data\\chicago-garbage.csv");
		try {
			//File directory = new File("./");
			//System.out.println(directory.getAbsolutePath());
			Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = DriverManager.getConnection(connectStr); 
    		Statement sqlStatement = connection.createStatement();
    				
    		sqlStatement.executeUpdate("DROP DATABASE IF EXISTS `garbage`;");
    		sqlStatement.executeUpdate("CREATE DATABASE IF NOT EXISTS `garbage`;");
    		sqlStatement.executeUpdate("USE `garbage`;");
			
    		sqlStatement.executeUpdate("CREATE TABLE Records (\r\n"
    				+ "    CreatedDate VARCHAR(60) NOT NULL,\r\n"
    				+ "	ProcessStatus VARCHAR(60) NOT NULL,\r\n"
    				+ "	CompletedDate VARCHAR(60) NOT NULL,\r\n"
    				+ "    ServiceRequestNumber VARCHAR(60) NOT NULL,\r\n"
    				+ "    BlackCartsDelivered INT(60),\r\n"
    				+ "	CartStatus VARCHAR(60) NOT NULL,\r\n"
    				+ "	StreetAddress VARCHAR(60) NOT NULL,\r\n"
    				+ "	ZipCode INT NOT NULL,\r\n"
    				+ "	Latitude DECIMAL(8,6) NOT NULL,\r\n"
    				+ "	Longitude DECIMAL(9,6) NOT NULL,\r\n"
    				+ "	LoadWeight INT NOT NULL,\r\n"
    				+ "	LoadCapacity INT NOT NULL,\r\n"
    				+ "    Note VARCHAR(60),\r\n"
    				+ "    PRIMARY KEY (ServiceRequestNumber)\r\n"
    				+ ");");
			
			List<List<String>> records = new ArrayList<>();
			int c= 0; //set limit to 10, test first
			scanner.nextLine();
			while (scanner.hasNextLine() && c < 10) {
		    	List<String> row = getRecordFromLine(scanner.nextLine());
		        records.add(row);
		        String createDate = row.get(0).replaceAll("/", "-");   //Need to change this to date type in the future
		        String dumpStatus = row.get(1);
		        String completeDate = row.get(2).replaceAll("/", "-");
		        String requestID = row.get(3);
		        int cartsNum;
		        if (row.get(5).equals("") || row.get(5) == null) {
		        	cartsNum = 0; 
		        } else {
		        	cartsNum = Integer.parseInt(row.get(5));
		        }
		        String cartStatus = row.get(7);
		        String address = row.get(8);
		        int zipCode = Integer.parseInt(row.get(9));
		        double lat = Double.parseDouble(row.get(16));
		        double lng = Double.parseDouble(row.get(17));
		        int weightLoad = Integer.parseInt(row.get(20));      //Need to random generate later on
		        int weightCapacity = Integer.parseInt(row.get(21));  //Need to random generate later on
		        String note = "NA";
		        
		        String SQL = "INSERT INTO Records VALUES ("
		        		+ "\"" + createDate 		+ "\","
	            		+ "\"" + dumpStatus 		+ "\","
	            		 + "\"" + completeDate 		+ "\","
	            		 + "\"" + requestID 		+ "\"," 
	            		 + cartsNum 		+ ","
	            		 + "\"" + cartStatus 		+ "\","
	            		 + "\"" + address 		+ "\","
	            		 + zipCode 		+ ","
	            		 + lat 		+ ","
	            		 + lng 		+ ","
	            		 + weightLoad 		+ ","
	            		 + weightCapacity 		+ ","	            		 
	            		 + "\"" + note  + "\"" 	+  ")";
		        System.out.println("SQL: " + SQL);
	    		sqlStatement.executeUpdate(SQL);
		        c++;
		    }
		    display(records);		
            return Response
          	      .status(Response.Status.OK)
        	      .entity("Sucessfully import to MySQL")
          	      .build();        
        } catch(Exception e)  {
        	//System.out.println(e);
        	e.printStackTrace();
            return  Response
          	      .status(Response.Status.BAD_REQUEST)
            	  .entity(e.toString())
            	  .build();
        }	 
    }
	
	@Path("/select-all")
	@GET
	@Produces("text/json")
	public Response SelectAllRecord ()  {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = DriverManager.getConnection(connectStr); 
    		Statement sqlStatement = connection.createStatement();	 
    		ResultSet resultSet = sqlStatement.executeQuery("Select * from Records;");
            JSONArray emplArray = new JSONArray();
            while (resultSet.next() ) {
	            JSONArray emplObject = new JSONArray();
            	emplObject.put( resultSet.getString("CreatedDate"));
            	emplObject.put( resultSet.getString("ProcessStatus"));
            	emplObject.put( resultSet.getString("CompletedDate"));
            	emplObject.put( resultSet.getString("ServiceRequestNumber"));
            	emplObject.put( resultSet.getString("BlackCartsDelivered"));
            	emplObject.put( resultSet.getString("CartStatus"));
            	emplObject.put( resultSet.getString("StreetAddress"));
            	emplObject.put( resultSet.getString("ZipCode"));
            	emplObject.put( resultSet.getDouble("Latitude"));
            	emplObject.put( resultSet.getDouble("Longitude"));
            	emplObject.put( resultSet.getInt("LoadWeight"));
            	emplObject.put( resultSet.getInt("LoadCapacity"));
            	emplObject.put( resultSet.getString("Note"));
            	System.out.println(emplObject.toString());
            	emplArray.put(emplObject);
            }
            
            //emplJSON.put("employees", emplArray);
            return Response
            	      .status(Response.Status.OK)
            	      .header("table", "Records")
            	      .entity(emplArray.toString())
            	      .build();
        } catch(Exception e) {
            System.out.println(e);
            return null;
        }
    }
	
	//just to display for the user page
	@Path("/select-one-random")
	@GET
	@Produces("text/json")
	public Response SelectOneRandom ()  {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = DriverManager.getConnection(connectStr); 
    		Statement sqlStatement = connection.createStatement();	 
    		ResultSet resultSet = sqlStatement.executeQuery("Select * from Records ORDER BY RAND() LIMIT 1;");
            JSONObject emplJSON = new JSONObject();
            JSONArray emplArray = new JSONArray();
            while (resultSet.next() ) {
	            JSONArray emplObject = new JSONArray();
            	emplObject.put( resultSet.getString("CreatedDate"));
            	emplObject.put( resultSet.getString("ProcessStatus"));
            	emplObject.put( resultSet.getString("CompletedDate"));
            	emplObject.put( resultSet.getString("ServiceRequestNumber"));
            	emplObject.put( resultSet.getString("BlackCartsDelivered"));
            	emplObject.put( resultSet.getString("CartStatus"));
            	emplObject.put( resultSet.getString("StreetAddress"));
            	emplObject.put( resultSet.getString("ZipCode"));
            	emplObject.put( resultSet.getDouble("Latitude"));
            	emplObject.put( resultSet.getDouble("Longitude"));
            	emplObject.put( resultSet.getInt("LoadWeight"));
            	emplObject.put( resultSet.getInt("LoadCapacity"));
            	emplObject.put( resultSet.getString("Note"));
            	emplArray.put(emplObject);
            }
            
            System.out.println("emplArray.toString(): " + emplArray.toString());
            return Response
            	      .status(Response.Status.OK)
            	      .header("table", "Records")
            	      .entity(emplArray.toString())
            	      //.entity("[[\"6-15-2012\",\"Completed - Dup\",\"8-27-2012\",\"12-01089274\",\"0\",\"\",\"4944 W KINZIE ST\",\"60644\",41.887978,-87.749685,58,50,\"NA\"]]")
            	      .build();
        }
        catch(Exception e)
        {
            System.out.println(e);
            return null;
        }
    }
	
	private List<String> getRecordFromLine(String line) {
	    List<String> values = new ArrayList<String>();
	    try (Scanner rowScanner = new Scanner(line)) {
	        rowScanner.useDelimiter(",");
	        while (rowScanner.hasNext()) {
	            values.add(rowScanner.next());
	        }
	    }
	    return values;
	}
	
	private void display(List<List<String>> records){
		String result = "";
		for(int i = 0; i < records.size(); i++){
			for(int j = 0; j < records.get(i).size(); j++){
				result += records.get(i).get(j) + " ";
			}
			result += "\n";
		}
		System.out.print(result);
		System.out.println();
	}	 
}
