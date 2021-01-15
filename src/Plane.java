import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Plane extends GameObject {
    Explode explode;        //爆炸效果优化
    int score=0;            //分数计算
    int bulletControl=0;    //炮弹冷却时间控制
    int maxBulletControl=3; //炮弹冷却时间设置
    int ammunition=1000;    //载弹量：1000
    public Plane(Image img,double x,double y,int speed,ArrayList<Shell> s,Universe p) {
        super(img,x,y,speed,img.getWidth(null),img.getHeight(null));
        this.shells=s;
        this.parent=p;
        explode=new Explode(x,y);
    }

    boolean left,right,up,down;
    boolean live=true;
    boolean isDefeat=false;    //是否失败
    private ArrayList<Shell> shells;
    public Universe parent;

    /**
     * 键盘控制方向
     * @param e
     */
    public void addDirection(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                left=live&(!parent.isGameOver);
                break;
            case KeyEvent.VK_RIGHT:
                right=live&(!parent.isGameOver);
                break;
            case KeyEvent.VK_UP:
                up=live&(!parent.isGameOver);
                break;
            case KeyEvent.VK_DOWN:
                down=live&(!parent.isGameOver);
                break;
            case KeyEvent.VK_A:
                left=live&(!parent.isGameOver);
                break;
            case KeyEvent.VK_D:
                right=live&(!parent.isGameOver);
                break;
            case KeyEvent.VK_W:
                up=live&(!parent.isGameOver);
                break;
            case KeyEvent.VK_S:
                down=live&(!parent.isGameOver);
                break;
            case KeyEvent.VK_SPACE:
                //暂停/继续游戏
                live=!live;
                break;
            case KeyEvent.VK_K:
                //按K键手动发射导弹
                Shell b=new Shell(parent.bullet,this,12);
                if(!parent.isGameOver&&live && bulletControl==this.maxBulletControl && this.ammunition!=0) {
                    shells.add(b);
                    bulletControl=0;
                    this.ammunition-=1;
                }
                if(shells.size()!=0 && shells.get(0).y<=0) {
                    shells.remove(0);
                }
                break;

            case KeyEvent.VK_R:
                //按R重新开始
                parent.isGameOver=false;
                parent.shellList=new ArrayList<Shell>();
                parent.enemies=new ArrayList<Enemy>();
                parent.realPlane=new Plane(parent.plane,parent.planeX,parent.planeY,parent.speed,parent.shellList,parent);
                break;
            //1、2切换战机
            case KeyEvent.VK_1:
                parent.realPlane.img=parent.plane1;
                parent.plane=parent.plane1;
                break;
            case KeyEvent.VK_2:
                parent.realPlane.img=parent.plane2;
                parent.plane=parent.plane2;
                break;
            //音乐开/关
            case KeyEvent.VK_M:
                if(parent.isMusicStart==true) {
                    parent.audio.stop();
                }else {
                    parent.audio.loop();
                }
                parent.isMusicStart=!parent.isMusicStart;
                break;
            default:
                break;
        }
    }

    /**
     * 松开键盘
     * @param e
     */
    public void minusDirection(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                left=false;
                break;
            case KeyEvent.VK_RIGHT:
                right=false;
                break;
            case KeyEvent.VK_UP:
                up=false;
                break;
            case KeyEvent.VK_DOWN:
                down=false;
                break;
            case KeyEvent.VK_A:
                left=false;
                break;
            case KeyEvent.VK_D:
                right=false;
                break;
            case KeyEvent.VK_W:
                up=false;
                break;
            case KeyEvent.VK_S:
                down=false;
                break;
            default:
                break;
        }
    }

    @Override
    public void drawMyself(Graphics g) {
        if(!parent.isGameOver) {
            super.drawMyself(g);
            //控制边界
            if(left) {
                x -= speed;
                if(x<=0) {
                    x=0;
                }
            }
            if(right) {
                x += speed;
                if(x>=435) {
                    x=435;
                }
            }
            if(up) {
                y -= speed;
                if(y<=45) {
                    y=45;
                }
            }
            if(down) {
                y += speed;
                if(y>=600) {
                    y=600;
                }
            }
        }
    }
}
