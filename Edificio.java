import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;

public class Edificio{
    
    public int x1, x2, h;
    
    
    public Edificio(int x1, int x2, int h){
        this.x1 = x1;
        this.x2 = x2;
        this.h = h;
    }
    
    public int getx1(){
       return this.x1;
    }
    
    public int getx2(){
       return this.x2;
    }
    
    public int geth(){
       return this.h;
    }
    
    public void print(){
    System.out.print("(" + x1 + "," + x2 + "," + h + ")");
    }  
    

}