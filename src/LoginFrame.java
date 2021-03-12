import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginFrame extends JFrame implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Container container = getContentPane();
    JLabel userLabel = new JLabel("USERNAME");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
    JButton resetButton = new JButton("RESET");
    JButton registerButton = new JButton("REGISTER");
    JCheckBox showPassword = new JCheckBox("Show Password");

    LoginFrame() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        userLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        userTextField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);
        loginButton.setBounds(50, 300, 100, 30);
        registerButton.setBounds(50, 350, 100, 30);
        resetButton.setBounds(200, 300, 100, 30);

    }

    public void addComponentsToContainer() {
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(registerButton);
        container.add(resetButton);
    }

    public void addActionEvent() {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
        registerButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String userText;
            String pwdText;
            userText = userTextField.getText();
            pwdText = new String(passwordField.getPassword());

            boolean loginResult = false;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("Dirver Loaded");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/login", "root", "root");
                Statement stmt = conn.createStatement();
                System.out.println("All Set");
                String query = String.format("SELECT * FROM user WHERE username='%s';", userText);
                ResultSet rs = stmt.executeQuery(query);

                if (rs.next()) {
                    String databasePassword = rs.getString("password");
                    if (databasePassword.equals(pwdText)) {
                        loginResult = true;
                    }
                }
                conn.close();
                stmt.close();
                rs.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            if (loginResult) {
                JOptionPane.showMessageDialog(this, "Login Successful");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }

        }
        if (e.getSource() == resetButton) {
            userTextField.setText("");
            passwordField.setText("");
        }
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }

        if (e.getSource() == registerButton) {
            Register frame = new Register();
            frame.setTitle("Register Form");
            frame.setBounds(10, 10, 370, 600);
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.setResizable(false);
            frame.setVisible(true);
        }
    }

}