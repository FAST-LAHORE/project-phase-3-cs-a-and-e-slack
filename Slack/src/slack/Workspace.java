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
    
    Workspace(String n, String c,String p) //creates new workspace
    {
        name=n;
        password=p;
        creator=c;
    }
    
    /*Workspace(String n) //load data from DB
    {
    name=n;
    
    String query="SELECT ";
    }*/
    
    boolean createWorkspace() throws SQLException
    {
        return obj.createWorkspace(name, creator, password);
    }
    
    
    
    
}
