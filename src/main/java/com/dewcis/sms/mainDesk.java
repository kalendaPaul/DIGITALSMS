package com.dewcis.sms;

import java.sql.Connection;

import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Image;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.JDesktopPane;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Clock;
import java.time.Duration;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import org.json.JSONArray;
import org.json.JSONObject;

public class mainDesk extends JPanel implements  ActionListener{
	Logger log = Logger.getLogger(mainDesk.class.getName());

	JSONObject loginResults = null;
	Device logConn = new Device();

	Vector<Vector<String>> rowData;
	Vector<String> columnNames;

	List<JButton> btns;
	List<JTextField> txfs;
	List<JComboBox> cmbs;
	List<String> lbls;
	List<JLabel> msg;
	
	Map<String, String> userFields;
	
	JTabbedPane tabbedPane = new JTabbedPane();
	JTabbedPane searchPane = new JTabbedPane();
	
	JPanel nonRegPanel,	regPanel,	mainPanel,	acInPanel,	logPanel, btaddPanel, ptbtnPanel;
	JPanel detailPanel, buttonPanel, fpPanel, camPanel, statusPanel,	phoneNoPanel,	searchPanel;
	JTable tableReg,	tableNon,	tableIN,	tableLog;


	String sessionId = null;
	String[] eventCodeName,logListcode;

	public mainDesk(JSONObject loginResults) {
		super(new BorderLayout());
        this.loginResults = loginResults;
        
        userFields = new HashMap<String, String>();
        lbls = new ArrayList<String>();
        btns = new ArrayList<JButton>();
        txfs = new ArrayList<JTextField>();
        cmbs = new ArrayList<JComboBox>();
        
        mainPanel = new JPanel(null);
        phoneNoPanel = new JPanel(null);
        phoneNoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Enter User Phone Number"));
        phoneNoPanel.setBounds(5, 5, 500, 50);
        mainPanel.add(phoneNoPanel);
        
        addField(phoneNoPanel, "Msisdn", "Phone", 10, 20, 100, 20, 200);
        
        searchPanel = new JPanel(null);
        searchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Subscription Fields/ Ondemand Subscription"));
        searchPanel.setBounds(5, 100, 500, 110);
        mainPanel.add(searchPanel);
        
        addField(searchPanel, "OfferCode", "Offer Code", 10, 20, 120, 20, 300);
        addField(searchPanel, "LinkId", "Link ID", 10, 50, 120, 20, 300);
        addField(searchPanel, "CpId", "CP ID", 10, 80, 120, 20, 300);
        
        // Butons panel
        buttonPanel = new JPanel(new GridLayout(0, 5));
        
        addButton(buttonPanel, "Subscription",  150, 5, 70, 15, true);
		addButton(buttonPanel, "Unsubscribe",  450, 5, 70, 15, true);
		addButton(buttonPanel, "Send SMS",  450, 5, 70, 15, true);
		addButton(buttonPanel, "Bulk SMS",  450, 5, 70, 15, true);
		addButton(buttonPanel, "Refresh Token",  450, 5, 70, 15, true);

		ptbtnPanel = new JPanel(new BorderLayout());
		addPanel(mainPanel, ptbtnPanel, "",10, 250, 700, 30);
		ptbtnPanel.add(buttonPanel, BorderLayout.PAGE_END);

		// Status panel
		msg = new ArrayList<JLabel>();
		statusPanel = new JPanel(null);
		statusPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Status / Message "));
		statusPanel.setBounds(5, 300, 700, 100);
		mainPanel.add(statusPanel);

		addMessage("Message", 10, 10, 120, 20, 500);
        
        
        super.add(mainPanel, BorderLayout.CENTER);
            
	}

