/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slack;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author MR
 */
public class userfiles {
    
 private String name;
 private String email;
 ArrayList<File> files;
 ArrayList<String> paths;   
 
 public void addtolist(File f, String s) throws SQLException
 {
     files.add(f);
     paths.add(s);
 } 
}
