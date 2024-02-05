package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import dao.DAO;
import dao.DAO_configuracoes;

public class LoginView extends JDesktopPane {

	JFrame frmLogin;
	private JTextField txtemail;
	private JPasswordField txtsenha;
	public DAO dao;
	public CadastroDeCenariosView window;
	private JButton btnCadastrar;
	private String verificarTipoDeUsuario;
	private JLabel logotipo;
	private Image imagem;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) {
				UIManager.put("nimbusOrange", new Color(0, 204, 0));
				try {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		try {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						LoginView window = new LoginView();
						window.frmLogin.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Erro ao tentar abrir o aplicativo!");
		}
	}

	/**
	 * Create the application.
	 * 
	 * @throws Exception
	 */
	public LoginView() throws Exception {
		initialize();
		dao = new DAO();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setFont(new Font("Dialog", Font.BOLD, 15));
		frmLogin.setType(Type.UTILITY);
		frmLogin.setTitle("Login");
		frmLogin.setBounds(550, 100, 460, 580);

		JLabel lblNewLabel = new JLabel("Email");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setHorizontalAlignment(SwingConstants.CENTER);
		lblSenha.setFont(new Font("Tahoma", Font.BOLD, 15));

		txtemail = new JTextField();
		txtemail.setColumns(10);

		txtsenha = new JPasswordField();

		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = txtemail.getText();
				String senha = String.valueOf(txtsenha.getPassword());
				verificarTipoDeUsuario = dao.verificarUsuario(email, email, senha);
				verificar(email, senha);

				switch (verificarTipoDeUsuario) {
				case "QA":
					try {
						DAO dao = new DAO();
						DAO_configuracoes config = new DAO_configuracoes();
						frmLogin.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
						frmLogin.dispose();
						config.definirTema();

						CadastroDeCenariosView window = new CadastroDeCenariosView();
						window.frmCadastroDeCenrios.dispose();
						window.frmCadastroDeCenrios.setVisible(true);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "" + ex.getMessage());
					}
					break;

				case "Administrador":
					try {
						AdministradorView adm = new AdministradorView();
						adm.frame.setVisible(true);
						frmLogin.dispose();
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "" + e2.getMessage());
					}
					break;

				case "":
					JOptionPane.showMessageDialog(null, "Cadastro não encontrado!");
					break;
				}
			}
		});
		btnEntrar.setFont(new Font("Tahoma", Font.BOLD, 17));

		JSeparator separator = new JSeparator();
		JSeparator separator_1 = new JSeparator();

		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setEnabled(false);
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sendEmail = txtemail.getText();
				String sendSenha = String.valueOf(txtsenha.getPassword());

				try {
					String ver = dao.verificarUsuario(sendEmail, sendEmail, sendSenha);

					if (ver.equals("Administrador")) {
						if (verificar(sendEmail, sendSenha)) {
							try {
								CadastroDeUsuarioView window = new CadastroDeUsuarioView();
								window.frame.setVisible(true);
								frmLogin.dispose();
							} catch (Exception exe) {
								exe.printStackTrace();
							}
						}
					} else
						JOptionPane.showMessageDialog(null,
								"Usuário não é administrador! Entrar em contato com seu Administrador/Líder/Gerente para efetuar o cadastro de um novo usuário!");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null,
							"Usuário não é administrador! Entrar em contato com seu Administrador/Líder/Gerente para efetuar o cadastro de um novo usuário!");
				}
			}
		});
		btnCadastrar.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnCadastrar.setEnabled(true);

		logotipo = new JLabel("");
		logotipo.setIcon(new ImageIcon("src/main/java/util/TesteLOGO.png"));	
		logotipo.setHorizontalAlignment(SwingConstants.CENTER);

		GroupLayout groupLayout = new GroupLayout(frmLogin.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(10)
						.addComponent(separator, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE).addGap(10))
				.addGroup(groupLayout.createSequentialGroup().addGap(10)
						.addComponent(txtemail, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE).addGap(10))
				.addGroup(groupLayout.createSequentialGroup().addGap(10)
						.addComponent(txtsenha, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE).addGap(10))
				.addGroup(groupLayout.createSequentialGroup().addGap(10)
						.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE).addGap(10))
				.addGroup(groupLayout.createSequentialGroup().addGap(75)
						.addComponent(btnEntrar, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE).addGap(27)
						.addComponent(btnCadastrar, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE).addGap(90))
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE).addContainerGap())
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(lblSenha, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE).addContainerGap())
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(logotipo, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(logotipo, GroupLayout.PREFERRED_SIZE, 318, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(separator, GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE).addGap(7)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(txtemail).addGap(10)
						.addComponent(lblSenha, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(txtsenha).addGap(6)
						.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE).addGap(21)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnEntrar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(btnCadastrar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
						.addGap(26)));
		frmLogin.getContentPane().setLayout(groupLayout);
	}

	public Boolean verificar(String email, String senha) {
		try {
			dao = new DAO();
			return dao.validarLoginDAO(email, email, senha);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
			return false;
		}
	}
}
