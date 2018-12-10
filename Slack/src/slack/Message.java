/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slack;


public class Message {
    
    private int id;
    private String message;
    private String sender;
    Message(int i, String m, String s)
    {
        id =i;
        message = m;
        sender = s;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public int getId()
    {
        return id;
    }
    
    public String getSender()
    {
        return sender;
    }
}
