/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testserverudp;

import java.io.IOException;
import java.io.StringReader;
import static java.lang.Math.toIntExact;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
//import sun.security.x509.IPAddressName;



/**
 *
 * @author Student
 */
public class ServerHandler{
DatagramSocket recieve;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    try {
        Server c = new Server();
        c.run();
    } catch (SocketException ex) {
        //Start Server
        Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
   
}
 class Server extends Thread{
      DatagramSocket socket;
      DatagramSocket sendSocket;
      Map<Integer, Location> dictionary = new HashMap<Integer, Location>();
      ArrayList<Integer> TimedOutClients;
      ArrayList<Timer> timers = new ArrayList<Timer>();
      Thread CheckTimedOut;
      int clientCount = 1;
      byte[] buf = new byte[512];
      
      public Server(DatagramSocket accept)
      {
          socket = accept;
          
      }
      public class Location
              {
                  InetAddress address;
                  int port, client;
                  public Location(InetAddress l, int p, int c)
                  {
                      address = l;
                      port = p;
                      client = c;
                  }
              }
      public Server() throws SocketException
      {
          socket = new DatagramSocket(9891);
          sendSocket = new DatagramSocket();
          TimedOutClients = new ArrayList<Integer>();
          // add timer for max amount of players
          TimedOutClients.add(1);
          TimedOutClients.add(2);
          TimedOutClients.add(3);
          TimedOutClients.add(4);  
           
          // Check timers to see if a player has timed out.
          CheckTimedOut = new Thread()
          {
            @Override
            public void run()
            {

                long startTime = System.currentTimeMillis();
                
                while(true)
                {
                for(int x = 0; x < timers.size(); x++)
                {
                    Timer t = timers.get(x);
                    if (!t.isAlive())
                    {
                        TimedOutClients.add(t.client);
                        timers.remove(x);
                        dictionary.remove(t.client);
                        
                        System.out.println("Client: " + t.client +" has timed out.");
                    }
                    else
                    {
                        
                    }
                }
               
                float currentTime = (float)(System.currentTimeMillis() - startTime)/1000;               
                //System.out.println("Server running for " + formatter.format(currentTime) + " seconds.");
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }
          };
          CheckTimedOut.start();
          
      }
      public void run()
      {
          float beforeTimeCheck = 0, afterTimeCheck = 0, deltaTime = 0;
          boolean running = true;
          
          while(running)
          {

              deltaTime = afterTimeCheck - beforeTimeCheck;
              
              beforeTimeCheck = System.currentTimeMillis()/1000;
              buf = new byte[512];
              DatagramPacket packet = new DatagramPacket(buf, buf.length);
              try {
                  socket.receive(packet);
              } catch (IOException ex) {
                  running = false;
              }
              InetAddress address = packet.getAddress();
              


              packet = new DatagramPacket(buf, buf.length);
              String r = new String(packet.getData(), 0, packet.getLength());
              JSONParser parser = new JSONParser();
             

              
              
              try {
                   
                   JSONObject obj = (JSONObject)parser.parse(r.trim());

                  ServerRigidBody players = new ServerRigidBody();
                  players.clientNumber = toIntExact((Long)obj.get("clientNumber"));

                  
                  // If the players client number is 0, that means the client has not been assigned a client
                  // number. Here we assign them one.
                  // If the player already has a client number, we reset their timer.
                  if (players.clientNumber == 0)
                  {
                       
                      players.clientNumber = TimedOutClients.remove(0);
                      String setClient = "SetClient: " + players.clientNumber;
                     
                      dictionary.put(players.clientNumber, new Location(address, players.clientNumber + 20000, players.clientNumber));

                      byte[] bytes = setClient.getBytes();
                      DatagramPacket setC = new DatagramPacket(bytes, bytes.length, address, 20000);
                      sendSocket.send(setC);
                      System.out.println("Incoming Client");

                      Timer t = new Timer(players.clientNumber);
                      t.start();
                      timers.add(t);
                      
                      continue;
                  }
                  else
                  {
                      
                     players = new ServerRigidBody(obj); 

                    for(Timer t: timers)
                    {
                        if (t.client == players.clientNumber)
                        {
                          
                            t.reset();
                            
                        }
                    }
                  obj = new JSONObject();
                  obj.put("RigidBody", players);
                  String send = players.getObject().toJSONString();
                  
                  byte[] send_bytes = send.getBytes();

                  for (Location a : dictionary.values())
                  {   
                     if( a.client != players.clientNumber) // send packet to non senders
                     {
                        DatagramPacket out = new DatagramPacket(send_bytes, send_bytes.length, a.address,
                        a.port);               
                        sendSocket.send(out);

                     }
                     

                  }

                  
                  }   
              }catch (ParseException ex) {
                  Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                  
              
              }catch (IOException ex) {
                  Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
              }

              
              
          }
          socket.close();
      }
      
  } 
