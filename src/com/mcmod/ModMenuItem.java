package com.mcmod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.mcmod.api.Mod;

public class ModMenuItem extends JMenuItem implements ModMenu, ActionListener {
	private Mod mod;
	
	public ModMenuItem(Mod mod) {
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
		mod.render();
	}
}
