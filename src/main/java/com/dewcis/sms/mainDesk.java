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

public class mainDesk extends JPanel implements MouseListener , ActionListener{
	Logger log = Logger.getLogger(mainDesk.class.getName());

	JSONObject loginResults = null;

	Vector<Vector<String>> rowData;
	Vector<String> columnNames;

	List<JButton> btns;
	List<JTextField> txfs;
	List<JComboBox> cmbs;
	
	Map<String, String> fields;
	
	JTabbedPane tabbedPane = new JTabbedPane();
	JTabbedPane searchPane = new JTabbedPane();
	
	JPanel nonRegPanel,	regPanel,	verifyPanel,	acInPanel,	logPanel;
	JPanel detailPanel, buttonPanel, fpPanel, camPanel, statusPanel,	devicePanel,	searchPanel;
	JTable tableReg,	tableNon,	tableIN,	tableLog;


	String sessionId = null;
	String[] eventCodeName,logListcode;

	public mainDesk(JSONObject loginResults) {
		super(new BorderLayout());
        this.loginResults = loginResults;
        
//         // Non Registred user panel
//         nonRegPanel = new JPanel(new BorderLayout());
//         tabbedPane.addTab("Not Registred", nonRegPanel);
        
//         //Registred user panel
//         regPanel = new JPanel(new BorderLayout());
//         tabbedPane.addTab("Registred", regPanel);
        
//         //activate Deactivate user panel
//         acInPanel = new JPanel(new BorderLayout());
//         tabbedPane.addTab("Inactivate Users", acInPanel);
        
//         //log user panel
//         logPanel = new JPanel(new BorderLayout());
//         tabbedPane.addTab("users Logs", logPanel);
        
//         rowData = new Vector<Vector<String>>();
//         columnNames = new Vector<String>();
//         columnNames.add("Date Time"); columnNames.add("Device ID"); columnNames.add("Device Name");
//         columnNames.add("Entity ID"); columnNames.add("User Name");columnNames.add("Event");
        
//         logModel = new DefaultTableModel(rowData,columnNames);
//         tableLog = new JTable(logModel);
//         JScrollPane scrollPanereg = new JScrollPane(tableLog);
//         logPanel.add(scrollPanereg, BorderLayout.CENTER);
        
//         // Getting details from the config.txt from class base_url
//         base_url base = new base_url();
//         Map<String, String> map = base.base_url();

// 		String baseUrl 		= map.get("baseUrl");
// 		String apiName		= map.get("apiName"); 
// 		String userId 		= map.get("user_name"); 
// 		String password		= map.get("user_password");
// 		// Login to device
//         Device log = new Device();
//         sessionId = log.login(apiName, userId, password,baseUrl);
        
//         // Add students on table
// //        Timer timer = new Timer(1000, new TimerListener());
// //        timer.start();
//         getStudents();
//         getLogs();
        
        //Verify user panel
        btns = new ArrayList<JButton>();
        txfs = new ArrayList<JTextField>();
        cmbs = new ArrayList<JComboBox>();
        
        verifyPanel = new JPanel(null);
        devicePanel = new JPanel(null);
        devicePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Verify User"));
        devicePanel.setBounds(5, 5, 500, 50);
        verifyPanel.add(devicePanel);
        
        addDevice("Device ID ", "541612052", 10, 20, 100, 20, 200);
        
        searchPanel = new JPanel(null);
        searchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Search Logs"));
        searchPanel.setBounds(5, 100, 800, 150);
        verifyPanel.add(searchPanel);
        
        addSearch("Device ID ", "541612052", 10, 20, 120, 20, 300);
        addSearch("Start Date ", "2018-08-01", 10, 50, 120, 20, 300);
        addSearch("End Date ", "2018-08-31",10, 80, 120, 20, 300);
        // addCombox("Event names ", eventCodeName,10, 110, 120, 20, 300);
        
        // Butons panel
        buttonPanel = new JPanel(null);
        buttonPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Functions"));
        buttonPanel.setBounds(5, 300, 800, 70);
        verifyPanel.add(buttonPanel);
        
        addButton("Verify", 350, 20, 75, 25, true);
        addButton("Search", 450, 20, 75, 25, true);
        
        tabbedPane.addTab("Verify User / Search logs", verifyPanel);
        
        super.add(tabbedPane, BorderLayout.CENTER);
            
	}

	
	// public void getStudents() {
          

