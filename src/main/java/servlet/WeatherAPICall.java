import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.*;

//Get Date
// ((JSONObject)(((JSONArray)(userInfo.json.get("list"))).get(0))).get("dt")

//Get Temp
// ((JSONObject)(((JSONObject)(((JSONArray)(userInfo.json.get("list"))).get(0))).get("main"))).get("temp")
public class WeatherAPICall {

	private static HttpURLConnection con;
	private JSONObject json;
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
				json = new JSONObject(content.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			con.disconnect();
		}
	}
	
	public String getDateAtIndex(int index) {
		String date = "default";
		try {
			date = (((JSONObject)(((JSONArray)(json.get("list"))).get(0))).get("dt")).toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public String getTempAtIndex(int index) {
		String temp = "";
		try {
			temp = ((JSONObject)((JSONObject)((JSONArray)json.get("list")).get(index)).get("main")).get("temp").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	public String getHumidityAtIndex(int index) {
		String temp = "";
		try {
			temp = ((JSONObject)((JSONObject)((JSONArray)json.get("list")).get(index)).get("main")).get("humidity").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return temp;
	}
}