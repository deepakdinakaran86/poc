/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.fms.web.services;

import static com.pcs.fms.web.constants.FMSWebConstants.FILE_IMAGE_TYPE;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.common.io.ByteStreams;
import com.pcs.fms.web.dto.Image;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Service
public class FileService extends BaseService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(FileService.class);

	@Value("${fms.ftp.server.host}")
	private String ftpServerHost;
	@Value("${fms.ftp.server.port}")
	private String ftpServerPort;
	@Value("${fms.ftp.server.user}")
	private String ftpServerUser;
	@Value("${fms.ftp.server.pass}")
	private String ftpServerPass;

	private static String FILE_PATH_HOME = "FMS/";
	private static String FILE_PATH_DEFAULT = "default/";

	private FTPClient getFtpClient() {
		FTPClient ftpClient = null;
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(ftpServerHost, Integer.parseInt(ftpServerPort));
			boolean login = ftpClient.login(ftpServerUser, ftpServerPass);
			if (login) {
				ftpClient.enterLocalPassiveMode();
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			} else {
				LOGGER.error("FTP Login error");
				ftpClient = null;
			}
		} catch (Exception e) {
			LOGGER.error("FTP Error: ", e.getMessage());
			ftpClient = null;
		}
		return ftpClient;
	}

	public String doUpload(CommonsMultipartFile fileData, Image imageDetails) {

		if (fileData != null) {
			String name = fileData.getOriginalFilename();

			if (name != null && name.length() > 0) {
				uploadfileFTP(fileData.getBytes(), imageDetails);
			}
		}
		return "success";
	}

	public String doUpload(String fileData, Image imageDetails) {

		if (StringUtils.isNotEmpty(fileData)) {
			byte[] decodeBase64 = Base64.decodeBase64(fileData);
			uploadfileFTP(decodeBase64, imageDetails);
		}
		return "success";
	}

	private void uploadfileFTP(byte[] fileByteArray, Image image) {

		FTPClient ftpClient = null;
		try {
			ftpClient = getFtpClient();
			if (ftpClient != null) {
				String path = FILE_PATH_HOME + image.getDomain() + "/"
				        + image.getModule();

				String firstRemoteFile = "/" + path + "/" + image.getFilename();

				ftpClient.changeWorkingDirectory(path);
				if (ftpClient.getReplyCode() == 550) {
					ftpCreateDirectoryTree(ftpClient, path);
				}

				InputStream inputStream = new ByteArrayInputStream(
				        fileByteArray);

				boolean done = ftpClient
				        .storeFile(firstRemoteFile, inputStream);
				inputStream.close();
				if (done) {
					LOGGER.info("The first file is uploaded successfully : ",
					        firstRemoteFile);
				} else {
					LOGGER.info("File upload failed please check permission.");
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (ftpClient != null) {
				try {
					if (ftpClient.isConnected()) {
						ftpClient.logout();
						ftpClient.disconnect();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public byte[] downloadfileFTP(String fileName) {

		byte[] bytes = null;
		FTPClient ftpClient = null;
		try {
			ftpClient = getFtpClient();
			if (ftpClient != null) {
				InputStream inputStream = ftpClient
				        .retrieveFileStream(fileName);

				if (inputStream != null) {
					bytes = ByteStreams.toByteArray(inputStream);
				}
			}
		} catch (IOException ex) {
			LOGGER.error("FTP Error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (ftpClient != null) {
				try {
					if (ftpClient.isConnected()) {
						ftpClient.logout();
						ftpClient.disconnect();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return bytes;
	}

	public String doDowload(Image imageReq) {

		String fileName = FILE_PATH_HOME + imageReq.getDomain() + "/"
		        + imageReq.getModule() + "/" + imageReq.getFilename();
		byte[] downloadfileFTP = downloadfileFTP(fileName);
		if (downloadfileFTP == null) {
			LOGGER.info("Fetch default image...");
			fileName = FILE_PATH_HOME + FILE_PATH_DEFAULT
			        + imageReq.getModule() + FILE_IMAGE_TYPE;
			downloadfileFTP = downloadfileFTP(fileName);
			if (downloadfileFTP == null) {
				return StringUtils.EMPTY;
			}
		}
		byte[] encodeBase64 = Base64.encodeBase64(downloadfileFTP);
		return new String(encodeBase64);
	}

	public String doDowloadNoDefault(Image imageReq) {

		String fileName = FILE_PATH_HOME + imageReq.getDomain() + "/"
		        + imageReq.getModule() + "/" + imageReq.getFilename();
		byte[] downloadfileFTP = downloadfileFTP(fileName);
		if (downloadfileFTP == null) {
			return StringUtils.EMPTY;
		}
		byte[] encodeBase64 = Base64.encodeBase64(downloadfileFTP);
		return new String(encodeBase64);
	}

	public boolean deletefileFTP(String fileName) {

		boolean deleteFile = false;
		FTPClient ftpClient = null;
		try {

			ftpClient = getFtpClient();
			if (ftpClient != null) {
				deleteFile = ftpClient.deleteFile(fileName);
			}
		} catch (IOException ex) {
			LOGGER.error("FTP Error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (ftpClient != null) {
				try {
					if (ftpClient.isConnected()) {
						ftpClient.logout();
						ftpClient.disconnect();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return deleteFile;
	}

	public boolean doDelete(Image imageReq) {

		String fileName = FILE_PATH_HOME + imageReq.getDomain() + "/"
		        + imageReq.getModule() + "/" + imageReq.getFilename();
		return deletefileFTP(fileName);
	}

	public Map<String, String> downloadAllFileFTP(Image imageReq) {
		String folder = FILE_PATH_HOME + imageReq.getDomain() + "/"
		        + imageReq.getModule() + "/";
		Map<String, String> byteMap = new HashMap<String, String>();
		FTPClient ftpClient = null;
		try {

			ftpClient = getFtpClient();
			if (ftpClient != null) {
				FTPFile[] files = ftpClient.listFiles(folder);
				for (FTPFile file : files) {
					ByteArrayOutputStream outstream = new ByteArrayOutputStream();
					ftpClient.retrieveFile(folder + "/" + file.getName(),
					        outstream);
					if (outstream.size() > 0) {

						byte[] bytes = outstream.toByteArray();
						if (bytes != null) {
							byte[] encodeBase64 = Base64.encodeBase64(bytes);
							if (encodeBase64 != null) {
								String byteString = new String(encodeBase64);
								byteMap.put(file.getName(), byteString);
							}
						}
					}
				}
			}

		} catch (IOException ex) {
			LOGGER.error("FTP Error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (ftpClient != null) {
				try {
					if (ftpClient.isConnected()) {
						ftpClient.logout();
						ftpClient.disconnect();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return byteMap;
	}

	private static void ftpCreateDirectoryTree(FTPClient client, String dirTree)
	        throws IOException {

		boolean dirExists = true;
		String filePath = "";
		String[] directories = dirTree.split("/");
		for (String dir : directories) {
			if (!dir.isEmpty()) {
				filePath = filePath + "/" + dir;
				if (dirExists) {
					dirExists = client.changeWorkingDirectory(filePath);
				}
				if (!dirExists) {
					client.makeDirectory(filePath);
				}
			}
		}
	}
}
