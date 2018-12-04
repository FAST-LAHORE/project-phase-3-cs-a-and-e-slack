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
 private String email;
 private String password;

 User(String n, String p)
 {
     email=n; 
     password=p;
 }
 
 boolean checkLogin() throws SQLException
    {
     return  obj.checkLogin(email, password);
    }
 
 
    boolean Signup(String n) throws SQLException
    {
       return obj.Signup(n, email, password);
    }
 
    String getName()
    {
        return email;
    }
    
    ArrayList<String> myWorkspace() throws SQLException
    {
       return obj.myWorkspace(email);
    }
    
}
