package SendEmail;

import javax.swing.*;

public class InputPanel {
    private JPanel panel;
    private JLabel text;
    private JTextField field;
    private JTextArea area;


    InputPanel(String text, String btn, TYPE type){
        panel = new JPanel();
        this.text = new JLabel(text);
        panel.add(this.text);

        field = new JTextField(12);
        area = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(area);
        area.setLineWrap(true);
        if (type == TYPE.NORMAL){
            panel.add(this.field);
        }
        if (type == TYPE.BODY){
            panel.add(scrollPane);
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public String getInput(TYPE type) {
        if (type == TYPE.NORMAL)
            return field.getText();
        if (type == TYPE.BODY)
            return area.getText();
        return null;
    }

    public void resetInput() {
        field.setText("");
        area.setText("");
    }
}
