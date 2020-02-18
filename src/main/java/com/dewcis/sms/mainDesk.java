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
        searchPanel.setBounds(5, 100, 800, 150);
        mainPanel.add(searchPanel);
        
        addField(searchPanel, "OfferCode", "Offer Code", 10, 20, 120, 20, 300);

        // addCombox("Event names ", eventCodeName,10, 110, 120, 20, 300);
        
        // Butons panel
        buttonPanel = new JPanel(new GridLayout(0, 4));
        
        addButton(buttonPanel, "Subscription",  150, 5, 70, 15, true);
		addButton(buttonPanel, "Unsubscribe",  450, 5, 70, 15, true);
		addButton(buttonPanel, "Send SMS",  450, 5, 70, 15, true);
		addButton(buttonPanel, "Bulk SMS",  450, 5, 70, 15, true);

		ptbtnPanel = new JPanel(new BorderLayout());
		addPanel(mainPanel, ptbtnPanel, "",10, 500, 600, 30);
		ptbtnPanel.add(buttonPanel, BorderLayout.PAGE_END);
        
        // tabbedPane.addTab("Verify User / Search logs", mainPanel);
        
        super.add(mainPanel, BorderLayout.CENTER);
            
	}

	public void addPanel(JPanel n1Panel, JPanel n2Panel, String pnTitle, int x, int y, int w, int h) {
		n2Panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), pnTitle));
		n2Panel.setBounds(x, y, w, h);
		n1Panel.add(n2Panel);
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
			logConn.subscr(loginResults, userFields);
		}

		if(ev.getActionCommand().equals("Unsubscribe")) {
			logConn.unsubscr(loginResults, userFields);
		}

		if(ev.getActionCommand().equals("Send SMS")) {
			logConn.sendSms(loginResults, userFields);
		}

		if(ev.getActionCommand().equals("Bulk SMS")) {
			logConn.bulkSms(loginResults, userFields);
		}

	}
}