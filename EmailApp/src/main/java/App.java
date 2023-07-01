import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class App {

	// File Location for user properties
	static final String USER_PROPS_LOC = "src/main/java/user.properties";

	// User properties
	static Properties userProperties = new Properties();
	
	public static void main(String[] args) {
		
		MailClient client = null;
		getUserProperties();
		System.out.println(userProperties.getProperty("account"));
		
		if (userProperties.getProperty("account").equals("gmail")) {
			System.out.println("working");
			client = new GMailClient();
		}
		
		client.sendMessage("crazedbstr@gmail.com", "This is a new messsage", "Houston we have touchdown again!!");

	}

	private static void getUserProperties() {

		try (FileReader in = new FileReader(App.USER_PROPS_LOC)) {
			userProperties.load(in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
