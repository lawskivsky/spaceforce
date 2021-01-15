import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * 注册窗口及功能实现
 */
public class Register extends JFrame{
    private Login parent;
    private JPanel panel;
    private JLabel labelUser;
    private JLabel labelNickname;
    private JLabel labelMail;
    private JLabel labelPassword;
    private JLabel labelConfirmPassword;
    private JTextField fieldUser;
    private JTextField fieldNickname;
    private JTextField fieldMail;
    private JPasswordField fieldPassword;
    private JPasswordField fieldConfirmPassword;
    private JButton buttonConfirm;
    private JButton buttonReturnLogin;


    public Register(Login parent) {
        this.parent=parent;
        this.init();
    }

    public void init() {

        //控件初始化
        panel=new JPanel(null);
        labelUser=new JLabel("账号");
        labelNickname=new JLabel("昵称");
        labelMail=new JLabel("邮箱");
        labelPassword=new JLabel("密码");
        labelConfirmPassword=new JLabel("确认密码");
        fieldUser=new JTextField();
        fieldNickname=new JTextField();
        fieldMail=new JTextField();
        fieldPassword=new JPasswordField();
        fieldConfirmPassword=new JPasswordField();
        buttonConfirm=new JButton("确认注册");
        buttonReturnLogin=new JButton("返回登录");

        //控件布局
        panel.setSize(400,350);
        labelUser.setFont(new Font("宋体",Font.PLAIN,20));
        labelUser.setBounds(50,20,90,30);
        labelNickname.setFont(new Font("宋体",Font.PLAIN,20));
        labelNickname.setBounds(50,60,90,30);
        labelMail.setFont(new Font("宋体",Font.PLAIN,20));
        labelMail.setBounds(50,100,90,30);
        labelPassword.setFont(new Font("宋体",Font.PLAIN,20));
        labelPassword.setBounds(50,140,90,30);
        labelConfirmPassword.setFont(new Font("宋体",Font.PLAIN,20));
        labelConfirmPassword.setBounds(50,180,90,30);
        fieldUser.setBounds(150,20,180,30);
        fieldNickname.setBounds(150,60,180,30);
        fieldMail.setBounds(150,100,180,30);
        fieldPassword.setBounds(150,140,180,30);
        fieldConfirmPassword.setBounds(150,180,180,30);
        buttonConfirm.setBounds(80,250,90,30);
        buttonReturnLogin.setBounds(200,250,90,30);

        //控件嵌套
        panel.add(labelUser);
        panel.add(labelNickname);
        panel.add(labelMail);
        panel.add(labelPassword);
        panel.add(labelConfirmPassword);
        panel.add(fieldUser);
        panel.add(fieldNickname);
        panel.add(fieldMail);
        panel.add(fieldPassword);
        panel.add(fieldConfirmPassword);
        panel.add(buttonConfirm);
        panel.add(buttonReturnLogin);

        //添加监控
        buttonReturnLogin.addActionListener(new Event_ReturnLogin(parent,this));
        buttonConfirm.addActionListener(new Event_ConfirmRegister());

        //主窗口布局
        this.setTitle("注册");
        Image icon=Toolkit.getDefaultToolkit().createImage(Main.class.getResource("images/icon.png"));    //工具箱
        this.setIconImage(icon);  //窗口图标设置
        this.setSize(400,350);
        this.setResizable(false);	//窗口不可改变大小
        this.setLocationRelativeTo(null);	//窗口居中
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//退出时关闭整个应用程序程序
        this.add(panel);
        this.setVisible(true);
    }



    /**
     * 确认注册
     */
    class Event_ConfirmRegister implements ActionListener{
        private String username;
        private String password;
        private String mail;
        private String nickname;
        @Override
        public void actionPerformed(ActionEvent e) {
            if(fieldUser.getText().isEmpty() || OperationString.Trim(fieldUser.getText()).length()==0) {
                JOptionPane.showMessageDialog(null,"请输入账号","警告",JOptionPane.WARNING_MESSAGE);
            }else if(fieldNickname.getText().isEmpty() || OperationString.Trim(fieldNickname.getText()).length()==0) {
                JOptionPane.showMessageDialog(null,"请输入昵称","警告",JOptionPane.WARNING_MESSAGE);
            }else if(fieldMail.getText().isEmpty() || OperationString.Trim(fieldMail.getText()).length()==0) {
                JOptionPane.showMessageDialog(null,"请输入邮箱","警告",JOptionPane.WARNING_MESSAGE);
            }else if((new String(fieldPassword.getPassword())).isEmpty() || OperationString.Trim((new String(fieldPassword.getPassword()))).length()==0) {
                JOptionPane.showMessageDialog(null,"请输入密码","警告",JOptionPane.WARNING_MESSAGE);
            }else if((new String(fieldConfirmPassword.getPassword())).isEmpty() || OperationString.Trim((new String(fieldConfirmPassword.getPassword()))).length()==0) {
                JOptionPane.showMessageDialog(null,"请输入确认密码","警告",JOptionPane.WARNING_MESSAGE);
            }else if((new String(fieldPassword.getPassword())).equals(new String(fieldConfirmPassword.getPassword()))==false) {
                JOptionPane.showMessageDialog(null,"请确保密码和确认密码一致","警告",JOptionPane.WARNING_MESSAGE);
            }else {

                username=OperationString.Trim(fieldUser.getText());
                nickname=OperationString.Trim(fieldNickname.getText());
                password=OperationString.Trim(new String(fieldPassword.getPassword()));
                mail=OperationString.Trim(fieldMail.getText());
                try{


                    Class.forName(ConnectMysql.DATABASE_DRIVER);    //加载数据库驱动程序
                    Connection conn= DriverManager.getConnection(ConnectMysql.DATABASE_URL,ConnectMysql.DATABASE_USER,ConnectMysql.DATABASE_PASSWORD);      //连接数据库

                    String sql_select="select * from user where username='"+username+"'";
                    String sql_insert="insert into user values('"+username+"','"+nickname+"','"+mail+"','"+password+"')";

                    Statement stmt=conn.createStatement();  //数据库操作对象
                    ResultSet res=stmt.executeQuery(sql_select);   //执行查询
                    int flag1=0;
                    while (res.next()) {
                        flag1++;
                    }
                    if(flag1==1) {
                        JOptionPane.showMessageDialog(null,"该账户已被注册，请重新输入账号","警告",JOptionPane.WARNING_MESSAGE);
                    }else {
                        stmt.executeUpdate(sql_insert);
                        JOptionPane.showMessageDialog(null,"注册成功","提示",JOptionPane.INFORMATION_MESSAGE);
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

    /**
     * 返回登陆界面
     */
    class Event_ReturnLogin implements ActionListener{
        private Login login;
        private Register register;
        public Event_ReturnLogin(Login login,Register register) {
            this.login=login;
            this.register=register;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            this.login.setVisible(true);
            this.register.dispose();    //只释放当前窗口资源
        }
    }
}


