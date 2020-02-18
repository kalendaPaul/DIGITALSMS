package com.dewcis.sms;

import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.awt.GridLayout;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.dewcis.swing.BImagePanel;
import org.json.JSONArray;
import org.json.JSONObject;

public class loginDesk implements ActionListener {
	Logger log = Logger.getLogger(loginDesk.class.getName());

	Connection db = null;

	JFrame frame;
	BImagePanel imagePanel;
	JPanel loginPanel;
	JLabel lUserName, lPassword, clearStatus, loginStatus;
	JTextField tfUserName;
	JPasswordField pwPassword;
	JButton btClear, btOkay;

	public static void main(String args[]) {
		loginDesk ld = new loginDesk();
	}

	public loginDesk() {
		imagePanel = new BImagePanel("/images/background.jpg");
		loginPanel = new JPanel(new GridLayout(0, 2, 2, 2));
		loginPanel.setOpaque(false);
		imagePanel.add(loginPanel);
		loginPanel.setLocation(250, 200);
		loginPanel.setSize(400, 120);

		lUserName = new JLabel("User Name : ");
		tfUserName = new JTextField(25);
		loginPanel.add(lUserName);
		loginPanel.add(tfUserName);
		
		lPassword = new JLabel("Password : ");
		pwPassword = new JPasswordField();
		
		pwPassword.setActionCommand("Login");
		pwPassword.addActionListener(this);
		loginPanel.add(lPassword);
		loginPanel.add(pwPassword);

		btClear = new JButton("Clear");
		btClear.addActionListener(this);
		btOkay = new JButton("Login");
		btOkay.addActionListener(this);
		loginPanel.add(btClear);
		loginPanel.add(btOkay);

		clearStatus = new JLabel();
		loginStatus = new JLabel();
		loginPanel.add(clearStatus);
		loginPanel.add(loginStatus);
		
		frame = new JFrame("SMS Project");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(imagePanel, BorderLayout.CENTER);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent ev) {
		String aKey = ev.getActionCommand();

		if("Clear".equals(aKey)) {
			tfUserName.setText("");
			pwPassword.setText("");
		} else if("Login".equals(aKey)) {
			String myPassword = new String(pwPassword.getPassword());

			JSONObject jLogin = new JSONObject();
			jLogin.put("username", tfUserName.getText());
			jLogin.put("password", myPassword);

			httpClient client = new httpClient();
			String url = "https://dtsvc.safaricom.com:8480/api/auth/login";// login uri
			String result = client.getTokens(url, jLogin.toString());// calling the login function then Getting Results containing tokens in  it
			
			
			if (result == null) {
				clearStatus.setText("Login error");
				loginStatus.setText("Invalid credentials");
			} else {
				JSONObject json = new JSONObject(result);
				frame.remove(imagePanel);

				mainDesk md = new mainDesk(json);
				frame.getContentPane().add(md, BorderLayout.CENTER);

				frame.revalidate();
				frame.repaint();
			}
		}
	}

	public void connectDB(String dbpath, String dbuser, String dbpassword) {
		try {
			db = DriverManager.getConnection(dbpath, dbuser, dbpassword);
		} catch (SQLException ex) {
			log.severe("Database connection Error. " + ex);
		}
	}

}
