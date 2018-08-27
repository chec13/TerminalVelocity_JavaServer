/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testserverudp;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Student
 */
public class ServerObject implements java.io.Serializable
{
    public int clientNumber;
    public Vector3 pos, euler;
    //boolean Timeout, connected = true;
    //public float timeLeft;
    
    public ServerObject(Vector3 g, int client)
    {
        clientNumber = client;
        pos = g;
       // startThread();
    }
    public ServerObject(int client)
    {
        clientNumber = client;
        pos = new Vector3();
        euler = new Vector3();
        //startThread();
        
    }
    public ServerObject()
    {
        pos = new Vector3();
        euler = new Vector3();
        //startThread();
    }
   
}
   