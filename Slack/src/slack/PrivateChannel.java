/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slack;

import java.sql.SQLException;

/**
 *
 * @author mac
 */
public class PrivateChannel extends Channel
{
    
    public PrivateChannel(String u, String n, int w) throws SQLException {
        super(u, n, w);
    }
    
    
}
