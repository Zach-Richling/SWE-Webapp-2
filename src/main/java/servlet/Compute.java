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
		response.setContentType("text/html");
		final PrintWriter out = response.getWriter();
		Cookie[] cookieArray;
		if (request.getCookies() == null) {
			// Add cookies here if the user does not have any
			Cookie userCookie = new Cookie("first_name", request.getParameter("First_Name"));
			response.addCookie(userCookie);
		}
		Cookie[] cookieArray = request.getCookies();
		String htmlServlet = "";
		htmlServlet = htmlServlet + "<!DOCTYPE html>" +
		"<html>" + 
		"<head>" +
		"<meta charset='UTF-8'>" + 
		"<title>Test Page</title>" +
		"</head>" + 
		"<body>" + 
		"<p> Hello, " + cookieArray[0].getValue() + "<p/>" + 
		"</body>" + 
		"</html>";
		out.print(htmlServlet);
		out.close();
		return;
	} // end doPost method

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// HTTP GET requests are forwarded on to the doPost method
		// (i.e., toPost handles both HTTP GET and HTTP POST requests)
		doPost(request, response);
	} // end doGet method

} // end ToUpper class
