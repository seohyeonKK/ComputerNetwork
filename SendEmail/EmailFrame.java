package SendEmail;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

enum TYPE{NORMAL, BODY}
public class EmailFrame {
    private JFrame frame = new JFrame("Sender Email");

    private InputPanel senderId = new InputPanel("Sender Id", "OK", TYPE.NORMAL);
    private InputPanel senderPw = new InputPanel("Sender Password", "OK", TYPE.NORMAL);
    private InputPanel senderServer = new InputPanel("Sender Server", "OK", TYPE.NORMAL);
    private InputPanel receiverID = new InputPanel("Receiver Id", "OK", TYPE.NORMAL);
    private InputPanel subject = new InputPanel("Subject", "OK", TYPE.NORMAL);
    private InputPanel body = new InputPanel("Body", "OK", TYPE.BODY);
    private JButton sendBtn = new JButton("Send");
    private JButton  resetBtn = new JButton("Reset");
    private JButton getFile = new JButton("Open file");

    private InputPanel attachment = new InputPanel("attachment", "OK", TYPE.NORMAL);
    private boolean send = false;
    private String filePath = "";


    public void createFrame() {
        frame.setSize(350, 450);
        Dimension frameSize = frame.getSize();
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((windowSize.width - frameSize.width) / 2,
                (windowSize.height - frameSize.height) / 2);

        frame.setLayout(new FlowLayout());
        frame.add(senderId.getPanel());
        frame.add(senderPw.getPanel());
        frame.add(senderServer.getPanel());
        frame.add(receiverID.getPanel());
        frame.add(subject.getPanel());
        frame.add(body.getPanel());
        frame.add(getFile);
        frame.add(sendBtn);
        frame.add(resetBtn);

        attachment.getTextArea().setColumns(20);
        frame.add(attachment.getPanel());

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sendBtn.addActionListener(new SendActionListener());
        resetBtn.addActionListener(new ResetActionListener());
        getFile.addActionListener(new OpenActionListener());
    }

    public String getSenderId() {
        return senderId.getInput(TYPE.NORMAL);
    }
    public String getSenderPw() {
        return senderPw.getInput(TYPE.NORMAL);
    }
    public String getSenderServer() {
        return senderServer.getInput(TYPE.NORMAL);
    }
    public String getReceiverId() {
        return receiverID.getInput(TYPE.NORMAL);
    }
    public String getSubject() {
        return subject.getInput(TYPE.NORMAL);
    }
    public String getBody() {
        return body.getInput(TYPE.BODY);
    }
    public boolean getSend() { return this.send; }
    public void setSend(boolean send) {
        this.send = send;
    }

    private boolean isFilled(){
        if (getSenderId().isEmpty() || getSenderPw().isEmpty() || getSenderServer().isEmpty()||
            getReceiverId().isEmpty() || getSubject().isEmpty() || getBody().isEmpty()){
            JOptionPane.showMessageDialog(null, "Please fill in all values", "warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    public String getFilePath() {
        return filePath;
    }

    class OpenActionListener implements ActionListener{

        private JFileChooser chooser;
        OpenActionListener() {
            chooser = new JFileChooser();
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            int ret = chooser.showOpenDialog(null);
            if (ret != JFileChooser.APPROVE_OPTION && filePath.isBlank()) {
                JOptionPane.showMessageDialog(null, "No file selected", "warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            File file = chooser.getSelectedFile();
            if (file != null && file.exists())
                filePath = file.getPath();
            if (!filePath.isBlank())
                attachment.getTextArea().setText(filePath);
            System.out.println("Selected File : " + filePath);
        }


    }

    class ResetActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            senderId.resetInput();
            senderPw.resetInput();
            senderServer.resetInput();
            receiverID.resetInput();
            subject.resetInput();
            body.resetInput();
            attachment.getTextArea().setText("");
            filePath = "";
        }
    }

    class SendActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isFilled())
                setSend(true);
        }
    }
}

