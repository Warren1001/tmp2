package com.kabryxis.tmp.swing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

@FunctionalInterface
public interface BasicMouseMotionListener extends MouseMotionListener {
	
	@Override
	default void mouseMoved(MouseEvent e) {}
	
}
