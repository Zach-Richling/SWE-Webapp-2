import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http;
import org.apache.commons.lang3;

import org.json.*;

@WebServlet(name = "Compute", urlPatterns = { "/Compute" })
public class Compute extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		final PrintWriter out = response.getWriter();
		if (request.getCookies().isEmpty()) {
			// Add cookies here if the user does not have any
			Cookie userCookie = new Cookie("name", "value");
			response.addCookie(userCookie);
		} else {
			// If the user does have a cookie do something with them.
			Cookie[] cookieArray = request.getCookies();
		}
		String htmlServlet = "";
		int col = 2;
		htmlServlet = htmlServlet + "<!DOCTYPE html> <html> <head> <meta charset='UTF-8'> <title>Test Page</title> </head> <body> </body> </html>";
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