 //        String mySql = "SELECT s.studentid, s.studentname, s.telno, s.email, e.entity_id "
 //                + "FROM students s INNER JOIN entitys e ON s.studentid = e.user_name "
 //                + "WHERE s.telno is not null AND s.email is not null LIMIT 200";
        
 //        fields = new HashMap<String, String>();
 //        fields.put("studentid", "Student ID");
 //        fields.put("studentname", "Student Name");
 //        fields.put("telno", "Tel No");
 //        fields.put("email", "EMail");
 //        fields.put("entity_id", "Entity ID");
        
 //        //Creating table for Non Registered students.
 //        tNonRegModel = new tableModel();
 //        tNonRegModel.makeTableNon(db, mySql, fields,sessionId);
 //        tableNon = new JTable(tNonRegModel);
 //        tableNon.addMouseListener(this);
 //        JScrollPane scrollPane = new JScrollPane(tableNon);
 //        nonRegPanel.add(scrollPane, BorderLayout.CENTER);
        
 //        //Creating table for Registered students
 //        tModel = new tableModel();
 //        tModel.makeTableReg(db, mySql, fields,sessionId);
 //        tableReg = new JTable(tModel);
 //        tableReg.addMouseListener(this);
 //        JScrollPane scrollPanereg = new JScrollPane(tableReg);
 //        regPanel.add(scrollPanereg, BorderLayout.CENTER);

 //        //Creating table for Inactive students
 //        tINModel = new tableModel();
 //        tINModel.makeTableIN(db, mySql, fields,sessionId);
 //        tableIN = new JTable(tINModel);
 //        tableIN.addMouseListener(this);
 //        JScrollPane scrollPaneInAc = new JScrollPane(tableIN);
 //        acInPanel.add(scrollPaneInAc, BorderLayout.CENTER);
	// }

	public void addButton(String btTitle, int x, int y, int w, int h, boolean enabled) {
		JButton btn = new JButton(btTitle);
		btn.setBounds(x, y, w, h);
		btn.addActionListener(this);
		btn.setEnabled(enabled);
		buttonPanel.add(btn);
		btns.add(btn);
	}

