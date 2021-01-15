import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 关于文档窗口
 */
public class About extends JFrame {
    private JLabel label;
    private String text;
    private Universe parent;

    public About(Universe p) {
        parent=p;
        set();
        init();
    }

    public void set() {
        text="<html>";
        text+="<h1 style='text-align:center'>Developed by</h1>";
        text+="<p style=\"text-align: center;color:#0000FF;font-size:12px\">吴伟鑫</p><br/>";
        text+="<p style=\"text-align: center;color:#0000FF;font-size:12px\">计科181</p><br/>";
        text+="<p style=\"text-align: center;color:#0000FF;font-size:12px\">201815210116</p><br/>";
        text+="Developed on";
        text+="<span style='color:#0000FF'> December 25, 2020</span><br/>";
        text+="</html>";

        label=new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void init() {
        this.setSize(400,250);
        this.setTitle("About");
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
            parent.isPrintAbout=false;
        }
    }
}