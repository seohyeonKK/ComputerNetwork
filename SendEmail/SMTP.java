package SendEmail;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.util.*;

public class SMTP {


    Sender sender;
    List<Receiver> receivers;
    Email email;

    Socket socket;
    SSLSocketFactory sf;
    BufferedReader br;
    PrintWriter pw;
    String line;

    SMTP(Email email, Sender sender, List<Receiver> receivers) throws IOException {
        this.sender = sender;
        this.receivers = receivers;
        this.email = email;

        sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        socket = sf.createSocket(sender.getServer(), sender.getPort());
    }


    public static void main(String[] args) throws Exception {
        EmailFrame frame = new EmailFrame();
        frame.createFrame();

        while(true) {
            if (frame.getSend()) {
                frame.setSend(false);
                //set SendEmail.Sender
                Sender sender = new Sender();
                sender.setId(frame.getSenderId());
                sender.setPassword(frame.getSenderPw());
                sender.setServer(frame.getSenderServer());
                sender.setPort(465);

                // set SendEmail.Receiver
                List<Receiver> receivers = Receiver.parse(frame.getReceiverId());

                // set email
                Email email = new Email();
                email.setSubject(frame.getSubject());
                email.setBody(frame.getBody());
                email.setFilePath(frame.getFilePath());

                // set smtpSender
                SMTP smtpSender = new SMTP(email, sender, receivers);

                // smtp server connect
                smtpSender.CONNECT();

                //EHLO
                smtpSender.EHLO();

                //auth login
                smtpSender.AUTH();

                //MAIL FROM
                smtpSender.FROM();

                // MAIL TO
                smtpSender.TO();

                // DATA
                smtpSender.DATA();

                // close
                smtpSender.close();
            }
            Thread.sleep(1000);

        }
    }

    public void CONNECT() throws IOException {
        System.out.println("### Connection Start");
        pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String line = br.readLine();
        System.out.println(line);
        System.out.println();
    }

    public void EHLO() throws IOException {
        System.out.println("### EHLO 명령을 전송합니다.");
        pw.println("EHLO localhost");
        for(int i=0; i<6; i++) {
            String line = br.readLine();
            System.out.println(line);
        }
        System.out.println();
    }

    public void AUTH() throws IOException {
        System.out.println("### AUTH 명령을 전송합니다");
        pw.println("AUTH LOGIN");
        line = br.readLine();
        System.out.println(line);

        System.out.println("## 아이디 입력");

        String encodedFrom = Base64.getEncoder().encodeToString(sender.getId().getBytes());
        pw.println(encodedFrom);
        line=br.readLine();
        System.out.println("응답:"+line);

        System.out.println("## 비밀번호 입력");
        String encodedPw = Base64.getEncoder().encodeToString(sender.getPassword().getBytes());
        pw.println(encodedPw);
        line=br.readLine();
        System.out.println("응답:"+line);
        System.out.println();



    }
    public void FROM() throws IOException {
        System.out.println("### MAIL FROM 명령을 전송합니다.");
        pw.println("MAIL FROM:<"+sender.getId()+">");
        line=br.readLine();
        System.out.println("응답:"+line);
        System.out.println();
    }

    public void TO() throws IOException {
        System.out.println("### RCPT 명령을 전송합니다.");
        for(Receiver r : receivers) {
            pw.println("RCPT TO:<" + r.getId() + ">");
        }
        line = br.readLine();
        System.out.println("응답:"+line);
        System.out.println();
    }

    private static byte[] getFileBinary(String filepath) {
        File file = new File(filepath);
        byte[] data = new byte[(int) file.length()];
        try (FileInputStream stream = new FileInputStream(file)) {
            stream.read(data, 0, data.length);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return data;
    }

    public void DATA() throws IOException {
        Scanner sc = new Scanner(System.in);

        if(sender.getId().split("@")[1].equals("gmail.com")){
            line = br.readLine();
            System.out.println(line);
            line = br.readLine();
            System.out.println(line);
        }

        System.out.println("### DATA 명령을 전송합니다.");
        pw.println("DATA");
        line=br.readLine();
        System.out.println("응답:"+line);
        System.out.println();

        System.out.println("### 내부 내용 설정");
        System.out.println("FROM 설정.");
        pw.println("FROM: " + sender.getId());

        System.out.println("TO 설정.");
        String toHeader = "";
        int size = receivers.size();
        for(int i = 0; i < size - 1; i++){
            toHeader += receivers.get(i).getId() + ", ";
        }
        if (size > 0)
            toHeader += receivers.get(size - 1).getId();
        pw.println("TO: " + toHeader);

        System.out.println("SUBJECT 설정.");
        pw.println("SUBJECT:" + email.getSubject());

        System.out.println("본문을 전송합니다.");
        String filePath = email.getFilePath();

        pw.println("Content-Type:multipart/mixed;boundary=\"wpqkfdydh1234214\"");
        pw.print("\r\n");
        pw.println("--wpqkfdydh1234214");
        pw.println("Content-Type:text/plain;charset=UTF-8");
        pw.println("Content-Disposition:inline");
        pw.print("\r\n");
        pw.println(email.getBody());
        if (!filePath.isBlank()) {
            String filename = filePath.substring(filePath.lastIndexOf('/' ) + 1);
            byte[] binary = getFileBinary(filePath);
            String base64data = Base64.getEncoder().encodeToString(binary);
            pw.println("--wpqkfdydh1234214");
            pw.println("Content-Type:application/octet-stream; name=" + filename);
            pw.println("Content-Transfer-Encoding:base64");
            pw.println("Content-Disposition:attachment;filename=" + filename+";");
            pw.print("\r\n");
            pw.println(base64data);
        }
        pw.println("--wpqkfdydh1234214--");
        pw.print("\r\n.\r\n");

        System.out.println("QUIT 명령을 전송합니다");
        pw.println("QUIT");

        line=br.readLine();
        System.out.println("응답:"+line);
        System.out.println();

        System.out.println("접속 종료합니다.");
    }

    public void close() throws IOException {
        br.close();
        pw.close();
        socket.close();
    }

}