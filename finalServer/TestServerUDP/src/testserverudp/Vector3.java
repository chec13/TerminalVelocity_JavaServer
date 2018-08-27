/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testserverudp;

/**
 *
 * @author Student
 */
public class Vector3 implements java.io.Serializable {
    public float x, y ,z;
    
    public Vector3(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3()
    {
        x = y = z = 0.0f;
    }
    public String toString()
    {
        return "{x: " + x + ", y: " + y + ", z: " + z + " }";
    }
            
            
}

    
