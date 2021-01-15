import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

/**
 * 登录窗口及功能实现
 */
public class Login extends JFrame{
    private JPanel panel;
    private JLabel labelTitle;
    private JLabel labelUsername;
    private JLabel labelPassword;
    private JTextField fieldUsername;
    private JPasswordField fieldPassword;
    private JButton buttonLogin;
    private JButton buttonRegister;
    private JButton buttonResetPassword;
    private UserInfo userInfo;
    private static final UserInfo superUser=new UserInfo("00000","超级用户","notepadplus.com","123456");    //超级用户         //超级用户

    public Login() {
        this.init();
    }

    public void init() {
        //控件初始化
        panel=new JPanel(null);
        labelTitle=new JLabel("欢迎游玩“SPACE FORCE”");
        labelUsername=new JLabel("账号");
        labelPassword=new JLabel("密码");
        fieldUsername=new JTextField();
        fieldPassword=new JPasswordField();
        buttonLogin=new JButton("登录");
        buttonRegister=new JButton("注册");

        //控件布局
        panel.setSize(400,350);
        labelTitle.setBounds(70,25,300,50);
        labelTitle.setFont(new Font("仿宋",Font.BOLD,19));
        labelUsername.setBounds(80,90,50,50);
        labelUsername.setFont(new Font("仿宋",Font.BOLD,20));
        labelPassword.setBounds(80,140,50,50);
        labelPassword.setFont(new Font("仿宋",Font.BOLD,20));
        fieldUsername.setBounds(150, 102, 150, 30);
        fieldPassword.setBounds(150, 150, 150, 30);
        buttonLogin.setBounds(80,210,80,30);
        buttonRegister.setBounds(200,210,80,30);

        //控件嵌套
        panel.add(labelTitle);
        panel.add(labelUsername);
        panel.add(labelPassword);
        panel.add(fieldUsername);
        panel.add(fieldPassword);
        panel.add(buttonLogin);
        panel.add(buttonRegister);

        //添加监控
        buttonLogin.addActionListener(new Event_Login(this));
        buttonRegister.addActionListener(new Event_Register(this));

        //主窗口布局
        this.setTitle("登录");
        Image icon=Toolkit.getDefaultToolkit().createImage(Main.class.getResource("images/icon.png"));    //工具箱
        this.setIconImage(icon);  //窗口图标设置
        this.setSize(400,350);
        this.setResizable(false);	//窗口不可改变大小
        this.setLocationRelativeTo(null);	//窗口居中
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//退出时关闭整个应用程序程序
        this.add(panel);
        this.setVisible(true);
    }
    //登录进软件
    class Event_Login implements ActionListener {
        private Login parent;
        public Event_Login(Login parent) {
            this.parent=parent;
        }
        private String username;
        private String password;
        @Override
        public void actionPerformed(ActionEvent e) {
            username= OperationString.Trim(fieldUsername.getText());
            password= OperationString.Trim(new String(fieldPassword.getPassword()));
            if(username.length()==0) {
                JOptionPane.showMessageDialog(null,"请输入账号","警告",JOptionPane.WARNING_MESSAGE);
            }else if(password.length()==0) {
                JOptionPane.showMessageDialog(null,"请输入密码","警告",JOptionPane.WARNING_MESSAGE);
            }else if(username.equals(superUser.getUsername()) && password.equals(superUser.getPassword())) {
                userInfo=superUser;
                parent.setVisible(false);
                new Universe(parent,userInfo);
            }else{
                try{
                    Class.forName(ConnectMysql.DATABASE_DRIVER);    //加载数据库驱动程序
                    Connection conn=DriverManager.getConnection(ConnectMysql.DATABASE_URL,ConnectMysql.DATABASE_USER,ConnectMysql.DATABASE_PASSWORD);      //连接数据库

                    String sql_select1="select * from user where username='"+username+"'";
                    String sql_select2="select * from user where username='"+username+"' and password='"+password+"'";

                    Statement stmt=conn.createStatement();  //数据库操作对象
                    ResultSet res=stmt.executeQuery(sql_select1);   //执行查询
                    int flag1=0;
                    while (res.next()) {
                        flag1++;
                    }
                    if(flag1==1) {
                        res=stmt.executeQuery(sql_select2);
                        int flag2=0;
                        while (res.next()) {
                            flag2++;
                            userInfo=new UserInfo(res.getString(1),res.getString(2),res.getString(3),res.getString(4));
                        }
                        if (flag2==1) {
                            res.close();
                            stmt.close();
                            conn.close();
                            JOptionPane.showMessageDialog(null,"登录成功","提示",JOptionPane.INFORMATION_MESSAGE);
                            parent.setVisible(false);
                            new Universe(parent,userInfo);
                        }else{
                            JOptionPane.showMessageDialog(null,"密码错误，请重新输入","警告",JOptionPane.WARNING_MESSAGE);
                        }
                    }else {
                        JOptionPane.showMessageDialog(null,"不存在该用户","警告",JOptionPane.WARNING_MESSAGE);
                    }

                    res.close();
                    stmt.close();
                    conn.close();

                }catch (  SQLException e_sql_driver) {
                    JOptionPane.showMessageDialog(null,"数据库驱动程序加载失败","错误",JOptionPane.ERROR_MESSAGE);
                }catch (ClassNotFoundException e_sql_conn) {
                    JOptionPane.showMessageDialog(null,"数据库连接失败，请检查网络","错误",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    //注册账户
    class Event_Register implements ActionListener {
        private Login parent;
        public Event_Register(Login parent) {
            this.parent=parent;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            parent.setVisible(false);
            new Register(parent);
        }
    }
}