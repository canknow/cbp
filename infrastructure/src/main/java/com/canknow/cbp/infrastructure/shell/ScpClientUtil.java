package com.canknow.cbp.infrastructure.shell;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 远程执行linux命令
 * @author: ZhangHouYing
 * @date: 2019-08-10 10:06
 */
public class ScpClientUtil {
	static private ScpClientUtil instance;

	private String ip;
	private int port;
	private String username;
	private String password;

	static synchronized public ScpClientUtil getInstance(String ip, int port, String username, String passward) {
		if (instance == null) {
			instance = new ScpClientUtil(ip, port, username, passward);
		}
		return instance;
	}

	public ScpClientUtil(String ip, int port, String username, String password) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public void getFile(String remoteFile, String localTargetDirectory) {
		Connection connection = new Connection(ip, port);

		try {
			connection.connect();
			boolean isAuthenticated = connection.authenticateWithPassword(username, password);

			if (!isAuthenticated) {
				System.err.println("authentication failed");
			}
			SCPClient client = new SCPClient(connection);
			client.get(remoteFile, localTargetDirectory);
		}
		catch (IOException exception) {
			Logger.getLogger(SCPClient.class.getName()).log(Level.SEVERE, null, exception);
		}
		finally {
			connection.close();
		}
	}

	public void putFile(String localFile, String remoteTargetDirectory) {
		putFile(localFile, null, remoteTargetDirectory);
	}

	public void putFile(String localFile, String remoteFileName, String remoteTargetDirectory) {
		putFile(localFile, remoteFileName, remoteTargetDirectory,null);
	}

	public void putFile(String localFile, String remoteFileName, String remoteTargetDirectory, String mode) {
		Connection connection = new Connection(ip, port);

		try {
			connection.connect();
			boolean isAuthenticated = connection.authenticateWithPassword(username, password);

			if (!isAuthenticated) {
				System.err.println("authentication failed");
			}
			SCPClient client = new SCPClient(connection);

			if ((mode == null) || (mode.length() == 0)) {
				mode = "0600";
			}
			if (remoteFileName == null) {
				client.put(localFile, remoteTargetDirectory);
			}
			else {
				client.put(localFile, remoteFileName, remoteTargetDirectory, mode);
			}
		}
		catch (IOException exception) {
			Logger.getLogger(ScpClientUtil.class.getName()).log(Level.SEVERE, null, exception);
		}
		finally {
			connection.close();
		}
	}
}
