package tema9;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

// Hilo para actualizar un label
public class EjemploHilos2 {
	private static JLabel label;
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		label = new JLabel(" ");
		f.add( label, BorderLayout.NORTH );
		JButton boton = new JButton( "Pulsa" );
		f.add( boton, BorderLayout.SOUTH );
		boton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Thread t = new Thread() {
					public void run() {
						for (char c='a'; c<='z'; c = (char) (c+1)) {
							label.setText( label.getText() + c );
							try { Thread.sleep(500); } catch (InterruptedException e) {}
						}
					}
				};
				t.start();
			}
		});
		f.pack();
		f.setVisible( true );
	}
}
