/**
 * 
 */
package com.pcs.saffron.g2021.SimulatorUI;

import java.io.OutputStream;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author Ashwini.s
 *
 */
public class CustomOutputStream extends OutputStream {
	private JTextArea textArea;
	private JScrollPane scroll;
	
	public CustomOutputStream(JTextArea textArea,JScrollPane scroll) {
		this.textArea = textArea;
		this.scroll = scroll;
	}
	
	public void write(int b) {
		
		// redirects data to the text area
		try{
		textArea.append(String.valueOf((char)b));
		// scrolls the text area to the end of data
		textArea.setCaretPosition(textArea.getDocument().getLength());	
		}catch(ClassCastException e){
			System.out.println("ClassCastException occured ");
		}
	}
}