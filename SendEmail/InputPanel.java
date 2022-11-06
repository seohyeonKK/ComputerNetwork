package SendEmail;

import javax.swing.*;

public class InputPanel {
    private JPanel panel;
    private JLabel text;
    private JTextField field;
    private JTextArea area;
    private JPasswordField password;


    InputPanel(String text, String btn, TYPE type){
        panel = new JPanel();
        this.text = new JLabel(text);
        panel.add(this.text);

        field = new JTextField(12);
        password = new JPasswordField( 12);
        area = new JTextArea(5, 20);

        if (type == TYPE.NORMAL){
            panel.add(this.field);
        }
        if (type == TYPE.PASSWORD){
            panel.add(this.password);
        }
        if (type == TYPE.BODY){
            panel.add(this.area);
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public String getInput(TYPE type) {
        if (type == TYPE.NORMAL)
            return field.getText();
        if (type == TYPE.PASSWORD)
            return password.getPassword().toString();
        if (type == TYPE.BODY)
            return area.getText();
        return null;
    }

    public void resetInput() {
        field.setText("");
        password.setText("");
        area.setText("");
    }
}