	public void addPanel(JPanel n1Panel, JPanel n2Panel, String pnTitle, int x, int y, int w, int h) {
		n2Panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), pnTitle));
		n2Panel.setBounds(x, y, w, h);
		n1Panel.add(n2Panel);
	}

	public void addMessage(String fieldTitle, int x, int y, int w, int h, int dw) {
		JLabel lbTitle = new JLabel(fieldTitle + " : ");
		lbTitle.setBounds(x, y, w, h);
		statusPanel.add(lbTitle);
		
		JLabel lbValue = new JLabel();
		lbValue.setBounds(x + w + 10, y, dw, h);
		statusPanel.add(lbValue);
		msg.add(lbValue);
	}

	public void addField(JPanel mypanel, String fieldKey, String fieldTitle, int x, int y, int w, int h, int dw) {
		JLabel lbTitle = new JLabel(fieldTitle + " : ");
		lbTitle.setBounds(x, y, w, h);
		mypanel.add(lbTitle);
		lbls.add(fieldKey);
		
		JTextField tfValue = new JTextField();
		tfValue.setBounds(x + w + 10, y, dw, h);
		mypanel.add(tfValue);
		txfs.add(tfValue);
	} 

	public void addButton(JPanel nPanel, String btTitle, int x, int y, int w, int h, boolean enabled) {
		JButton btn = new JButton(btTitle);
		btn.setBounds(x, y, w, h);
		btn.addActionListener(this);
		btn.setEnabled(enabled);
		nPanel.add(btn);
		btns.add(btn);
	}

	public void addSearch(String fieldTitle, String fieldValue, int x, int y, int w, int h, int dw) {
		JLabel lbTitle = new JLabel(fieldTitle + " : ");
		lbTitle.setBounds(x, y, w, h);
		searchPanel.add(lbTitle);
		
		JTextField tfDevice = new JTextField();
		tfDevice.setBounds(x + w + 5, y, dw, h);
		tfDevice.setText(fieldValue);
		searchPanel.add(tfDevice);
		txfs.add(tfDevice);
	}

	public void addTxtLbs(){
		// System.out.println("the user acor id = " + lbls.size() );
			userFields.clear();
            for (int x=0; x<lbls.size(); x++) {
                    userFields.put(lbls.get(x), txfs.get(x).getText());
            }
	}
	

	public void actionPerformed(ActionEvent ev) {

		if(ev.getActionCommand().equals("Subscription")) {
			addTxtLbs();
			String feedback = logConn.subscr(loginResults, userFields);
			JSONObject jResults = new JSONObject(feedback);
			if (jResults.has("message")) {
				msg.get(0).setText(jResults.getString("message"));
			}else{
				msg.get(0).setText(jResults.get("responseParam").toString());
			}
			
		}

		if(ev.getActionCommand().equals("Unsubscribe")) {
			addTxtLbs();
			String feedback = logConn.unsubscr(loginResults, userFields);
			JSONObject jResults = new JSONObject(feedback);
			if (jResults.has("message")) {
				msg.get(0).setText(jResults.getString("message"));
			}else{
				msg.get(0).setText(jResults.get("responseParam").toString());
			}
		}

		if(ev.getActionCommand().equals("Send SMS")) {
			addTxtLbs();
			String feedback = logConn.sendSms(loginResults, userFields);
			JSONObject jResults = new JSONObject(feedback);
			if (jResults.has("message")) {
				msg.get(0).setText(jResults.getString("message"));
			}else{
				msg.get(0).setText(jResults.get("responseParam").toString());
			}
		}

		if(ev.getActionCommand().equals("Bulk SMS")) {
			String feedback = logConn.bulkSms(loginResults, userFields);
			JSONObject jResults = new JSONObject(feedback);
			if (jResults.has("message")) {
				msg.get(0).setText(jResults.getString("message"));
			}else{
				msg.get(0).setText(feedback);
			}
		}

		if(ev.getActionCommand().equals("Refresh Token")) {
			String refreshToken = logConn.rfToken(loginResults.getString("refreshToken"));
			if(refreshToken.length()!=0){
				loginResults.remove("token");
				JSONObject jResults = new JSONObject(refreshToken);
				loginResults.put("token", jResults.getString("token"));
				msg.get(0).setText("Token has been Refreshed");
			}else if (refreshToken.length()==0) {
				msg.get(0).setText("Token has not yet Expered");
			}
			
		}

	}
}