	public void addDevice(String fieldTitle, String fieldValue, int x, int y, int w, int h, int dw) {
		JLabel lbTitle = new JLabel(fieldTitle + " : ");
		lbTitle.setBounds(x, y, w, h);
		devicePanel.add(lbTitle);
		
		JTextField tfDevice = new JTextField();
		tfDevice.setBounds(x + w + 5, y, dw, h);
		tfDevice.setText(fieldValue);
		devicePanel.add(tfDevice);
		txfs.add(tfDevice);
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

	// public void addCombox(String fieldTitle, String[] fieldValue, int x, int y, int w, int h, int dw) {
	// 	JLabel lbTitle = new JLabel(fieldTitle + " : ");
	// 	lbTitle.setBounds(x, y, w, h);
	// 	searchPanel.add(lbTitle);
		
	// 	JComboBox cmbValues = new JComboBox(fieldValue);
	// 	cmbValues.setBounds(x + w + 5, y, dw, h);
	// 	searchPanel.add(cmbValues);
	// 	cmbs.add(cmbValues);
	// }
	
	
	public void mousePressed(MouseEvent ev) {}
	public void mouseReleased(MouseEvent ev) {}
	public void mouseEntered(MouseEvent ev) {}
	public void mouseExited(MouseEvent ev) {}

	public void mouseClicked(MouseEvent ev) {
		// // for getting the current JTabbedPane that is active
		// int selectedIndex = tabbedPane.getSelectedIndex();
		// if(selectedIndex == 0) {
		// 	// Selected Row in the Non Registerd users in the first JTabbedPane called "Non Registred".
		// 	System.out.println("selected tab Index is: " + selectedIndex);
		// 	int bRow = tableNon.getSelectedRow();
		// 	if ((bRow != -1) && (ev.getClickCount() == 2)) {
		// 		int index = tableNon.convertRowIndexToModel(bRow);
		// 		enrollDesk eDesk = new enrollDesk(tNonRegModel.getTitles(), tNonRegModel.getRowValues(index), sessionId);
		// 	}
			
		// }else if(selectedIndex == 1) {
		// 	// Selected Row in the Registerd users in the second JTabbedPane called "Registred".
		// 	System.out.println("selected tab Index is: " + selectedIndex);
		// 	int aRow = tableReg.getSelectedRow();
		// 	if ((aRow != -1) && (ev.getClickCount() == 2)) {
		// 		int index = tableReg.convertRowIndexToModel(aRow);
		// 		registerDesk rDesk = new registerDesk(tModel.getTitles(), tModel.getRowValues(index), sessionId);
		// 		// frame.revalidate();
		// 		// frame.repaint();
		// 	}
		// }else if(selectedIndex == 2) {
		// 	// Selected Row in the Active/Inactive users in the third JTabbedPane called "Activate / Inactivate Users ".
		// 	System.out.println("selected tab Index is: " + selectedIndex);
		// 	int cRow = tableIN.getSelectedRow();
		// 	if ((cRow != -1) && (ev.getClickCount() == 2)) {
		// 		int index = tableIN.convertRowIndexToModel(cRow);
		// 		Vector<String> rowData = tINModel.getRowValues(index);
  //                               String user_id = rowData.get(2);
		// 		Device dev = new Device();
		// 		String userResults = dev.userDetails(user_id,sessionId);

		// 		activateDesk aDesk = new activateDesk(tINModel.getTitles(), userResults, sessionId);
		// 	}
		// }
	}

	public void actionPerformed(ActionEvent ev) {

	// 	if(ev.getActionCommand().equals("Verify")) {
			
	// 		Clock c = Clock.systemUTC();  
	// 		Duration d = Duration.ofSeconds(-15);  
	// 		Clock clock = Clock.offset(c, d);    

	// 		JSONArray jEvent = new JSONArray();
	// 		JSONObject jEventDetails = new JSONObject();
	// 		jEventDetails.put("device_id", txfs.get(0).getText());
	// 		jEventDetails.put("end_datetime", c.instant());
	// 		jEventDetails.put("start_datetime",clock.instant());
	// 		jEvent.put(jEventDetails);

	// 		JSONArray jEventCode = new JSONArray();
	// 		jEventCode.put(4865);
	// 		JSONObject jSearchEvent = new JSONObject();
	// 		jSearchEvent.put("device_query_list", jEvent);
	// 		jSearchEvent.put("event_type_code_list", jEventCode);
	// 		jSearchEvent.put("limit", 0);
	// 		jSearchEvent.put("offset", 0);

	// 		Device dev = new Device();
	// 		String eventlogView = dev.searchLogEvent(jSearchEvent,sessionId);

	// 		JSONObject jsonObject = new JSONObject(eventlogView);
	// 		JSONArray tsmresponse = (JSONArray) jsonObject.get("records");
	// 		if (!tsmresponse.isNull(0)) {
 //                String userID =null;
 //                for(int i=0; i<tsmresponse.length(); i++){
 //                    userID = Integer.toString(tsmresponse.getJSONObject(i).getJSONObject("user").getInt("user_id"));
 //                }
 //                String userResults = dev.userDetails(userID,sessionId);
 //                VerifyDesk ver = new VerifyDesk(userResults);
                
 //            }else{
            
 //                JOptionPane.showMessageDialog(null, "User Not found");
 //            }

	// 	}

	// 	if(ev.getActionCommand().equals("Search")) {
	// 		String eventLOG=null;
	// 		int eventLogName = cmbs.get(0).getSelectedIndex();
	// 		if(0!=eventLogName)
	// 		{   
	// 			eventLOG = logListcode[eventLogName];
	// 			JSONArray jCode = new JSONArray();
	// 			jCode.put(eventLOG);

	// 			JSONArray jEvent = new JSONArray();
	// 			JSONObject jEventDetails = new JSONObject();
	// 			jEventDetails.put("device_id", txfs.get(1).getText());
	// 			jEventDetails.put("end_datetime", txfs.get(3).getText()+"T23:59:00.00Z");
	// 			jEventDetails.put("start_datetime", txfs.get(2).getText()+"T00:00:00.00Z");
	// 			jEvent.put(jEventDetails);

	// 			JSONObject jSearchEvent = new JSONObject();
	// 			jSearchEvent.put("device_query_list", jEvent);
	// 			jSearchEvent.put("event_type_code_list", jCode);
	// 			jSearchEvent.put("limit", 0);
	// 			jSearchEvent.put("offset", 0);

	// 			Device dev = new Device();
	// 			String eventlogView = dev.searchLogEvent(jSearchEvent,sessionId);
	// 			searchLogDesk srch = new searchLogDesk(eventlogView);
	// 		}else if (0==eventLogName) {
	// 			JOptionPane.showMessageDialog(null, "You can't Search For None Log Events");
	// 		}
	// 	}
	}
}