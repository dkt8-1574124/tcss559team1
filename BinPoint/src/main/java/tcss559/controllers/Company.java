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
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Map.Entry;

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

	// allows the company to view garbage weights for all households
	@Path("weight")
	@GET
	@Produces("application/json")
	public Response SelectWeight () {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(connectStr); 
			Statement sqlStatement = connection.createStatement();	 
			ResultSet resultSet = sqlStatement.executeQuery("Select * from garbage;");
			JSONObject housholdJSON = new JSONObject();
			JSONArray householdArray = new JSONArray();
			while (resultSet.next() ) {
				JSONObject householdObject = new JSONObject();
				householdObject.put("ID", resultSet.getString("Service Request Number"));
				householdObject.put("Zip Code", resultSet.getInt("ZIP Code"));
				householdObject.put("Latitude", resultSet.getDouble("Latitude"));
				householdObject.put("Longitude", resultSet.getDouble("Longitude"));
				householdObject.put("LoadWeight", resultSet.getInt("LoadWeight"));
				householdObject.put("LoadCapacity", resultSet.getInt("LoadCapacity"));
				householdObject.put("Discount", resultSet.getString("Note"));
				householdArray.put(householdObject);
			}

			housholdJSON.put("household", householdArray);
			return Response
					.status(Response.Status.OK)
					.header("table", "garbage")
					.entity(housholdJSON.toString())
					.build();
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
	}

	// allows the company to view garbage weights for all households
		@Path("weight/{id}")
		@GET
		@Produces("application/json")
		public Response SelectWeight (@PathParam("id") String id) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection = DriverManager.getConnection(connectStr); 
				Statement sqlStatement = connection.createStatement();	 
				ResultSet resultSet = sqlStatement.executeQuery("Select * from garbage WHERE Service Request Number = " + id + ";");
				JSONObject housholdJSON = new JSONObject();
				JSONArray householdArray = new JSONArray();
				while (resultSet.next() ) {
					JSONObject householdObject = new JSONObject();
					householdObject.put("ID", resultSet.getString("Service Request Number"));
					householdObject.put("Zip Code", resultSet.getInt("ZIP Code"));
					householdObject.put("Latitude", resultSet.getDouble("Latitude"));
					householdObject.put("Longitude", resultSet.getDouble("Longitude"));
					householdObject.put("LoadWeight", resultSet.getInt("LoadWeight"));
					householdObject.put("LoadCapacity", resultSet.getInt("LoadCapacity"));
					householdObject.put("Discount", resultSet.getString("Note"));
					householdArray.put(householdObject);
				}

				housholdJSON.put("household", householdArray);
				return Response
						.status(Response.Status.OK)
						.header("table", "garbage")
						.entity(housholdJSON.toString())
						.build();
			}
			catch(Exception e)
			{
				System.out.println(e);
				return null;
			}
		}

	// allows the company to change garbage weights for household
	@Path("/weight")
	@DELETE
	@Produces("text/json")
	public void garbageWeightMeasurementDELETE() {

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
		//use 60647 and 5-9-2017 for DEMO 

		try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = DriverManager.getConnection(connectStr); 
    		Statement sqlStatement = connection.createStatement();
    		System.out.println("SELECT * "
  				  + "FROM Records "
  				  + "WHERE CreatedDate = \"" + cd + "\" AND "
  						+ "ZipCode = " + zc + " AND "
  						+ "ProcessStatus = \"Open\";"); //zip code doesnt need quotation marks
    		ResultSet resultSet = sqlStatement.executeQuery("SELECT * "
									    				  + "FROM Records "
									    				  + "WHERE CreatedDate = \"" + cd + "\" AND "
									    						+ "ZipCode = " + zc + " AND "
									    						+ "ProcessStatus = \"Open\";");
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
            //System.out.println(emplArray);
            //emplJSON.put("employees", emplArray);
            return Response
            	      .status(Response.Status.OK)
            	      .header("table", "Records")
            	      .entity(emplArray.toString())
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
	public String getSaleHouses(@FormParam("zipcode") String zc,
			 @FormParam("collectiondate") String cd) throws UnirestException {
		Unirest.config().verifySsl(false);
		Response availableHouses = getPickupRequests(zc, cd); //extract matched records from MySQL database
		JSONArray jArray = new JSONArray(availableHouses.getEntity().toString());
		
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
		
		System.out.println(response.getBody());
		
		return response.getBody();			
	}
}
