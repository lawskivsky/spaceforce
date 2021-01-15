import java.awt.*;

/**
 * 炮弹类
 */
public class Shell extends GameObject{
    Plane parent;
    Rectangle plane;
    boolean live=true;
    public Shell(Image bullet,Plane p,int speed) {
        parent=p;
        img=bullet;
        plane=p.getRect();
        x=plane.x+plane.width/2;
        y=plane.y-5;
        this.speed=speed  ;
        width=img.getWidth(null);
        height=img.getHeight(null);
    }
    public void drawMyself(Graphics g) {
        if(this.live ) {
            if(parent.live && !parent.parent.isGameOver) {
                y-=speed;
            }
            g.drawImage(img,(int)x,(int)y,null);
        }
    }
}
