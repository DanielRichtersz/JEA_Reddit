package danielrichtersz.models;

public class TestRedditor {

    private String password;
    private Long id;
    private String username;

    public TestRedditor(String name, String password, Long id) {
        this.id = id;
        this.username = name;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }
}
