import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import org.apache.commons.lang3.*;

@WebServlet(name = "Compute", urlPatterns = { "/Compute" })
public class Compute extends HttpServlet {
	

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WeatherAPICall data = new WeatherAPICall(request.getParameter("ZipCode"));
		String zip = request.getParameter("ZipCode");
		String highLow = request.getParameter("HighLow");
		String cloud = request.getParameter("CloudCover");
		String pressure = request.getParameter("Pressure");
		String avgtemp = request.getParameter("Avg");
		String windSpeed = request.getParameter("WindSpeed");
		String rain = request.getParameter("Rain");
		String snow = request.getParameter("Snow");
		String humidity = request.getParameter("Humidity");
		
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
		"	margin-top = 0;" +
		"	height: 100%;" +
		"	width: 100%;" +
		"}" +
		".periodData{" +
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
		"<p> Hello user, </p>" + 
		"<p> Zipcode: " + zip + " Preferences Set: highLow: " + highLow + "; cloud: " + cloud + "; pressure: " + pressure + "; avgtemp: " + avgtemp + "; windSpeed: " + windSpeed + "; rain: " + rain + "; snow: " + snow + "; humidity: " + humidity + "</p>" +
		getDataForFullDay(1,data) +
		getDataForFullDay(2,data) +
		"</body>" + 
		"</html>";
		out.print(htmlServlet);
		out.close();
		return;
	} // end doPost method
	
	// return html for 24hr period 
	// Date refers to date on the start of the 24hr period
	public String getDataForFullDay(int day, WeatherAPICall data) {
		int dayStart = day * 8;
		return
		"<table>"+
		"<th colspan=\"9\"> Date: " + data.date(data.getDateTextAtIndex(dayStart)) + "</th>" +
		"<tr><td>Time</td><td>Avg Temp</td><td>High/Low</td><td>Humidity</td>"+
		"<td>Description</td><td>Wind Speed</td><td>Rain</td><td>Snow</td><td>Pressure</td></tr>"+
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

		return
		"<tr>"+
		"<td>" + data.time(data.getDateTextAtIndex(index)) + "</td>" +
		"<td>" + data.getTempAtIndex(index) + "</td>" +
		"<td>" + data.getTempMaxAtIndex(index) + "/" + data.getTempMinAtIndex(index) + "</td>" +
		"<td>" + data.getHumidityAtIndex(index) + "</td>" +
		"<td>" + data.getDescriptionAtIndex(index) + "</td>" +
		"<td>" + data.getWindSpeedAtIndex(index) + "</td>" +
		"<td>" + data.getRainAtIndex(index) + "</td>" +
		"<td>" + data.getSnowAtIndex(index) + "</td>" +
		"<td>" + data.getPressureAtIndex(index) + "</td>"+
		"</tr>";
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// HTTP GET requests are forwarded on to the doPost method
		// (i.e., toPost handles both HTTP GET and HTTP POST requests)
		doPost(request, response);
	} // end doGet method

} // end ToUpper class
