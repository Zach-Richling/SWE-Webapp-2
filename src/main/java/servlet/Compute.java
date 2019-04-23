import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import org.apache.commons.lang3.*;

@WebServlet(name = "Compute", urlPatterns = { "/Compute" })
public class Compute extends HttpServlet {
	
		String highLow;
		String cloud;
		String pressure;
		String avgtemp;
		String windSpeed;
		String rain;
		String snow;
		String humidity;
		
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WeatherAPICall currentData = new WeatherAPICall(request.getParameter("ZipCode"), 1);
		WeatherAPICall data = new WeatherAPICall(request.getParameter("ZipCode"), 0);
		if (!currentData.isValid) {
			response.setContentType("text/html");
			final PrintWriter out = response.getWriter();
			//response.sendRedirect(response.encodeRedirectURL("https://web-app3.herokuapp.com/"));
			out.print("<script type=\"text/javascript\">");
			out.print("alert('Invalid Zip Code');");
			out.print("window.location = \"https://web-app3.herokuapp.com/\";");
			out.print("</script>");
			out.close();
			return;
		}
		String zip = request.getParameter("ZipCode");
		highLow = request.getParameter("HighLow");
		cloud = request.getParameter("CloudCover");
		pressure = request.getParameter("Pressure");
		avgtemp = request.getParameter("Avg");
		windSpeed = request.getParameter("WindSpeed");
		rain = request.getParameter("Rain");
		snow = request.getParameter("Snow");
		humidity = request.getParameter("Humidity");
		
		response.setContentType("text/html");
		final PrintWriter out = response.getWriter();
		Cookie[] cookieArray = request.getCookies();
		// Cookie array is set up as follows: zip, high/low, cloud, pressure, avgtemp, windSpeed, rain, snow, humidity.
		/*
		for (Cookie cookie : cookieArray) {
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		*/
		if (zip == null) {
			Cookie cookie = new Cookie("zip", "invalid");
			cookie.setMaxAge(604800);
			response.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("zip", zip);
			cookie.setMaxAge(604800);
			response.addCookie(cookie);
		}
		if (highLow == null) {
			Cookie cookie = new Cookie("highLow", "false");
			cookie.setMaxAge(604800);
			response.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("highLow", "true");
			cookie.setMaxAge(604800);
			response.addCookie(cookie);
		}
		if (cloud == null) {
			Cookie cookie = new Cookie("cloud", "false");
			cookie.setMaxAge(604800);
			response.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("cloud", "true");
			cookie.setMaxAge(604800);
			response.addCookie(cookie);
		}
		if (pressure == null) {
			Cookie cookie = new Cookie("pressure", "false");
			cookie.setMaxAge(604800);
			response.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("pressure", "true");
			cookie.setMaxAge(604800);
			response.addCookie(cookie);
		}
		if (avgtemp == null) {
			Cookie cookie = new Cookie("avgtemp", "false");
			cookie.setMaxAge(604800);
			response.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("avgtemp", "true");
			cookie.setMaxAge(604800);
			response.addCookie(cookie);
		}
		if (windSpeed == null) {
			Cookie cookie = new Cookie("windSpeed", "false");
			cookie.setMaxAge(604800);
			response.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("windSpeed", "true");
			cookie.setMaxAge(604800);
			response.addCookie(cookie);
		}
		if (rain == null) {
			Cookie cookie = new Cookie("rain", "false");
			cookie.setMaxAge(604800);
			response.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("rain", "true");
			cookie.setMaxAge(604800);
			response.addCookie(cookie);
		}
		if (snow == null) {
			Cookie cookie = new Cookie("snow", "false");
			cookie.setMaxAge(604800);
			response.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("snow", "true");
			cookie.setMaxAge(604800);
			response.addCookie(cookie);
		}
		if (humidity == null) {
			Cookie cookie = new Cookie("humidity", "false");
			cookie.setMaxAge(604800);
			response.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("humidity", "true");
			cookie.setMaxAge(604800);
			response.addCookie(cookie);
		}
		
