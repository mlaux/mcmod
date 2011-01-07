package com.mcmod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;

import com.mcmod.api.Mod;

public class TogglableModMenuItem extends JCheckBoxMenuItem implements ModMenu, ActionListener {
	private Mod mod;
	
	public TogglableModMenuItem(Mod mod) {
		super(mod.getName());
		this.mod = mod;
		
		super.setToolTipText(mod.getDescription());
		super.addActionListener(this);
	}
	
	public Mod getMod() {
		return mod;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(super.isSelected()) {
			mod.enable();
		} else {
			mod.disable();
		}
	}
}
