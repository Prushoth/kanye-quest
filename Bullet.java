import java.util.*;
import java.awt.*;

public class Bullet {
    public double x,y, ang;

    public Bullet(double x, double y, double ang){
        this.x = x;
        this.y = y;
        this.ang = ang;
    }
    public void move(){
        x += 10*Math.cos(ang);
        y += 10*Math.sin(ang);
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }

    public void draw(Graphics g){
        g.fillOval((int)Math.round(x), (int)Math.round(y), 5,5);
    }
}
