package com.pcs.saffron.g2021.SimulatorUI;

import java.awt.SystemColor;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.pcs.saffron.g2021.SimulatorEngine.CS.message.Points;

public class PointTabGUI {
	
	public static JPanel pointConfigPanel;
	public static JLabel lblPointId;
	public static JTextField pIdField;
	public static JLabel lblPname;
	public static JTextField pNameField;
	public static JLabel lblPunit;
	public static JTextField pUnitField;
	public static JLabel lblPtype;
	public static JTextField pTypeField;
	public static JLabel lblPdatatype;
	public static JTextField pDataTypeField;
	public static JLabel lblPdaqtype;
	public static JTextField pDaqTypeField;
	public static JLabel lblPdaqtime;
	public static JTextField pDaqTimeField;
	public static JLabel lblPdaqcovth;
	public static JTextField pDaqCOV_THField;
	public static JLabel lblPalarmtype;
	public static JTextField pAlarmTypeField;
	public static JLabel lblPalarmcriticality;
	public static JTextField pAlarmCriticalField;
	public static JLabel lblPalarmlt;
	public static JTextField pAlarm_LTField;
	public static JLabel lblPalarmut;
	public static JTextField pAlarm_UTField;
	public static JLabel lblNormaltext;
	public static JTextField normalTextField;
	public static JLabel lblOffnormaltext;
	public static JTextField offNormalTextField;
	public static JLabel lblOffnormaltextlt;
	public static JTextField offNormalText_LTField;
	public static JLabel lblOffnormaltestut;
	public static JTextField offNormalText_UTField;
	
