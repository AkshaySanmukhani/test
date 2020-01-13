import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;
import java.io.*;
import java.util.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.*;
import com.mongodb.*;
import com.mongodb.client.*;

public class studentManagement extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
sendResponse(request,response);
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
//Thread.sleep(2000);

String s="requestArrived";
System.out.println(s);
StringBuilder sb=new StringBuilder();
BufferedReader br=request.getReader();
String str;
while((str=br.readLine())!=null)
{
sb.append(str);
}
System.out.println("json" + sb.toString() +" json");
JSONParser parser =new JSONParser();
JSONObject json=(JSONObject) parser.parse(sb.toString());
System.out.println(json);
String action=(String)json.get("action");
if(action.equalsIgnoreCase("add"))add(json);
else if(action.equalsIgnoreCase("edit"))edit(json);
else if(action.equalsIgnoreCase("delete"))delete(json);
else System.out.println("krna baki h" +action);
sendResponse(request,response);

}catch(Exception e)
{
e.printStackTrace();
}

}
public void sendResponse(HttpServletRequest request,HttpServletResponse response)
{
try
{
response.setContentType("application/json");
mongoEg eg=new mongoEg("studentdb","students");
List<DBObject>list=eg.getAllRecords();
List<Student>list1=new LinkedList<>();
for(int x=0;x<list.size();x++)
{
Student s=new Student();
s.setId((list.get(x).get("_id")).toString());
s.setName((list.get(x).get("name")).toString());
s.setNumber((list.get(x).get("number")).toString());
System.out.println(s.getId()+" id get ki");
System.out.println(s.getName()+" name get kia");
System.out.println(s.getNumber()+" number get kia");

list1.add(s);
}
response.getWriter().print(new Gson().toJson(list1));
}catch(Exception e)
{
e.printStackTrace();
}
}

public void add(JSONObject json)
{
System.out.println("ADD me aaya");
System.out.println(json);
mongoEg eg=new mongoEg("studentdb","students");
String[] ss=new String[((json.size()*2)-2)];
Set<String> keys = json.keySet();
int x=0;
for(String s:keys)
{
if(s.equalsIgnoreCase("action"))
{
continue;
}
ss[x]=s;
ss[x+1]=(String)json.get(s);

x++;
x++;
}
for(int i=0;i<ss.length;i++)System.out.print(ss[i] +" ");
eg.add(ss);
//sendResponse();
}

public void edit(JSONObject json)
{
System.out.println("edit me aaya");
System.out.println(json);

mongoEg eg=new mongoEg("studentdb","students");
String[] ss=new String[((json.size()*2)-4)];
String key="";
Set<String> keys = json.keySet();
int x=0;
for(String s:keys)
{
if(s.equalsIgnoreCase("action"))
{
continue;
}
if(s.equalsIgnoreCase("id"))
{
key=(String)json.get(s);
continue;
}
ss[x]=s;
ss[x+1]=(String)json.get(s);

x++;
x++;
}
for(int i=0;i<ss.length;i++)System.out.print(ss[i] +" ");
eg.update(key,ss);

}

public void delete(JSONObject json)
{
System.out.println("delete me aaya");
System.out.println(json);
String id=(String)json.get("id");
mongoEg eg=new mongoEg("studentdb","students");
eg.deleteRecord(id,true);
}


}
