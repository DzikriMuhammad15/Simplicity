package simplicity;
public class Point {
    private int X;
    private int Y;

    //constructor Point
    public Point(int x, int y){
        this.X = x;
        this.Y = y;
    }

    //getter X
    public int getX(){
        return this.X;
    }

    //getter Y
    public int getY(){
        return this.Y;
    }

    //setter X
    public void setX(int x){
        this.X = x;
    }

    //setter Y
    public void setY(int y){
        this.Y = y;
    }
    public boolean isEqual(int x, int y){
        if (this.X == x && this.Y == y){
            return true;
        }
        else{
            return false;
        }
    }
}
