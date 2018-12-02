/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slack;

/**
 *
 * @author mac
 */
public class Workspace 
{
    private String name;
    private String directory;
    private String creator;
    
    Workspace(String n, String dir,String c)
    {
        name=n;
        directory=dir;
        creator=c;
    }
    
    
}
