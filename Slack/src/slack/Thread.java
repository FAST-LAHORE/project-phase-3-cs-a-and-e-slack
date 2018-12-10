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
 * @author saira
 */
public class Thread {
 
   // String user;
    Message message;
    //int messageid;
    String ChatType;
    ArrayList<Message> Replies = new ArrayList<>();
    Thread(Message m, String c)
    {
     //user = u;
        message = m;
        ChatType = c;
    }
    
    ArrayList<Message> GetThreadDetails() throws SQLException
    {
        ResultSet rs = obj.GetThread(message);
        Replies.clear();
        while(rs.next())
        {
            Replies.add(new Message(message.getId(),rs.getString("REPLY"), rs.getString("SENDER")));
           
        }
        return Replies;
    }
    
    public void AddReply(String u, String m) throws SQLException
    {
        obj.AddReply(message, u, m);
    }
}
