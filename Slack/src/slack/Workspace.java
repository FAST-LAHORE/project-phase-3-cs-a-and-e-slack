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
    ArrayList<User> users;
    ArrayList<Chats> chats;
    
    Workspace(String n, String c,String p) //creates new workspace
    {
        name=n;
        password=p;
        creator=c;
    }
    
    
    boolean createWorkspace() throws SQLException
    {
        return obj.createWorkspace(name, creator, password);
    }
    
    void addUser(User a)
    {
        users.add(a);
    }
    
    
    
}
