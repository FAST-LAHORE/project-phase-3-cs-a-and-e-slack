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

/**
 *
 * @author mac
 */
public class DirectMessage extends Chats
{
    private String user;
    private String person;
    private ArrayList<String> Chat = new ArrayList<>();
    DirectMessage(String u, String p)
    {
        user = u;
        person = p;
    }
    
    public ArrayList<String> GetChat() throws SQLException
    {
        ResultSet rs = obj.GetDirectMessages(user, person);
        //String message=null;
        while(rs.next())
        {
           Chat.add(rs.getString("sender"));
           Chat.add(rs.getString("messages"));
        }
        if(Chat.isEmpty())
            Chat.add("This is the very begining of your direct messages with " + person);
        return Chat;    
    }
    
    public void AddMessage(String message) throws SQLException
    {
        obj.AddDirectMessage(user, person, message);
    }
    
    public void Addfile() throws SQLException
    {
        
    }
}
