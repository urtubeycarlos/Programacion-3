package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MenuContextual extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	
	public MenuContextual() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setToolTipText("");
		menuBar.setBounds(0, 0, 53, 21);
		contentPane.add(menuBar);
		
		JMenu mnNewMenu = new JMenu("Archivo");
		menuBar.add(mnNewMenu);
		
		JMenuBar menuBar2 = new JMenuBar();
		menuBar.setToolTipText("");
		menuBar.setBounds(0, 0, 90, 21);
		contentPane.add(menuBar2);
		
		JMenu mnNewMenu2 = new JMenu("Ver");
		menuBar.add(mnNewMenu2);
		
		JMenuItem mntmPuntajes = new JMenuItem("Puntajes");
		mnNewMenu2.add(mntmPuntajes);
	
		JMenuItem mntmNuevoJuego = new JMenuItem("Nuevo Juego");
		mnNewMenu.add(mntmNuevoJuego);
		
		mnNewMenu.addSeparator();
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mnNewMenu.add(mntmSalir);
		
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
	}
}
