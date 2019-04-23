import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.*;

public class WeatherAPICall {

	private static HttpURLConnection con;
	public boolean isValid;
	public JSONObject json;
	
	public WeatherAPICall(String zipcode, int check) throws MalformedURLException, ProtocolException, IOException {
		String url = "http://api.openweathermap.org/data/2.5/forecast?zip=" +
		zipcode + ",us&appid=8080d1949b56c215e309570924559b1e&units=imperial";
		if( check == 1 )
		{
			url = "http://api.openweathermap.org/data/2.5/weather?zip=" +
			zipcode + ",us&appid=8080d1949b56c215e309570924559b1e&units=imperial";
		}
		
			
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
			} catch (Exception e) {
				isValid = false;
			}
			try {
				json = new JSONObject(content.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			con.disconnect();
		}
		try{
			if (json.get("cod") == "404") {
				isValid = false;
			} else {
				isValid = true;
			}
		} catch(JSONException e) {
			e.printStackTrace();
		}
	}
	// Each day has 8 entries in the JSON Object. Each represents a 3 hour span on that day. Day 1 is : 0-7, Day 2 is: 8-15, Etc.
	
	// Returns the date at a certain index in UTC.
	public String getDateAtIndex(int index) {
		String date = "default";
		try {
			date = (((JSONObject)(((JSONArray)(json.get("list"))).get(index))).get("dt")).toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	// Returns the date at a certain index in this format: Year-Month-Day Time
	public String getDateTextAtIndex(int index) {
		String date = "default";
		try {
			date = (((JSONObject)(((JSONArray)(json.get("list"))).get(index))).get("dt_txt")).toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return formatDate(date);
	}
	
	// Returns the average temperature for a day.
	public String getTempAtIndex(int index) {
		String temp = "";
		try {
			temp = ((JSONObject)((JSONObject)((JSONArray)json.get("list")).get(index)).get("main")).get("temp").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if( index == -1 )
		{
			try {
			temp = ((JSONObject)json.get("main")).get("temp").toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		return temp;
	}
	
	// Returns the minimum temperature for a given index.
	public String getTempMinAtIndex(int index) {
		String temp = "";
		try {
			temp = ((JSONObject)((JSONObject)((JSONArray)json.get("list")).get(index)).get("main")).get("temp_min").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if( index == -1 )
		{
			try {
			temp = ((JSONObject)json.get("main")).get("temp_min").toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return temp;
	}
	
	// Returns the maximum temperature for a given index.
	public String getTempMaxAtIndex(int index) {
		String temp = "";
		try {
			temp = ((JSONObject)((JSONObject)((JSONArray)json.get("list")).get(index)).get("main")).get("temp_max").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if( index == -1 )
		{
			try {
			temp = ((JSONObject)json.get("main")).get("temp_max").toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return temp;
	}
	
	// Returns the humidity for a day.
	public String getHumidityAtIndex(int index) {
		String temp = "";
		try {
			temp = ((JSONObject)((JSONObject)((JSONArray)json.get("list")).get(index)).get("main")).get("humidity").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if( index == -1 )
		{
			try {
			temp = ((JSONObject)json.get("main")).get("humidity").toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return temp;
	}
	
	// Returns the description of the weather, such as "light rain".
	public String getDescriptionAtIndex(int index) {
		String temp = "";
		try {
			temp = ((JSONObject)((JSONArray)((JSONObject)((JSONArray)json.get("list")).get(index)).get("weather")).get(0)).get("description").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if( index == -1 )
		{
			try {
			temp = ((JSONObject)((JSONArray)json.get("weather")).get(0)).get("description").toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return temp;
	}
	
	// Returns the wind speed in miles per hour.
	public String getWindSpeedAtIndex(int index) {
		String temp = "";
		try {
			temp = ((JSONObject)((JSONObject)((JSONArray)json.get("list")).get(index)).get("wind")).get("speed").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if( index == -1 )
		{
			try {
			temp = ((JSONObject)json.get("wind")).get("speed").toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return temp;
	}
	
	// Returns the amount of rain, if any, at a given index.
	public String getRainAtIndex(int index) {
		String temp = "";
		try {
			temp = ((JSONObject)((JSONObject)((JSONArray)json.get("list")).get(index)).get("rain")).get("3h").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if( index == -1 )
		{
			try {
			temp = ((JSONObject)json.get("rain")).get("3h").toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if(temp.equals(""))
			temp = "None";
		return temp;
	}
	
	// Returns the amount of snow, if any, at a given index.
	public String getSnowAtIndex(int index) {
		String temp = "";
		try {
			temp = ((JSONObject)((JSONObject)((JSONArray)json.get("list")).get(index)).get("snow")).get("3h").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if( index == -1 )
		{
			try {
			temp = ((JSONObject)json.get("snow")).get("3h").toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if(temp.equals(""))
			temp = "None";
		return temp;
	}
	
	// Returns the air pressure at a given index.
	public String getPressureAtIndex(int index) {
		String temp = "";
		try {
			temp = ((JSONObject)((JSONObject)((JSONArray)json.get("list")).get(index)).get("main")).get("pressure").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if( index == -1 )
		{
			try {
			temp = ((JSONObject)json.get("main")).get("pressure").toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		double percentage = (Double.parseDouble(temp)/1013) * 100;
		temp = "" + String.format("%.2f", percentage) + "%";
		return temp;
	}
	
	public String convertCToF(String temp) {
		double c = Double.parseDouble(temp);
		double f = c * (1.8) + 32;
		return Double.toString(f);
	}
	
	public String formatDate(String date) {
		Pattern p = Pattern.compile("(.*)-(.*)-(.*) (\\d+)");
		Matcher m = p.matcher(date);
		String month = "";
		String day = "";
		String year = "";
		String time = "";
		while(m.find()){
			month = m.group(2);
			day = m.group(3);
			year = m.group(1);
			time = m.group(4);
		}
		int timeInt = Integer.parseInt(time);
		if(timeInt == 12){
			time = "12pm";
		} else if(timeInt == 0){
			time = "12am";
		} else if(timeInt > 12) {
			time = Integer.toString(timeInt % 12) + "pm";
		} else {
			time = Integer.toString(timeInt) + "am";
		}
		return month+"/"+day+"/"+year+";"+time;
	}
	public String date(String temp) {
		Pattern p = Pattern.compile("(.*);");
		Matcher m = p.matcher(temp);
		String date = "";
		while(m.find()){
			date = m.group(1);
		}
		return date;
	}
	public String time(String temp) {
		Pattern p = Pattern.compile(";(.*)");
		Matcher m = p.matcher(temp);
		String time = "";
		while(m.find()){
			time = m.group(1);
		}
		return time;
	}
}