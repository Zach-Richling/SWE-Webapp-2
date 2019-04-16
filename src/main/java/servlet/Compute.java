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
		WeatherAPICall data = new WeatherAPICall(request.getParameter("ZipCode"));
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
		String checkValue = "didnt get there";
		/* Cookie[] cookieArray = request.getCookies();
		if (cookieArray == null) {
			// Add cookies here if the user does not have any
			response.addCookie(new Cookie("first_name", request.getParameter("First_Name")));
			checkValue = "got here";
		}
		checkValue = cookieArray[0].getValue();
		*/
		checkValue = data.getWindSpeedAtIndex(1);
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
		"	background-image: linear-gradient(to bottom right, gray, white);" +
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
		"td {" +
		"	text-align: center;" +
		"}" +
		".periodData{" +
		"	display: block;" +
		"	overflow: auto;" +
		"	background-image: linear-gradient(to bottom right, #99c0ff, #fff38e);" +
		"	width: 90%;" +
		"	height: 115px;" +
		"	margin: 25px 5%;" +
		"	border-radius: 15px;" +
		"}" +
		".HighLowCurrent {" +
		"	padding-top: 1px;" +
		"	margin-left: 20px;" +
		"	margin-right: 80%;" +
		"}" +
		"</style>" +
		"</head>" + 
		"<body>" + 
		"<div class=\"topnav\" id=\"topNav\">" +
		"<a class=\"active\" href=\"index.html\"><b>Home</b></a>" +
		"<a href=\"javascript:void(0);\" class=\"icon\" onclick=\"myFunction()\">" +
		"	<i class=\"fa fa-bars\"></i>" +
		"</a>" +
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
		"document.getElementById(\"formContainer\").style.display = \"none\";" +
		"//document.getElementById(\"dataContainer\").style.display = \"none\";" +
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
		"</script>" +
		"</body>" + 
		"</html>";
		out.print(htmlServlet);
		out.close();
		
		return;
	} // end doPost method
	
	public String getDataFor5Day(WeatherAPICall data) {
		
		String toReturn = "";
		
		for(int x = 0; x < 3; x++){
			toReturn += "<div class=\"periodData\">" + getDataForFullDay(x,data) + "</div>";
		}
		
		return toReturn;
	}
	
	// return html for 24hr period 
	// Date refers to date on the start of the 24hr period
	public String getDataForFullDay(int day, WeatherAPICall data) {
		
		int dayStart = day * 8;
		
		while(!data.time(data.getDateTextAtIndex(dayStart)).equals("12am"))
			dayStart++;

		String toReturn = "";

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
		getDataForIndex(dayStart + 1,data)+
		getDataForIndex(dayStart + 2,data)+
		getDataForIndex(dayStart + 3,data)+
		getDataForIndex(dayStart + 4,data)+
		getDataForIndex(dayStart + 5,data)+
		getDataForIndex(dayStart + 6,data)+
		getDataForIndex(dayStart + 7,data)+
		getDataForIndex(dayStart + 8,data)+
		"</table>";
	}
	
	// 
	public String getDataForIndex(int index, WeatherAPICall data) {
		
		String toReturn = "";
		if(data.getDescriptionAtIndex(index) == null){
			toReturn += "N/A";
			return toReturn;
		}
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
		
		return
		"<tr>"+
		"<td>" + data.time(data.getDateTextAtIndex(index)) + "</td>" +
		toReturn +
		"</tr>";
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// HTTP GET requests are forwarded on to the doPost method
		// (i.e., toPost handles both HTTP GET and HTTP POST requests)
		doPost(request, response);
	} // end doGet method

} // end ToUpper class
