package com.denvik.venaqua.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 * Execute file download in a background thread and update the progress. 
 * @author www.codejava.net
 *
 */
public class DownloadTask extends SwingWorker<Void, Void> {

	private static final int BUFFER_SIZE = 4096;
	
	private String host;
	private int port;
	private String username;
	private String password;
	
	private String downloadPath;
	private String saveDir;
	
	public DownloadTask(String host, int port, String username, String password, String downloadPath, String saveDir )
    {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.downloadPath = downloadPath;
		this.saveDir = saveDir;
	}

	/**
	 * Executed in background thread
	 */	
	@Override
	protected Void doInBackground() throws Exception {
        FTPUtility util = new FTPUtility(host, port, username, password);
		try {
            System.out.println( "trying to connect...." );
            util.connect();
			
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			long totalBytesRead = 0;
			int percentCompleted = 0;
			
			long fileSize = util.getFileSize(downloadPath);
            System.out.println( "File Size : " + fileSize );

            String fileName = new File(downloadPath).getName();
            System.out.println( "File Name : " + fileName );
			
			File downloadFile = new File(saveDir + File.separator + fileName);
			FileOutputStream outputStream = new FileOutputStream(downloadFile);
			
			util.downloadFile(downloadPath);
			InputStream inputStream = util.getInputStream();
			
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
				totalBytesRead += bytesRead;
				percentCompleted = (int) (totalBytesRead * 100 / fileSize);
				setProgress(percentCompleted);
			}

			outputStream.close();

            System.out.println( "Finishing all pending tasks..." );
            util.finish();
		} catch ( FTPException ex) {
			JOptionPane.showMessageDialog(null, "Error downloading file: " + ex.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);			
			ex.printStackTrace();
			setProgress(0);
			cancel(true);			
		} finally {
            System.out.println( "trying to disconnect..." );
            util.disconnect();
		}
		
		return null;
	}

	/**
	 * Executed in Swing's event dispatching thread
	 */
	@Override
	protected void done() {
		if (!isCancelled()) {
            System.out.println( "Task completed successfully...." );
        }
	}	
}