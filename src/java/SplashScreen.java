package java;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.Timer;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.SwingConstants;



public class SplashScreen extends JWindow {
private Timer timer = null;
	private int counter = 0;
ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("../DIE/src/images/EditorLogo.png"));
		

	/**
	 * Create the frame.
	 */
	public SplashScreen() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setOpacity(1.0F);
		setBounds(100, 100, 600, 500);
		
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setIcon(icon);
		lblNewLabel.setBounds(161, 26, 246, 284);
		getContentPane().add(lblNewLabel);
		
		
		JLabel lblNewLabel1 = new JLabel("DIGITAL IMAGE EDITOR");
		lblNewLabel1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel1.setForeground(Color.RED);
		lblNewLabel1.setHorizontalAlignment(SwingConstants.CENTER);
			
		lblNewLabel1.setBounds(107, 330, 308, 50);
		getContentPane().add(lblNewLabel1);
		final JProgressBar progressBar = new JProgressBar();
		progressBar.setForeground(SystemColor.textHighlight);
		progressBar.setFont(new Font("Tahoma", Font.BOLD, 11));
		progressBar.setBackground(SystemColor.inactiveCaptionBorder);
		progressBar.setStringPainted(true);
		progressBar.setBounds(38, 444, 522, 14);
		getContentPane().add(progressBar);
		
		
		
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(progressBar.getMaximum()>counter){
					if(counter<5)
						progressBar.setString("Loading Libraries . .         ");						
					else if(counter<10)
						progressBar.setString("Loading Libraries . . . .     ");
					else if(counter<15)
						progressBar.setString("Loading Libraries . . . . . . ");
					else if(counter<20)
						progressBar.setString("Linking Libraries . .         ");
					else if(counter<25)
						progressBar.setString("Linking Libraries . . . .     ");
					else if(counter<30)
						progressBar.setString("Linking Libraries . . . . . . ");
					else if(counter<35)
						progressBar.setString("Creating Interface . .         ");
					else if(counter<40)
						progressBar.setString("Creating Interface . . . .     ");
					else if(counter<45)
						progressBar.setString("Creating Interface . . . . . . ");
					else if(counter<50)
						progressBar.setString("Start Ediiting  ");
					
				counter = counter + 1;
				progressBar.setValue(counter);
				progressBar.setBackground(Color.GREEN);
				progressBar.setBorder(null);
				
				
				}
				else
				{
					SplashScreen.this.setVisible(false);
					 try {
						Die.f= new Die();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
						Die.f.setVisible(true);
					timer.stop();
					
				}
			}
		};
		timer = new Timer(60,listener);
		timer.start();
	
		
	}
}
