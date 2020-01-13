import javax.servlet.*;
import javax.servlet.http.*;
import com.mongodb.*;
import com.mongodb.client.*;
import java.util.*;
public class startupServlet extends HttpServlet
{

public void init()
{
System.out.println("------------------------loadind data Structure---------");
mongoEg eg=new mongoEg("studentdb","students");
List<DBObject>list=eg.getAllRecords();
List<Student>list1=new LinkedList<>();
for(int x=0;x<list.size();x++)
{
Student s=new Student();
s.setId((list.get(x).get("_id")).toString());
s.setName((list.get(x).get("name")).toString());
s.setNumber((list.get(x).get("number")).toString());
list1.add(s);
}

ServletContext sc=getServletContext();
sc.setAttribute("student",list1);

System.out.println("-------------------completed--------------");
}
}//class
