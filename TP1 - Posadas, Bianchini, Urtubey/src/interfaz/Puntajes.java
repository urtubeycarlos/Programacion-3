package interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.HashMap;
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
		setBounds(100, 100, 450, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		Dimension pantallaTamano = Toolkit.getDefaultToolkit().getScreenSize(); 
		this.setLocation((pantallaTamano.width/2)-(this.getWidth()/2), (pantallaTamano.height/2)-(this.getHeight()/2)); 
				
		{
			JTable tblEjemplo = new JTable();
	        JScrollPane scpEjemplo= new JScrollPane();
	        DefaultTableModel dtmEjemplo = new DefaultTableModel(getFilas(),
	                                                             getColumnas());
	 
	        tblEjemplo=new JTable(dtmEjemplo){
			
	        private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int vColIndex) {
	            return false;
	        }}; 
	        
	        tblEjemplo.setModel(dtmEjemplo);
	        scpEjemplo.add(tblEjemplo);
	        this.add(scpEjemplo);
	        this.setSize(500, 300);
	        scpEjemplo.setViewportView(tblEjemplo);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.WEST);
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
			 fila[i][0] = i+1;
			 fila [i][1] = datos.get(i+1).elem1;
			 fila [i][2] = datos.get(i+1).elem2;
		 }  return fila;
    }
	
	private String[] getColumnas(){
	          String columna[] = new String[]{"Puesto","Nombres","Puntajes"};
	          return columna;
	}


}
