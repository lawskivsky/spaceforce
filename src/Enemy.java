import java.awt.*;
import java.util.Random;

public class Enemy extends GameObject {
    Universe parent;    //父窗口
    Explode explode;    //爆炸效果
    int explodeCount=0;
    int score=1;        //敌机价值
    boolean live=true;  //存活
    boolean pass=false; //超越
    int realSpeed=0;

    public Enemy(Image m,int speed,int score,Universe p) {
        this.parent=p;
        img=m;
        this.score=score;
        this.speed=speed;
        width=img.getWidth(null);
        height=img.getHeight(null);
        y=0;
        this.x=new Random().nextInt(501-this.img.getWidth(null));
        explode=new Explode(x,y);
    }

    public void setSpeed(boolean planeLive) {
        if(planeLive) {
            realSpeed=speed;
        }
        else {
            realSpeed=0;
        }
    }

    public void drawMyself(Graphics g) {
        y+=this.realSpeed;
        g.drawImage(img,(int)x,(int)y,null);
    }
}
