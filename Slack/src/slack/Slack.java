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
    boolean update(String fn,String dn,String j,String p,String s,String e) throws SQLException{
        String query = "UPDATE LOGIN SET \"NAME\" = ?, PHONE = ?,DISPLAY_NAME = ?,JOB = ?,SKYPE = ? WHERE EMAIL = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, fn);
        ps.setString(2, p);
        ps.setString(3, dn);
        ps.setString(4, j);
        ps.setString(5, s);
        ps.setString(6, e);
        int r = ps.executeUpdate();
        if(r > 0){
            return true;
        }else{
            return false;
        }
    }
    boolean updatePassword(String np,String e) throws SQLException{
        String query = "UPDATE LOGIN SET PASSWORD = ? WHERE EMAIL = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1,np);
        ps.setString(2, e);
        int r = ps.executeUpdate();
        if(r > 0){
            return true;
        }else{
            return false;
        }
    }
    boolean createWorkspace(String wname,String creator,String pass,String acode) throws SQLException
    {
       
        String query="INSERT INTO HARIS.WORKSPACE (\"NAME\", CREATOR,PASSWORD,ACCESCODE)"+"VALUES ( ?, ?,?,?)";
        PreparedStatement ps=conn.prepareStatement(query);
        ps.setString(1, wname);
        ps.setString(2, creator);
        ps.setString(3, pass);
        ps.setString(4, acode);
        
        
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
        
        String abc="INSERT INTO HARIS.MYWORKSAPCES (USERNAME, \"TYPE\", PASSWORD, WORKSPACEID)" +"VALUES (?, 1,?,?)";
        PreparedStatement in=conn.prepareStatement(abc);
        in.setString(1, creator);
       // 
        in.setString(2, pass);
         in.setInt(3, id);
        in.executeUpdate();
       
             return true;
         }
       return false;
    }

    ResultSet getinvites(String e) throws SQLException
    {
        String q="Select WSNAME FROM HARIS.INVITES WHERE EMAIL=?";
        PreparedStatement pss=conn.prepareStatement(q);
        pss.setString(1, e);
        ResultSet x=pss.executeQuery();
        return x;
    }

    ArrayList<String> myWorkspace(String name) throws SQLException
    {
        ArrayList<String> arr=new ArrayList<>();
        String q="Select WORKSPACEID FROM HARIS.MYWORKSAPCES WHERE USERNAME=?";
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
        String q="SELECT CREATOR, PASSWORD, ID FROM HARIS.WORKSPACE WHERE \"NAME\" = ?";
        PreparedStatement ps=conn.prepareStatement(q);
        ps.setString(1,n);
        ResultSet rs=ps.executeQuery();
        return rs;
    }
    
    ResultSet GetWorkspaceMembers(int n) throws SQLException
    {
       // int id=IDbyName(n);
        String q="SELECT USERNAME FROM HARIS.MYWORKSAPCES WHERE WORKSPACEID = ?";
        PreparedStatement ps=conn.prepareStatement(q);
        ps.setInt(1,n);
        ResultSet rs=ps.executeQuery();
        return rs;
    }
    
    ResultSet GetJoinedChannels(int n, String m, String p) throws SQLException
    {
        String q="SELECT CHANNELNAME FROM HARIS.CHANNELS JOIN HARIS.MYCHANNELS ON CHANNELS.ID = MYCHANNELS.CHANNELID WHERE MYCHANNELS.USERNAME = ? AND CHANNELS.WORKSPACEID=? AND CHANNELS.CHANNELTYPE=?";
        PreparedStatement ps=conn.prepareStatement(q);
        ps.setInt(2,n);     
        ps.setString(1,m);
        ps.setString(3,p);
        ResultSet rs=ps.executeQuery();
        return rs;
    }
    
    ResultSet GetThread(Message m) throws SQLException
    {
         String q = "SELECT REPLY, SENDER FROM HARIS.THREAD WHERE MESSAGEID = ?";
         PreparedStatement ps=conn.prepareStatement(q);
         ps.setInt(1, m.getId());
         ResultSet rs=ps.executeQuery();
        return rs;
    }
    
    public void AddReply(Message M, String u, String m) throws SQLException
    {
        String q = "INSERT INTO HARIS.THREAD (MESSAGEID, REPLY, SENDER)" + "VALUES(?,?,?)";
        PreparedStatement ps=conn.prepareStatement(q);
        ps.setInt(1, M.getId());
        ps.setString(2, m);
        ps.setString(3, u);
        ps.executeUpdate();
        
    }
    ResultSet GetDirectMessages(String u, String p) throws SQLException
    {
        String q = "SELECT ID, MESSAGES, SENDER FROM HARIS.DIRECTMESSAGES WHERE SENDER =? AND RECEIVER =? OR SENDER=? AND RECEIVER=?";
        PreparedStatement ps=conn.prepareStatement(q);
        ps.setString(1, u);
        ps.setString(2, p);
        ps.setString(3, p);
        ps.setString(4, u);
        ResultSet rs=ps.executeQuery();
        return rs;
    }

    ResultSet GetSender(int u) throws SQLException
    {
        String q = "SELECT FROM HARIS.DIRECTMESSAGES WHERE MESSAGEID =?";
        PreparedStatement ps=conn.prepareStatement(q);
        ps.setInt(1, u);
        ResultSet rs=ps.executeQuery();
        return rs;
    }
    
    ResultSet GetMessageId(String user,  String person, String message) throws SQLException
    {
        String q = "SELECT MESSAGES, SENDER FROM HARIS.DIRECTMESSAGES WHERE SENDER =? AND RECEIVER =? AND MESSAGE = ?";
        PreparedStatement ps=conn.prepareStatement(q);
        ps.setString(1, user);
        ps.setString(2, person);
        ps.setString(3, message);
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

    public void AddChannelMessage(String m, String u, int c) throws SQLException
    {
        String q = "INSERT INTO HARIS.CHANNELMESSAGES (MESSAGES, SENDER,CHANNELID )" + "VALUES(?,?,?)";
        PreparedStatement ps=conn.prepareStatement(q);
        ps.setString(1,m);
        ps.setString(2,u);
        ps.setInt(3,c);
        ps.executeUpdate();
    }
    
    public int GetWorkspaceId(String name) throws SQLException
    {
        String q = "Select ID FROM WORKSPACE WHERE NAME =?";
        PreparedStatement ps=conn.prepareStatement(q);
       // ps.setInt(1,nam);
        ps.setString(1,name);
        ResultSet rs=ps.executeQuery();
        int id =0;
        while(rs.next())
        {
            id = rs.getInt("ID");
        }
        return id;
    }
    public int GetChannelId(String name, int w) throws SQLException
    {
        String q="SELECT ID FROM HARIS.CHANNELS WHERE WORKSPACEID = ? AND CHANNELNAME=?";
        PreparedStatement ps=conn.prepareStatement(q);
        ps.setInt(1,w);
        ps.setString(2,name);
        ResultSet rs=ps.executeQuery();
        int id =0;
        while(rs.next())
        {
            id = rs.getInt("ID");
        }
        return id;
    }
    ResultSet GetAllPublicChannels(int w) throws SQLException
    {
        String q="SELECT CHANNELNAME FROM HARIS.CHANNELS WHERE WORKSPACEID = ? AND CHANNELTYPE=?";
        PreparedStatement ps=conn.prepareStatement(q);
        ps.setInt(1,w);   
        ps.setString(2,"public");
        ResultSet rs=ps.executeQuery();
        return rs;
    }
    ResultSet GetChannelMessages(String c, int w) throws SQLException
    {
        String r = "SELECT ID FROM HARIS.CHANNELS WHERE CHANNELNAME =? AND WORKSPACEID=?";
       
        PreparedStatement ps=conn.prepareStatement(r);
        ps.setString(1, c);
        ps.setInt(2, w);
       ResultSet rs=ps.executeQuery();
       int id = 0;
       while(rs.next())
        {
            id = rs.getInt("ID");
        }
        String q = "SELECT ID, MESSAGES, SENDER FROM HARIS.CHANNELMESSAGES WHERE CHANNELID =?";
        PreparedStatement ps1=conn.prepareStatement(q);
        ps1.setInt(1, id);
       ResultSet rs1=ps1.executeQuery();
        
        return rs1;
    }
    
    ResultSet getuserfiles(String e, String w) throws SQLException
    {
        String q = "SELECT FILEPATH FROM HARIS.USERFILES WHERE EMAIL =? AND WORKSPACENAME=?";
        PreparedStatement ps=conn.prepareStatement(q);
        ps.setString(1, e);
        ps.setString(2, w);
       
        ResultSet rs=ps.executeQuery();
        return rs;
    }
    public boolean addfile(String e,String p,String w) throws SQLException
    {
        String q = "INSERT INTO HARIS.USERFILES (EMAIL,FILEPATH,WORKSPACENAME )" + "VALUES(?,?,?)";
        PreparedStatement ps=conn.prepareStatement(q);
        
        ps.setString(1, e);
        ps.setString(2, p);
        ps.setString(3, w);
        int tr= ps.executeUpdate();
        if(tr>0)
            return true;
        return false;        
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

    boolean addusertoworkspace(String e, String w,String p) throws SQLException
    {
        String q="SELECT ID FROM HARIS.WORKSPACES WHERE \"NAME\" = ?";
        PreparedStatement ps=conn.prepareStatement(q);
        ps.setString(1,w);
        ResultSet rs=ps.executeQuery();
        
        int id=0;
        if(rs.next())
        {
            id=rs.getInt("ID");
        }
        
        String abc="INSERT INTO HARIS.MYWORKSAPCES (USERNAME, \"TYPE\", ID,PASSWORD)" +"VALUES (?, 0, ?,?)";
        PreparedStatement in=conn.prepareStatement(abc);
        in.setString(1,e);
        in.setInt(2, id);
        in.setString(3,p);
        
        in.executeUpdate();
        return true;
    }
    
    
    public ArrayList getNotif(String name) throws SQLException{
        String q = "SELECT DESCRIPTION FROM NOTIFICATIONS WHERE WORKSPACE ='" + name+"'";
        PreparedStatement ps = conn.prepareStatement(q);
        //ps.setString(1, name);
        System.out.println(q);
        ArrayList a = new ArrayList();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            a.add(rs.getString("DESCRIPTION"));
        }
        for(int i = 0;i<a.size();i++){
            System.out.println(a.get(i));
        }
        return a;
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
        
        String query="SELECT * FROM HARIS.MYWORKSAPCES WHERE USERNAME=? AND PASSWORD=? AND WORKSPACEID=?";
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
    
    public boolean addtomychannels(String c,String w,String e, int t) throws SQLException
    {
        String query="INSERT INTO HARIS.MYCHANNELS (CHANNEL, WORKSPACE,USERNAME,TYPE)" +"VALUES (?, ?, ?,?)";
        PreparedStatement ps=conn.prepareStatement(query);
        
        ps.setString(1, c);
        ps.setString(2, w);
        ps.setString(3, e);
        ps.setInt(4,t);
        
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
