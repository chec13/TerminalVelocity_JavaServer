/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testserverudp;

import static java.lang.Math.toIntExact;
import org.json.simple.JSONObject;

/**
 *
 * @author Student
 */
public class ServerRigidBody extends ServerObject implements java.io.Serializable {
    public float Mass, Drag, Angular_Drag;
    //public Vector3 pos, euler;
    public boolean useGravity = true, isKinematic = false;
    public RigidbodyInterpolation interpolate;
    public CollisionDetectionMode collision_detection;
    public Vector3 vel = new Vector3();
    
    public ServerRigidBody( float m, float d, float ad, boolean u, boolean ik, Vector3 p, int client)
    {
        //connected = true;
        pos = new Vector3();
        euler = new Vector3();
        Mass = m;
        Drag = d;
        Angular_Drag = ad;

        useGravity = u;
        isKinematic = ik;

        //interpolate = i;
        //collision_detection = cd;
        

        pos = p;
        clientNumber = client;
    }
    public ServerRigidBody()
    {
        pos = new Vector3();
        euler = new Vector3();
    }
    public void toLog()
    {
        System.out.println("Client: " + clientNumber + " has a RigidBody with a mass of " + Mass + "KG. Position: " 
                + pos.toString());
    }
    public ServerRigidBody(JSONObject obj)
    {
        //connected = true;
        
        pos = new Vector3();
        euler = new Vector3();
        Mass = (float)((double)obj.get("Mass"));
        useGravity = (boolean)obj.get("useGravity");
                  //client.collision_detection = (CollisionDetectionMode)obj.get("collision_detection");
        Angular_Drag = (float)(double)obj.get("Angular_Drag");
        
                  JSONObject vector = (JSONObject)obj.get("pos");
                  
                  pos.x = (float)(double)vector.get("x");
                  pos.y = (float)(double)vector.get("y");
                  pos.z = (float)(double)vector.get("z");
                  
                  //System.out.println(pos.toString());
                  vector = (JSONObject)obj.get("euler");
                  euler.x = (float)(double)vector.get("x");
                  euler.y = (float)(double)vector.get("y");
                  euler.z = (float)(double)vector.get("z");
                  
                  vector = (JSONObject)obj.get("vel");
                  vel.x = (float)(double)vector.get("x");
                  vel.y = (float)(double)vector.get("y");
                  vel.z = (float)(double)vector.get("z");
                  
                  //client.interpolate = (RigidbodyInterpolation)obj.get("interpolate");
        clientNumber = toIntExact((Long)obj.get("clientNumber"));
        isKinematic = (boolean)obj.get("isKinematic");
        
        
    }
    public JSONObject getObject()
    {
        JSONObject obj = new JSONObject();
        JSONObject vector = new JSONObject();
        
        vector.put("x", pos.x);
        vector.put("y", pos.y);
        vector.put("z", pos.z);
        obj.put("position", vector);
        
        vector = new JSONObject();
        vector.put("x", euler.x);
        vector.put("y", euler.y);
        vector.put("z", euler.z);
        obj.put("euler", vector);
        
        vector = new JSONObject();
        vector.put("x", vel.x);
        vector.put("y", vel.y);
        vector.put("z", vel.z);
        obj.put("vel", vector);
        
        obj.put("clientNumber", clientNumber);
        obj.put("isKinematic", isKinematic);
        obj.put("mass", Mass);
        obj.put("useGravity", useGravity);
        obj.put("angularDrag", Angular_Drag);
        
        return obj;
    }
    
}
 enum CollisionDetectionMode
    {Discrete, Continuous, ContinuousDynamic}
 enum RigidbodyInterpolation
    {None, Interpolate, Extrapolate}
 enum RigidbodyConstraints
    {None,FreezePositionX,FreezePositionY,FreezePositionZ,FreezePosition,FreezeRotationX,FreezeRotationY,
     FreezeRotationZ,FreezeRotation,FreezeAll}
