import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.*;


/*
try {
	WeatherAPICall userInfo = new WeatherAPICall("68135");
	System.out.println(((JSONObject) ((JSONObject) userInfo.array.get(0)).get("main")).get("temp"));
} catch (Exception e) {
	System.out.println(e);
}
*/
public class WeatherAPICall {

	private static HttpURLConnection con;
	private JSONObject json;
	public JSONArray array;
	private JSONParser parser;
	public WeatherAPICall(String zipcode) throws MalformedURLException, ProtocolException, IOException {
		String url = "http://api.openweathermap.org/data/2.5/forecast?zip=" + zipcode
				+ ",us&appid=8080d1949b56c215e309570924559b1e&units=imperial";
		try {
			URL myurl = new URL(url);
			con = (HttpURLConnection) myurl.openConnection();
			con.setRequestMethod("GET");
			StringBuilder content;
			try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
				String line;
				content = new StringBuilder();
				while ((line = in.readLine()) != null) {
					content.append(line);
					content.append(System.lineSeparator());
				}
			}
			try {
				parser = new JSONParser(content.toString());
				json = new JSONObject(parser.toString());
				array = new JSONArray(parser.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			con.disconnect();
		}
	}
}