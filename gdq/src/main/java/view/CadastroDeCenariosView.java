package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

import dao.DAO;
import model.Cenarios;
import model.ConfiguracoesCenarios;
import model.Pessoa;
import model.Projeto;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.KeyAdapter;

public class CadastroDeCenariosView {

	JFrame frmCadastroDeCenrios;
	public JComboBox<String> comboBox_projeto;
	private Cenarios cenario;
	private DAO dao;
	private Projeto projeto;
	private JTextArea txtDescricao;
	private JTextField sendHistoria;
	private JComboBox<?> comboBox_abordagem;
	private JComboBox comboBox_criticidade;
	private JComboBox comboBox_regressao;
	private JComboBox comboBox_automacao;
	private JComboBox comboBox_status;
	private JTextField sendLinkDoBug;
	private JComboBox comboBox_motivoDaNaoExecucao;
	private JButton btnSalvar;
	private JButton btnConsultar;
	private JLabel lblMotivoDaNo;
	private JLabel lblStatus;
	private JTable tabela_cenarios;
	private JScrollPane scrollPane_1;
	private JCheckBox checkbox_bug;
	private JComboBox<String> comboBox_sprint;
	private JMenuItem mntmNewMenuItem;
	private JLabel sendCodigoCenario;
	public JTextField sendQA;
	private JLabel lblQa;
	private JTextField sendQTDhistorias;
	private JLabel lblProjeto_2;
	private JTextField sendHistoriasEntregues;
	private JLabel lblProjeto_3;
	private JTextField sendStoryPoints;
	private JLabel lblProjeto_4;
	private JTextField sendaTotalDevs;
	private JLabel lblLinkDoBug;
	public int idPESSOA;
	private JButton btnNovo;
	public static String nome;
	public static String sprint;
	Vector<Integer> id_informacoes = new Vector<Integer>();
	ConfiguracoesCenarios config;
	public int limparTodosOsCampos;
	public int manterDescricao;
	public String tema;

	/**
	 * Launch the application.
	 * 
	 * @throws Exception
	 */

//	public static void main(String[] args) throws Exception {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					CadastroDeCenariosView window = new CadastroDeCenariosView();
//					window.frmCadastroDeCenrios.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 * 
	 * @throws Exception
	 */
	public CadastroDeCenariosView() throws Exception {
		dao = new DAO();
		initialize();
		restaurarConfiguracoes();
		projeto = new Projeto();
		new Pessoa();

		atualizarTabela();
		restaurarProjetos();
		restaurarDadosComboBoxSprint();
		preencherCampoInformacoesDoProjeto((String) comboBox_sprint.getSelectedItem(),
				(String) comboBox_projeto.getSelectedItem());
		String escolhaTema = dao.recuperarTema();
		definirTema(escolhaTema);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws ParseException
	 */
	public void initialize() throws ParseException {
		frmCadastroDeCenrios = new JFrame();
		frmCadastroDeCenrios.setTitle("Cadastro de cenários");
		frmCadastroDeCenrios.setBounds(20, 20, 1500, 780);
		frmCadastroDeCenrios.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String escolhaTema = dao.recuperarTema();
		definirTema(escolhaTema);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 193, 703, 223);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 426, 1466, 285);

