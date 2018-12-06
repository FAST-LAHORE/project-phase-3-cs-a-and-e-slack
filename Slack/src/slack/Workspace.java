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
public class Workspace 
{
    private String name;
    private String password;
    private String creator;
    ArrayList<String> users=new ArrayList<>();
    ArrayList<Chats> chats;
    
    Workspace(String n, String c,String p) throws SQLException //creates new workspace
    {
        name=n;
        password=p;
        creator=c;
        GetMembers();
       
    }
    
    Workspace(String n) throws SQLException
    {
        name =n;
        ResultSet rs = obj.GetWorkspaceDetails(n);
        if(rs.next())
        {
            creator = rs.getString("creator");
            password = rs.getString("password");
        }
        GetMembers();
    }
    void GetMembers() throws SQLException
    {
        ResultSet rs = obj.GetWorkspaceMembers(name);
        while(rs.next())
        {
            users.add(rs.getString("USERNAME"));
        }
       
    }
    boolean createWorkspace() throws SQLException
    {
        return obj.createWorkspace(name, creator, password);
    }
    
    void addUser(String a)
    {
        users.add(a);
    }
    
    ArrayList<String> getUsers() throws SQLException
    {
        if(users!=null)
            users.clear();
        GetMembers();
        return users;
    }
    
    
    
}
