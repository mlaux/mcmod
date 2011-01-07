package com.mcmod.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ReflectionExplorer extends JFrame {
	private Object o;
	private Class<?> clazz;
	private JList list;
	
	public ReflectionExplorer(Object o) {
		super("Exploring: " + o.getClass().getSimpleName());
		this.o = o;
		this.clazz = o.getClass();
		
		list = new JList();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) return;
				Object z = list.getSelectedValue();
				if(z != null)
					new ReflectionExplorer(z).setVisible(true);
			}
		});
		
		add(new JScrollPane(list));
		
		populateList();
		setSize(400, 400);
	}
	
	public void populateList() {
		List<Object> objects = new ArrayList<Object>();
		
		for(Field f : clazz.getFields()) {
			try {
				f.setAccessible(true);
				objects.add(f.get(o));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		list.setListData(objects.toArray());
		list.validate();
	}
	
	public void tick() {
		populateList();
	}
}
