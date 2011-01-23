package com.mcmod.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class Util {

	public static File versionFile = new File(getWorkingDirectory("minecraft")
			.getPath() + "/bin/version");

	public static File getWorkingDirectory(String file) {
		String userHome = System.getProperty("user.home");
		String os = System.getProperty("os.name").toLowerCase();

		File workingDirectory = null;

		if (os.contains("win")) {
			String data = System.getenv("APPDATA");

			if (data != null) {
				workingDirectory = new File(data, "." + file);
			} else {
				workingDirectory = new File(userHome, "." + file);
			}
		} else if (os.contains("unix") || os.contains("linux")) {
			workingDirectory = new File(userHome, "." + file);
		} else if (os.contains("mac")) {
			workingDirectory = new File(userHome,
					"Library/Application Support/" + file);
		} else {
			workingDirectory = new File(userHome, file);
		}

		return workingDirectory;
	}

	public static String sendGetRequest(String urlStr) {
		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String readVersionFile() {
		File file = versionFile;
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			String version = dis.readUTF();
			dis.close();
			return version;
		} catch(Exception e) { e.printStackTrace(); }
		return "0";
	}

	public static void writeVersionFile(String version) throws Exception {
		File file = versionFile;
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
		dos.writeUTF(version);
		dos.close();
	}
	
	public static String[] readLoginInfo() {
		String[] data = {"", null};
		try {
		      File lastLogin = new File(Util.getWorkingDirectory("minecraft"), "lastlogin");

		      Cipher cipher = getCipher(2, "passwordfile");
		      DataInputStream dis;
		      if (cipher != null)
		        dis = new DataInputStream(new CipherInputStream(new FileInputStream(lastLogin), cipher));
		      else {
		        dis = new DataInputStream(new FileInputStream(lastLogin));
		      }
		      data[0] = dis.readUTF();
		      data[1] = dis.readUTF();
		      dis.close();
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		return data;
	}
	
	private static Cipher getCipher(int i, String string) throws Exception {
		Random random = new Random(43287234L);
	    byte[] salt = new byte[8];
	    random.nextBytes(salt);
	    PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, 5);

	    SecretKey pbeKey = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(new PBEKeySpec(string.toCharArray()));
	    Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
	    cipher.init(i, pbeKey, pbeParamSpec);
	    return cipher;
	}

	public static void writeLoginInfo(String user, String pass) {
		try {
		      File lastLogin = new File(Util.getWorkingDirectory("minecraft"), "lastlogin");

		      Cipher cipher = getCipher(1, "passwordfile");
		      DataOutputStream dos;
		      if (cipher != null)
		        dos = new DataOutputStream(new CipherOutputStream(new FileOutputStream(lastLogin), cipher));
		      else {
		        dos = new DataOutputStream(new FileOutputStream(lastLogin));
		      }
		      dos.writeUTF(user);
		      dos.writeUTF((pass == null) ? "" : pass);
		      dos.close();
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
	}
	
	
}
