import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Random;

/**
 * 重画窗口类
 */
class PaintThread extends Thread{

	private Universe parent;
	public PaintThread(Universe p) {
		this.parent=p;
	}
	public void run() {
		while(true) {

			//更新敌机
			int t=(new Random().nextInt(120))+1;
			if(!parent.isGameOver && parent.realPlane.live) {
				if(t%60==0) {
					parent.enemies.add(new Enemy(parent.big,8,2,parent));
				}
				if (t % 9 == 0) {
					parent.enemies.add(new Enemy(parent.small, 12,1, parent));
				}
				if (t % 26== 0) {
					parent.enemies.add(new Enemy(parent.enemyBullet, 18,3,parent));
				}
			}

			//删除敌机内存(飞越的或被击毙的)
			if(parent.enemies.size()!=0) {
				Enemy temp=parent.enemies.get(0);
				if(temp.y>700 || (temp.explodeCount>16)) {
					parent.enemies.remove(0);
				}
			}

			this.parent.repaint();		//重画窗口
			try {
				Thread.sleep(1000/24);	//24帧
			}catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "窗口重画异常："+e,"异常",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
