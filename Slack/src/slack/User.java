/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slack;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static slack.Slack.conn;
import static slack.Slack.obj;

/**
 *
 * @author mac
 */
public class User 
{
 private String name;
 private String email;
 private String password;
 private String phone;
 private String skype;
 private String job;
 private String displayName;
 ArrayList<String> created;
 ArrayList<String> Joined;
 

 User(String n, String p) throws SQLException
 {
     email=n; 
     password=p;
     Joined=obj.myWorkspace(email);
     //name lelo database se
 }
 
 boolean checkLogin() throws SQLException
    {
     ResultSet rs=  obj.checkLogin(email, password);
     if(rs.next())
     {
         name=rs.getString("Name");
         return true;
     }
     else
         return false;
    }
 
 
    boolean Signup(String n) throws SQLException
    {
       name=n;
       return obj.Signup(name, email, password);
    }
 
    String getName()
    {
        return email;
    }
    
    ArrayList<String> myWorkspace() throws SQLException
    {
        Joined=obj.myWorkspace(email);
       return Joined;
    }
    
    void addCreatedWS(String obj)
    {
        created.add(obj);
    }
    
    void addJoinedWS(String obj)
    {
        Joined.add(obj);
    }
    public String getPhone(){
        return phone;
    }
    public String getSkype(){
        return skype;
    }
    public String getdisplayName(){
        return displayName;
    }
    public String getJob(){
        return job;
    }
}
