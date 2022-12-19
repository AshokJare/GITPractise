package touristAPITest;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonLibraryTest {

	private static ObjectMapper obMapper=getDefaultObjectMapper();
	
	private static ObjectMapper getDefaultObjectMapper() {
		ObjectMapper defaultobMapper=new ObjectMapper();
		// ---- Configurations can be done here
		return defaultobMapper;
	}
	
}