		tabela_cenarios = new JTable();
		tabela_cenarios.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				KeyStroke teste = KeyStroke.getKeyStrokeForEvent(e);
				if(String.valueOf(teste).toLowerCase().equals("pressed enter")) {
					JOptionPane.showMessageDialog(null, "DEU CEEEERRRRRRTOOOOOOO");	
				}
//				}else JOptionPane.showMessageDialog(null, "Não é possível fazer alteração", "", JOptionPane.WARNING_MESSAGE);
				
			}
		});
		tabela_cenarios.setAutoCreateRowSorter(true);
		tabela_cenarios.setAutoscrolls(false);
		tabela_cenarios.setFillsViewportHeight(true);
		tabela_cenarios.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tabela_cenarios.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent e) {
//				try {
//					setCamposFromTabela();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//			}
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					setCamposFromTabela();			
				}catch (Exception ex) {
				}
			}
		});
		
		tabela_cenarios.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "COD", "Projeto", "Historia", "Descricao", "Abordagem", "Prioridade", "Regressivo",
						"Automacao", "Status", "BUG", "Link do BUG", "Motivo da nao execucao", "Sprint",
						"Data de cadastro" }));
		tabela_cenarios.getColumnModel().getColumn(0).setPreferredWidth(10); // COD
		tabela_cenarios.getColumnModel().getColumn(3).setPreferredWidth(120); // Descricao
		tabela_cenarios.getColumnModel().getColumn(4).setPreferredWidth(20); // Abordagem
		tabela_cenarios.getColumnModel().getColumn(5).setPreferredWidth(20); // Prioridade
		tabela_cenarios.getColumnModel().getColumn(6).setPreferredWidth(15); // Regressivo
		tabela_cenarios.getColumnModel().getColumn(7).setPreferredWidth(20); // Automacao
		tabela_cenarios.getColumnModel().getColumn(8).setPreferredWidth(20); // Status
		tabela_cenarios.getColumnModel().getColumn(9).setPreferredWidth(6); // BUG
		tabela_cenarios.getColumnModel().getColumn(11).setPreferredWidth(20);
		tabela_cenarios.getColumnModel().getColumn(12).setPreferredWidth(20);
		tabela_cenarios.getColumnModel().getColumn(13).setPreferredWidth(70);

		scrollPane.setViewportView(tabela_cenarios);

		// ========================================== MENU

		JMenuBar menuBar = new JMenuBar();
		frmCadastroDeCenrios.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Arquivos");
		menuBar.add(mnNewMenu);

		mntmNewMenuItem = new JMenuItem("Exportar");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ExportView window = new ExportView();
					window.frmExport.setVisible(true);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		mntmNewMenuItem_1 = new JMenuItem("Cadastrar projeto");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					restaurarProjetos();
					CadastrarNomeDoProjetoView window = new CadastrarNomeDoProjetoView(idPESSOA, "QA");
					window.frmCadastroDeProjeto.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		mntmNewMenuItem_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
		mnNewMenu.add(mntmNewMenuItem_1);
		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem menuProjeto = new JMenuItem("Informacoes do projeto");
		menuProjeto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CadastroDeProjetoView window = new CadastroDeProjetoView();
					window.frame.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		menuProjeto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
		mnNewMenu.add(menuProjeto);

		JMenuItem menu_exit = new JMenuItem("Sair");
		menu_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginView windowLogin;
				try {
					windowLogin = new LoginView();
					windowLogin.frmLogin.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				frmCadastroDeCenrios.dispose();
			}
		});

		mntmNewMenuItem_2 = new JMenuItem("Lixeira");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Lixeira window = new Lixeira("QA");
					window.lixeiraView.setVisible(true);
				} catch (Exception xe) {
					xe.printStackTrace();
				}
			}
		});
		mntmNewMenuItem_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
		mnNewMenu.add(mntmNewMenuItem_2);

		mntmConfig = new JMenuItem("Configuração");
		mntmConfig.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
		mntmConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					restaurarConfiguracoes();
					configuracoesCadastroCenariosView window = new configuracoesCadastroCenariosView(frmCadastroDeCenrios);
					window.frmConfiguraoDoCadastro.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		mnNewMenu.add(mntmConfig);
		menu_exit.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		mnNewMenu.add(menu_exit);
		
		
		sendCodigoCenario = new JLabel("");
		sendCodigoCenario.setBounds(64, 8, 59, 35);
		sendCodigoCenario.setHorizontalAlignment(SwingConstants.CENTER);
		if(escolhaTema.toLowerCase().equals("dark")) {
			sendCodigoCenario.setForeground(Color.BLUE);
			sendCodigoCenario.setBackground(Color.BLUE);
		}else {
			sendCodigoCenario.setForeground(Color.BLACK);
			sendCodigoCenario.setBackground(Color.BLACK);			
		}
		sendCodigoCenario.setFont(new Font("Tahoma", Font.BOLD, 20));
		sendCodigoCenario.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));

		sendHistoria = new JTextField();
		sendHistoria.setBounds(209, 10, 737, 31);
		sendHistoria.setColumns(10);

		sendLinkDoBug = new JTextField();
		sendLinkDoBug.setBounds(434, 92, 512, 31);
		sendLinkDoBug.setEnabled(false);
		sendLinkDoBug.setColumns(10);

		txtDescricao = new JTextArea();
		scrollPane_1.setViewportView(txtDescricao);

		comboBox_abordagem = new JComboBox();
		comboBox_abordagem.setBounds(103, 53, 129, 27);
		comboBox_abordagem.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				if ((String) comboBox_abordagem.getSelectedItem() == "Positivo") {
				} else if ((String) comboBox_abordagem.getSelectedItem() == "Negativo") {
				} else {
					JOptionPane.showMessageDialog(null, "Selecione uma opção válida!!");
					comboBox_abordagem.setSelectedIndex(1);
				}
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboBox_abordagem.setModel(new DefaultComboBoxModel(new String[] { "Positivo", "Negativo" }));
		comboBox_abordagem.setEditable(true);
		comboBox_abordagem.setFont(new Font("Tahoma", Font.PLAIN, 15));

		comboBox_criticidade = new JComboBox();
		comboBox_criticidade.setBounds(103, 90, 129, 27);
		comboBox_criticidade.setModel(new DefaultComboBoxModel(new String[] { "Baixo", "Média", "Alta", "Crítica" }));
		comboBox_criticidade.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBox_criticidade.setEditable(true);

		comboBox_regressao = new JComboBox();
		comboBox_regressao.setBounds(105, 127, 127, 27);
		comboBox_regressao.setModel(new DefaultComboBoxModel(new String[] { "Nao", "Sim" }));
		comboBox_regressao.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBox_regressao.setEditable(true);

		comboBox_automacao = new JComboBox();
		comboBox_automacao.setBounds(343, 133, 127, 27);
		comboBox_automacao.setModel(new DefaultComboBoxModel(new String[] { "Nao", "Sim" }));
		comboBox_automacao.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBox_automacao.setEditable(true);

		comboBox_status = new JComboBox();
		comboBox_status.setBounds(561, 128, 175, 31);
		comboBox_status.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				if ((String) comboBox_status.getSelectedItem() == "Em andamento") {
				} else if ((String) comboBox_status.getSelectedItem() == "OK") {
				} else if ((String) comboBox_status.getSelectedItem() == "NOK") {
				} else if ((String) comboBox_status.getSelectedItem() == "Bloqueado") {
				} else {
					JOptionPane.showMessageDialog(null, "Selecione uma opção válida!!");
					comboBox_status.setSelectedIndex(2);
				}
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboBox_status.setModel(new DefaultComboBoxModel(new String[] { "Em andamento", "OK", "NOK", "Bloqueado" }));
		comboBox_status.setFont(new Font("Tahoma", Font.BOLD, 15));
		comboBox_status.setEditable(true);

		comboBox_motivoDaNaoExecucao = new JComboBox();
		comboBox_motivoDaNaoExecucao.setBounds(434, 51, 512, 27);
		comboBox_motivoDaNaoExecucao.setModel(new DefaultComboBoxModel(new String[] { "",
				"Funcionalidade prevista e nao entregue", "Funcionalidade saiu do escopo desta sprint",
				"Demora na entrega da funcionalidade", "Falta de planejamento de tempo do QA",
				"Teste nao era aplicavel", "Equipe despriorizou o teste ou o criterio de aceite",
				"Equipe priorizou testar na proxima sprint", "Ambiente indisponivel para teste" }));
		comboBox_motivoDaNaoExecucao.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBox_motivoDaNaoExecucao.setEditable(true);

		// ========================================== COMBOBOX
		// ==========================================

		// ========================================== LABEL
		JLabel testeCode = new JLabel("COD:");
		testeCode.setBounds(10, 8, 59, 35);
		testeCode.setFont(new Font("Tahoma", Font.BOLD, 20));

		JLabel historia = new JLabel("Historia: ");
		historia.setBounds(133, 8, 808, 35);
		historia.setFont(new Font("Tahoma", Font.BOLD, 15));

		lblLinkDoBug = new JLabel("Link do Bug:");
		lblLinkDoBug.setBounds(328, 90, 96, 33);
		lblLinkDoBug.setEnabled(false);
		lblLinkDoBug.setFont(new Font("Tahoma", Font.BOLD, 15));

		JLabel lblDescrio = new JLabel("Cenário");
		lblDescrio.setBounds(10, 164, 104, 27);
		lblDescrio.setForeground(Color.GRAY);
		lblDescrio.setFont(new Font("Tahoma", Font.BOLD, 15));

		JLabel lblNewLabel = new JLabel("Abordagem:");
		lblNewLabel.setBounds(10, 53, 96, 27);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));

		JLabel lblPrioridade = new JLabel("Criticidade:");
		lblPrioridade.setBounds(10, 90, 96, 27);
		lblPrioridade.setFont(new Font("Tahoma", Font.BOLD, 15));

		JLabel lblRegresso = new JLabel("Regressão:");
		lblRegresso.setBounds(10, 127, 96, 27);
		lblRegresso.setFont(new Font("Tahoma", Font.BOLD, 15));

		JLabel lblAutomao = new JLabel("Automação:");
		lblAutomao.setBounds(248, 133, 96, 27);
		lblAutomao.setFont(new Font("Tahoma", Font.BOLD, 15));

		lblMotivoDaNo = new JLabel("Motivo da não execução:");
		lblMotivoDaNo.setBounds(242, 52, 191, 27);
		lblMotivoDaNo.setFont(new Font("Tahoma", Font.BOLD, 15));

		lblStatus = new JLabel("Status:");
		lblStatus.setBounds(492, 127, 59, 33);
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 15));

		panel = new JPanel();
		panel.setBounds(951, 8, 525, 173);
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Informações do projeto", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setLayout(null);

		JLabel lblProjeto = new JLabel("Projeto:");
		lblProjeto.setBounds(23, 17, 62, 33);
		panel.add(lblProjeto);
		lblProjeto.setFont(new Font("Tahoma", Font.BOLD, 15));

		comboBox_projeto = new JComboBox();
		comboBox_projeto.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				comboBox_sprint.removeAllItems();
				restaurarDadosComboBoxSprint();