		String htmlServlet = "";
		String formated = "";
		
		htmlServlet = htmlServlet + 
		"<!DOCTYPE html>" +
		"<html>" + 
		"<head>" +
		"	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
		"<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">" +
		"<title>WeatherForcast</title>" +
		"<style>" +
		"html {" +
		"	height: 100%;" +	
		"}" +
		"body {" +
		"	margin: 0;" +
		"	min-height: 100%;" +
		"	background-color: #f2f2f2;" +
		"	background-image: linear-gradient(to bottom right, #bfbfbf, white);" +
		"	font-family: Arial, Helvetica, sans-serif;" +
		"}" +
		".topnav {" +
		"	overflow: hidden;" +
		"	background-color: #333;" +
		"}" +
		".topnav a {" +
		"	float: left;" +
		"	display: block;" +
		"	color: #f2f2f2;" +
		"	text-align: center;" +
		"	padding: 14px 16px;" +
		"	text-decoration: none;" +
		"	font-size: 17px;" +
		"}" +
		".topnav a:hover {" +
		"	background-color: #ddd;" +
		"	color: black;" +
		"}" +
		".topnav a.active {" +
		"	background-color: #0066ff;" +
		"	color: white;" +
		"}" +
		".topnav input[type=Numeric] {" +
		"	float: right;" +
		"	padding: 6px;" +
		"	border: none;" +
		"	margin-top: 8px;" +
		"	margin-right: 16px;" +
		"	font-size: 17px;" +
		"}" +
		".topnav .icon {" +
		"	display: none;" +
		"}" +
		"@media screen and (max-width: 600px) {" +
		"	.topnav a:not(:first-child) {display: none;}" +
		"	.topnav a.icon {" +
		"			float: right;" +
		"		display: block;" +
		"	}" +
		"}" +
		"@media screen and (max-width: 600px) {" +
		"	.topnav.responsive {position: relative;}" +
		"	.topnav.responsive .icon {" +
		"		position: absolute;" +
		"		right: 0;" +
		"		top: 0;" +
		"	}" +
		"	.topnav.responsive a {" +
		"		float: none;" +
		"		display: block;" +
		"		text-align: left;" +
		"	}" +
		"}" +
		"#formContainer {" +
		"	background-color: white;" +
		"	height: 100%;" +
		"	width: 15%;" +
		"	position: fixed;" +
		"	top: 0;" +
		"	right: 0;" +
		"	padding-left: 10px;" +
		"	padding-top: 20px;" +
		"}" +
		"#formContainer .submitPref {" +
		"	background-color: #0066ff;" +
		"	border: none;" +
		"	height: 25px;" +
		"	color: white;" +
		"}" +
		"#dataContainer {" +
		"	display: block;" +
		"	overflow: auto;" +
		"	margin-top = 0;" +
		"	height: 100%;" +
		"	width: 100%;" +
		"}" +
		"th, td {" +
		"	color: white;" +
		"	text-align: center;" +
		"}" +
		".periodData{" +
		"	display: block;" +
		"	overflow: auto;" +
		"	text-align: center;" +
		"	background-image: linear-gradient(to bottom right, #424242, #212121);" +
		"	width: 65%;" +
		"	height: auto;" +
		"	margin: 25px 5%;" +
		"	border-radius: 15px;" +
		"}" +
		".HighLowCurrent {" +
		"	padding-top: 1px;" +
		"	margin-left: 20px;" +
		"	margin-right: 80%;" +
		"}" +
		".periodData table {" +
		"	margin: 0 auto;" +
		"}" +
		"</style>" +
		"</head>" + 
		"<body onload=loadCookies()>" + 
		"<div class=\"topnav\" id=\"topNav\">" +
		"<a class=\"active\" href=\"index.html\"><b>Home</b></a>" +
		"<a href=\"javascript:void(0);\" class=\"icon\" onclick=\"myFunction()\">" +
		"	<i class=\"fa fa-bars\"></i>" +
		"</a>" +
	"</div>" +
	"<div id=\"dataContainer\" style=\"padding-left:16px\">" +
		getCurrentData(currentData) +
		"</div>" +
		"<div id=\"formContainer\">" +
		"	<form action=\"Compute\" method=\"POST\">" +
		"		<input type=\"Numeric\" name=\"ZipCode\" placeholder=\"Enter zipcode...\" maxlength=\"5\" required><br><br>" +
		"		<input type=\"checkbox\" name=\"CloudCover\" value=\"cloud\">Cloud Coverage</br>" +
		"		<input type=\"checkbox\" name=\"Pressure\" value=\"pressure\">Pressure</br>" +
		"		<input type=\"checkbox\" name=\"Avg\" value=\"avg\">Average Temperature</br>" +
		"		<input type=\"checkbox\" name=\"HighLow\" value=\"highlow\">High/Low</br>" +
		"		<input type=\"checkbox\" name=\"WindSpeed\" value=\"windspeed\">Wind Speed</br>" +
		"		<input type=\"checkbox\" name=\"Rain\" value=\"rain\">Rain</br>" +
		"		<input type=\"checkbox\" name=\"Snow\" value=\"snow\">Snow</br>" +
		"		<input type=\"checkbox\" name=\"Humidity\" value=\"humidity\">Humidity</br>" +
		"		<br><input type=\"submit\" class='submitPref' value=\"Update Preferences\">" +
		"	</form>" +
		"</div>" +
	"<div id=\"dataContainer\" style=\"padding-left:16px\">" +
		getDataFor5Day(data) +
		"</div>" +
		"<div id=\"formContainer\">" +
		"	<form action=\"Compute\" method=\"POST\">" +
		"		<input type=\"Numeric\" name=\"ZipCode\" placeholder=\"Enter zipcode...\" maxlength=\"5\" required><br><br>" +
		"		<input type=\"checkbox\" name=\"CloudCover\" value=\"cloud\">Cloud Coverage</br>" +
		"		<input type=\"checkbox\" name=\"Pressure\" value=\"pressure\">Pressure</br>" +
		"		<input type=\"checkbox\" name=\"Avg\" value=\"avg\">Average Temperature</br>" +
		"		<input type=\"checkbox\" name=\"HighLow\" value=\"highlow\">High/Low</br>" +
		"		<input type=\"checkbox\" name=\"WindSpeed\" value=\"windspeed\">Wind Speed</br>" +
		"		<input type=\"checkbox\" name=\"Rain\" value=\"rain\">Rain</br>" +
		"		<input type=\"checkbox\" name=\"Snow\" value=\"snow\">Snow</br>" +
		"		<input type=\"checkbox\" name=\"Humidity\" value=\"humidity\">Humidity</br>" +
		"		<br><input type=\"submit\" class='submitPref' value=\"Update Preferences\">" +
		"	</form>" +
		"</div>" +
		"<script>" +
		/*
		"document.getElementById(\"formContainer\").style.display = \"none\";" +
		"document.getElementById(\"dataContainer\").style.display = \"none\";" +
		*/
		"function myFunction() {" +
		"	var x = document.getElementById(\"topNav\");" +
		"	if (x.className === \"topnav\") {" +
		"		x.className += \" responsive\";" +
		"	} else {" +
		"		x.className = \"topnav\";" +
		"	}" +
		"}" +
		"function toggleDiv(id){" +
		"	var toHide = document.getElementById(id);" +
		"	if(toHide.style.display == null || toHide.style.display == \"none\"){" +
		"		toHide.style.display = \"block\";" +
		"	}" +
		"	else{" +
		"		toHide.style.display = \"none\";" +
		"	}" +
		"}" +
		"function goHome(){" +
		"	if(document.getElementById(\"formContainer\").style.display == \"block\")" +
		"		toggleDiv(\"formContainer\");" +
		"	if(document.getElementById(\"dataContainer\").style.display == \"block\")" +
		"		toggleDiv(\"dataContainer\");" +
		"}" +
		"function getBoolean(value) {" +
			"switch(value) {" +
				"case \"true\":" +
					"return true;" +
				"default:" +
					"return false;" +
			"}" +
		"}" +
		"function loadCookies() {" +
			"var zipCookie = getCookie(\"zip\");" +
			"var cloudCookie = getCookie(\"cloud\");" +
			"var pressureCookie = getCookie(\"pressure\");" +
			"var avgTempCookie = getCookie(\"avgtemp\");" +
			"var highLowCookie = getCookie(\"highLow\");" +
			"var windCookie = getCookie(\"windSpeed\");" +
			"var rainCookie = getCookie(\"rain\");" +
			"var snowCookie = getCookie(\"snow\");" +
			"var humidityCookie = getCookie(\"humidity\");" +
			"if (zipCookie !== \"\") {" +
				"document.getElementsByName(\"ZipCode\")[1].value = zipCookie;" +
			"}" +
			"if (cloudCookie !== \"\") {" +
				"document.getElementsByName(\"CloudCover\")[1].checked = getBoolean(cloudCookie);" +
			"}" +
			"if (pressureCookie !== \"\") {" +
				"document.getElementsByName(\"Pressure\")[1].checked = getBoolean(pressureCookie);" +
			"}" +
			"if (avgTempCookie !== \"\") {" +
				"document.getElementsByName(\"Avg\")[1].checked = getBoolean(avgTempCookie);" +
			"}" +
			"if (highLowCookie !== \"\") {" +
				"document.getElementsByName(\"HighLow\")[1].checked = getBoolean(highLowCookie);" +
			"}" +
			"if (windCookie !== \"\") {" +
				"document.getElementsByName(\"WindSpeed\")[1].checked = getBoolean(windCookie);" +
			"}" +
			"if (rainCookie !== \"\") {" +
				"document.getElementsByName(\"Rain\")[1].checked = getBoolean(rainCookie);" +
			"}" +
			"if (snowCookie !== \"\") {" +
				"document.getElementsByName(\"Snow\")[1].checked = getBoolean(snowCookie);" +
			"}" +
			"if (humidityCookie !== \"\") {" +
				"document.getElementsByName(\"Humidity\")[1].checked = getBoolean(humidityCookie);" +
			"}" +
		"}" +
		"function getCookie(cname) {" +
			"var name = cname + \"=\";" +
			"var decodedCookie = decodeURIComponent(document.cookie);" +
			"var ca = decodedCookie.split(';');" +
			"for(var i = 0; i <ca.length; i++) {" +
				"var c = ca[i];" +
				"while (c.charAt(0) == ' ') {" +
				  "c = c.substring(1);" +
				"}" +
				"if (c.indexOf(name) == 0) {" +
				  "return c.substring(name.length, c.length);" +
				"}" +
			  "}" +
			  "return \"\";" +
		"}" +
		"</script>" +
		"</body>" + 
		"</html>";
		out.print(htmlServlet);
		out.close();
		
