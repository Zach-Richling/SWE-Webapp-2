import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import javax.servlet.annotation.WebServlet;
@WebServlet(name = "Compute", urlPatterns = {
    "/Compute"
})
public class Compute extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        final PrintWriter out = response.getWriter();
		String firstNumber = request.getParameter("First_Number");
		String secondNumber = request.getParameter("Second_Number");
		String sum = request.getParameter("Sum_Input");
		String difference = request.getParameter("Difference_Input");
		String product = request.getParameter("Product_Input");
		String htmlServlet = "";
		int col = 2;
		htmlServlet = htmlServlet + 
		"<!DOCTYPE html>\n" +
            "<html lang='en'>\n" +
            "  <head>\n" +
            "    <meta charset='UTF-8'>\n" +
            "    <title>Compute</title>\n" +
            "    <style>\n" +
            "      table, th, td { border:1px solid black;\n" +
            "                      margin:10px;\n" +
            "                      padding:10px 30px 10px 30px; }\n" +
            "      .inline_wide { display: inline-block; width:100%; }\n" +
            "      body { text-align: center; }\n" +
            "      body > * { text-align: left; }\n" +
            "      form { display: inline-block; }\n" +
            "    </style>\n" +
            "  </head>\n" +
            "  <body>\n" +
            "    <form>\n" +
			"		<hr>\n" +
            "      <table>\n" +
            "        <tr>\n" +
            "          <td>First Number </td>\n" +
            "          <td>Second Number</td>\n";
			if (sum != null) {
				if (sum.equals("true")) {
				htmlServlet = htmlServlet + 
				"<td> Sum </td>";
				col++;
				}
			}
			if (difference != null) {
				if (difference.equals("true")) {
				htmlServlet = htmlServlet + 
				"<td> Difference </td>";
				col++;
				}
			}
			if (product != null) {
				if (product.equals("true")) {
				htmlServlet = htmlServlet + 
				"<td> Product </td>";
				col++;
				}
			}
			htmlServlet = htmlServlet +
            "        </tr>\n" +
            "        <tr>\n" +
            "          <td>" + firstNumber +  "</td>\n" +
            "          <td>" + secondNumber + "</td>\n";
			if (sum != null) {
				if (sum.equals("true")) {
				htmlServlet = htmlServlet + 
				"<td>" + (Integer.parseInt(firstNumber) + Integer.parseInt(secondNumber)) + "</td>";
				}
			}
			if (difference != null) {
				if (difference.equals("true")) {
				htmlServlet = htmlServlet + 
				"<td>" + (Integer.parseInt(firstNumber) - Integer.parseInt(secondNumber)) + "</td>";
				}
			}
			if (product != null) {
				if (product.equals("true")) {
				htmlServlet = htmlServlet + 
				"<td>" + (Integer.parseInt(firstNumber) * Integer.parseInt(secondNumber)) + "</td>";
				}
			}
			htmlServlet = htmlServlet +
            "        </tr>\n" +
            "      </table>\n" +
			"		<hr>\n" +
			"		<button type='submit' style='display: block; margin: 0 auto;'\n" +
            "                    formaction='index.html'>\n" +
            "                    Return to Homepage</button>\n" +
            "    </form>\n" +
            "  </body>\n" +
            "</html>\n";
        out.print(htmlServlet);
        out.close();
        return;
    } // end doPost method
    @Override
    public void doGet(HttpServletRequest request,
        HttpServletResponse response)
    throws ServletException, IOException {
        // HTTP GET requests are forwarded on to the doPost method
        // (i.e., toPost handles both HTTP GET and HTTP POST requests)
		doPost(request, response);
    } // end doGet method
	
} // end ToUpper class
