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

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

// relative URI path which will serve as the base class to host REST API
// http://localhost:port_number/REST
/**
 * @author binca
 *
 */
@Path("/household")
public class Household {
	// This method will get localized weather information from US WeatherAPI by Zip service
	
	/**
	 * @return latitude and longtitude base on your current ip address
	 * @throws UnirestException
	 */
	@Path("/")
	@GET
	@Produces("text/json")
	public String getLocalWeather() throws UnirestException {
		String current_ip = getIPHandler();
		Unirest.config().verifySsl(false);
		HttpResponse<String> response = Unirest.get("https://ip-geo-location.p.rapidapi.com/ip/" + current_ip + "?format=json")
				.header("x-rapidapi-host", "ip-geo-location.p.rapidapi.com")
				.header("x-rapidapi-key", "6659a2fffamsh53f5c342fb3f492p1dd60ejsn95a96ab2070d")
				.asString();
		return response.getBody();
	}
	
	/**
	 * @return your current ip address
	 */
	@Path("/ip")
	@GET
	@Produces("text/json")
	public String getIPHandler() {
		Client client = ClientBuilder.newClient();
		WebTarget myResource = client.target("http://api.ipify.org");
		String response = myResource.request(MediaType.APPLICATION_JSON)
		.get(String.class);
		return response;
	}
}
