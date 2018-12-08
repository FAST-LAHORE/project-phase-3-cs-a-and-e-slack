/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slack;

import java.sql.*;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author mac
 */
public class Slack {

    public static Stack<JFrame> stack=new Stack<>();
    public static Connection conn;
    public static String CurrentUser;
    Slack()
    {
        try {
            // TODO code application logic here

            conn=DriverManager.getConnection("jdbc:derby://localhost:1527/SlackDB", "haris","haris");
        } catch (SQLException ex) {
            System.out.println("DB Connection Error");
            return;
        }
    }
    
    ResultSet checkLogin(String n, String p) throws SQLException
    {
        String query="SELECT* FROM LOGIN WHERE EMAIL=? and PASSWORD=?";
        PreparedStatement ps=conn.prepareStatement(query);
        
        ps.setString(1, n);
        ps.setString(2, p);
        
        ResultSet rs=ps.executeQuery();
        
        return rs;

    }
    
    boolean Signup(String n, String e, String p) throws SQLException
    {
        String query="INSERT INTO LOGIN (\"NAME\", EMAIL, PASSWORD)" +"VALUES (?, ?, ?)";
         
        PreparedStatement ps=conn.prepareStatement(query);
        
        ps.setString(1, n);
        ps.setString(2, e);
        ps.setString(3, p);
        
        int tr= ps.executeUpdate();
       if(tr>0)
           return true;
       return false;
    }
    
    boolean createWorkspace(String wname,String creator,String pass) throws SQLException
    {
        String query="INSERT INTO HARIS.WORKSPACE (\"NAME\", CREATOR,PASSWORD)"+"VALUES ( ?, ?,?)";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setString(1, wname);
        ps.setString(2, creator);
        ps.setString(3, pass);
        
        
        int tr= ps.executeUpdate();
         if(tr>0) 
         {
        String q="Select ID FROM HARIS.WORKSPACE WHERE \"NAME\"=? AND CREATOR=? AND PASSWORD=?";
        PreparedStatement pss=conn.prepareStatement(q);
        pss.setString(1, wname);
        pss.setString(2, creator);
        pss.setString(3, pass);
        ResultSet x=pss.executeQuery();
        int id=0;
        if(x.next())
        {
            id=x.getInt("ID");
        }
        
        String abc="INSERT INTO HARIS.MYWORKSAPCES (USERNAME, \"TYPE\", ID)" +"VALUES (?, 1, ?)";
        PreparedStatement in=conn.prepareStatement(abc);
        in.setString(1, creator);
        in.setInt(2, id);
        in.executeUpdate();
        
             return true;
         }
       return false;
    }
    
    ArrayList<String> myWorkspace(String name) throws SQLException
    {
        ArrayList<String> arr=new ArrayList<>();
        String q="Select ID FROM HARIS.MYWORKSAPCES WHERE USERNAME=?";
        PreparedStatement pss=conn.prepareStatement(q);
        pss.setString(1, name);
        ResultSet x=pss.executeQuery();
        
        
        while(x.next())
        {
            String q2="Select NAME FROM HARIS.WORKSPACE WHERE ID=? ";
            PreparedStatement ps2=conn.prepareStatement(q2);
            ps2.setInt(1, x.getInt(1));
            
           ResultSet rs=ps2.executeQuery();
           String wname=null;
           if(rs.next())
             wname=rs.getString(1);
           
           arr.add(wname);
           
           
        }
        
        
        return arr;
        
    }
    
    ResultSet GetWorkspaceDetails(String n) throws SQLException
    {
        String q="SELECT CREATOR, PASSWORD FROM HARIS.WORKSPACE WHERE \"NAME\" = ?";
        PreparedStatement ps=conn.prepareStatement(q);
        ps.setString(1,n);
        ResultSet rs=ps.executeQuery();
        return rs;
    }
    
    ResultSet GetWorkspaceMembers(String n) throws SQLException
    {
        int id=IDbyName(n);
        String q="SELECT USERNAME FROM HARIS.MYWORKSAPCES WHERE ID = ?";
        PreparedStatement ps=conn.prepareStatement(q);
        ps.setInt(1,id);
        ResultSet rs=ps.executeQuery();
        return rs;
    }
    
    ResultSet GetJoinedChannels(String n, String m, String p) throws SQLException
    {
        String q="SELECT CHANNEL, TYPE FROM HARIS.MYCHANNELS WHERE USERNAME =? AND WORKSPACE = ? AND TYPE=?";
        PreparedStatement ps=conn.prepareStatement(q);
        ps.setString(2,n);     
        ps.setString(1,m);
        ps.setString(3,p);
        ResultSet rs=ps.executeQuery();
        return rs;
    }
    
