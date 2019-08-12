package com.kabryxis.tmp;

import com.kabryxis.kabutils.concurrent.Threads;
import com.kabryxis.kabutils.swing.Swings;
import com.kabryxis.tmp.swing.BasicKeyListener;
import com.kabryxis.tmp.swing.LoginScreen;
import com.kabryxis.tmp.user.UserManager;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.fullscreen.windows.Win32FullScreenStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EventListener;

public class TMP {
	
	public static final Dimension DEFAULT_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	public static final Rectangle DEFAULT_BOUNDS = new Rectangle(0, 0, DEFAULT_SIZE.width, DEFAULT_SIZE.height);
	public static final Color DEFAULT_BG_COLOR = Color.DARK_GRAY.darker().darker();
	
	private static JFrame frame;
	private static EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private static EmbeddedMediaPlayer mediaPlayer;
	private static UserManager userManager;
	
	public static void main(String[] args) {
		Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
			e.printStackTrace();
			Threads.sleep(20000);
		});
		try {
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
		} catch(UnsupportedLookAndFeelException ignore) {}
		
		frame = new JFrame("Tarrant's Media Player");
		frame.setBounds(DEFAULT_BOUNDS);
		frame.setBackground(Color.BLUE);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) { exit(); }
			
		});
		frame.addKeyListener((BasicKeyListener)e -> {
			if(e.getExtendedKeyCode() == KeyEvent.VK_ESCAPE) exit();
		});
		
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		frame.setContentPane(mediaPlayerComponent);
		mediaPlayer = mediaPlayerComponent.mediaPlayer();
		
		userManager = new UserManager();
		LoginScreen loginScreen = new LoginScreen(userManager);
		frame.add(loginScreen.getImageCropScreen());
		
		userManager.setLoginScreen(loginScreen);
		
		frame.add(loginScreen);
		frame.pack(); // must use this to correctly lay any jpanels of the same size using #setPreferredSize
		frame.setVisible(true);
		new Win32FullScreenStrategy(frame).enterFullScreenMode();
		
		//mediaPlayer.media().play("C:\\MDB\\3dkanojo\\1\\1x1.mp4");
	}
	
	public static EmbeddedMediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}
	
	public static void addListener(EventListener... listeners) {
		Swings.addListener(frame, listeners);
	}
	
	public static void exit() {
		mediaPlayerComponent.release();
		System.exit(0);
	}
	
}
