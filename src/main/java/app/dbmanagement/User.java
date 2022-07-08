package app.dbmanagement;

public class User {
    private String login;
    private String password;
    private String study_group;

    public User(String login, String password, String study_group) {
        this.login = login;
        this.password = password;
        this.study_group = study_group;
    }

    public User() {

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudy_group() {
        return study_group;
    }

    public void setStudy_group(String study_group) {
        this.study_group = study_group;
    }
}
