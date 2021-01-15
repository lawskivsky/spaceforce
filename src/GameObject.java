import java.awt.*;

public class GameObject {
    Image img;          //图片对象
    double x,y;         //物体坐标
    int speed;          //移动速度
    int width,height;   //图片的矩形宽度和高度；

    public GameObject() {

    }

    public GameObject(Image img,double x,double y) {
        this.x=x;
        this.y=y;
        this.img=img;
        if(this.img!=null) {
            this.width=img.getWidth(null);
            this.height=img.getHeight(null);
        }
    }

    public GameObject(Image img,double x,double y,int speed,int width,int height) {
        this.x=x;
        this.y=y;
        this.img=img;
        this.speed=speed;
        this.width=width;
        this.height=height;
    }

    /**
     * 绘制对象
     * @param g
     */
    public void drawMyself(Graphics g) {
        g.drawImage(img,(int)x,(int)y,null);
    }

    /**
     * 返回物体对应的矩形区域，以便碰撞检测
     * @return
     */
    public Rectangle getRect() {
        return new Rectangle((int)x,(int)y,width+3,height-3);
    }
}
