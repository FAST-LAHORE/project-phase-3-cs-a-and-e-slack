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

    
    public static Connection conn;
    
    Slack()
    {
        try {
            // TODO code application logic here

            conn=DriverManager.getConnection("jdbc:derby://localhost:1527/SlackDB", "Haris","12345");
        } catch (SQLException ex) {
            System.out.println("DB Connection Error");
            return;
        }
    }
    
    boolean checkLogin(String n, String p) throws SQLException
    {
        String query="SELECT* FROM LOGIN WHERE EMAIL=? and PASSWORD=?";
        PreparedStatement ps=conn.prepareStatement(query);
        
        ps.setString(1, n);
        ps.setString(2, p);
        
        ResultSet rs=ps.executeQuery();
        
        if(rs.next())
            return true;
        else
            return false;
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
        Login jframe=new Login(obj);
        jframe.setTitle("Login To Slack");
        jframe.setLocation(400, 150);
        jframe.setSize(320, 350);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);    
        
            }
        });
    }
    
}
