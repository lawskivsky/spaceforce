import javax.swing.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Universe extends Frame {

	private Font font;
	private Font font1;
	private Font font2;
	private MenuBar menuBar;
	private Menu menuOption;
	private Menu menuOthers;
	private Menu menuChooseFighter;
	private MenuItem menuItemMusic;
	private MenuItem menuItemRLogin;
	private MenuItem menuItemExit;
	private MenuItem menuItemJ20;
	private MenuItem menuItemJ15;
	private MenuItem menuItemHelp;
	private MenuItem menuItemAbout;
	private MenuItem menuItemInfo;

	private Login parent;			//登录父窗口
	protected UserInfo userInfo;		//个人信息

	public AudioClip audio; 		//音乐播放器
	boolean isMusicStart=true;		//音乐是否开始播放
	boolean isGameOver=true;		//是否游戏结束了
	boolean isPrintHelp=false;			//是否显示帮助信息
	boolean isPrintAbout=false;			//是否显示关于学习
	boolean isPrintInfo=false;			//是否显示个人信息

	public Universe(Login p,UserInfo u) {
		this.parent=p;
		this.userInfo=u;
		this.newControl();
		this.setControl();
		this.windowsLayoutSetting();
	}

	/**
	 * 控件初始化
	 */
	public void newControl() {
		font=new Font("宋体",Font.BOLD,25);
		font1=new Font("宋体",Font.ITALIC,50);
		font2=new Font("宋体",Font.PLAIN,16);
		menuBar=new MenuBar();
		menuOption=new Menu("Options");
		menuChooseFighter=new Menu("Choose Fighter");
		menuItemMusic=new MenuItem("Open/Close Music (M)");
		menuItemExit=new MenuItem("Exit");
		menuItemJ15=new MenuItem("J-15 (1)");
		menuItemJ20=new MenuItem("J-20 (2)");
		menuOthers=new Menu("Others");
		menuItemHelp=new MenuItem("Help");
		menuItemInfo=new MenuItem("PersonInfo");
		menuItemRLogin=new MenuItem("Login");
		menuItemAbout=new MenuItem("About");
	}

	/**
	 * 控件设置
	 */
	public void setControl() {
		//控件嵌套
		menuChooseFighter.add(menuItemJ15);
		menuChooseFighter.add(menuItemJ20);
		menuOption.add(menuChooseFighter);
		menuOption.add(menuItemMusic);
		menuOption.add(menuItemRLogin);
		menuOption.add(menuItemExit);
		menuOthers.add(menuItemInfo);
		menuOthers.add(menuItemHelp);
		menuOthers.add(menuItemAbout);
		menuBar.add(menuOption);
		menuBar.add(menuOthers);
	}
	
	/**
	 * 窗口布局设置
	 */
	public void windowsLayoutSetting() {
		this.setSize(500,700);
		this.setTitle("SPACE FORCE");
		this.setResizable(false);						//不可放大
		this.setLocationRelativeTo(null);				//居中屏幕
		this.setMenuBar(menuBar);							//添加操作栏

		Image icon=Toolkit.getDefaultToolkit().createImage(Main.class.getResource("images/icon.png"));    //工具箱
		this.setIconImage(icon);  //窗口图标设置

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int res=JOptionPane.showConfirmDialog(null,"Quit The Game Or Not?","Tip",JOptionPane.YES_NO_OPTION);
				if(res== JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});		//退出时关闭程序
		this.audio=Applet.newAudioClip(Universe.class.getResource("musics/start_new_journey.wav"));	//背景音乐
		this.audio.loop();	//循环播放


		this.setVisible(true);
		if(this.realPlane.live) {
			new PaintThread(this).start();
		}

		//监听
		addKeyListener(new KeyMonitor());
		menuItemJ15.addActionListener(new FighterChange_Listener("J15"));
		menuItemJ20.addActionListener(new FighterChange_Listener("J20"));
		menuItemMusic.addActionListener(new MusicControl());
		menuItemExit.addActionListener(new ExitListener());
		menuItemHelp.addActionListener(new Help_About_info_Listener(this,0));
		menuItemAbout.addActionListener(new Help_About_info_Listener(this,1));
		menuItemInfo.addActionListener(new Help_About_info_Listener(this,2));
		menuItemRLogin.addActionListener(new Relogin_listener(this));
	}



	//图：背景，飞机，炮弹,大敌机，小敌机
	Image background=GetImage.getImage("images/space.jpg");
	Image plane1=GetImage.getImage("images/j15b.png");
	Image plane2=GetImage.getImage("images/j20e.png");
	Image plane=plane2;
	Image bullet=GetImage.getImage("images/bullet3.png");
	Image big=GetImage.getImage("images/big.png");
	Image small=GetImage.getImage("images/small.png");
	Image enemyBullet=GetImage.getImage("images/bullet4.png");
	ArrayList<Shell> shellList=new ArrayList<Shell>();		//炮弹集
	ArrayList<Enemy> enemies=new ArrayList<Enemy>();	//敌机集
	int planeX=225;					//初识位置X
	int planeY=500;					//初始位置Y
	int speed=10;					//飞行速度
	Plane realPlane=new Plane(plane,planeX,planeY,this.speed,shellList,this);		//飞机类



	@Override
	public void paint(Graphics g) {
		//重画所有控件
		super.paint(g);
		//画背景
		g.drawImage(this.background,0,0,null);
		//画主战机
		realPlane.drawMyself(g);

		if(isGameOver) {
			printInfo(g,"press key \"R\" to ",190,300,font2,Color.lightGray);
			printInfo(g,"START GAME",110,360,font1,Color.lightGray);
		}else {
			if(realPlane!=null && realPlane.live==false) {
				printInfo(g,"press key \"SPACE\" to ",175,300,font2,Color.lightGray);
				printInfo(g,"CONTINUE",120,360,font1,Color.lightGray);
			}
		}

		//航弹发射冷却时间=帧率×realPlane.maxBulletControl
		if(realPlane.bulletControl<realPlane.maxBulletControl) {
			realPlane.bulletControl++;
		}
		//画航弹
		for(int i=0; i<shellList.size(); i++) {
			Shell temp=shellList.get(i);
			temp.drawMyself(g);

			Enemy temp2;
			for(int j=0; j<enemies.size(); j++) {
				temp2=enemies.get(j);
				//敌机碰撞
				if(temp.getRect().intersects(temp2.getRect()) &&temp.live &&temp2.live) {
					//敌机爆炸
					temp2.live=false;
					temp.live=false;
					realPlane.score+=temp2.score;//分数增加
				}
			}
		}
		//画出敌机
		for(int i=0; i<enemies.size(); i++) {
			Enemy temp=enemies.get(i);
			if(temp.live==true && temp.pass==false ) {
				temp.setSpeed(this.realPlane.live &&(!this.isGameOver));
				temp.drawMyself(g);
			}
			//我方阵亡
			if(temp.getRect().intersects(realPlane.getRect()) && !realPlane.isDefeat ) {
				this.isGameOver=true;
				temp.live=false;
			}

		}
		//炮弹用完，游戏结束
		if( realPlane.ammunition==0) {
			this.isGameOver=true;
		}
		int flag=0;
		if(flag==0) {
			flag=1;
		}else {
			//阵亡爆炸特效
			if(this.isGameOver) {
				realPlane.explode.set(realPlane.x,realPlane.y);
				realPlane.explode.draw(g);
			}
		}

		//敌机爆炸特效
		for(int i=0; i<enemies.size(); i++) {
			Enemy temp=enemies.get(i);
			if(temp.live==false) {
				temp.explode.set(temp.x,temp.y);
				temp.explode.draw(g);
				temp.explodeCount++;
			}
		}
		//画出分数
		printInfo(g,"SCORE:"+realPlane.score,330,80,font,Color.WHITE);
		//画出弹药剩余量
		printInfo(g,"MISSIBLE:"+realPlane.ammunition,300,110,font,Color.WHITE);

	}


	/**
	 * 双缓冲技术
	 * (1)在内存中创建与画布一致的缓冲区；
	 * (2)在缓冲区画图；
	 * (3)将缓冲区位图复制到当前画布上
	 * (4)释放内存缓冲区
	 */
	private Image offScreenImage=null;
	private Graphics goff=null;
	@Override
	public void update(Graphics g) {
		if(offScreenImage==null){
			offScreenImage=this.createImage(this.getWidth(),this.getHeight());
			goff=offScreenImage.getGraphics();
		}
		paint(goff);
		g.drawImage(offScreenImage,0,0,null);
	}

	/**
	 * 键盘监听适配器
	 */
	class KeyMonitor extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			realPlane.addDirection(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			realPlane.minusDirection(e);
		}
	}

	/**
	 * 音乐开关
	 */
	class MusicControl implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(isMusicStart==true) {
				audio.stop();
			}else {
				audio.loop();
			}
			isMusicStart=!isMusicStart;
		}
	}

	/**
	 * 退出游戏
	 */
	class ExitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int res=JOptionPane.showConfirmDialog(null,"Quit The Game Or Not?","Tip",JOptionPane.YES_NO_OPTION);
			if(res== JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
	}

	/**
	 * 更换战机监听
	 */
	class FighterChange_Listener implements ActionListener {
		private String title;
		public FighterChange_Listener(String t){
			this.title=t;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(title.equals("J15")) {
				realPlane.img=plane1;
				plane=plane1;
			}
			else if(title.equals("J20")) {
				realPlane.img=plane2;
				plane=plane2;
			}
		}
	}
	//画布输出文字设定
	public void printInfo(Graphics g,String str,int x,int y,Font f,Color color) {
		Color c=g.getColor();
		g.setColor(color);
		g.setFont(f);
		g.drawString(str,x,y);
	}

	class Help_About_info_Listener implements ActionListener{
		Universe parent;
		int choose;
		public Help_About_info_Listener(Universe p,int choose) {
			this.parent=p;
			this.choose=choose;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(choose==0) {
				if(parent.isPrintHelp==false) {
					parent.isPrintHelp=true;
					new Help(parent);
				}
			}else if(choose==1) {
				if(parent.isPrintAbout==false) {
					parent.isPrintAbout=true;
					new About(parent);
				}
			}
			else if(choose==2) {
				if(parent.isPrintInfo==false) {
					parent.isPrintInfo=true;
					new Info(parent);
				}
			}
		}
	}
	//重新登录
	class Relogin_listener implements ActionListener{
		private Universe parent;
		public Relogin_listener(Universe p){
			this.parent=p;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			this.parent.parent.setVisible(true);
			this.parent.dispose();
		}
	}
}