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


//relative URI path which will serve as the base class to host REST API
//http://localhost:port_number/user
@Path("/company")
public class Company {
	// TODO: declare fields

	// allows the company to change garbage weights for household
	@Path("/weight")
	@POST
	@Produces("text/json")
	public void garbageWeightMeasurementPOST() {

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

}
