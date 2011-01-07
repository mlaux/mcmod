package com.mcmod.util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
		list.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				Object z = list.getSelectedValue();
				
				new ReflectionExplorer(z).show();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		super.add(new JScrollPane(list));
		
		populateList();
	}
	
	public void populateList() {
		List<Object> objects = new ArrayList<Object>();
		
		for(Field f : clazz.getFields()) {
			try {
				f.setAccessible(true);
				objects.add(f.get(o));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		list.setListData(objects.toArray());
		pack();
	}
	
	public void tick() {
		populateList();
	}
}
