/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testserverudp;

import java.util.ArrayList;

/**
 *
 * @author student
 */
public class Timer extends Thread {
   long startTime, currentTime;
   int client;
   
    @Override
    public void run()
    {
       
        
        while(true)
        {
            currentTime = System.currentTimeMillis()/1000;
            System.out.println(client + ": " + (currentTime - startTime));
            if( currentTime - startTime >= 10)
            {
                System.out.println();
                break;
            }
        }
             
    }
    public synchronized void  reset()
    {
        
        startTime = System.currentTimeMillis()/1000;
        
    }
   

    /**
     *
     * @param c pass client number, then starts the thread.
     */
    public Timer(int c)
    {
        client = c;
        startTime = System.currentTimeMillis()/1000;
        
    }
}
