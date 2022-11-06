package SendEmail;

public class Sender {
    private String id;
    private String password;
    private String server;
    private Integer port;

    public Integer getPort() {
        return port;
    }

    public String getServer() {
        return server;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
