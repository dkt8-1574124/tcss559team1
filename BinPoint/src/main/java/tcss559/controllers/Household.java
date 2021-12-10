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
	// TODO: declare fields here
	public double setWeight = 50.0; // lbs

	/**
	 * @return latitude and longtitude base on your current ip address
	 * @throws UnirestException
	 */
	@Path("/")
	@GET
	@Produces("text/json")
	public String getLatLng() throws UnirestException {
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
