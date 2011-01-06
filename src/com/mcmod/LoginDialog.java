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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class LoginDialog extends JDialog implements ActionListener {
	private JTextField usernameField = new JTextField(12);
	private JTextField sidField = new JTextField(12);
	
	private String user;
	private String sid;
	
	public LoginDialog() {
		super((Frame) null, "McMod");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setLayout(new BorderLayout());
		
		JLabel logo = new JLabel(new ImageIcon(getClass().getResource("mcmod.png")));
		add(logo, BorderLayout.NORTH);
		
		JPanel panel = new JPanel(new GridLayout(2, 2));
		panel.setBorder(new CompoundBorder(new TitledBorder(""), new EmptyBorder(10, 10, 10, 10)));
		
		JLabel lab = new JLabel("Username: ");
		panel.add(lab);
		panel.add(usernameField);
		lab = new JLabel("Session ID: ");
		panel.add(lab);
		panel.add(sidField);
		
		JPanel cont = new JPanel();
		cont.add(panel);
		add(cont, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		JButton button = new JButton("Login");
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
		if(e.getActionCommand().equals("Quit"))
			System.exit(0);
		else if(e.getActionCommand().equals("Login")) {
			user = usernameField.getText();
			sid = sidField.getText();
			setVisible(false);
		}
	}
	
	public String[] getInfo() {
		return new String[] { user, sid };
	}
}
