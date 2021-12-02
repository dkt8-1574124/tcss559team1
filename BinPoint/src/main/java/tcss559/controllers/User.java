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

// Assumption: One household user per garbage bin

// relative URI path which will serve as the base class to host REST API
// http://localhost:port_number/user
@Path("/user")
public class User {
	// TODO: declare fields here
	public double setWeight = 50.0; // lbs
	
	// retrieves garbage weight currently in user's garbage container
	@Path("/weight")
	@GET
	@Produces("text/json")
	public void garbageWeightMeasurement() {
		// point to the mysql database and get the weight data
		// credentials must be verified and user profile information
		// is retrieved to get the bin information
		// print out the weight content to the user on html page
	}
	
	// shows notification to user alerting that garbage container is full
	@Path("/full")
	@GET
	@Produces("text/json")
	public void fullContainerIndicator() {
		// point to mysql database and get boolean field (full)
		// if full is true
		// 	send notification to user
	}
	
	// shows a warning that the alloted weight allowed is over which could
	// result as a charge for the user
	@Path("/overload")
	@GET
	@Produces("text/json")
	public void loadWarning() {
		double allotedWeight = 0.0; // dummy variable
		if (setWeight < allotedWeight) {
			// send notification to user of a potential charge
			// 
		}
	}
	
	// service discount for being a great customer 
	// TODO: discuss some requirements, whether this should be a get method
	@Path("/service")
	@GET
	@Produces("text/json")
	public void serviceDiscount() {
		// unsure of what to write yet
		// send notification to user
	}
}
