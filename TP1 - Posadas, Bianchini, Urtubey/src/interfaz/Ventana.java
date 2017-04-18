package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

public class Ventana extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField Nombre;

	/**
	 * Create the dialog.
	 */
	public Ventana(Integer puntos) {
		setBounds(270, 210, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Felicitaciones! Sus movimientos fueron: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(98, 11, 275, 62);
		contentPanel.add(lblNewLabel);
		{
			JButton btnNewButton = new JButton(""+ puntos);
			btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnNewButton.setEnabled(false);
			btnNewButton.setBounds(150, 67, 142, 44);
			contentPanel.add(btnNewButton);
		}
		
		JLabel lblNewLabel_1 = new JLabel("Para guardar su puntaje, ingrese su nombre: ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(75, 122, 311, 31);
		contentPanel.add(lblNewLabel_1);
		
		Nombre = new JTextField();
		Nombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Nombre.setBounds(150, 154, 142, 44);
		contentPanel.add(Nombre);
		Nombre.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Aceptar");
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String nombre = Nombre.getText();
						System.out.println(nombre);
						System.exit(0);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void mostrar(){
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
