/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slack;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author mac
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    Slack obj;
    public Login(Slack o) {
        obj=o;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setText("Welcome to Slack");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(80, 10, 143, 16);
        getContentPane().add(email);
        email.setBounds(40, 90, 240, 26);

        jLabel2.setText("Username:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(110, 70, 90, 20);

        jLabel3.setText("Password:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(110, 130, 70, 16);

        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(110, 190, 79, 29);

        jLabel4.setText("Not registered? Signup");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(30, 290, 160, 16);

        jButton2.setText("Signup");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(180, 290, 70, 29);
        getContentPane().add(jLabel6);
        jLabel6.setBounds(160, 260, 170, 0);
        getContentPane().add(password);
        password.setBounds(40, 150, 240, 26);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/slack/Slack_Icon.png"))); // NOI18N
        getContentPane().add(jLabel5);
        jLabel5.setBounds(20, 0, 290, 310);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String e=email.getText();
        //System.out.println(e);
        String p=password.getText();
        //System.out.println(p);
        boolean isLogin=false;
        try {
            isLogin=obj.checkLogin(e, p);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        if(isLogin==false)
            jLabel6.setText("Login Failed, retry pls !!");
        else
        {
            MainMenu mainFrame=new MainMenu();
            mainFrame.setTitle("Slack");
            mainFrame.setLocation(400,150);
            mainFrame.setSize(320,350);
            mainFrame.setVisible(true);
            
            this.setVisible(false);
            this.dispose();
             jLabel6.setText("Login Successful");
             
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        this.setVisible(false);
        Signup s=new Signup(this.obj);
        s.setTitle("Signup");
            s.setLocation(400,150);
            s.setSize(320,350);
            s.setVisible(true);
           s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
        
             
            
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField email;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPasswordField password;
    // End of variables declaration//GEN-END:variables
}
