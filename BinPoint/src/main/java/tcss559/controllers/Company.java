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
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Map.Entry;
import java.text.SimpleDateFormat;  
import java.util.Date;  

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONArray;
import org.json.JSONObject;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;


//relative URI path which will serve as the base class to host REST API
//http://localhost:port_number/user
/**
 * @author binca
 *
 */
@Path("/company")
public class Company {
	// Google SQL server!
	public String mysql_ip = "34.133.84.229";
	public String username = "tcss559";
	public String password = "tcss559";
	public String connectStr ="jdbc:mysql://" + mysql_ip + ":3306/garbage?user=" + username + "&password=" + password ;
	
	/**
	 * This modify method will distribute add,delete,put requests to the corresponding functions.
	 * @param addRequest      Create new pickup request base on address
	 * @param deleteRequest   Delete pickup request base on request id
	 * @param discountRequest Apply discount base on request id
	 * @return
	 */
	@Path("/modify")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("text/json")
	public Response modifyDatabase(@FormParam("add") String addRequest,
			@FormParam("delete") String deleteRequest,
			@FormParam("discount") String discountRequest) {
		System.out.println("add: " + addRequest);
		System.out.println("delete: " + deleteRequest);
		System.out.println("discount: " + discountRequest);
		if (!addRequest.equals("")) {
			return addWeight(addRequest);
		} else if (!deleteRequest.equals("")) {
			return deleteWeight(deleteRequest);
		} else {
			return applyDiscount(discountRequest);
		}
	}
	
	// allows the company to view garbage weights for all households
	@Path("add")
	@POST
	@Produces("application/json")
	public Response addWeight (@FormParam("add") String addRequest) {
		try {
			Date date = new Date();  
			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");  
		    String createdDate = formatter.format(date);
		    String processStatus = "Open";
		    String completedDate = "";
		    formatter = new SimpleDateFormat("yyyy-MMddHHmm");  
		    String time = formatter.format(date);
		    String rid = time.substring(2); //emulate an id by time "year-monthdatehourminutes"
		    int processedBin = 0;
		    String binStatus = "Have not picked up";
		    String address = addRequest;
		    int zipCode = 60600;
		    double lat = 41.00000000;
		    double lng = -87.00000000;
		    int weight = new Random().nextInt(65);
		    int capacity = 50;
		    String note = "FALSE";
		    String SQL = "INSERT INTO Records VALUES ("
	        		+ "\"" + createdDate 		+ "\","
	        		+ "\"" + processStatus 		+ "\","
	        		+ "\"" + completedDate 		+ "\"," 
	        		+ "\"" + rid 		+ "\","
	        		+ processedBin 		+ ","
	        		+ "\"" + binStatus 	+ "\"," 
	        		+ "\"" + address 	+ "\", " 
	        		+ zipCode 	+ ","
	        		+ lat 	+ ","
	        		+ lng 		+ "," 
	        		+ weight 		+ ","
	        		+ capacity 		+ "," 
	        		+ "\"" + note 		+ "\")";
		    System.out.println(SQL);
		    
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = DriverManager.getConnection(connectStr); 
    		Statement sqlStatement = connection.createStatement();	 
    		sqlStatement.executeUpdate(SQL);

            return Response
            	      .status(Response.Status.OK)
            	      .header("table", "Records")
            	      .entity("Sucessfully insert!")
            	      .build();
        } catch(Exception e) {
            System.out.println(e);
            return null;
        }
	}
	
	@Path("/delete")
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("text/json")
	public Response deleteWeight (@FormParam("delete") String deleteRequest) {
		try {
			String SQL = "DELETE FROM Records WHERE ServiceRequestNumber = \"" + deleteRequest + "\"";
		    System.out.println(SQL);
				
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = DriverManager.getConnection(connectStr); 
    		Statement sqlStatement = connection.createStatement();	 
    		sqlStatement.executeUpdate(SQL);
          
            return Response
            	      .status(Response.Status.OK)
            	      .header("table", "Records")
            	      .entity("Sucessfully delete!")
            	      .build();
        } catch(Exception e) {
            System.out.println(e);
            return null;
        }
	}

	@Path("discount")
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("application/json")
	public Response applyDiscount (@FormParam("discount") String discountRequest) {	
		try {
			String SQL = "UPDATE Records SET Note = \"TRUE\" WHERE ServiceRequestNumber = \"" + discountRequest + "\";";
		    System.out.println(SQL);
		    	
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = DriverManager.getConnection(connectStr); 
    		Statement sqlStatement = connection.createStatement();	 
    		sqlStatement.executeUpdate(SQL);
          
            return Response
            	      .status(Response.Status.OK)
            	      .header("table", "Records")
            	      .entity("Sucessfully apply discount!")
            	      .build();
        } catch(Exception e) {
            System.out.println(e);
            return null;
        }
	}
	
	@Path("/request-early")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("text/json")
	public void requestEarlyPickup(@FormParam("re-address") String earlyRequest) {
		//System.out.println("earlyRequest: "+Arrays.toString(earlyRequest.substring(51).split(",")));
		//extract address: [5448 W GIDDINGS ST,  Chicago,  60630]
		addWeight(earlyRequest.substring(51).split(",")[0]); 
	}
	
	// allows the company to change garbage weights for household
	@Path("/weight")
	@PUT
	@Produces("text/json")
	public void garbageWeightMeasurementPUT() {

	}

	// notifies the company when a container is full and needs to be picked up
	@Path("/full")
	@GET
	@Produces("text/json")
	public void fullContainerIndicator() {
		// point to mysql database and get boolean field (full)
		// if full is true
		// send notification to user
	}

