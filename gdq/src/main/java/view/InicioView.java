package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InicioView extends JFrame {

	private JPanel contentPane;
	private JButton btnCadastro;
	private JButton btnFilaDeTrabalho;
	private JLabel logo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InicioView frame = new InicioView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InicioView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 250, 407, 268);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnCadastro = new JButton("Cadastrar");
		btnCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				CadastroDeCenariosView window = new CadastroDeCenariosView();
				window.frmCadastroDeCenrios.dispose();
				window.frmCadastroDeCenrios.setVisible(true);
				}catch (Exception ex) {
					System.out.println("Deu ruim");
				}
			}
		});
		btnCadastro.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnCadastro.setIcon(null);
		btnCadastro.setBounds(46, 63, 140, 121);
		contentPane.add(btnCadastro);
		
		btnFilaDeTrabalho = new JButton("Fila ");
		btnFilaDeTrabalho.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnFilaDeTrabalho.setBounds(209, 63, 140, 121);
		contentPane.add(btnFilaDeTrabalho);
		
		
		
		
	}
}
