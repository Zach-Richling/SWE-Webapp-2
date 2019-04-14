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
		
		String date = "";
		String datetext = "";
		String temp = "";
		String mintemp = "";
		String maxtemp = "";
		String humidity = "";
		String description = "";
		String windspeed = "";
		String rain = "";
		String snow = "";
		String pressure = "";
		
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
		htmlServlet = htmlServlet + 
		"<!DOCTYPE html>" +
		"<html>" + 
		"<head>" +
		"<meta charset='UTF-8'>" + 
		"<title>Test Page</title>" +
		"</head>" + 
		"<body>" + 
		"<p> Hello user, </p>" + 
		"<p> Zipcode: " + zip + "<p/>" + 
		"<p> Date Text: " + datetext + "<p/>" +
		"<p> Temp: " + temp + "<p/>" +
		"<p> Min Temp: " + mintemp + "<p/>" +
		"<p> Max Temp: " + maxtemp + "<p/>" +
		"<p> Humidity: " + humidity + "<p/>" +
		"<p> Description: " + description + "<p/>" +
		"<p> Wind Speed: " + windspeed + "<p/>" +
		"<p> Rain: " + rain + "<p/>" +
		"<p> Snow: " + snow + "<p/>" +
		"<p> Pressure: " + pressure + "<p/>" +
		"</body>" + 
		"</html>";
		out.print(htmlServlet);
		out.close();
		return;
	} // end doPost method
	
	public void setDataForIndex(int index, WeatherAPICall data) {
		date = data.getDateAtIndex(index);
		datetext = data.getDateTextAtIndex(index);
		temp = data.getTempAtIndex(index);
		mintemp = data.getTempMinAtIndex(index);
		maxtemp = data.getTempMaxAtIndex(index);
		humidity = data.getHumidityAtIndex(index);
		description = data.getDescriptionAtIndex(index);
		windspeed = data.getWindSpeedAtIndex(index);
		rain = data.getRainAtIndex(index);
		snow = data.getSnowAtIndex(index);
		pressure = data.getPressureAtIndex(index);
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// HTTP GET requests are forwarded on to the doPost method
		// (i.e., toPost handles both HTTP GET and HTTP POST requests)
		doPost(request, response);
	} // end doGet method

} // end ToUpper class
