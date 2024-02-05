package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import dao.DAO;
import dao.DAO_pessoa;
import model.Pessoa;

public class CadastroDeUsuarioView {

	JFrame frame;
	private JTextField sendNome;
	private JTextField sendEmail;
	private JTextField sendSenha;
	private JTextField sendConfirmarSenha;
	private JTextField txtcaminho;
	private JFileChooser arquivo;
	private File file;
	private int op;
	private JTextField sendEmpresa;
	private JTextField sendCargo;
	private JTable table;
	private JButton btnCadastrar;
	private JButton btnAlterar;
	private JButton btnExcluir;
	private JScrollPane scrollPane;
	private JButton btnAdicionarImagem;
	private DAO_pessoa dao;
	private DAO dao_;
	private String txtsenha;
	private String txtconfirmarSenha;
	private JLabel mostrarImagem;
	private Pessoa pessoa;

	private List<Pessoa> pessoas = new ArrayList<>(); 
	private JLabel sendId;
	private JComboBox<Object> comboBox_tipoDeUsuario;
	private JButton btnNovo;
	private JLabel lblEmail_2;
	private JLabel lblEmail_3;
	private JTextField sendLider;
	private JComboBox<String> comboBox_squad;
	protected String nome;
	protected String email;
	protected String empresa;
	protected String cargo;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenuItem opcao_sair;
	private JComboBox<String> comboBox_projeto;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmNewMenuItem_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;

				}
			}
		} catch (ClassNotFoundException ex) {
			System.err.println(ex);
		} catch (InstantiationException ex) {
			System.err.println(ex);
		} catch (IllegalAccessException ex) {
			System.err.println(ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			System.err.println(ex);
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroDeUsuarioView window = new CadastroDeUsuarioView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws Exception
	 */
	public CadastroDeUsuarioView() throws Exception {
		initialize();
		atualizarTabela();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(150, 10, 1187, 711);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setForeground(Color.BLUE);
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Cadastro", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(30, 144, 255)));
		panel_1.setBounds(10, 3, 698, 332);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		sendNome = new JTextField();
		sendNome.setBounds(65, 62, 623, 31);
		panel_1.add(sendNome);
		sendNome.setColumns(10);

		JLabel lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setBounds(10, 65, 50, 28);
		panel_1.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 101, 45, 28);
		panel_1.add(lblEmail);
		lblEmail.setFont(new Font("Times New Roman", Font.BOLD, 15));

		sendEmail = new JTextField();
		sendEmail.setBounds(65, 101, 623, 31);
		panel_1.add(sendEmail);
		sendEmail.setColumns(10);

		JLabel lblEmail_1 = new JLabel("Empresa:");
		lblEmail_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblEmail_1.setBounds(10, 180, 70, 29);
		panel_1.add(lblEmail_1);

		sendEmpresa = new JTextField();
		sendEmpresa.setColumns(10);
		sendEmpresa.setBounds(77, 178, 284, 31);
		panel_1.add(sendEmpresa);

		JLabel lblEmail_1_2 = new JLabel("Cargo:");
		lblEmail_1_2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblEmail_1_2.setBounds(10, 139, 50, 30);
		panel_1.add(lblEmail_1_2);

		sendCargo = new JTextField();
		sendCargo.setColumns(10);
		sendCargo.setBounds(65, 138, 396, 32);
		panel_1.add(sendCargo);

		JPanel panel_1_1_1 = new JPanel();
		panel_1_1_1.setLayout(null);
		panel_1_1_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(255, 255, 255), new Color(160, 160, 160)), "Cadastro da senha", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(30, 144, 255)));
		panel_1_1_1.setBounds(10, 219, 369, 105);
		panel_1.add(panel_1_1_1);

		sendConfirmarSenha = new JTextField();
		sendConfirmarSenha.setBounds(127, 59, 229, 34);
		panel_1_1_1.add(sendConfirmarSenha);
		sendConfirmarSenha.setColumns(10);

		JLabel lblEmail_1_1 = new JLabel("Confirmar Senha:");
		lblEmail_1_1.setBounds(10, 59, 117, 34);
		panel_1_1_1.add(lblEmail_1_1);
		lblEmail_1_1.setFont(new Font("Times New Roman", Font.BOLD, 15));

		sendSenha = new JTextField();
		sendSenha.setBounds(127, 15, 229, 34);
		panel_1_1_1.add(sendSenha);
		sendSenha.setColumns(10);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(67, 15, 60, 34);
		panel_1_1_1.add(lblSenha);
		lblSenha.setFont(new Font("Times New Roman", Font.BOLD, 15));

		JLabel lblCodigo = new JLabel("Codigo:");
		lblCodigo.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblCodigo.setBounds(10, 20, 62, 35);
		panel_1.add(lblCodigo);

		sendId = new JLabel("");
		sendId.setHorizontalAlignment(SwingConstants.CENTER);
		sendId.setForeground(new Color(30, 144, 255));
		sendId.setFont(new Font("Tahoma", Font.BOLD, 20));
		sendId.setBorder(
				new BevelBorder(BevelBorder.LOWERED, Color.BLACK, new Color(0, 0, 0), Color.BLACK, Color.DARK_GRAY));
		sendId.setBackground(new Color(0, 0, 0));
		sendId.setBounds(67, 20, 59, 35);
		panel_1.add(sendId);

		JLabel lblTipoDeUsurio = new JLabel("Tipo:");
		lblTipoDeUsurio.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblTipoDeUsurio.setBounds(471, 138, 45, 29);
		panel_1.add(lblTipoDeUsurio);

		comboBox_tipoDeUsuario = new JComboBox<Object>();
		comboBox_tipoDeUsuario.setFont(new Font("Tahoma", Font.BOLD, 15));
		comboBox_tipoDeUsuario.setModel(new DefaultComboBoxModel(new String[] { "QA", "Administrador" }));
		comboBox_tipoDeUsuario.setBounds(515, 138, 173, 31);
		panel_1.add(comboBox_tipoDeUsuario);

		lblEmail_2 = new JLabel("Squad:");
		lblEmail_2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblEmail_2.setBounds(381, 237, 50, 29);
		panel_1.add(lblEmail_2);

		lblEmail_3 = new JLabel("Lider:");
		lblEmail_3.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblEmail_3.setBounds(384, 279, 50, 29);
		panel_1.add(lblEmail_3);

		sendLider = new JTextField();
		sendLider.setColumns(10);
		sendLider.setBounds(432, 277, 256, 31);
		panel_1.add(sendLider);

		comboBox_squad = new JComboBox<String>();
		comboBox_squad.setModel(new DefaultComboBoxModel(new String[] {"teste", "teste"}));
		comboBox_squad.setFont(new Font("Tahoma", Font.BOLD, 15));
		comboBox_squad.setBounds(432, 238, 256, 31);
		panel_1.add(comboBox_squad);

		comboBox_projeto = new JComboBox<String>();
		comboBox_projeto.setFont(new Font("Tahoma", Font.BOLD, 15));
		comboBox_projeto.setBounds(432, 178, 256, 31);
		panel_1.add(comboBox_projeto);

		JLabel lblEmail_2_1 = new JLabel("Projetos:");
		lblEmail_2_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblEmail_2_1.setBounds(369, 180, 62, 29);
		panel_1.add(lblEmail_2_1);

		JPanel panel_2 = new JPanel();
		panel_2.setForeground(Color.BLUE);
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Imagem", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(30, 144, 255)));
		panel_2.setBounds(718, 0, 450, 389);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);

		mostrarImagem = new JLabel("");
		mostrarImagem.setBounds(10, 20, 430, 370);
		panel_2.add(mostrarImagem);

		JPanel panel_1_1 = new JPanel();
		panel_1_1.setForeground(Color.BLUE);
		panel_1_1.setLayout(null);
		panel_1_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Cadastro da imagem", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(30, 144, 255)));
		panel_1_1.setBounds(718, 393, 450, 138);
		frame.getContentPane().add(panel_1_1);

		txtcaminho = new JTextField();
		txtcaminho.setEnabled(false);
		txtcaminho.setEditable(false);
		txtcaminho.setBounds(10, 29, 430, 38);
		txtcaminho.setColumns(10);
		panel_1_1.add(txtcaminho);

		btnAdicionarImagem = new JButton("Adicionar imagem");
		btnAdicionarImagem.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnAdicionarImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				arquivo = new JFileChooser();
				arquivo.setDialogTitle("Adicionar imagem");
				arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
				op = arquivo.showOpenDialog(btnAdicionarImagem);
				if (op == JFileChooser.APPROVE_OPTION) {
					file = new File("");
					file = arquivo.getSelectedFile();
//					String fileName = file.getAbsolutePath();
					txtcaminho.setText(file.getAbsolutePath());
					ImageIcon imagem = new ImageIcon(file.getPath());
					mostrarImagem.setIcon(new ImageIcon(imagem.getImage().getScaledInstance(mostrarImagem.getWidth(),
							mostrarImagem.getHeight(), Image.SCALE_DEFAULT)));
				}
			}
		});
		btnAdicionarImagem.setBounds(79, 77, 280, 51);
		panel_1_1.add(btnAdicionarImagem);

		btnAlterar = new JButton("Alterar");
		btnAlterar.setForeground(Color.BLACK);
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nome = sendNome.getText();
				email = sendEmail.getText();
				empresa = sendEmpresa.getText();
				cargo = sendCargo.getText();

				if (nome.equals("") || email.equals("") || empresa.equals("") || cargo.equals("")) {
					JOptionPane.showMessageDialog(null,
							"Não é permitido a alteração enquanto os campos não estiverem preenchidos!");
				} else {
					try {
						pessoa = new Pessoa();
						pessoa.setId_pessoa(Integer.parseInt(sendId.getText()));
						pessoa.setNome(sendNome.getText());
						pessoa.setEmail(sendEmail.getText());
						pessoa.setEmpresa(sendEmpresa.getText());
						pessoa.setCargo(sendCargo.getText());
						pessoa.setSenha(sendSenha.getText());
						pessoa.setCaminhoDaFoto(txtcaminho.getText());
						pessoa.setTipoDeUsuario((String) comboBox_tipoDeUsuario.getSelectedItem());
						pessoa.setSquad((String) comboBox_squad.getSelectedItem());

						// abrir conexao
						dao = new DAO_pessoa();

						// Alterar
						dao.Alterar_pessoas(pessoa);
						JOptionPane.showMessageDialog(null, "Alterado com Sucesso!!!");
						atualizarTabela();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Erro ao alterar!!!" + e1.getMessage());
					}
				}

			}
		});
		btnAlterar.setFont(new Font("Tahoma", Font.PLAIN, 38));
		btnAlterar.setBackground(new Color(255, 215, 0));
		btnAlterar.setBounds(959, 588, 208, 50);
		frame.getContentPane().add(btnAlterar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.setForeground(Color.BLACK);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dao = new DAO_pessoa();
					int id_pessoa = Integer.parseInt(sendId.getText());

					switch (JOptionPane.showConfirmDialog(null,
							"Tem certeza que deseja excluir esse usuário?",
							"Confirmar exclusão", JOptionPane.YES_NO_OPTION)) {
					case 0:
						dao.EditarParaEXCLUIDO(id_pessoa);
						JOptionPane.showMessageDialog(null, "Excluido com Sucesso!!!");
						atualizarTabela();
						limpar();
						break;

					case 1:
						JOptionPane.showMessageDialog(null, "Usuário não foi excluido!");
						break;
					}

					atualizarTabela();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Erro ao Excluir Usuário!!!");
				}
			}
		});
		btnExcluir.setFont(new Font("Tahoma", Font.PLAIN, 38));
		btnExcluir.setBackground(new Color(255, 69, 0));
		btnExcluir.setBounds(959, 533, 209, 50);
		frame.getContentPane().add(btnExcluir);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 345, 698, 290);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "id", "Nome", "Email", "Empresa",
				"Cargo", "Senha", "Tipo de usuário", "Caminho da foto", "Data cadastro", "Projeto", "Squad" }));

		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				setCamposFromTabela();
			}
		});
		scrollPane.setViewportView(table);

		btnNovo = new JButton("Novo");
		btnNovo.setForeground(Color.BLACK);
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					atualizarTabela();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				limpar();
			}
		});
		btnNovo.setFont(new Font("Tahoma", Font.PLAIN, 38));
		btnNovo.setBackground(new Color(245, 245, 245));
		btnNovo.setBounds(718, 588, 231, 50);
		frame.getContentPane().add(btnNovo);

		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setForeground(Color.BLACK);
		btnCadastrar.setBounds(718, 533, 231, 50);
		frame.getContentPane().add(btnCadastrar);
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					txtsenha = sendSenha.getText();
					txtconfirmarSenha = sendConfirmarSenha.getText();
					nome = sendNome.getText();
					email = sendEmail.getText();
					empresa = sendEmpresa.getText();
					cargo = sendCargo.getText();

					Boolean duplicado = verificarDuplicidade(nome, email, txtsenha,
							(String) comboBox_tipoDeUsuario.getSelectedItem());

					if (duplicado == true) {
						JOptionPane.showMessageDialog(null,
								"Já possui este cadastro no sistema, por favor inserir um novo cadastro!");

					} else if (duplicado == false) {

						if (nome.equals(""))
							JOptionPane.showMessageDialog(null, "Preencha o campo nome!");
						else if (email.equals(""))
							JOptionPane.showMessageDialog(null, "Preencha o campo email!");
						else if (empresa.equals(""))
							JOptionPane.showMessageDialog(null, "Preencha o campo empresa!");
						else if (cargo.equals(""))
							JOptionPane.showMessageDialog(null, "Preencha o campo cargo!");
						else if (txtsenha.equals(""))
							JOptionPane.showMessageDialog(null, "Preencha o campo senha!");
						else if (txtconfirmarSenha.equals(""))
							JOptionPane.showMessageDialog(null, "Preencha o campo confirmação de senha!");
						else {
							try {
								if (txtsenha.equals(txtconfirmarSenha)) {
									dao = new DAO_pessoa();

									pessoa = new Pessoa();
									pessoa.setId_pessoa(0);
									pessoa.setNome(sendNome.getText());
									pessoa.setEmail(sendEmail.getText());
									pessoa.setEmpresa(sendEmpresa.getText());
									pessoa.setCargo(sendCargo.getText());
									pessoa.setSenha(sendSenha.getText());
									pessoa.setCaminhoDaFoto(txtcaminho.getText());
									pessoa.setTipoDeUsuario((String) comboBox_tipoDeUsuario.getSelectedItem());
									String dataNow = dao.getDateNow();
									pessoa.setData_pessoa(dataNow);
									pessoa.setSquad((String) comboBox_squad.getSelectedItem());

									// salvar
									dao.SalvarPessoa(pessoa);
									atualizarTabela();
									JOptionPane.showMessageDialog(null, "Cadastrado com Sucesso!!!");
								} else
									JOptionPane.showMessageDialog(null, "Senhas incompátiveis!");
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(null, "Erro ao cadastar!!!" + e1.getMessage());
							}
						}
					} else
						JOptionPane.showMessageDialog(null, "Cadastre outro usuário!");

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Ocorreu um erro na busca de dados!");
				}
			}
		});
		btnCadastrar.setBackground(new Color(135, 206, 250));
		btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 38));

		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		mnNewMenu = new JMenu("Arquivos");
		menuBar.add(mnNewMenu);

		opcao_sair = new JMenuItem("Sair");
		opcao_sair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					frame.dispose();
					LoginView window = new LoginView();
					window.frmLogin.setVisible(true);
				} catch (Exception ex) {

				}
			}
		});
		opcao_sair.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		mnNewMenu.add(opcao_sair);

		mntmNewMenuItem = new JMenuItem("Cadastrar projeto");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			private int id_pessoa;

			public void actionPerformed(ActionEvent e) {
				if (sendId.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Selecione um usuário para cadastrar um novo projeto!");
				} else {
					try {
						id_pessoa = Integer.parseInt(sendId.getText());
						CadastrarNomeDoProjetoView window = new CadastrarNomeDoProjetoView(id_pessoa, "ADM");
						window.frmCadastroDeProjeto.setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("CadastarSquad");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String squadName = JOptionPane.showInputDialog(null, "Insira o nome da squad");
				comboBox_squad.addItem(squadName);
			}
		});
		mntmNewMenuItem_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		mnNewMenu.add(mntmNewMenuItem_1);
		
		mntmNewMenuItem_2 = new JMenuItem("Lixeira");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Lixeira window = new Lixeira("ADMINISTRADOR");
					window.lixeiraView.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		mntmNewMenuItem_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
		mnNewMenu.add(mntmNewMenuItem_2);

	}

	public Boolean verificarDuplicidade(String nome, String email, String senha, String verificarTipoDeUsuario) {
		try {
			dao = new DAO_pessoa();
			return dao.validarDuplicidade(nome, email, senha, verificarTipoDeUsuario);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
			return false;
		}
	}

	/**
	 * METODO PARA FAZER REQUISICAO NO BANCO DE DADOS E PREENCHER OS VALORES NA
	 * TABELA.
	 * 
	 * @throws Exception
	 */

	public void atualizarTabela() throws Exception {
		try {
			dao = new DAO_pessoa();
			List<Object> ce = dao.buscarTodos_pessoasATIVAS();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setNumRows(0);
			for (int x = 0; x != ce.size(); x++) {
				model.addRow((Object[]) ce.get(x));
			}
			try {
				dao_ = new DAO();
				ResultSet rs = dao_.listarProjeto();

				while (rs.next()) {
					comboBox_projeto.addItem(rs.getString(2));
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	/**
	 * METODO PARA QUANDO O USUARIO SELECIONAR UMA LINHA DA TABELA, PREENCHERA OS
	 * CAMPOS DE ACORDO COM OS DADOS SELECIONADOS.
	 */

	public void setCamposFromTabela() {
		sendId.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
		sendNome.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
		sendEmail.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
		sendEmpresa.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 3)));
		sendCargo.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 4)));
		sendSenha.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 5)));
		comboBox_tipoDeUsuario.setSelectedItem(String.valueOf(table.getValueAt(table.getSelectedRow(), 6)));
		txtcaminho.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 7)));
		ImageIcon imagem = new ImageIcon(txtcaminho.getText());
		mostrarImagem.setIcon(new ImageIcon(imagem.getImage().getScaledInstance(mostrarImagem.getWidth(),
				mostrarImagem.getHeight(), Image.SCALE_DEFAULT)));
		comboBox_projeto.setSelectedItem(String.valueOf(table.getValueAt(table.getSelectedRow(), 9)));
		comboBox_squad.setSelectedItem(String.valueOf(table.getValueAt(table.getSelectedRow(), 10)));
		restaurarProjetos();
	}

	public void restaurarProjetos() {
		try {
			comboBox_projeto.removeAllItems();
			dao_ = new DAO();
			int id = Integer.parseInt(sendId.getText());
			ResultSet rs = dao_.listarProjetoComId(id);

			while (rs.next()) {
				comboBox_projeto.addItem(rs.getString(2));
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Carregar projeto: " + e.getMessage());
		}
	}

	/**
	 * METODO PARA LIMPAR TODOS OS CAMPOS
	 */

	public void limpar() {
		sendId.setText("");
		sendNome.setText("");
		sendEmail.setText("");
		sendEmpresa.setText("");
		sendCargo.setText("");
		sendSenha.setText("");
		sendConfirmarSenha.setText("");
		sendLider.setText("");
		txtcaminho.setText("");
		mostrarImagem.setIcon(null);
		comboBox_projeto.removeAllItems();
		comboBox_squad.removeAllItems();
	}

	/**
	 * METODO PARA VINCULAR OS VALORES OBTIDOS NA TABELA E SETAR A IMAGEM OBTIDA COM
	 * O VALOR DE CADA TABELA SELECIONADA.
	 * 
	 * (NÃO CONSEGUI FAZER FUNCIONAR)
	 */

	public void vincularImagem() {
		Pessoa p = pessoas.get(table.getSelectedRow());
		txtcaminho.setText(p.getCaminhoDaFoto());
		ImageIcon imagem = new ImageIcon(p.getCaminhoDaFoto());
		mostrarImagem.setIcon(new ImageIcon(imagem.getImage().getScaledInstance(mostrarImagem.getWidth(),
				mostrarImagem.getHeight(), Image.SCALE_DEFAULT)));

	}
}
