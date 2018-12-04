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
           return true;
       return false;
    }
    
    public static void main(String[] args) {
        
        Slack obj=new Slack();
        
       
        
        
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