//				preencherCampoInformacoesDoProjeto();
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboBox_projeto.setBounds(95, 17, 419, 35);
		panel.add(comboBox_projeto);
		comboBox_projeto.setEditable(true);
		comboBox_projeto.setFont(new Font("Tahoma", Font.PLAIN, 15));

		lblQa = new JLabel("QA:");
		lblQa.setBounds(22, 55, 28, 27);
		panel.add(lblQa);
		lblQa.setFont(new Font("Tahoma", Font.BOLD, 15));

		sendQA = new JTextField();
		sendQA.setBounds(60, 55, 454, 31);
		panel.add(sendQA);
		sendQA.setEnabled(false);
		sendQA.setEditable(false);
		sendQA.setColumns(10);

		JLabel lblProjeto_1 = new JLabel("QTD Historias:");
		lblProjeto_1.setBounds(58, 92, 88, 31);
		panel.add(lblProjeto_1);
		lblProjeto_1.setFont(new Font("Tahoma", Font.BOLD, 12));

		lblProjeto_2 = new JLabel("Historias Entregues:");
		lblProjeto_2.setBounds(23, 133, 134, 27);
		panel.add(lblProjeto_2);
		lblProjeto_2.setFont(new Font("Tahoma", Font.BOLD, 12));

		sendHistoriasEntregues = new JTextField();
		sendHistoriasEntregues.setBounds(156, 130, 46, 31);
		panel.add(sendHistoriasEntregues);
		sendHistoriasEntregues.setEnabled(false);
		sendHistoriasEntregues.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sendHistoriasEntregues.setEditable(false);
		sendHistoriasEntregues.setColumns(10);

		sendQTDhistorias = new JTextField();
		sendQTDhistorias.setBounds(156, 92, 46, 31);
		panel.add(sendQTDhistorias);
		sendQTDhistorias.setEnabled(false);
		sendQTDhistorias.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sendQTDhistorias.setEditable(false);
		sendQTDhistorias.setColumns(10);

		lblProjeto_3 = new JLabel("Story points:");
		lblProjeto_3.setBounds(211, 92, 88, 33);
		panel.add(lblProjeto_3);
		lblProjeto_3.setFont(new Font("Tahoma", Font.BOLD, 12));

		lblProjeto_4 = new JLabel("Total Devs:");
		lblProjeto_4.setBounds(221, 130, 78, 33);
		panel.add(lblProjeto_4);
		lblProjeto_4.setFont(new Font("Tahoma", Font.BOLD, 12));

		sendaTotalDevs = new JTextField();
		sendaTotalDevs.setBounds(299, 130, 46, 31);
		panel.add(sendaTotalDevs);
		sendaTotalDevs.setEnabled(false);
		sendaTotalDevs.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sendaTotalDevs.setEditable(false);
		sendaTotalDevs.setColumns(10);

		sendStoryPoints = new JTextField();
		sendStoryPoints.setBounds(299, 92, 46, 31);
		panel.add(sendStoryPoints);
		sendStoryPoints.setEnabled(false);
		sendStoryPoints.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sendStoryPoints.setEditable(false);
		sendStoryPoints.setColumns(10);

		comboBox_sprint = new JComboBox();
		comboBox_sprint.setBounds(355, 130, 160, 31);
		panel.add(comboBox_sprint);
		comboBox_sprint.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				try {
					limpar_informacoes();
					preencherCampoInformacoesDoProjeto((String) comboBox_projeto.getSelectedItem(),
							(String) comboBox_sprint.getSelectedItem());
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboBox_sprint.setFont(new Font("Tahoma", Font.BOLD, 15));
		comboBox_sprint.setEditable(true);

		// ========================================== LABEL
		// ==========================================

		// ========================================== CHECKBOX

		checkbox_bug = new JCheckBox("BUG");
		checkbox_bug.setBounds(242, 91, 80, 30);
		checkbox_bug.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkbox_bug.isSelected()) {
					lblLinkDoBug.setEnabled(true);
					sendLinkDoBug.setEnabled(true);
				} else {
					lblLinkDoBug.setEnabled(false);
					sendLinkDoBug.setEnabled(false);
				}
			}
		});

		checkbox_bug.setFont(new Font("Tahoma", Font.BOLD, 15));

		// ========================================== CHECKBOX
		// ==========================================

		// ========================================== BUTTONS

		btnSalvar = new JButton("Salvar");
		btnSalvar.setForeground(Color.BLACK);
		btnSalvar.setBounds(1116, 361, 360, 55);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ================================
				nome = (String) comboBox_projeto.getSelectedItem();
				sprint = (String) comboBox_sprint.getSelectedItem();
				String sprint = (String) comboBox_sprint.getSelectedItem();
				if (sprint == "Selecionar Sprint") {
					JOptionPane.showMessageDialog(null, "Selecione uma sprint!", "Sprint",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					try {
						dao = new DAO();

						cenario = new Cenarios();
						cenario.setCodTeste(0);
						cenario.setHistoria(sendHistoria.getText());
						cenario.setDescricao(txtDescricao.getText());
						cenario.setAbordagem((String) comboBox_abordagem.getSelectedItem());
						cenario.setPrioridade((String) comboBox_criticidade.getSelectedItem());
						cenario.setEscopoDeRegressao((String) comboBox_regressao.getSelectedItem());
						cenario.setAutomacao((String) comboBox_automacao.getSelectedItem());
						cenario.setStatus((String) comboBox_status.getSelectedItem());
						cenario.setBug(checkbox_bug.isSelected());
						cenario.setLinkDoBug(sendLinkDoBug.getText());
						cenario.setMotivoDaNaoExecusao((String) comboBox_motivoDaNaoExecucao.getSelectedItem());

						if (chcDuplicidade.isSelected()) {
							dao.Salvar(cenario, nome, sprint);
							cenario.setAbordagem("Negativo");
							dao.Salvar(cenario, nome, sprint);
						} else {
							dao.Salvar(cenario, nome, sprint);
						}
						JOptionPane.showMessageDialog(null, "Salvo com Sucesso!!!");
						atualizarTabela();
						restaurarConfiguracoes();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Erro ao Salvar!!!" + e1.getMessage());
					}
					// ================================
				}
			}
		});
		btnSalvar.setBackground(new Color(124, 252, 0));
		btnSalvar.setFont(new Font("Trebuchet MS", Font.BOLD, 32));

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setForeground(Color.BLACK);
		btnExcluir.setBounds(1116, 192, 175, 50);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dao = new DAO();
					int codigo = Integer.parseInt(sendCodigoCenario.getText());

					switch (JOptionPane.showConfirmDialog(null, "Deseja excluir esse cenário?", "Confirmação",
							JOptionPane.YES_NO_OPTION)) {
					case 0:
						dao.EditarStatusParaExcluido(codigo);
						JOptionPane.showMessageDialog(null, "Excluido com Sucesso!!!");
						break;

					case 1:
						JOptionPane.showMessageDialog(null, "Cenário não foi excluido!");
						break;
					}

					atualizarTabela();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Erro ao Excluir!!!");
				}
			}
		});
		btnExcluir.setFont(new Font("Trebuchet MS", Font.BOLD, 32));
		btnExcluir.setBackground(new Color(255, 0, 0));

		btnConsultar = new JButton("Consultar");
		btnConsultar.setForeground(Color.BLACK);
		btnConsultar.setBounds(1116, 247, 360, 55);
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// =================================
				ConsultarView window = new ConsultarView();
				window.frmConsulteCenrios.setVisible(true);
				// ================================
			}
		});
		btnConsultar.setFont(new Font("Trebuchet MS", Font.BOLD, 32));
		btnConsultar.setBackground(new Color(135, 206, 250));

		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.setForeground(Color.BLACK);
		btnAlterar.setBounds(1301, 192, 175, 50);
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// =============================================================
				nome = (String) comboBox_projeto.getSelectedItem();
				sprint = (String) comboBox_sprint.getSelectedItem();
				try {
					// criado objeto leitor para pegar os dados da tela
					cenario = new Cenarios();
					cenario.setHistoria(sendHistoria.getText());
					cenario.setDescricao(txtDescricao.getText());
					cenario.setAbordagem((String) comboBox_abordagem.getSelectedItem());
					cenario.setPrioridade((String) comboBox_criticidade.getSelectedItem());
					cenario.setEscopoDeRegressao((String) comboBox_regressao.getSelectedItem());
					cenario.setAutomacao((String) comboBox_automacao.getSelectedItem());
					cenario.setStatus((String) comboBox_status.getSelectedItem());
					cenario.setBug(checkbox_bug.isSelected());
					cenario.setLinkDoBug(sendLinkDoBug.getText());
					cenario.setMotivoDaNaoExecusao((String) comboBox_motivoDaNaoExecucao.getSelectedItem());
					cenario.setCodTeste(Integer.parseInt(sendCodigoCenario.getText()));

					// abrir conexao
					dao = new DAO();

					// Alterar
					dao.Alterar(cenario, nome, sprint);
					JOptionPane.showMessageDialog(null, "Alterado com Sucesso!!!");
					atualizarTabela();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Erro ao Alterar!!!\n" + e1.getMessage());
				}
			}
		});
		btnAlterar.setFont(new Font("Trebuchet MS", Font.BOLD, 32));
		btnAlterar.setBackground(new Color(255, 255, 0));

		btnNovo = new JButton("Novo");
		btnNovo.setForeground(Color.BLACK);
		btnNovo.setBounds(1116, 304, 360, 55);
		btnNovo.setFont(new Font("Trebuchet MS", Font.BOLD, 37));
		btnNovo.setBackground(new Color(245, 245, 245));
		frmCadastroDeCenrios.getContentPane().setLayout(null);
		frmCadastroDeCenrios.getContentPane().add(sendCodigoCenario);
		frmCadastroDeCenrios.getContentPane().add(testeCode);
		frmCadastroDeCenrios.getContentPane().add(sendHistoria);
		frmCadastroDeCenrios.getContentPane().add(historia);
		frmCadastroDeCenrios.getContentPane().add(comboBox_abordagem);
		frmCadastroDeCenrios.getContentPane().add(lblNewLabel);
		frmCadastroDeCenrios.getContentPane().add(lblMotivoDaNo);
		frmCadastroDeCenrios.getContentPane().add(comboBox_motivoDaNaoExecucao);
		frmCadastroDeCenrios.getContentPane().add(lblPrioridade);
		frmCadastroDeCenrios.getContentPane().add(comboBox_criticidade);
		frmCadastroDeCenrios.getContentPane().add(checkbox_bug);
		frmCadastroDeCenrios.getContentPane().add(lblLinkDoBug);
		frmCadastroDeCenrios.getContentPane().add(sendLinkDoBug);
		frmCadastroDeCenrios.getContentPane().add(comboBox_regressao);
		frmCadastroDeCenrios.getContentPane().add(lblRegresso);
		frmCadastroDeCenrios.getContentPane().add(lblAutomao);
		frmCadastroDeCenrios.getContentPane().add(comboBox_automacao);
		frmCadastroDeCenrios.getContentPane().add(lblStatus);
		frmCadastroDeCenrios.getContentPane().add(comboBox_status);
		frmCadastroDeCenrios.getContentPane().add(lblDescrio);
		frmCadastroDeCenrios.getContentPane().add(panel);
		frmCadastroDeCenrios.getContentPane().add(scrollPane_1);
		frmCadastroDeCenrios.getContentPane().add(btnSalvar);
		frmCadastroDeCenrios.getContentPane().add(btnConsultar);
		frmCadastroDeCenrios.getContentPane().add(btnNovo);
		frmCadastroDeCenrios.getContentPane().add(btnAlterar);
		frmCadastroDeCenrios.getContentPane().add(btnExcluir);
		frmCadastroDeCenrios.getContentPane().add(scrollPane);

		JLabel lblResultadoEsperado = new JLabel("Resultado Esperado");
		lblResultadoEsperado.setForeground(Color.GRAY);
		lblResultadoEsperado.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblResultadoEsperado.setBounds(721, 164, 161, 27);
		frmCadastroDeCenrios.getContentPane().add(lblResultadoEsperado);

		JScrollPane scrollPane_1_1 = new JScrollPane();
		scrollPane_1_1.setBounds(721, 193, 385, 223);
		frmCadastroDeCenrios.getContentPane().add(scrollPane_1_1);

		JTextArea textArea = new JTextArea();
		scrollPane_1_1.setViewportView(textArea);

		chcDuplicidade = new JCheckBox("duplicidade");
		chcDuplicidade.setBounds(335, 38, 93, 21);
		frmCadastroDeCenrios.getContentPane().add(chcDuplicidade);
		chcDuplicidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox_abordagem.setSelectedItem("Positivo");
			}
		});
		chcDuplicidade.setEnabled(false);

		chcLimparTodosOsCampos = new JCheckBox("limparCampos");
		chcLimparTodosOsCampos.setBounds(245, 38, 93, 21);
		frmCadastroDeCenrios.getContentPane().add(chcLimparTodosOsCampos);
		chcLimparTodosOsCampos.setVisible(false);
		chcLimparTodosOsCampos.setEnabled(false);
		chcDuplicidade.setVisible(false);
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// =================================
				try {
//					limpar();
//					limpar_informacoes();
					comboBox_sprint.removeAllItems();
					restaurarDadosComboBoxSprint();
					comboBox_projeto.removeAllItems();
					restaurarProjetos();
					atualizarTabela();
					restaurarConfiguracoes();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				// ================================
			}
		});
		escolhaTema = dao.recuperarTema();
		definirTema(escolhaTema);
	}

	public void setCamposFromTabela() throws SQLException, Exception {
		sendCodigoCenario.setText(String.valueOf(tabela_cenarios.getValueAt(tabela_cenarios.getSelectedRow(), 0)));
		comboBox_projeto
				.setSelectedItem(String.valueOf(tabela_cenarios.getValueAt(tabela_cenarios.getSelectedRow(), 1)));
		sendHistoria.setText(String.valueOf(tabela_cenarios.getValueAt(tabela_cenarios.getSelectedRow(), 2)));
		txtDescricao.setText(String.valueOf(tabela_cenarios.getValueAt(tabela_cenarios.getSelectedRow(), 3)));
		comboBox_abordagem
				.setSelectedItem(String.valueOf(tabela_cenarios.getValueAt(tabela_cenarios.getSelectedRow(), 4)));
		comboBox_criticidade
				.setSelectedItem(String.valueOf(tabela_cenarios.getValueAt(tabela_cenarios.getSelectedRow(), 5)));
		comboBox_regressao
				.setSelectedItem(String.valueOf(tabela_cenarios.getValueAt(tabela_cenarios.getSelectedRow(), 6)));
		comboBox_automacao
				.setSelectedItem(String.valueOf(tabela_cenarios.getValueAt(tabela_cenarios.getSelectedRow(), 7)));
		comboBox_status
				.setSelectedItem(String.valueOf(tabela_cenarios.getValueAt(tabela_cenarios.getSelectedRow(), 8)));

		String bug = String.valueOf(tabela_cenarios.getValueAt(tabela_cenarios.getSelectedRow(), 9));
		if (bug.toString().equals("Sim")) {
			checkbox_bug.setSelected(true);
			sendLinkDoBug.setEnabled(true);
			lblLinkDoBug.setEnabled(true);
		} else {
			checkbox_bug.setSelected(false);
			sendLinkDoBug.setEnabled(false);
			lblLinkDoBug.setEnabled(false);
		}

		sendLinkDoBug.setText(String.valueOf(tabela_cenarios.getValueAt(tabela_cenarios.getSelectedRow(), 10)));
		comboBox_motivoDaNaoExecucao
				.setSelectedItem(String.valueOf(tabela_cenarios.getValueAt(tabela_cenarios.getSelectedRow(), 11)));
		comboBox_sprint
				.setSelectedItem(String.valueOf(tabela_cenarios.getValueAt(tabela_cenarios.getSelectedRow(), 12)));

		preencherCampoInformacoesDoProjeto((String) comboBox_projeto.getSelectedItem(),
				(String) comboBox_sprint.getSelectedItem());
	}

	public void preencherCampoInformacoesDoProjeto(String proj, String spri) {
		try {
			dao = new DAO();

//			System.out.println("PROJETO: "+proj+" SPRINT: "+spri);

			projeto = dao.buscarTodos_informacoes(proj, spri);
			sendQA.setText(projeto.getQa());
			sendQTDhistorias.setText(Integer.toString(dao.countQtdDeCenariosPorSprint(projeto.getId())));
			sendHistoriasEntregues.setText(Integer.toString(dao.countQtdDeCenariosPorSprint_OK(projeto.getId())));
			sendStoryPoints.setText(Integer.toString(projeto.getStoryPoints()));
			sendaTotalDevs.setText(Integer.toString(projeto.getTotalDevs()));

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void atualizarTabela() throws Exception {
		try {
			List<Object> ce = dao.buscarTodos();
			DefaultTableModel model = (DefaultTableModel) tabela_cenarios.getModel();
			model.setNumRows(0);
			for (int x = 0; x != ce.size(); x++) {
				model.addRow((Object[]) ce.get(x));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private JPanel panel;
	private JMenuItem mntmNewMenuItem_1;
	private JMenuItem mntmNewMenuItem_2;
	private JMenuItem mntmConfig;
	public JCheckBox chcLimparTodosOsCampos;
	private JCheckBox chcDuplicidade;
	public int duplicidade;

	public void restaurarProjetos() {
		try {
			dao = new DAO();
			ResultSet rs = dao.listarProjeto();

			while (rs.next()) {
				id_informacoes.addElement(rs.getInt(1));
				comboBox_projeto.addItem(rs.getString(2));
				idPESSOA = rs.getInt(3);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Carregar projeto: " + e.getMessage());
		}
	}

	public void restaurarDadosComboBoxSprint() {
		try {
			dao = new DAO();
			String nomeDoProjeto = (String) comboBox_projeto.getSelectedItem();

			ResultSet rs = dao.listarSprint(nomeDoProjeto);
			comboBox_sprint.addItem("Selecionar Sprint");
			while (rs.next()) {
				id_informacoes.addElement(rs.getInt(1));
				comboBox_sprint.addItem(rs.getString(2));
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Carregar sprint: " + e.getMessage());
		}
	}

	public void limpar() {
		sendCodigoCenario.setText(null);
		sendHistoria.setText(null);
		txtDescricao.setText(null);
		comboBox_abordagem.setSelectedIndex(0);
		comboBox_criticidade.setSelectedIndex(0);
		comboBox_regressao.setSelectedIndex(0);
		comboBox_automacao.setSelectedIndex(0);
		comboBox_status.setSelectedIndex(0);
		checkbox_bug.setSelected(false);
		sendLinkDoBug.setText(null);
		comboBox_motivoDaNaoExecucao.setSelectedIndex(0);
	}

	public void limpar_informacoes() {
		sendaTotalDevs.setText(null);
		sendQA.setText(null);
		sendQTDhistorias.setText(null);
		sendHistoriasEntregues.setText(null);
		sendStoryPoints.setText(null);
	}

	public void restaurarConfiguracoes() {
		try {
			DAO dao = new DAO();
			ConfiguracoesCenarios config = new ConfiguracoesCenarios();

			ResultSet rs = dao.RestaurarConfiguracoesDoCenario();
			while (rs.next()) {
				comboBox_abordagem.setSelectedIndex(rs.getInt("abordagem"));
				comboBox_criticidade.setSelectedIndex(rs.getInt("criticidade"));
				comboBox_status.setSelectedIndex(rs.getInt("estatus"));
				limparTodosOsCampos = rs.getInt("limparTodosOsCampos");
				duplicidade = rs.getInt("duplicidade");

				config.setTema(rs.getString("tema"));
				tema = config.getTema();

				if (limparTodosOsCampos == 1) {
					chcLimparTodosOsCampos.setSelected(true);
					limpar();
				} else {
					chcLimparTodosOsCampos.setSelected(false);
				}

				manterDescricao = rs.getInt("manterCampoDescricao");
				if (manterDescricao == 0) {
					txtDescricao.setText(null);
				}

				comboBox_automacao.setSelectedIndex(rs.getInt("opcaoSimAutomatizados"));
				comboBox_regressao.setSelectedIndex(rs.getInt("opcaoSimRegressivo"));

				if (duplicidade == 1) {
					chcDuplicidade.setSelected(true);
					limpar();
				} else {
					chcDuplicidade.setSelected(false);
				}
				
				
			}
		} catch (Exception e) {
			System.out.println("Erro ao buscar dados no banco!");
		}
	}

	public void definirTema(String escolhaTema) {
		
		try {
			if (escolhaTema.equals("Dark")) {
				UIManager.setLookAndFeel("com.formdev.flatlaf.themes.FlatMacDarkLaf");
			} else if (escolhaTema.equals("Light")) {
				UIManager.setLookAndFeel("com.formdev.flatlaf.themes.FlatMacLightLaf");
			} else if (escolhaTema.equals("Nimbus")) {
				for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						javax.swing.UIManager.setLookAndFeel(info.getClassName());
						break;
					}
					UIManager.put("nimbusOrange", new Color(0, 204, 0));
				}
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Tema não definido");
		}
	}

}