		return;
	} // end doPost method
	
	
	public String getDataFor5Day(WeatherAPICall data) {
		
		int start = 0;
		int noPartial = 0;
		String toReturn = "";
		
		//checks for partial day at beginning of data
		if(!data.time(data.getDateTextAtIndex(start)).equals("12am")){
			toReturn +=
			"<div class=\"periodData\">" +
			"<table>"+
			"<th colspan=\"9\"> Date: " + data.date(data.getDateTextAtIndex(start)) + "</th>" +
			"<tr><td>Time</td>";
			if(!(avgtemp == null)){toReturn += "<td>Avg Temp</td>";}
			if(!(highLow == null)){toReturn += "<td>High/Low</td>";}
			if(!(humidity == null)){toReturn += "<td>Humidity</td>";}
			if(!(cloud == null)){toReturn += "<td>Description</td>";}
			if(!(windSpeed == null)){toReturn += "<td>Wind Speed</td>";}
			if(!(rain == null)){toReturn += "<td>Rain</td>";}
			if(!(snow == null)){toReturn += "<td>Snow</td>";}
			if(!(pressure == null)){toReturn += "<td>Pressure</td>";}
			toReturn += "</tr>";
			while(!data.time(data.getDateTextAtIndex(start)).equals("12am")){
				toReturn += getDataForIndex(start++,data);
			}
			toReturn += "</table></div>";
		}
		if(start == 0){
			noPartial++;
		}
		//4 full days
		for(int x = 0; x < 4 + noPartial; x++){
			toReturn += "<div class=\"periodData\">" + getDataForFullDay(x,data) + "</div>";
		}
		//checks for partial day at end of data
		if(start != 0){
			toReturn +=
			"<div class=\"periodData\">" +
			"<table>"+
			"<th colspan=\"9\"> Date: " + data.date(data.getDateTextAtIndex(32 + start)) + "</th>" +
			"<tr><td>Time</td>";
			if(!(avgtemp == null)){toReturn += "<td>Avg Temp</td>";}
			if(!(highLow == null)){toReturn += "<td>High/Low</td>";}
			if(!(humidity == null)){toReturn += "<td>Humidity</td>";}
			if(!(cloud == null)){toReturn += "<td>Description</td>";}
			if(!(windSpeed == null)){toReturn += "<td>Wind Speed</td>";}
			if(!(rain == null)){toReturn += "<td>Rain</td>";}
			if(!(snow == null)){toReturn += "<td>Snow</td>";}
			if(!(pressure == null)){toReturn += "<td>Pressure</td>";}
			toReturn += "</tr>";
			for(int x = 32 + start; x < 40; x++){
				toReturn += getDataForIndex(x,data);
			}
			toReturn += "</table></div>";
		}
		
		return toReturn;
	}
	
	// return html for 24hr period 
	// Date refers to date on the start of the 24hr period
	public String getDataForFullDay(int day, WeatherAPICall data) {
		
		String toReturn = "";
		int dayStart = day * 8;
		
		while(!data.time(data.getDateTextAtIndex(dayStart)).equals("12am")){
			dayStart++;
		}

		if(!(avgtemp == null)){toReturn += "<td>Avg Temp</td>";}
		if(!(highLow == null)){toReturn += "<td>High/Low</td>";}
		if(!(humidity == null)){toReturn += "<td>Humidity</td>";}
		if(!(cloud == null)){toReturn += "<td>Description</td>";}
		if(!(windSpeed == null)){toReturn += "<td>Wind Speed</td>";}
		if(!(rain == null)){toReturn += "<td>Rain</td>";}
		if(!(snow == null)){toReturn += "<td>Snow</td>";}
		if(!(pressure == null)){toReturn += "<td>Pressure</td>";}
		
		return
		"<table>"+
		"<th colspan=\"9\"> Date: " + data.date(data.getDateTextAtIndex(dayStart)) + "</th>" +
		"<tr><td>Time</td>"+
		toReturn +
		"</tr>"+
		getDataForIndex(dayStart,data)+
		getDataForIndex(dayStart + 1,data)+
		getDataForIndex(dayStart + 2,data)+
		getDataForIndex(dayStart + 3,data)+
		getDataForIndex(dayStart + 4,data)+
		getDataForIndex(dayStart + 5,data)+
		getDataForIndex(dayStart + 6,data)+
		getDataForIndex(dayStart + 7,data)+
		"</table>";
	}

	public String getDataForIndex(int index, WeatherAPICall data) {
		
		String toReturn = "";
		
		if(!(avgtemp == null)){
			toReturn += "<td>" + data.getTempAtIndex(index) + "</td>";
		}
		if(!(highLow == null)){
			toReturn += "<td>" + data.getTempMaxAtIndex(index) + "/" + data.getTempMinAtIndex(index) + "</td>";
		}
		if(!(humidity == null)){
			toReturn += "<td>" + data.getHumidityAtIndex(index) + "</td>";
		}
		if(!(cloud == null)){
			toReturn += "<td>" + data.getDescriptionAtIndex(index) + "</td>";
		}
		if(!(windSpeed == null)){
			toReturn += "<td>" + data.getWindSpeedAtIndex(index) + "</td>";
		}
		if(!(rain == null)){
			toReturn += "<td>" + data.getRainAtIndex(index) + "</td>";
		}
		if(!(snow == null)){
			toReturn += "<td>" + data.getSnowAtIndex(index) + "</td>";
		}
		if(!(pressure == null)){
			toReturn += "<td>" + data.getPressureAtIndex(index) + "</td>";
		}
		if( index == -1 )
		{
			return
			"<tr>"+
			//"<td>" + data.getDateTextAtIndex(index) + "</td>" +
			"<td>Current</td>" +
			toReturn +
			"</tr>";
			
		}
		return
		"<tr>"+
		//"<td>" + data.getDateTextAtIndex(index) + "</td>" +
		"<td>" + data.time(data.getDateTextAtIndex(index)) + "</td>" +
		toReturn +
		"</tr>";
	}
	//Gets data for current weather
	public String getCurrentData(WeatherAPICall data) {
		String toReturn = "";
		
	
			toReturn +=
			"<div class=\"periodData\">" +
			"<table>"+
			"<th colspan=\"9\"> Current </th>" +
			"<tr><td>Time</td>";
			if(!(avgtemp == null)){toReturn += "<td>Avg Temp</td>";}
			if(!(highLow == null)){toReturn += "<td>High/Low</td>";}
			if(!(humidity == null)){toReturn += "<td>Humidity</td>";}
			if(!(cloud == null)){toReturn += "<td>Description</td>";}
			if(!(windSpeed == null)){toReturn += "<td>Wind Speed</td>";}
			if(!(rain == null)){toReturn += "<td>Rain</td>";}
			if(!(snow == null)){toReturn += "<td>Snow</td>";}
			if(!(pressure == null)){toReturn += "<td>Pressure</td>";}
			toReturn += "</tr>";
			
			toReturn += getDataForIndex(-1,data);
			/*
			if(!(avgtemp == null)){
				toReturn += "<td>" + data.getTempAtIndex(-1) + "</td>";
			}
			if(!(highLow == null)){
				toReturn += "<td>" + data.getTempMaxAtIndex(-1) + "/" + data.getTempMinAtIndex(0) + "</td>";
			}
			if(!(humidity == null)){
				toReturn += "<td>" + data.getHumidityAtIndex(-1) + "</td>";
			}
			if(!(cloud == null)){
				toReturn += "<td>" + data.getDescriptionAtIndex(-1) + "</td>";
			}
			if(!(windSpeed == null)){
				toReturn += "<td>" + data.getWindSpeedAtIndex(-1) + "</td>";
			}
			if(!(rain == null)){
				toReturn += "<td>" + data.getRainAtIndex(-1) + "</td>";
			}
			if(!(snow == null)){
				toReturn += "<td>" + data.getSnowAtIndex(-1) + "</td>";
			}
			if(!(pressure == null)){
				toReturn += "<td>" + data.getPressureAtIndex(-1) + "</td>";
			}
			*/
			toReturn += "</table></div>";
		
		return toReturn;
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// HTTP GET requests are forwarded on to the doPost method
		// (i.e., toPost handles both HTTP GET and HTTP POST requests)
		doPost(request, response);
	} // end doGet method

} // end ToUpper class
