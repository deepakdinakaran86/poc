package com.pcs.saffron.g2021.SimulatorUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.saffron.g2021.SimulatorEngine.CS.app.AppEngineImpl;
import com.pcs.saffron.g2021.SimulatorEngine.CS.config.SimulatorConfiguration;
import com.pcs.saffron.g2021.SimulatorEngine.CS.exceptions.ConfigurationException;
import com.pcs.saffron.g2021.SimulatorEngine.CS.listener.ServerMessageListener;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.Points;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.PointsDiscoveryMesasge;
import com.pcs.saffron.g2021.SimulatorEngine.CS.serverMessageHandler.ServerMessageNotifier;
import com.pcs.saffron.g2021.SimulatorEngine.CS.session.SessionInfo;
import com.pcs.saffron.g2021.SimulatorEngine.CS.tcpClient.TCPClient;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.AppTranistion;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.AppTrasition;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.ConversionUtils;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.SequenceNoGenerator;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.ServerResponseStatus;
import com.pcs.saffron.g2021.SimulatorEngine.DS.schedular.DataServer;

/**
 *
 * @author Santhosh
 */
@SuppressWarnings("serial")
public class SimulatorGUI extends JFrame implements ServerMessageListener,TreeSelectionListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimulatorGUI.class);

	private	JTabbedPane tabbedPane;
	private	JPanel mainConfigPanel;
	private	JPanel pointsPanel;
	private JFrame frame;
	private JPanel topPanel;
	private JPanel configPanel;

	//cs panel
	private JPanel csPanel;
	private JLabel lblCSHost;
	private JLabel lblStatus;
	private JLabel lblCsMacId;
	private JLabel lblBrowse;
	private JLabel lblCSPort;
	private static JTextArea csStatus;
	private JButton btnUpload;
	private JLabel lblCSSstatus;
	private JButton btnInfo;
	private static JButton btnStartCS;
	private static JButton btnStopCS;
	private static JTextField csMacIdField;
	private static JTextField csHostField;
	private JTextField uploadedFileField;
	private static JTextField csPortField;
	private JTextArea textAreaInfo;

	//ds panel
	private JPanel dsPanel; 
	private JLabel lblDSHost;
	private JLabel lblDSType;
	private JLabel lblDSPort;
	private JButton btnDSStop;
	private JLabel lblDSStatus;
	private JTextArea dsStatus;
	private JTextField dsHostField;
	private JTextField dsTypeField;
	private JTextField dsPortField;

	//txn panel 
	private JPanel txnPanel;
	private JLabel lblCurrentState;
	private JLabel lblLastServerResponse;
	private JLabel lblLastMsgEDCP;
	private JLabel lblCurrentMode;
	private JLabel lblCurrentSeqNo;
	private JLabel lblSessionId;
	private JLabel lblUnitId;
	public static JTextField txSessionId;
	private static JTextField txSeqNo;
	public static JTextField txMsgEDCP;
	public static JTextField txCurrentMode;
	public static JTextField txServerResponse;
	public static JTextField txCurrentState;
	public static JTextField txUnitId;

	//console panel
	private JPanel console_panel;
	private JButton btnClearLogs;
	private JTextArea txMsgConsole;	

	private JTree tree;
	private DefaultMutableTreeNode rootNode;

	// Runtime configurations
	private static Properties prop;	
	private static int connectionStatusCS = ConnectionStatus.CS_DISCONNECTED.getValue();
	private int connectionStatusDS = ConnectionStatus.DS_DISCONNECTED.getValue();
	private static int currentSeqNo = 0;
	private static boolean csConncetionAvailability = false;
	private static boolean dsConncetionAvailabilty = false;
	public static volatile  String notifictionMessage = null;
	public static byte[] csServerResponse;
	public static volatile boolean notifyCheck = false;
	public static int retrialCount = 0;
	public static int retrailTime = 0;
	public static int pointsSync = 0;
	private static long timeBasedSchedulingTime = 0;
	private static long stateChangeSchedulingTime = 0;
	private static long covSchedulingTime = 0;
	public static boolean fileToBeProcessed = false;
	public static PointsDiscoveryMesasge pointDiscovry = null;
	private JFileChooser fc;
	private File file = null;
	private String fileContent = "";
	private static Thread th;

	public final static String statusMessages[] = { "Disconnected", "Connected", "Connecting..." };




	/**
	 * Creates new form SimulatorGUI
	 */
	public SimulatorGUI() {

		setResizable(false);
		getContentPane().setBackground(SystemColor.activeCaption);

		frame = new JFrame();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("G2021 Simulator");
		setForeground(java.awt.Color.white);

		topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel );


		mainConfigPanel = new JPanel();
		mainConfigPanel.setBackground(SystemColor.activeCaption);
		mainConfigPanel.setLayout( null );		

		pointsPanel = new JPanel();
		pointsPanel.setBackground(SystemColor.activeCaption);

		// Create a tabbed pane
		tabbedPane = new JTabbedPane();
		tabbedPane.setBackground(SystemColor.activeCaption);
		tabbedPane.addTab( "ServerConfig", mainConfigPanel );

		setSize(new Dimension(1044, 774));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		initComponents();
		loadPropertyFile();
		createPage1();
		createPage2();
		setDefaults();
		ServerMessageNotifier.getInstance().addListener(this);
		fc = new JFileChooser();
	}

	private void initComponents() {

		AppTranistion.setReason(AppTrasition.POWER_UP);		

		configPanel = new JPanel();
		configPanel.setBorder(new TitledBorder(null, "Configurations", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		configPanel.setBackground(SystemColor.activeCaption);
		configPanel.setBounds(0, 0, 656, 355);
		mainConfigPanel.add(configPanel);

		csPanel = new JPanel();
		csPanel.setBorder(new TitledBorder(null, "Control Server", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		csPanel.setBackground(SystemColor.activeCaption);

		lblCSHost = new JLabel("Host :");

		lblStatus = new JLabel("Status :");

		lblCsMacId = new JLabel("mac Id :");

		lblBrowse = new JLabel("Browse :");

		csMacIdField = new JTextField();
		csMacIdField.setText((String) null);

		csHostField = new JTextField();
		csHostField.setText((String) null);

		lblCSPort = new JLabel("Port");

		csStatus = new JTextArea();
		csStatus.setEnabled(false);
		csStatus.setEditable(false);
		csStatus.setBackground(SystemColor.activeCaption);

		uploadedFileField = new JTextField();

		btnUpload = new JButton("Upload");
		btnUpload.setBackground(SystemColor.activeCaption);
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnUploadActionPerformed(e);
			}
		});

		lblCSSstatus = new JLabel();
		lblCSSstatus.setText("Status :");

		csPortField = new JTextField();
		csPortField.setText((String) null);

		btnInfo = new JButton("i");
		btnInfo.setBackground(SystemColor.activeCaption);
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnInfoActionPerformed(e);
			}
		});

		btnStartCS = new JButton("Start");
		btnStartCS.setBackground(SystemColor.activeCaption);
		btnStartCS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnStartCSActionPerformed(evt);
			}
		});

		btnStopCS = new JButton("Stop");		
		btnStopCS.setEnabled(false);
		btnStopCS.setBackground(SystemColor.activeCaption);
		btnStopCS.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnStopCSActionPerformed(evt);
			}
		});


		dsPanel = new JPanel();
		dsPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Data Server", TitledBorder.RIGHT, TitledBorder.TOP, null, null));
		dsPanel.setBackground(SystemColor.activeCaption);

		lblDSHost = new JLabel();
		lblDSHost.setText("Host :");

		dsHostField = new JTextField();
		dsHostField.setEnabled(false);
		dsHostField.setEditable(false);

		lblDSType = new JLabel("Type :");

		dsTypeField = new JTextField();
		dsTypeField.setEnabled(false);
		dsTypeField.setEditable(false);
		dsTypeField.setColumns(10);

		lblDSPort = new JLabel();
		lblDSPort.setText("Port :");

		dsPortField = new JTextField();
		dsPortField.setEnabled(false);
		dsPortField.setEditable(false);

		btnDSStop = new JButton("Stop");
		btnDSStop.setBackground(SystemColor.activeCaption);
		btnDSStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStopDSActionPerformed(e);
			}
		});

		lblDSStatus = new JLabel();
		lblDSStatus.setText("Status :");

		dsStatus = new JTextArea();
		dsStatus.setWrapStyleWord(true);
		dsStatus.setEnabled(false);
		dsStatus.setEditable(false);
		dsStatus.setBackground(SystemColor.activeCaption);

		txnPanel = new JPanel();
		txnPanel.setBorder(new TitledBorder(null, "Transactions", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		txnPanel.setBackground(SystemColor.activeCaption);
		txnPanel.setBounds(662, 0, 366, 355);
		mainConfigPanel.add(txnPanel);

		lblCurrentState = new JLabel("Current State :");

		lblLastServerResponse = new JLabel("Last Server Response :");

		lblLastMsgEDCP = new JLabel("Last Msg from EDCP :");

		lblCurrentMode = new JLabel("Current Mode :");

		lblCurrentSeqNo = new JLabel("Current Seq No :");

		lblSessionId = new JLabel("Session Id :");

		txSessionId = new JTextField();
		txSessionId.setEnabled(false);
		txSessionId.setEditable(false);
		txSessionId.setColumns(10);

		txSeqNo = new JTextField();
		txSeqNo.setText("0");
		txSeqNo.setEnabled(false);
		txSeqNo.setEditable(false);
		txSeqNo.setColumns(10);

		txMsgEDCP = new JTextField();
		txMsgEDCP.setText("NA");
		txMsgEDCP.setForeground(new Color(51, 51, 51));
		txMsgEDCP.setBackground(SystemColor.activeCaption);

		txCurrentMode = new JTextField();
		txCurrentMode.setEnabled(false);
		txCurrentMode.setEditable(false);
		txCurrentMode.setColumns(10);

		txServerResponse = new JTextField();
		txServerResponse.setText("NA");
		txServerResponse.setForeground(new Color(51, 51, 51));
		txServerResponse.setEditable(false);
		txServerResponse.setBackground(SystemColor.activeCaption);

		txCurrentState = new JTextField();
		txCurrentState.setText("NA");
		txCurrentState.setForeground(new Color(51, 51, 51));
		txCurrentState.setEditable(false);
		txCurrentState.setBackground(SystemColor.activeCaption);

		txUnitId = new JTextField();
		txUnitId.setEnabled(false);
		txUnitId.setEditable(false);
		txUnitId.setColumns(10);		
		lblUnitId = new JLabel("Unit Id :");	

	}

	public void createPage1()
	{		
		buildConfigLayout();		

		buildTxnLayout();	

		builkdConsoleLayout();		
	}

	public void createPage2()
	{
		tabbedPane.addTab( "Points", pointsPanel );

		rootNode =  new DefaultMutableTreeNode("NewPoints");

		//createNodes(top);

		tree = new JTree(rootNode);
		tree.setVisibleRowCount(30);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);		

		/*String path = "com/pcs/saffron/g2021/resources/images/point.jpg";
        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource(path));
        System.out.println(imageIcon);
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();       
        renderer.setLeafIcon(imageIcon);

        tree.setCellRenderer(renderer);*/

		tree.addTreeSelectionListener(this);

		PointTabGUI.pointConfigPanel = new JPanel();
		PointTabGUI.pointConfigPanel.setBorder(new TitledBorder(null, "Point Configuration", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		PointTabGUI.pointConfigPanel.setBackground(SystemColor.activeCaption);


		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new LineBorder(SystemColor.menu));		

		GroupLayout gl_points = new GroupLayout(pointsPanel);
		gl_points.setHorizontalGroup(
				gl_points.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_points.createSequentialGroup()
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(PointTabGUI.pointConfigPanel, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
						.addContainerGap())
				);
		gl_points.setVerticalGroup(
				gl_points.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
				.addGroup(gl_points.createSequentialGroup()
						.addContainerGap(264, Short.MAX_VALUE)
						.addComponent(PointTabGUI.pointConfigPanel, GroupLayout.PREFERRED_SIZE, 454, GroupLayout.PREFERRED_SIZE))
				);	

		tree.setBackground(SystemColor.inactiveCaption);
		scrollPane.setViewportView(tree);
		pointsPanel.setLayout(gl_points);
		topPanel.add(tabbedPane, BorderLayout.CENTER );

	}

	private void createNodes(DefaultMutableTreeNode top) {

		DefaultMutableTreeNode category = null;

		if(top.getChildCount() > 0){
			top.removeAllChildren();
		}

		createPointPanel();

		if(pointDiscovry != null){

			for (Points point : Arrays.asList(pointDiscovry.getPoints())) {
				category = new DefaultMutableTreeNode(point);
				top.add(category);
			}
		}		

	}

	private void createPointPanel() {	

		PointTabGUI.createPanelForPoints();
	}

	private void buildConfigLayout() {


		buildControlServerLayout();

		buildDataServerLayout();

		GroupLayout gl_configPanel = new GroupLayout(configPanel);
		gl_configPanel.setHorizontalGroup(
				gl_configPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 656, Short.MAX_VALUE)
				.addGroup(gl_configPanel.createSequentialGroup()
						.addComponent(csPanel, GroupLayout.PREFERRED_SIZE, 299, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(dsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(66, Short.MAX_VALUE))
				);
		gl_configPanel.setVerticalGroup(
				gl_configPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 355, Short.MAX_VALUE)
				.addGroup(gl_configPanel.createSequentialGroup()
						.addGroup(gl_configPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(csPanel, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)
								.addComponent(dsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap(12, Short.MAX_VALUE))
				);
		configPanel.setLayout(gl_configPanel);

	}

	private void buildControlServerLayout() {
		GroupLayout gl_csPanel = new GroupLayout(csPanel);
		gl_csPanel.setHorizontalGroup(
				gl_csPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 299, Short.MAX_VALUE)
				.addGroup(gl_csPanel.createSequentialGroup()
						.addGroup(gl_csPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_csPanel.createSequentialGroup()
										.addContainerGap()
										.addGroup(gl_csPanel.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_csPanel.createParallelGroup(Alignment.LEADING)
														.addComponent(lblCSHost)
														.addComponent(lblStatus)
														.addComponent(lblCsMacId))
												.addComponent(lblBrowse))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(gl_csPanel.createParallelGroup(Alignment.LEADING, false)
												.addComponent(csMacIdField, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_csPanel.createSequentialGroup()
														.addComponent(csHostField, 94, 94, 94)
														.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(lblCSPort))
												.addComponent(csStatus, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
												.addComponent(uploadedFileField, 110, 110, 110)
												.addGroup(gl_csPanel.createSequentialGroup()
														.addGap(45)
														.addComponent(btnUpload, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
										.addGroup(gl_csPanel.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_csPanel.createSequentialGroup()
														.addGap(218)
														.addComponent(lblCSSstatus))
												.addGroup(gl_csPanel.createSequentialGroup()
														.addGap(18)
														.addGroup(gl_csPanel.createParallelGroup(Alignment.LEADING)
																.addComponent(csPortField, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
																.addComponent(btnInfo)))))
								.addGroup(gl_csPanel.createSequentialGroup()
										.addGap(19)
										.addComponent(btnStartCS, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
										.addGap(60)
										.addComponent(btnStopCS, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		gl_csPanel.setVerticalGroup(
				gl_csPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 320, Short.MAX_VALUE)
				.addGroup(gl_csPanel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_csPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCSHost)
								.addComponent(csHostField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCSPort)
								.addComponent(csPortField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(26)
						.addGroup(gl_csPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCsMacId)
								.addComponent(csMacIdField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(27)
						.addGroup(gl_csPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCSSstatus)
								.addComponent(lblStatus)
								.addComponent(csStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(27)
						.addGroup(gl_csPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblBrowse)
								.addComponent(uploadedFileField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnInfo))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btnUpload, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_csPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnStartCS, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnStopCS, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
						.addGap(43))
				);
		csPanel.setLayout(gl_csPanel);
	}

	private void buildDataServerLayout() {
		GroupLayout gl_dsPanel = new GroupLayout(dsPanel);
		gl_dsPanel.setHorizontalGroup(
				gl_dsPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 269, Short.MAX_VALUE)
				.addGroup(gl_dsPanel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_dsPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_dsPanel.createSequentialGroup()
										.addGroup(gl_dsPanel.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_dsPanel.createSequentialGroup()
														.addComponent(lblDSHost)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(dsHostField, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_dsPanel.createSequentialGroup()
														.addComponent(lblDSType)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(dsTypeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblDSPort)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(dsPortField, 63, 63, 63))
								.addGroup(gl_dsPanel.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnDSStop, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_dsPanel.createSequentialGroup()
												.addComponent(lblDSStatus)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(dsStatus, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED))))
						.addContainerGap(24, Short.MAX_VALUE))
				);
		gl_dsPanel.setVerticalGroup(
				gl_dsPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 320, Short.MAX_VALUE)
				.addGroup(gl_dsPanel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_dsPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblDSHost)
								.addComponent(dsHostField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDSPort)
								.addComponent(dsPortField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(32)
						.addGroup(gl_dsPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblDSType)
								.addComponent(dsTypeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(33)
						.addGroup(gl_dsPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblDSStatus)
								.addComponent(dsStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(38)
						.addComponent(btnDSStop, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(86, Short.MAX_VALUE))
				);
		dsPanel.setLayout(gl_dsPanel);
	}


	private void buildTxnLayout() {
		GroupLayout gl_txnPanel = new GroupLayout(txnPanel);
		gl_txnPanel.setHorizontalGroup(
				gl_txnPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 366, Short.MAX_VALUE)
				.addGroup(gl_txnPanel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_txnPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_txnPanel.createSequentialGroup()
										.addGroup(gl_txnPanel.createParallelGroup(Alignment.LEADING)
												.addComponent(lblCurrentState)
												.addComponent(lblLastServerResponse)
												.addComponent(lblLastMsgEDCP)
												.addComponent(lblCurrentMode)
												.addComponent(lblCurrentSeqNo)
												.addComponent(lblSessionId))
										.addGap(18)
										.addGroup(gl_txnPanel.createParallelGroup(Alignment.LEADING)
												.addComponent(txSessionId, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
												.addComponent(txSeqNo, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
												.addComponent(txMsgEDCP, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
												.addGroup(gl_txnPanel.createParallelGroup(Alignment.LEADING, false)
														.addComponent(txCurrentMode, GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
														.addComponent(txServerResponse, GroupLayout.PREFERRED_SIZE, 174, Short.MAX_VALUE)
														.addComponent(txCurrentState))
												.addComponent(txUnitId, 174, 174, 174))
										.addGap(56))
								.addGroup(gl_txnPanel.createSequentialGroup()
										.addComponent(lblUnitId)
										.addContainerGap(305, Short.MAX_VALUE))))
				);
		gl_txnPanel.setVerticalGroup(
				gl_txnPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 355, Short.MAX_VALUE)
				.addGroup(gl_txnPanel.createSequentialGroup()
						.addGap(30)
						.addGroup(gl_txnPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(txCurrentMode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCurrentMode))
						.addGap(18)
						.addGroup(gl_txnPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCurrentState)
								.addComponent(txCurrentState, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_txnPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(txServerResponse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblLastServerResponse))
						.addGap(25)
						.addGroup(gl_txnPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblLastMsgEDCP)
								.addComponent(txMsgEDCP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_txnPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCurrentSeqNo)
								.addComponent(txSeqNo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_txnPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblUnitId)
								.addComponent(txUnitId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_txnPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSessionId)
								.addComponent(txSessionId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(52, Short.MAX_VALUE))
				);
		txnPanel.setLayout(gl_txnPanel);

	}

	public void loadPropertyFile() {

		prop = new Properties();
		InputStream inputStream = null;
		try {

			// loading properties file
			inputStream = this.getClass().getClassLoader()
					.getResourceAsStream("com/pcs/saffron/g2021/simulatorProperties/simulator.properties");
			prop.load(inputStream);
			csHostField.setText(prop.getProperty("hostIp"));
			csPortField.setText(prop.getProperty("port"));
			csMacIdField.setText(prop.getProperty("macId"));
			retrialCount = Integer.parseInt(prop.getProperty("retrialCount"));
			retrailTime = Integer.parseInt(prop.getProperty("retrailTime"));
			timeBasedSchedulingTime = Long.parseLong(prop.getProperty("timeBasedSchedulingTime"));
			stateChangeSchedulingTime = Long.parseLong(prop.getProperty("stateChangeSchedulingTime"));
			covSchedulingTime = Long.parseLong(prop.getProperty("covSchedulingTime"));
			inputStream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void builkdConsoleLayout() {
		console_panel = new JPanel();
		console_panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Message Logs", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		console_panel.setBackground(SystemColor.activeCaption);
		console_panel.setBounds(0, 366, 1028, 343);
		mainConfigPanel.add(console_panel);

		btnClearLogs = new JButton("Clear Logs");
		btnClearLogs.setBackground(SystemColor.activeCaption);

		btnClearLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnClearLogsActionPerformed(e);
			}
		});

		txMsgConsole = new JTextArea();
		txMsgConsole.setEditable(false);
		txMsgConsole.setLineWrap(true);
		txMsgConsole.setBackground(SystemColor.controlHighlight);

		DefaultCaret caret = (DefaultCaret)txMsgConsole.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

		JScrollPane logMsgscroller = new JScrollPane();
		logMsgscroller.setViewportBorder(UIManager.getBorder("Button.border"));

		logMsgscroller.setViewportView(txMsgConsole);


		PrintStream printStream = new PrintStream(new CustomOutputStream(txMsgConsole,logMsgscroller));
		System.setOut(printStream);
		System.setErr(printStream);

		GroupLayout gl_console_panel = new GroupLayout(console_panel);
		gl_console_panel.setHorizontalGroup(
				gl_console_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 1028, Short.MAX_VALUE)
				.addGroup(gl_console_panel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_console_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_console_panel.createSequentialGroup()
										.addComponent(btnClearLogs)
										.addGap(37))
								.addGroup(gl_console_panel.createSequentialGroup()
										.addComponent(logMsgscroller)
										.addContainerGap())))
				);
		gl_console_panel.setVerticalGroup(
				gl_console_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 343, Short.MAX_VALUE)
				.addGroup(gl_console_panel.createSequentialGroup()
						.addComponent(btnClearLogs)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(logMsgscroller, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
						.addContainerGap())
				);
		console_panel.setLayout(gl_console_panel);
	}

	private void setDefaults() {
		txCurrentState.setText("NA");
		txServerResponse.setText("NA");
		txMsgEDCP.setText("NA");
		txSeqNo.setText(Integer.toString(currentSeqNo));
	}

	private void btnUploadActionPerformed(ActionEvent e) {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Json Documents", "json");
		fc.setFileFilter(filter);
		int rVal = fc.showOpenDialog(SimulatorGUI.this);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			getPoints();
			if(pointDiscovry != null){
				if(PointDiscoveryValidator.validatePointsObject(pointDiscovry)){
					System.out.println("json validated....");
					fileToBeProcessed = true;
					uploadedFileField.setText(fc.getSelectedFile().getName());
					createNodes(rootNode);
				}else{
					JOptionPane.showMessageDialog(frame, "Please upload  valid points information", "Points Discovery Data",JOptionPane.WARNING_MESSAGE);
				}

			}else{
				fileToBeProcessed = false;
				JOptionPane.showMessageDialog(frame, "Json Parse Exception....\n Please upload  valid json", "Points Discovery Data",JOptionPane.WARNING_MESSAGE);
			}

		}
	}

	private void btnInfoActionPerformed(ActionEvent e) {
		if (fileToBeProcessed) {
			JScrollPane scrollPane = new JScrollPane(textAreaInfo);
			JOptionPane.showMessageDialog(frame, scrollPane, "Points Discovery Data", JOptionPane.PLAIN_MESSAGE);

		} else {
			JOptionPane.showMessageDialog(frame, "Please upload a file", "Points Discovery Data",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void btnStartCSActionPerformed(ActionEvent evt) {

		if(!validatePreconditions()){
			return;
		}

		connectionStatusCS = ConnectionStatus.CS_CONNECTING.getValue();
		csStatus.setText(statusMessages[connectionStatusCS]);
		btnStartCS.setEnabled(false);
		btnStopCS.setEnabled(true);
		csHostField.setEnabled(false);
		csPortField.setEnabled(false);
		csMacIdField.setEnabled(false);

		th = new Thread() {
			public void run() {
				try {
					csConncetionAvailability = AppEngineImpl.openClientConnection(csHostField.getText(),
							new Integer(csPortField.getText()));
					if (csConncetionAvailability) {
						connectionStatusCS = ConnectionStatus.CS_CONNECTED.getValue();
						csStatus.setText(statusMessages[connectionStatusCS]);
						csStatus.update(csStatus.getGraphics());
						txCurrentMode.setText(CurrentServer.CS.getValue());
						if (dsConncetionAvailabilty) {
							DataServerCloseConnection();
						}	
						EDCPHandShaking edcp = new EDCPHandShaking();
						if(edcp.startHandShakingMechannism()){
							switchToDataServer();
						}else{
							LOGGER.error("Handshake mechnism failed.. ");
							LOGGER.error(" stopping the present thread... ");
							SimulatorGUI.stopCsServer();
						}
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};

		th.start();    

	}

	private boolean validatePreconditions() {
		if (csHostField.getText().trim().isEmpty() || csPortField.getText().trim().isEmpty()
				|| csMacIdField.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Please Enter cross sever host address, port number and mac id of device ", "",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if( file == null){
			JOptionPane.showMessageDialog(frame, "Please upload points before start simulation ", "",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private void DataServerOpenConnection() {
		btnStartCS.setEnabled(true);
		btnStopCS.setEnabled(false);
		csHostField.setEnabled(true);
		csPortField.setEnabled(true);
		csMacIdField.setEnabled(true);
		connectionStatusCS = ConnectionStatus.CS_DISCONNECTED.getValue();
		connectionStatusDS = ConnectionStatus.DS_CONNECTED.getValue();
		csStatus.setText(statusMessages[connectionStatusCS]);
		dsStatus.setText(statusMessages[connectionStatusDS]);
		try {
			if (csConncetionAvailability){			
				btnStartCS.setEnabled(true);
				btnStopCS.setEnabled(false);
				csHostField.setEnabled(true);
				csPortField.setEnabled(true);
				csMacIdField.setEnabled(true);
				connectionStatusCS = ConnectionStatus.CS_DISCONNECTED.getValue();
				csStatus.setText(statusMessages[connectionStatusCS]);
				txCurrentMode.setText(CurrentServer.NONE.getValue());
				TCPClient.stopSimulator();
				csConncetionAvailability = false;			
			}

			if(!csConncetionAvailability){

				if(SessionInfo.getInstance().getDataServerHostType().intValue() == 0){
					dsHostField.setText(String.valueOf(SessionInfo.getInstance().getDataServerIp()));
				}
				else if(SessionInfo.getInstance().getDataServerHostType().intValue() == 1){
					dsHostField.setText(String.valueOf(SessionInfo.getInstance().getDataServerDomainName()));
				}else{
					dsHostField.setText(csHostField.getText());
				}
				dsPortField.setText(String.valueOf(SessionInfo.getInstance().getDport()));


				dsConncetionAvailabilty = TCPClient.openClientConnection(dsHostField.getText(),Integer.parseInt(dsPortField.getText()));
				if(dsConncetionAvailabilty){
					txCurrentMode.setText(CurrentServer.DS.getValue());
				}
			}
		}catch (NumberFormatException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void DataServerCloseConnection() {		
		try {
			dsHostField.setText("");
			dsPortField.setText("");
			dsTypeField.setText("");
			connectionStatusDS = ConnectionStatus.DS_DISCONNECTED.getValue();
			dsStatus.setText(statusMessages[connectionStatusDS]);
			txCurrentMode.setText(CurrentServer.NONE.getValue());
			txCurrentState.setText("");
			txMsgEDCP.setText("");
			txServerResponse.setText("");
			TCPClient.stopSimulator();
			DataServer.stopRealDataProcessing();
		}catch (ConfigurationException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void switchToDataServer(){
			DataServerOpenConnection();
			if(dsConncetionAvailabilty){
				DataServer ds = new DataServer();
				Vector<Points> v = new Vector<Points>();
				v.addAll(Arrays.asList(pointDiscovry.getPoints()));
				ds.setPointList(v);
				ds.setTimeBasedSchedulingTime(timeBasedSchedulingTime);
				ds.setStateChangeSchedulingTime(stateChangeSchedulingTime);
				ds.setCovSchedulingTime(covSchedulingTime);
				ds.setRetrialCount(retrialCount);
				ds.setRetrialTime(retrailTime);
				ds.startRealDataProcessing(ds);

			}
	}

	private void btnStopCSActionPerformed(ActionEvent evt) {
		stopCsServer();
	}

	public static void stopCsServer(){
		try {
			btnStartCS.setEnabled(true);
			btnStopCS.setEnabled(false);
			csHostField.setEnabled(true);
			csPortField.setEnabled(true);
			csMacIdField.setEnabled(true);
			connectionStatusCS = ConnectionStatus.CS_DISCONNECTED.getValue();
			csStatus.setText(statusMessages[connectionStatusCS]);
			txCurrentMode.setText(CurrentServer.NONE.getValue());
			txMsgEDCP.setText("");			
			txCurrentState.setText("");
			txServerResponse.setText("");			
			if(th != null){
				TCPClient.stopSimulator();				
				Thread.sleep(1000);
				th.interrupt();
			}

		} catch (ConfigurationException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {			
			//e.printStackTrace();
		}
	}

	private void btnStopDSActionPerformed(ActionEvent e) {
		DataServerCloseConnection();
	}

	private void btnClearLogsActionPerformed(ActionEvent e) {
		txMsgConsole.setText("");
	}

	public static void updateSequenceNo() {
		txSeqNo.setText(String.valueOf(SequenceNoGenerator.getSequenceNo()));
	}

	public static byte[] getHelloMsg() {
		SimulatorConfiguration msg = new SimulatorConfiguration();
		String asset = "asset"+csMacIdField.getText();
		int i=0;
		i++;
		String[] clientInfo = { "cummins", "otr", "bigg", null, null, null, null, null, "TRUCK", asset+i };
		List<String> clients = Arrays.asList(clientInfo);
		msg.setMacId(Long.valueOf(csMacIdField.getText(),36));
		msg.setClientInfo(clients);
		return AppEngineImpl.getHelloMsg(msg);		
	}

	private PointsDiscoveryMesasge getPoints(){
		ObjectMapper mapper = new ObjectMapper();	
		InputStream in = null;
		BufferedReader br = null;
		try {
			if(file != null){
				fileContent = "";
				in = new FileInputStream(file);				
				br = new BufferedReader(new InputStreamReader(in));
				String line;
				while ((line = br.readLine()) != null) {
					fileContent += line + " \n";
				}	
				pointDiscovry = mapper.readValue(fileContent,PointsDiscoveryMesasge.class);
				textAreaInfo = new JTextArea(20, 50);
				textAreaInfo.setText(fileContent);
				textAreaInfo.setEditable(false);
				in.close();
			}

		} catch (JsonParseException e) {
			LOGGER.error("JsonParseException ");
		} catch (JsonMappingException e) {
			LOGGER.error("JsonMappingException ");
		} catch (IOException e) {
			LOGGER.error("IOException "+e.getMessage());
		}
		return pointDiscovry;

	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {

		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			//java.util.logging.Logger.getLogger(SimulatorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,ex);
		} catch (InstantiationException ex) {
			//java.util.logging.Logger.getLogger(SimulatorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,ex);
		} catch (IllegalAccessException ex) {
			//java.util.logging.Logger.getLogger(SimulatorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,ex);
		} catch (UnsupportedLookAndFeelException ex) {
			//java.util.logging.Logger.getLogger(SimulatorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,ex);
		}

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new SimulatorGUI().setVisible(true);
			}
		});
	}

	public void notifyAcknowledgement(String message,byte[] serverResponse) {
		//System.out.println("message arrived in notifierrrrrrrrr " + message);
		notifictionMessage = message;
		csServerResponse = serverResponse;
		if(notifictionMessage != null){
			if(notifictionMessage.equals(ServerResponseStatus.POINTREALTIMEDATA)){
				txServerResponse.setText(ConversionUtils.getHex(csServerResponse).toString());
				txCurrentState.setText(ServerResponseStatus.POINTREALTIMEDATA);
			}
			else if(notifictionMessage.equals(ServerResponseStatus.POINTALARM)){
				txServerResponse.setText(ConversionUtils.getHex(csServerResponse).toString());
				txCurrentState.setText(ServerResponseStatus.POINTALARM);
			}
		}		

	}

	public void notifyDataServerRequests(Integer seqNo, byte[] dsServerReq) {
		txMsgEDCP.setText(ConversionUtils.getHex(dsServerReq).toString());
		txSeqNo.setText(String.valueOf(seqNo));
	}

	public void valueChanged(TreeSelectionEvent e) {		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();		
		if (node == null || node.isRoot()){
			PointTabGUI.clearPointValues();
			return;
		}
		Object nodeInfo = node.getUserObject();
		if(nodeInfo instanceof Points){
			Points point  = (Points) nodeInfo;			
			PointTabGUI.displayPointInfo(point);
		}		
	}	
}
