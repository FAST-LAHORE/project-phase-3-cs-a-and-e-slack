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
    private int id;
    private ArrayList<Message> Chat = new ArrayList<>();
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
        Chat.clear();
        while(rs.next())
        {
           Chat.add(new Message(rs.getInt("ID"),rs.getString("messages"),rs.getString("sender")));
        }
        return Chat; 
        
    }
     
    public void AddMessage(String message) throws SQLException
    {
        obj.AddChannelMessage(message ,user, id);
    }
    
    public void Addfile() throws SQLException
    {
        
    }
}