    //ResultSet GetChannelType()
    ResultSet GetDirectMessages(String u, String p) throws SQLException
    {
        String q = "SELECT MESSAGES, SENDER FROM HARIS.DIRECTMESSAGES WHERE SENDER =? AND RECEIVER =? OR SENDER=? AND RECEIVER=?";
        PreparedStatement ps=conn.prepareStatement(q);
        ps.setString(1, u);
        ps.setString(2, p);
        ps.setString(3, p);
        ps.setString(4, u);
        ResultSet rs=ps.executeQuery();
        return rs;
    }

    
    public void AddDirectMessage(String u, String p, String m) throws SQLException
    {
        String q = "INSERT INTO HARIS.DIRECTMESSAGES (MESSAGES, SENDER,RECEIVER )" + "VALUES(?,?,?)";
        PreparedStatement ps=conn.prepareStatement(q);
        ps.setString(1,m);
        ps.setString(2,u);
        ps.setString(3,p);
        ps.executeUpdate();
    }

    public void AddChannelMessage(String m, String u, String c,  String w) throws SQLException
    {
        String q = "INSERT INTO HARIS.CHANNELMESSAGES (MESSAGE, SENDER,CHANNEL,WORKSPACE )" + "VALUES(?,?,?,?)";
        PreparedStatement ps=conn.prepareStatement(q);
        ps.setString(1,m);
        ps.setString(2,u);
        ps.setString(3,c);
        ps.setString(4,w);
        ps.executeUpdate();
    }
    
    ResultSet GetAllPublicChannels(String w) throws SQLException
    {
        String q="SELECT NAME FROM HARIS.CHANNEL WHERE WORKSPACE = ? AND TYPE=?";
        PreparedStatement ps=conn.prepareStatement(q);
        ps.setString(1,w);   
        ps.setString(2,"public");
        ResultSet rs=ps.executeQuery();
        return rs;
    }
    ResultSet GetChannelMessages(String c, String w) throws SQLException
    {
        String q = "SELECT MESSAGE, SENDER FROM HARIS.CHANNELMESSAGES WHERE CHANNEL =? AND WORKSPACE=?";
        PreparedStatement ps=conn.prepareStatement(q);
        ps.setString(1, c);
        ps.setString(2, w);
       
        ResultSet rs=ps.executeQuery();
        return rs;
    }
    
    int IDbyName(String n) throws SQLException
    {
        String q="SELECT ID FROM HARIS.WORKSPACE WHERE \"NAME\" = ?";
        PreparedStatement ps=conn.prepareStatement(q);
        ps.setString(1,n);
        ResultSet rs=ps.executeQuery();
        int id=0;
        if(rs.next())
        {
            id=rs.getInt(1);
        }
        
        return id;
        
    }
    
    
    
    /*NamebyID(String n) throws SQLException
    {
    String tr=null;
    String q="SELECT Name FROM HARIS.WORKSPACE WHERE \"NAME\" = ?";
    PreparedStatement ps=conn.prepareStatement(q);
    ps.setInt(1,n);
    ResultSet rs=ps.executeQuery();
    
    if(rs.next())
    {
    tr=rs.getString(1);
    }
    
    return tr;
    
    }*/
    
    
    
    boolean WSLogin(String n,String e, String p) throws SQLException
    {
        
        int id=IDbyName(n);
        
        String query="SELECT * FROM HARIS.MYWORKSAPCES WHERE USERNAME=? AND PASSWORD=? AND ID=?";
        PreparedStatement ps=conn.prepareStatement(query);
        
        ps.setString(1, e);
        ps.setString(2, p);
        ps.setInt(3, id);
        
        ResultSet tr= ps.executeQuery();
       if(tr.next())
           return true;
       return false;
    }
    
    
    boolean SendInvite(int wid, String wn, String e) throws SQLException
    {
        String query="INSERT INTO INVITES (WSID, WSNAME,EMAIL)" +"VALUES (?, ?, ?)";
         
        PreparedStatement ps=conn.prepareStatement(query);
        
        ps.setInt(1, wid);
        ps.setString(2, wn);
        ps.setString(3, e);
        
        int tr= ps.executeUpdate();
       if(tr>0)
           return true;
       return false;
    }
    
    
    
    public static  Slack obj;
    public static void main(String[] args) {
        
        obj=new Slack();
       
       
        
        
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
        Login jframe=new Login();
        jframe.setTitle("Login To Slack");
        jframe.setLocation(400, 150);
        jframe.setSize(320, 350);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);
            }
        });
    }
    
}
