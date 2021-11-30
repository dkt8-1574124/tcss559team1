package tcss559.controllers;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

import java.util.HashSet;
import java.util.Set;
@ApplicationPath("/api")
// This main java class will be used to declare a root 
// resource for our application as well as other
// provider classes
public class BinPointApplication extends Application{
	// This method returns a collection (non-empty) with
	// specific classes to provide support for which are 
	// going to be handled when published our JAX-RS application
	@Override
	public Set<Class<?>> getClasses() {
		HashSet h = new HashSet<Class<?>>();
		// add classes that you wish to be supported by application
		// h.add( gcpREST.class );
		HashSet resources = new HashSet<Class<?>>();
		//add classes that you wish to be supported by application
		//resources.add( gcpREST.class );
		resources.add(Upload.class);
		resources.add(MultiPartFeature.class);
		resources.add(User.class);
		resources.add(Company.class);
	
		return resources;
	}
}
