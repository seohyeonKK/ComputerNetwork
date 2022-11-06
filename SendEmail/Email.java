package SendEmail;

public class Email {
    private String subject;
    private String body;

    public String getBody() {
        return body;
    }

    public String getSubject() {
        return subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
