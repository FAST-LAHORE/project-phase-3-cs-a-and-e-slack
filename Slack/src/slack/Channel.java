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
    
    Channel(String u, String n)
    {
        user = u;
        name = n;
    }
    
     public ArrayList<String> GetChat() throws SQLException
    {
        ResultSet rs = obj.GetChannelMessages( name, CurrentWorkspace);
        //String message=null;
        while(rs.next())
        {
           Chat.add(rs.getString("sender"));
           Chat.add(rs.getString("message"));
        }
//        if(Chat.isEmpty())
//            Chat.add("This is the very begining of your direct messages with " + person);
        return Chat;    
    }
     
    public void AddMessage(String message) throws SQLException
    {
        obj.AddChannelMessage(message ,user, name, CurrentWorkspace );
    }
    
}