	public static void createPanelForPoints(){

		lblPointId = new JLabel("Point Id:");

		pIdField = new JTextField();
		pIdField.setBackground(SystemColor.text);
		pIdField.setEnabled(false);
		pIdField.setColumns(10);
		
		lblPname = new JLabel("PName :");
		
		pNameField = new JTextField();
		pNameField.setEnabled(false);
		pNameField.setColumns(10);
		pNameField.setBackground(SystemColor.text);
		
		lblPunit = new JLabel("PUnit :");
		
		pUnitField = new JTextField();
		pUnitField.setEnabled(false);
		pUnitField.setColumns(10);
		pUnitField.setBackground(SystemColor.text);
		
		lblPtype = new JLabel("PType :");
		
		pTypeField = new JTextField();
		pTypeField.setEnabled(false);
		pTypeField.setColumns(10);
		pTypeField.setBackground(SystemColor.text);
		
		lblPdatatype = new JLabel("PDataType :");
		
		pDataTypeField = new JTextField();
		pDataTypeField.setEnabled(false);
		pDataTypeField.setColumns(10);
		pDataTypeField.setBackground(SystemColor.text);
		
		lblPdaqtype = new JLabel("PDAQType :");
		
		pDaqTypeField = new JTextField();
		pDaqTypeField.setEnabled(false);
		pDaqTypeField.setColumns(10);
		pDaqTypeField.setBackground(SystemColor.text);
		
		lblPdaqtime = new JLabel("PDAQTime :");
		
		pDaqTimeField = new JTextField();
		pDaqTimeField.setEnabled(false);
		pDaqTimeField.setColumns(10);
		pDaqTimeField.setBackground(SystemColor.text);
		
		lblPdaqcovth = new JLabel("PDAQCOV_TH :");
		
		pDaqCOV_THField = new JTextField();
		pDaqCOV_THField.setEnabled(false);
		pDaqCOV_THField.setColumns(10);
		pDaqCOV_THField.setBackground(SystemColor.text);
		
		lblPalarmtype = new JLabel("PAlarmType :");
		
		pAlarmTypeField = new JTextField();
		pAlarmTypeField.setEnabled(false);
		pAlarmTypeField.setColumns(10);
		pAlarmTypeField.setBackground(SystemColor.text);
		
		lblPalarmcriticality = new JLabel("PAlarmCriticality :");
		
		pAlarmCriticalField = new JTextField();
		pAlarmCriticalField.setEnabled(false);
		pAlarmCriticalField.setColumns(10);
		pAlarmCriticalField.setBackground(SystemColor.text);
		
		lblPalarmlt = new JLabel("PAlarm_LT :");
		
		pAlarm_LTField = new JTextField();
		pAlarm_LTField.setEnabled(false);
		pAlarm_LTField.setColumns(10);
		pAlarm_LTField.setBackground(SystemColor.text);
		
		lblPalarmut = new JLabel("PAlarm_UT :");
		
		pAlarm_UTField = new JTextField();
		pAlarm_UTField.setEnabled(false);
		pAlarm_UTField.setColumns(10);
		pAlarm_UTField.setBackground(SystemColor.text);
		
		lblNormaltext = new JLabel("NormalText :");
		
		normalTextField = new JTextField();
		normalTextField.setEnabled(false);
		normalTextField.setColumns(10);
		normalTextField.setBackground(SystemColor.text);
		
		lblOffnormaltext = new JLabel("OffNormalText :");
		
		offNormalTextField = new JTextField();
		offNormalTextField.setEnabled(false);
		offNormalTextField.setColumns(10);
		offNormalTextField.setBackground(SystemColor.text);
		
		lblOffnormaltextlt = new JLabel("OffNormalText_LT :");
		
		offNormalText_LTField = new JTextField();
		offNormalText_LTField.setEnabled(false);
		offNormalText_LTField.setColumns(10);
		offNormalText_LTField.setBackground(SystemColor.text);
		
		lblOffnormaltestut = new JLabel("OffNormalTest_UT :");
		
		offNormalText_UTField = new JTextField();
		offNormalText_UTField.setEnabled(false);
		offNormalText_UTField.setColumns(10);
		offNormalText_UTField.setBackground(SystemColor.text);
		GroupLayout gl_panel = new GroupLayout(pointConfigPanel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPointId)
								.addComponent(lblPunit)
								.addComponent(lblPdatatype)
								.addComponent(lblPalarmlt)
								.addComponent(lblNormaltext)
								.addComponent(lblPdaqtime)
								.addComponent(lblPalarmtype))
							.addGap(51)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_panel.createSequentialGroup()
												.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
													.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
														.addComponent(pUnitField, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
														.addComponent(pIdField, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
													.addComponent(pDataTypeField, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
												.addGap(60))
											.addGroup(gl_panel.createSequentialGroup()
												.addComponent(pAlarmTypeField, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)))
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(pDaqTimeField, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)))
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_panel.createSequentialGroup()
													.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
														.addComponent(lblPalarmut)
														.addComponent(lblOffnormaltext)
														.addComponent(lblOffnormaltestut))
													.addPreferredGap(ComponentPlacement.RELATED)
													.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
														.addComponent(pAlarm_UTField, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
														.addComponent(offNormalTextField, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
														.addComponent(offNormalText_UTField, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)))
												.addGroup(gl_panel.createSequentialGroup()
													.addComponent(lblPalarmcriticality)
													.addGap(18)
													.addComponent(pAlarmCriticalField, GroupLayout.PREFERRED_SIZE, 204, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED))))
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(lblPdaqcovth)
											.addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
											.addComponent(pDaqCOV_THField, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel.createSequentialGroup()
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(lblPdaqtype)
												.addComponent(lblPname)
												.addComponent(lblPtype))
											.addGap(41)
											.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
												.addComponent(pDaqTypeField, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
												.addGroup(gl_panel.createSequentialGroup()
													.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
													.addComponent(pNameField, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE))
												.addComponent(pTypeField, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE))))
									.addGap(51))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(pAlarm_LTField, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
									.addContainerGap())
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(offNormalText_LTField)
										.addComponent(normalTextField, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
									.addContainerGap(424, Short.MAX_VALUE))))
						.addComponent(lblOffnormaltextlt)))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPointId)
						.addComponent(lblPname)
						.addComponent(pIdField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(pNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(34)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblPunit)
							.addComponent(pUnitField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblPtype)
							.addComponent(pTypeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(35)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPdatatype)
						.addComponent(pDataTypeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPdaqtype)
						.addComponent(pDaqTypeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(40)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPdaqcovth)
								.addComponent(pDaqCOV_THField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(33)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPdaqtime)
								.addComponent(pDaqTimeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(25)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(pAlarmTypeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPalarmcriticality)
						.addComponent(lblPalarmtype)
						.addComponent(pAlarmCriticalField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(30)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPalarmlt)
						.addComponent(pAlarm_LTField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPalarmut)
						.addComponent(pAlarm_UTField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(29)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNormaltext)
						.addComponent(normalTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblOffnormaltext)
						.addComponent(offNormalTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(29)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblOffnormaltextlt)
						.addComponent(offNormalText_LTField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblOffnormaltestut)
						.addComponent(offNormalText_UTField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(26, Short.MAX_VALUE))
		);
		pointConfigPanel.setLayout(gl_panel);
	}
	
	public static void displayPointInfo(final Points point) {	
		updatePanelWithPointInfo(point);
		
	}

	public static void clearPointValues() {
		
		pIdField.setText("");
		pNameField.setText("");
		pUnitField.setText("");
		pTypeField.setText("");
		pDataTypeField.setText("");
		pDaqTypeField.setText("");
		pDaqTimeField.setText("");
		pDaqCOV_THField.setText("");
		pAlarmTypeField.setText("");
		pAlarmCriticalField.setText("");
		pAlarm_LTField.setText("");
		pAlarm_UTField.setText("");
		normalTextField.setText("");
		offNormalTextField.setText("");
		offNormalText_LTField.setText("");
		offNormalText_UTField.setText("");
	}

	public static void updatePanelWithPointInfo(Points point) {
		pIdField.setText(String.valueOf(point.getpID()));
		pNameField.setText(point.getpName());
		pUnitField.setText(point.getpUnit());
		pTypeField.setText(String.valueOf(point.getpType()));
		pDataTypeField.setText(String.valueOf(point.getpDataType()));
		pDaqTypeField.setText(String.valueOf(point.getpDAQType()));
		pDaqTimeField.setText(String.valueOf(point.getpDAQTime()));
		pDaqCOV_THField.setText(String.valueOf(point.getpDAQCOV_TH()));
		pAlarmTypeField.setText(String.valueOf(point.getpAlarmType()));
		pAlarmCriticalField.setText(String.valueOf(point.getpAlarmCriticality()));
		pAlarm_LTField.setText(String.valueOf(point.getpAlarm_LT()));
		pAlarm_UTField.setText(String.valueOf(point.getpAlarm_UT()));
		normalTextField.setText(String.valueOf(point.getNormalTEXT()));
		offNormalTextField.setText(String.valueOf(point.getOffnormalTEXT()));
		offNormalText_LTField.setText(String.valueOf(point.getOffnormalTEXT_LT()));
		offNormalText_UTField.setText(String.valueOf(point.getOffnormalTEXT_UT()));
	}
	
}
