import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 帮助文档窗口
 */
public class Help extends JFrame {

    private JPanel panel;
    private JLabel label;
    private String text;
    private Universe parent;


    public Help(Universe p) {
        parent=p;
        set();
        init();
    }

    public void set() {
        panel=new JPanel(null);
        text="<html>";
        text+="<h3>KeyBoard Control</h3>";
        text+="Start/ReStart Game:";
        text+="<span style=\"color:#0000FF\">  R</span><br/>";
        text+="Pause/Continue Game:";
        text+="<span style=\"color:#0000FF\">  SPACE</span><br/>";
        text+="Move: ";
        text+="<span style=\"color:#0000FF\">  Up Down Left Right</span>";
        text+=" Or ";
        text+="<span style=\"color:#0000FF\">W S A D</span><br/>";
        text+="Shooting:";
        text+="<span style=\"color:#0000FF\">  K</span><br/>";
        text+="Open/Close Music:";
        text+="<span style=\"color:#0000FF\">  M</span><br/>";
        text+="Fighter Switch:";
        text+="<span style=\"color:#0000FF\">  Num 1 or Num 2</span><br/>";
        text+="</html>";

        label=new JLabel(text);
        label.setBounds(10,10,300,160);
        panel.add(label);
    }

    public void init() {
        this.setSize(400,250);
        this.setTitle("Help");
        this.setResizable(false);						//不可放大
        this.setLocationRelativeTo(null);				//居中屏幕
        Image icon=Toolkit.getDefaultToolkit().createImage(Main.class.getResource("images/icon.png"));    //工具箱
        this.setIconImage(icon);  //窗口图标设置
        this.add(panel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);		//退出时关闭程序
        this.addWindowListener(new MyWindowListener());
        this.setVisible(true);
    }

    class MyWindowListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            parent.isPrintHelp=false;
        }
    }

}