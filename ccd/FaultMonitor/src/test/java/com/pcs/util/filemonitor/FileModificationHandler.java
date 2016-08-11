/**
 * 
 */
package com.pcs.util.filemonitor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

/**
 * @author pcseg171
 *
 */
public class FileModificationHandler extends FileAlterationListenerAdaptor {

	@Override
	public void onDirectoryCreate(File directory) {
		System.err.println("Directory Created "+directory.getName());
	}

	@Override
	public void onFileCreate(File file) {
		System.err.println("File Created "+file.getName());
		
		ByteArrayInputStream arrayInputStream = new ByteArrayInputStream("".getBytes());
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(arrayInputStream));
		StringBuffer tets = new StringBuffer();
		
	}

	
	

}
