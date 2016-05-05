public class Kanye{
    private double[] coords = new double[2];
    private String weapon, powerup;
    private int speed;
    private boolean shooting;

    public Kanye(){
        weapon = "assaultrifle";
        powerup = "none";
        coords[0] = 600;
        coords[1] = 600;
        speed = 2;
    }
    public void setShooting(boolean s){
        shooting = s;
    }
    public boolean isShooting(){
        return shooting;
    }



    public double getX(){ return coords[0]; }

    public double getY(){ return coords[1]; }

    public String getWep(){ return weapon; }
  
    public String getPower(){ return powerup; }
    
    public int getSpeed(){
        return speed;
    }
    
    public void move(double displacedx, double displacedy){
        coords[0] += displacedx;
        coords[1] += displacedy;
    }
}