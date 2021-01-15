import java.awt.*;

/**
 * 爆炸效果类
 */
public class Explode {
    double x,y;
    int count;
    //int time=0;
    static Image[] imgs=new Image[16];
    public Explode(double x,double y) {
        this.x=x;
        this.y=y;
        count=0;
    }
    static {
        for(int i=0; i<16;i++) {
            imgs[i]=GetImage.getImage("images/explode/e"+(i+1)+".gif");
        }
    }
    //画出爆炸图片
    public void draw(Graphics g) {
        if(count<16 ) {
            g.drawImage(imgs[count],(int)x,(int)y,null);
            count++;
        }
    }
    //爆炸位置
    public void set(double x,double y) {
        this.x=x;
        this.y=y;
    }
}
