/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static slack.Slack.obj;
import static slack.MainMenu.CurrentWorkspace;
/**
 *
 * @author mac
 */
public class Channel extends Chats
{
    private String name;
    private String type;
    private String user;
    private ArrayList<String> Chat = new ArrayList<>();
    
    

    private int id;
    private ArrayList<Message> Chats = new ArrayList<>();
    
    Channel(String u, String n, int Wid) throws SQLException
    {
        user = u;
        name = n;
        GetChannelId();
    }
    
    public void GetChannelId() throws SQLException
    {
        Workspace ws = new Workspace(CurrentWorkspace);
        id = obj.GetChannelId(name, ws.getId());
    }
     public ArrayList<Message> GetChat() throws SQLException
    {
        Workspace ws = new Workspace(CurrentWorkspace);
        ResultSet rs = obj.GetChannelMessages( name, ws.getId());
        //String message=null;
        Chats.clear();
        while(rs.next())
        {
           Chats.add(new Message(rs.getInt("ID"),rs.getString("messages"),rs.getString("sender")));
        }
        return Chats; 
        
    }
     
     public String gettype() throws SQLException
     {
        Workspace ws = new Workspace(CurrentWorkspace);
        type = obj.getchanneltype(name, ws.getId());
        return type;
     }
     
     public String getname()
     {
         return name;
     }
     
    public void AddMessage(String message) throws SQLException
    {
        obj.AddChannelMessage(message ,user, id);
    }
    
    public String getcreator()
    {
        return user;
    }
    
    public boolean setdetails(String a, String b) throws SQLException
    {
        
        return obj.setchanneldetails(id,name,a,b);
    }
    
    
    
    
}
