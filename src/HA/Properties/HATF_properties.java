package HA.Properties;



import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class HATF_properties {

	public final static String PROPERTY_FILENAME = "config/HATAF.properties";

	private Properties _automation_properties = new Properties();
	
	public final static String BASEOKTAURL = "BASEOKTAURL";
	public final static String OkTAUNAME = "OkTAUNAME";
	public final static String OKTAPWD = "OKTAPWD";
	public final static String OKTATENANT = "OKTATENANT";	
	public final static String BASEURL = "BASEURL";
	public final static String BROWSER = "BROWSER";
	public final static String UNAME = "UNAME";
	public final static String PWD = "PWD";
	public final static String TENANT = "TENANT";
	public static final String BROWSERBASEDTENANT = "BROWSERBASEDTENANT";
	public final static String WSURL = "WSURL";
	public final static String WSUNAME = "WSUNAME";
	public final static String WSPWD = "WSPWD";
	public final static String CONN_STR = "CONN_STR";
	public final static String SQLJAVADriver = "SQLJAVADriver";
	public final static String TEST_DATA = "TEST_DATA";
	public final static String boxURL = "boxURL";
	public final static String boxUNAME = "boxUNAME";
	public final static String boxPWD = "boxPWD";
	public final static String Exestartmail = "Exestartmail";
	public final static String Exemail = "Exemail";
	public final static String waitTime = "waitTime";
	public final static String projPATH = "projPATH";

	public static final String WSTENANT = "WSTENANT";
	
	public final static String CUNAME = "CUNAME";
	public final static String CPWD = "CPWD";

	
	

	/**
	 * Loads the properties file
	 */
	public HATF_properties() {
		try {
			_automation_properties.load(new FileInputStream(PROPERTY_FILENAME));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		assert !_automation_properties.isEmpty();
	}

	/**
	 * returns the value of the property denoted by key
	 * 
	 * @param key
	 * @return
	 */
	public String getProperty(final String key) {
		String property = _automation_properties.getProperty(key);
		return property != null ? property.trim() : property;
	}

}
