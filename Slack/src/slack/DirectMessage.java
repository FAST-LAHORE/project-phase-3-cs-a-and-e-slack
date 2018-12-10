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
    private ArrayList<Message> Chat = new ArrayList<>();
    //private int id;
    DirectMessage(String u, String p)
    {
        user = u;
        person = p;
    }
    
    DirectMessage(int id)
    {
        
    }
    public ArrayList<Message> GetChat() throws SQLException
    {
        ResultSet rs = obj.GetDirectMessages(user, person);
         Chat.clear();
        while(rs.next())
        {
           Chat.add(new Message(rs.getInt("ID"),rs.getString("messages"),rs.getString("sender"))); 
        }
        
        if(Chat.isEmpty())
            Chat.add(new Message(0,("This is the very begining of your direct messages with " + person), user));
        return Chat;    
    }
    
    public void AddMessage(String message) throws SQLException
    {
        obj.AddDirectMessage(user, person, message);
        //will return messageid
    }
    
    public int GetMessageId(String user,  String person, String message)
    {
       // ResultSet rs = obj.GetMessageId(user, person, message );
        return 1;
    }
    public void Addfile() throws SQLException
    {
        
    }
    
    public String GetSender(int id) throws SQLException
    {
       ResultSet rs = obj.GetSender(id);
       String a = null;
       if(rs.next())
          a  = rs.getString("SENDER");
       return a;
    }
}
