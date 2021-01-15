/**
 * 用户信息类
 */
public class UserInfo {
    private String username;
    private String nickname;
    private String mail;
    private String password;

    public UserInfo(String u, String n, String m, String p) {
        this.username=u;
        this.nickname=n;
        this.mail=m;
        this.password=p;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }
}
