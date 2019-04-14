import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import org.apache.commons.lang3.*;

@WebServlet(name = "Compute", urlPatterns = { "/Compute" })
public class Compute extends HttpServlet {
	
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
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WeatherAPICall data = new WeatherAPICall(request.getParameter("ZipCode"));
		String zip = request.getParameter("ZipCode");
		
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
		"<meta charset='UTF-8'>" + 
		"<title>Test Page</title>" +
		"</head>" + 
		"<body>" + 
		"<p> Hello user, </p>" + 
		"<p> Zipcode: " + zip + "<p/>" + 
		getDataForFullDay(0,data) +
		"</body>" + 
		"</html>";
		out.print(htmlServlet);
		out.close();
		return;
	} // end doPost method
	
	public String getDataForFullDay(int day, WeatherAPICall data) {
		return
		setDataForIndex(1,data)+
		setDataForIndex(2,data)+
		setDataForIndex(3,data)+
		setDataForIndex(4,data)+
		setDataForIndex(5,data)+
		setDataForIndex(6,data)+
		setDataForIndex(7,data)+
		setDataForIndex(8,data);
	}
	public String setDataForIndex(int index, WeatherAPICall data) {
		
		return
		"<p> Date Text: " + data.getDateTextAtIndex(index) + "<p/>" +
		"<p> Avg Temp: " + data.getTempAtIndex(index) + "<p/>" +
		"<p> Min Temp: " + data.getTempMinAtIndex(index) + "<p/>" +
		"<p> Max Temp: " + data.getTempMaxAtIndex(index) + "<p/>" +
		"<p> Humidity: " + data.getHumidityAtIndex(index) + "<p/>" +
		"<p> Description: " + data.getDescriptionAtIndex(index) + "<p/>" +
		"<p> Wind Speed: " + data.getWindSpeedAtIndex(index) + "<p/>" +
		"<p> Rain: " + data.getRainAtIndex(index) + "<p/>" +
		"<p> Snow: " + data.getSnowAtIndex(index) + "<p/>" +
		"<p> Pressure: " + data.getPressureAtIndex(index) + "<p/>";
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// HTTP GET requests are forwarded on to the doPost method
		// (i.e., toPost handles both HTTP GET and HTTP POST requests)
		doPost(request, response);
	} // end doGet method

} // end ToUpper class
