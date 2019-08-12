package com.kabryxis.tmp.swing;

import com.kabryxis.kabutils.swing.Swings;
import com.kabryxis.tmp.TMP;

import java.awt.*;
import java.util.EventListener;

public class ComponentBuilder<T extends Component> {
	
	protected T component;
	
	public ComponentBuilder(T component) {
		this.component = component;
		bgColor(TMP.DEFAULT_BG_COLOR);
	}
	
	public ComponentBuilder<T> bounds(int x, int y, int width, int height) {
		component.setBounds(x, y, width, height);
		return this;
	}
	
	public ComponentBuilder<T> size(int width, int height) {
		component.setSize(width, height);
		return this;
	}
	
	public ComponentBuilder<T> preferredSize(int width, int height) {
		component.setPreferredSize(new Dimension(width, height));
		return this;
	}
	
	public ComponentBuilder<T> minSize(int width, int height) {
		component.setMinimumSize(new Dimension(width, height));
		return this;
	}
	
	public ComponentBuilder<T> loc(int x, int y) {
		component.setLocation(x, y);
		return this;
	}
	
	public ComponentBuilder<T> loc(Point loc) {
		component.setLocation(loc);
		return this;
	}
	
	public ComponentBuilder<T> fgColor(Color foregroundColor) {
		component.setForeground(foregroundColor);
		return this;
	}
	
	public ComponentBuilder<T> bgColor(Color backgroundColor) {
		component.setBackground(backgroundColor);
		return this;
	}
	
	public ComponentBuilder<T> listeners(EventListener... listeners) {
		Swings.addListener(component, listeners);
		return this;
	}
	
	public ComponentBuilder<T> visible(boolean visible) {
		component.setVisible(visible);
		return this;
	}
	
	public T build() {
		return component;
	}
	
}