	// gives company the location to pick up full garbage
	@Path("/location")
	@GET
	@Produces("text/json")
	public void locationTracking() {
		/*
		 * scan database where users have above 75% full garbage
		 * add those users to a map
		 * sort the locations by proximity
		 * create the path for garbage trucks
		 */
	}

	// classifies neighborhood about garbage statistics
	@Path("/neighborhood")
	@GET
	@Produces("text/json")
	public void neighborhoodInformation() {
		/*
		 * 
		 */
	}
	
	@Path("/")
	@GET
	@Produces("text/json")
	public Response myServiceIsAvailable() {
		return Response
        	  .status(Response.Status.OK)
      	      .entity("Sucessfully access to Waste Management Service")
        	  .build(); 
	}
	
	@POST
	@Path("optimize")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("text/json")
	public Response getPickupRequests(@FormParam("zipcode") String zc,
							 @FormParam("collectiondate") String cd) throws UnirestException {
		try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = DriverManager.getConnection(connectStr); 
    		Statement sqlStatement = connection.createStatement();
    		System.out.println("SELECT * "
  				  + "FROM Records "
  				  + "WHERE CreatedDate = \"" + cd + "\" AND "
  						+ "ZipCode = " + zc + " AND "
  						+ "(ProcessStatus = \"Open\" OR "
  						+ "ProcessStatus = \"Open - Dup\");"); 
    		//zip code doesnt need quotation marks
    		ResultSet resultSet = sqlStatement.executeQuery("SELECT * "
									    				  + "FROM Records "
									    				  + "WHERE CreatedDate = \"" + cd + "\" AND "
									    						+ "ZipCode = " + zc + " AND ("
									    						+ "ProcessStatus = \"Open - Dup\" OR ProcessStatus = \"Open\");");
            JSONArray recordArray = new JSONArray();
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
            	recordArray.put(emplObject);
            }
            //System.out.println(emplArray);
            //emplJSON.put("employees", emplArray);
            return Response
            	      .status(Response.Status.OK)
            	      .header("table", "Records")
            	      .entity(recordArray.toString())
            	      .build();
            //return emplArray.toString();
        } catch(Exception e) {
            System.out.println(e);
            return null;
        }
	}
	
	/**
	 * Get selected result from the above method, then format it to match the API input.
	 * Send the formated input to the API service to get the list of optimize paths.
	 * @param zc Zip code area to collect garbage
	 * @param cd Collection date request
	 * @return the list of optimize paths
	 * @throws UnirestException
	 */
	@Path("/optimize/result")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("text/json")
	public String getOptimizePath(@FormParam("zipcode") String zc,
			 @FormParam("collectiondate") String cd) throws UnirestException {
		Unirest.config().verifySsl(false);
		Response availableHouses = getPickupRequests(zc, cd); //extract matched records from MySQL database
		JSONArray jArray = new JSONArray(availableHouses.getEntity().toString());
		
		Map<String, double[]>  myMap = new HashMap<String, double[]>(); //store house address and its latlong

		//prepare two important input for the API: 
		//visitList is the number of stops to pick up garbage bins
		JSONObject input = new JSONObject();
		JSONObject visitList = new JSONObject();
		JSONObject fleet = new JSONObject();
		int count = 1;
		for (int i = 0; i < jArray.length(); i++) { 
			JSONArray record = jArray.getJSONArray(i);
			
			JSONObject location = new JSONObject();
			location.put("name", record.get(6)); //address
			location.put("lat", record.get(8)); 
			location.put("lng", record.get(9)); 
			
			myMap.put((String) record.get(6), new double[] { ((BigDecimal) record.get(8)).doubleValue(),  ((BigDecimal) record.get(9)).doubleValue()});
			
			JSONObject garbageBin = new JSONObject();
			garbageBin.put("location", location);
			
			visitList.put("garbage_bin_" + count, garbageBin);
			count++;
		}
		input.put("visits", visitList);
		
		//and fleet is number of garbage deployed trucks 
		//(in this case, we use one truck)
		JSONArray record = jArray.getJSONArray(0);
		JSONObject location = new JSONObject();
		location.put("id", "START!"); 
		location.put("name", record.get(6)); //address
		location.put("lat", record.get(8)); 
		location.put("lng", record.get(9));
		JSONObject garbageBin = new JSONObject();
		garbageBin.put("start_location", location);
		JSONObject garbageTruck = new JSONObject();
		garbageTruck.put("garbage_truck_1", garbageBin);
		input.put("fleet",garbageTruck);
		
		System.out.println(input.toString());
		
		//now call API...
		HttpResponse<String> response = Unirest.post("http://api.routific.com/v1/vrp")
		  .header("Content-Type", "application/json")
		  .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MWFlNzA2YjdlNDM1MTAwMTg4NTljNjMiLCJpYXQiOjE2Mzg4MjE5OTV9.mz5HyfGKdUh6xl8j-mKxPQq2n-oxd1ouKKNmPp_U_IY")
		  .body(input.toString())
		  .asString();
		//System.out.println(response.getBody());
		
		//this API removes our latlong, we need to add them again
		JSONObject completeResponse = new JSONObject(response.getBody());
		
		for (int i = 0; i < jArray.length()+1; i++) {
			JSONArray binAddressList = completeResponse.getJSONObject("solution")
					.getJSONArray("garbage_truck_1");
			JSONObject binAddress = (JSONObject) binAddressList.get(i);
			double[] latlong = myMap.get(binAddress.get("location_name"));
			System.out.println(binAddress.get("location_name"));
			binAddress.put("lat", latlong[0]);
			binAddress.put("lng", latlong[1]);
		}
		System.out.println(completeResponse);
			
		//return response.getBody();
		return completeResponse.toString();
	}
}
