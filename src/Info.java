import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 关于文档窗口
 */
public class Info extends JFrame {
    private JLabel label;
    private String text;
    private Universe parent;

    public Info(Universe p) {
        parent=p;
        set();
        init();
    }

    public void set() {
        text="<html>";
        text+="Nickname:";
        text+="<span style=\"color:#0000FF\">"+ parent.userInfo.getNickname() +"</span><br/>";
        text+="Account:";
        text+="<span style=\"color:#0000FF\"> "+parent.userInfo.getUsername()+"</span><br/>";
        text+="Mail:";
        text+="<span style=\"color:#0000FF\"> "+parent.userInfo.getMail()+" </span><br/>";
        text+="</html>";

        label=new JLabel(text);
        label.setFont(new Font("宋体",Font.BOLD,25));
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void init() {
        this.setSize(400,250);
        this.setTitle("Person Info");
        this.setResizable(false);						//不可放大
        this.setLocationRelativeTo(null);				//居中屏幕
        Image icon=Toolkit.getDefaultToolkit().createImage(Main.class.getResource("images/icon.png"));    //工具箱
        this.setIconImage(icon);  //窗口图标设置
        this.add(label);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);		//退出时关闭程序
        this.addWindowListener(new MyWindowListener());
        this.setVisible(true);
    }

    class MyWindowListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            parent.isPrintInfo=false;
        }
    }
}