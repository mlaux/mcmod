package com.mcmod;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.mcmod.util.Util;

@SuppressWarnings("serial")
public class LoginDialog extends JDialog implements ActionListener {
	// http://www.minecraft.net/game/getversion.jsp?user=<username>&password=<password>&version=<launcher
	// version>
	private static final String AUTH_URL = "http://www.minecraft.net/game/getversion.jsp";
	// http://minecraft.net/game/minecraft.jar?user=<username>&ticket=<download
	// ticket>
	private static final String PARAM_FORMAT = "?user=%s&password=%s&version=%s";
	private static final String LAUNCHER_VER = "12";

	private JTextField usernameField = new JTextField(12);
	private JPasswordField passField = new JPasswordField(12);
	private String currentVersion;
	private String localVersion;
	private String downloadTicket;
	private String user;
	private String sid;

	public LoginDialog() {
		super((Frame) null, "McMod");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setLayout(new BorderLayout());

		JLabel logo = new JLabel(new ImageIcon(getClass().getResource(
				"mcmod.png")));
		add(logo, BorderLayout.NORTH);

		JPanel panel = new JPanel(new GridLayout(2, 2));
		panel.setBorder(new CompoundBorder(new TitledBorder(""),
				new EmptyBorder(10, 10, 10, 10)));

		JLabel lab = new JLabel("Username: ");
		panel.add(lab);
		panel.add(usernameField);
		lab = new JLabel("Password: ");
		panel.add(lab);
		panel.add(passField);

		JPanel cont = new JPanel();
		cont.add(panel);
		add(cont, BorderLayout.CENTER);

		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		JButton button = new JButton("Login");
		getRootPane().setDefaultButton(button);
		button.addActionListener(this);
		bottom.add(button);
		button = new JButton("Quit");
		button.addActionListener(this);
		bottom.add(button);

		add(bottom, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Quit")) {
			System.exit(0);

		} else if (e.getActionCommand().equals("Login")) {
			String result = Util.sendGetRequest(AUTH_URL + String.format(PARAM_FORMAT, 
					usernameField.getText(), new String(passField.getPassword()), LAUNCHER_VER));
			System.out.println(result);
			if(result == null) {
				tryOffline("Unable to authenticate because Minecraft.net is offline, play offline?");
				return;
			} else if (result.equals("Bad login")) {
				tryOffline("Bad login, play offline?");
			} else if (result.equals("Old version")) {
				tryOffline("Old version of McMod, please update. Play offline?");
			} else {
				String[] data = result.split(":");
				currentVersion = data[0];
				downloadTicket = data[1];
				user = data[2];
				sid = data[3];
				if (Util.versionFile.exists()) {
					if (currentVersion.equals("-1") || currentVersion.equals(Util.readVersionFile())) {
						setVisible(false);
					} else {
						tryOffline("New version of Minecraft available. To update, please run Minecraft. Play offline?");
					}
				}
			}
		}
	}

	private void tryOffline(String message) {
		int res = JOptionPane.showConfirmDialog(null, message,
				"Unable to login", JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE);
		if (res == JOptionPane.YES_OPTION) {
			user = usernameField.getText();
			setVisible(false);
		}
	}

	/**
	 * Gets the player's authentication information
	 * 
	 * @return 
	 *		 [user][sid][currentMCVersion][currentLocalVersion][downloadTicket]
	 */
	public String[] getInfo() {
		return new String[] { user, sid, currentVersion, localVersion,
				downloadTicket };
	}
}
