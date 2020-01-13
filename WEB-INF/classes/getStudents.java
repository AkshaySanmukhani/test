import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;
import java.util.*;
public class getStudents extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{

doPost(request,response);
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
List<String>list;
ServletContext servletContext=(ServletContext)getServletContext();
list=(List)servletContext.getAttribute("student");
response.setContentType("application/json");
response.getWriter().print(new Gson().toJson(list));
}catch(Exception e)
{
e.printStackTrace();
}
}
}
