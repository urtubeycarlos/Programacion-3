package interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logica.Tablero;
import logica.Tupla;

import javax.swing.JTable;

public class Puntajes extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Tablero _tablero;
	
	public Puntajes(Tablero tablero) {
		
		_tablero = tablero;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		//Tomo el tamaño de la pantalla 
		Dimension pantallaTamano = Toolkit.getDefaultToolkit().getScreenSize(); 
		//Al ancho de la pantalla lo divido en 2 y le resto la mitad del ancho de mi ventana, con eso queda centrada en el eje X, para el eje Y es lo mismo pero con el alto: 
		this.setLocation((pantallaTamano.width/2)-(this.getWidth()/2), (pantallaTamano.height/2)-(this.getHeight()/2)); 
				
		{
			JTable tblEjemplo = new JTable();
	        JScrollPane scpEjemplo= new JScrollPane();
	        //Llenamos el modelo
	        DefaultTableModel dtmEjemplo = new DefaultTableModel(getFilas(),
	                                                             getColumnas());
	 
	        tblEjemplo=new JTable(dtmEjemplo){
	        /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int vColIndex) {
	            return false;
	        }}; 
	        
	        tblEjemplo.setModel(dtmEjemplo);
	        scpEjemplo.add(tblEjemplo);
	        this.add(scpEjemplo);
	        this.setSize(500, 100);
	        scpEjemplo.setViewportView(tblEjemplo);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.WEST);
			{
				JButton cancelButton = new JButton("Cerrar");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						System.exit(0);
					}
				});

			}
		}
	}
	
	public void mostrar(){
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	private Object[][] getFilas(){
		 HashMap<Integer, Tupla<String, Integer>> datos = _tablero.obtenerPuntajes();
		 
		 Object fila [][] = new Object [datos.size()][3];
		 
		 for(Integer i = 0; i <datos.size(); i++){
			 fila[i][0] = i;
			 fila [i][1] = datos.get(i).elem1;
			 fila [i][2] = datos.get(i).elem2;
		 }
//         Object fila[][]=new Object[][] {
//                            {"1", "Pedro", "22"},
//                            {"2", "Rumpelstinsky", "24"},
//                            {"3", "Shrek", "33"}};
         return fila;
    }
	
	private String[] getColumnas(){
	          String columna[]=new String[]{"Puesto","Nombres","Puntajes"};
	          return columna;
	}


}
