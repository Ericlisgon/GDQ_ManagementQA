package view;

import java.awt.Color;
import java.awt.Dimension;
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
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import dao.DAO;
import dao.DAOADM;
import dao.DAO_configuracoes;
import excel.EditorExcel;
import excel.ExportExcel;
import model.ConfiguracoesCenarios;
import model.Pessoa;

public class AdministradorView {

	JFrame frame;
	private JTextField sendId;
	private JTable table;
	private JScrollPane scrollPane;
	private DAOADM dao;
	private JTextField send_id;
	private JTextField send_nome;
	private JPanel panel;
	private JLabel mostrarImagem;
	private JLabel Nome;
	private JTextField sendNOME;
	private JComboBox<String> comboBox_projeto;
	private JTextField sendQTDHistorias;
	private JLabel lblQtd;
	private JLabel lblStatus;
	private JLabel statusProjeto;
	private JLabel qtdBug;
	private JLabel lblBug;
	private JLabel lblDiasParaFinalizao;
	private JLabel diasParaFinalizar;
	private JLabel lblQuantidadeDeAutomaes;
	private JLabel qtdAutomacoes;
	private JLabel lblTestesPrioridadeAlta;
	private JLabel testeCritico;
	private JLabel lblTestesPrioridadeAlto;
	private JLabel testeAlto;
	private JLabel lblTestesPrioridadeBaixo;
	private JLabel testeBaixo;
	private JLabel testeMedio;
	private JLabel lblTestesPrioridadeMdio;
	private JLabel testeFinalizado;
	private JLabel lblQuantidadeTestesFinalizados;
	private JLabel testeEmAberto;
	private JLabel lblTestesEmAberto;
	private JLabel lblTestesPositivos;
	private JLabel testePositivo;
	private JLabel lblTestesNegativo;
	private JLabel testeNegativo;

	private JComboBox<String> comboBox_sprint;
	private JLabel lblSprint;
	private JButton btnExportar;
	private JProgressBar progressBar;
	private LocalDate data = null;
	private LocalDate dataStart = null;
	private JFormattedTextField dataFim;
	private JFormattedTextField dataInicio;
	private JLabel statusPro;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenuItem mntmNewMenuItem;
	private DAO_configuracoes daoConfig;
	private JTextField txtcaminho;
	private JFileChooser arquivo;
	private File file;
	private int op;

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
				UIManager.put("nimbusOrange", new Color(0, 204, 0));
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
					AdministradorView window = new AdministradorView();
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
	public AdministradorView() throws Exception {
		initialize();
		dao = new DAOADM();
		daoConfig = new DAO_configuracoes();
		atualizarTabela();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		// frame.setBounds(0, 0, 1700, 850);
		frame.setBounds(-10, -3, Integer.MAX_VALUE, Integer.MAX_VALUE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setRequestFocusEnabled(false);
		scrollPane.setBounds(10, 529, 1520, 274);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setPreferredScrollableViewportSize(new Dimension(20, 20));
		table.setSurrendersFocusOnKeystroke(true);
		table.setFillsViewportHeight(true);
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				setCamposFromTabela();
			}
		});
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nome", "Email", "Empresa",
				"Cargo", "Senha cadastrada", "Caminho da foto", "Tipo de usuário", "Data" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		scrollPane.setViewportView(table);

		send_id = new JTextField();
		send_id.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				TableRowSorter<TableModel> filtro = null;
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				filtro = new TableRowSorter<TableModel>(model);
				table.setRowSorter(filtro);

				if (send_id.getText().length() == 0) {
					filtro.setRowFilter(null);
				} else {
					filtro.setRowFilter(RowFilter.regexFilter("(?i)" + send_id.getText(), 0));
				}
			}
		});
		send_id.setBounds(10, 489, 45, 30);
		frame.getContentPane().add(send_id);
		send_id.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setBounds(10, 473, 25, 13);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Nome");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1_1.setBounds(68, 467, 66, 25);
		frame.getContentPane().add(lblNewLabel_1_1);

		send_nome = new JTextField();
		send_nome.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				TableRowSorter<TableModel> filtro = null;
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				filtro = new TableRowSorter<TableModel>(model);
				table.setRowSorter(filtro);

				if (send_nome.getText().length() == 0) {
					filtro.setRowFilter(null);
				} else {
					filtro.setRowFilter(RowFilter.regexFilter("(?i)" + send_nome.getText(), 1)); // 1 -> significa a
																									// coluna na tabela
				}
			}
		});
		send_nome.setColumns(10);
		send_nome.setBounds(66, 489, 362, 30);
		frame.getContentPane().add(send_nome);

		panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Dados",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 10, 1520, 447);
		frame.getContentPane().add(panel);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(1045, 10, 465, 427);
		panel.add(panel_2);
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Imagem",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		mostrarImagem = new JLabel("");
		mostrarImagem.setBounds(10, 30, 445, 387);
		panel_2.add(mostrarImagem);

		JLabel lblNewLabel = new JLabel("ID:");
		lblNewLabel.setBounds(10, 25, 45, 39);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));

		sendId = new JTextField();
		sendId.setHorizontalAlignment(SwingConstants.CENTER);
		sendId.setBounds(41, 25, 45, 39);
		panel.add(sendId);
		sendId.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		sendId.setFont(new Font("Tahoma", Font.BOLD, 20));
		sendId.setBackground(new Color(255, 255, 255));
		sendId.setEnabled(false);
		sendId.setEditable(false);
		sendId.setColumns(10);

		Nome = new JLabel("Nome:");
		Nome.setFont(new Font("Tahoma", Font.BOLD, 15));
		Nome.setBounds(96, 25, 57, 39);
		panel.add(Nome);

		sendNOME = new JTextField();
		sendNOME.setEditable(false);
		sendNOME.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sendNOME.setBounds(153, 25, 413, 39);
		panel.add(sendNOME);
		sendNOME.setColumns(10);

		comboBox_projeto = new JComboBox<String>();
		comboBox_projeto.setEnabled(false);
		comboBox_projeto.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				limpar();
				comboBox_sprint.setEnabled(true);
				restaurarSprint();
				restaurarAbordagem();
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboBox_projeto.setFont(new Font("Tahoma", Font.BOLD, 15));
		comboBox_projeto.setBounds(655, 129, 380, 39);
		panel.add(comboBox_projeto);

		sendQTDHistorias = new JTextField();
		sendQTDHistorias.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sendQTDHistorias.setEditable(false);
		sendQTDHistorias.setColumns(10);
		sendQTDHistorias.setBounds(197, 79, 57, 39);
		panel.add(sendQTDHistorias);

		lblQtd = new JLabel("Quantidade de historias: ");
		lblQtd.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblQtd.setBounds(10, 84, 193, 28);
		panel.add(lblQtd);

		lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblStatus.setBounds(814, 232, 64, 39);
		panel.add(lblStatus);

		statusProjeto = new JLabel("");
		statusProjeto.setToolTipText("Este campo é utilizado para verificar o risco do projeto!");
		statusProjeto.setHorizontalAlignment(SwingConstants.CENTER);
		statusProjeto.setFont(new Font("Tahoma", Font.BOLD, 20));
		statusProjeto.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		statusProjeto.setBounds(877, 232, 158, 39);
		panel.add(statusProjeto);

		qtdBug = new JLabel("");
		qtdBug.setBackground(Color.WHITE);
		qtdBug.setHorizontalAlignment(SwingConstants.CENTER);
		qtdBug.setFont(new Font("Tahoma", Font.PLAIN, 15));
		qtdBug.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		qtdBug.setBounds(197, 128, 57, 39);
		panel.add(qtdBug);

		lblBug = new JLabel("Quantidade de BUG:");
		lblBug.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblBug.setBounds(10, 128, 158, 39);
		panel.add(lblBug);

		lblDiasParaFinalizao = new JLabel("Dias para finalização:");
		lblDiasParaFinalizao.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDiasParaFinalizao.setBounds(10, 177, 177, 39);
		panel.add(lblDiasParaFinalizao);

		diasParaFinalizar = new JLabel("");
		diasParaFinalizar.setHorizontalAlignment(SwingConstants.CENTER);
		diasParaFinalizar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		diasParaFinalizar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		diasParaFinalizar.setBounds(197, 177, 57, 39);
		panel.add(diasParaFinalizar);

		lblQuantidadeDeAutomaes = new JLabel("Quantidade de automações:");
		lblQuantidadeDeAutomaes.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblQuantidadeDeAutomaes.setBounds(286, 79, 214, 39);
		panel.add(lblQuantidadeDeAutomaes);

		qtdAutomacoes = new JLabel("");
		qtdAutomacoes.setHorizontalAlignment(SwingConstants.CENTER);
		qtdAutomacoes.setFont(new Font("Tahoma", Font.PLAIN, 15));
		qtdAutomacoes.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		qtdAutomacoes.setBounds(509, 79, 57, 39);
		panel.add(qtdAutomacoes);

		lblTestesPrioridadeAlta = new JLabel("Testes prioridade critico:");
		lblTestesPrioridadeAlta.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTestesPrioridadeAlta.setBounds(286, 128, 214, 39);
		panel.add(lblTestesPrioridadeAlta);

		testeCritico = new JLabel("");
		testeCritico.setHorizontalAlignment(SwingConstants.CENTER);
		testeCritico.setFont(new Font("Tahoma", Font.PLAIN, 15));
		testeCritico.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		testeCritico.setBounds(509, 128, 57, 39);
		panel.add(testeCritico);

		lblTestesPrioridadeAlto = new JLabel("Testes prioridade alto:");
		lblTestesPrioridadeAlto.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTestesPrioridadeAlto.setBounds(286, 177, 214, 39);
		panel.add(lblTestesPrioridadeAlto);

		testeAlto = new JLabel("");
		testeAlto.setHorizontalAlignment(SwingConstants.CENTER);
		testeAlto.setFont(new Font("Tahoma", Font.PLAIN, 15));
		testeAlto.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		testeAlto.setBounds(509, 177, 57, 39);
		panel.add(testeAlto);

		lblTestesPrioridadeBaixo = new JLabel("Testes prioridade baixo");
		lblTestesPrioridadeBaixo.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTestesPrioridadeBaixo.setBounds(286, 275, 214, 39);
		panel.add(lblTestesPrioridadeBaixo);

		testeBaixo = new JLabel("");
		testeBaixo.setHorizontalAlignment(SwingConstants.CENTER);
		testeBaixo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		testeBaixo.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		testeBaixo.setBounds(509, 275, 57, 39);
		panel.add(testeBaixo);

		testeMedio = new JLabel("");
		testeMedio.setHorizontalAlignment(SwingConstants.CENTER);
		testeMedio.setFont(new Font("Tahoma", Font.PLAIN, 15));
		testeMedio.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		testeMedio.setBounds(509, 226, 57, 39);
		panel.add(testeMedio);

		lblTestesPrioridadeMdio = new JLabel("Testes prioridade médio");
		lblTestesPrioridadeMdio.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTestesPrioridadeMdio.setBounds(286, 226, 214, 39);
		panel.add(lblTestesPrioridadeMdio);

		testeFinalizado = new JLabel("");
		testeFinalizado.setFont(new Font("Tahoma", Font.PLAIN, 15));
		testeFinalizado.setHorizontalAlignment(SwingConstants.CENTER);
		testeFinalizado.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		testeFinalizado.setBounds(197, 226, 57, 39);
		panel.add(testeFinalizado);

		lblQuantidadeTestesFinalizados = new JLabel("Testes Finalizados:");
		lblQuantidadeTestesFinalizados.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblQuantidadeTestesFinalizados.setBounds(10, 226, 177, 39);
		panel.add(lblQuantidadeTestesFinalizados);

		testeEmAberto = new JLabel("");
		testeEmAberto.setHorizontalAlignment(SwingConstants.CENTER);
		testeEmAberto.setFont(new Font("Tahoma", Font.PLAIN, 15));
		testeEmAberto.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		testeEmAberto.setBounds(197, 275, 57, 39);
		panel.add(testeEmAberto);

		lblTestesEmAberto = new JLabel("Testes em aberto:");
		lblTestesEmAberto.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTestesEmAberto.setBounds(10, 275, 177, 39);
		panel.add(lblTestesEmAberto);

		lblTestesPositivos = new JLabel("Testes positivos:");
		lblTestesPositivos.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTestesPositivos.setBounds(10, 327, 177, 39);
		panel.add(lblTestesPositivos);

		testePositivo = new JLabel("");
		testePositivo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		testePositivo.setHorizontalAlignment(SwingConstants.CENTER);
		testePositivo.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		testePositivo.setBounds(197, 327, 57, 39);
		panel.add(testePositivo);

		lblTestesNegativo = new JLabel("Testes negativo:");
		lblTestesNegativo.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTestesNegativo.setBounds(286, 327, 214, 39);
		panel.add(lblTestesNegativo);

		testeNegativo = new JLabel("");
		testeNegativo.setHorizontalAlignment(SwingConstants.CENTER);
		testeNegativo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		testeNegativo.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		testeNegativo.setBounds(509, 327, 57, 39);
		panel.add(testeNegativo);

		comboBox_sprint = new JComboBox<String>();
		comboBox_sprint.setToolTipText("");
		comboBox_sprint.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				restaurarAbordagem();
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboBox_sprint.setFont(new Font("Tahoma", Font.BOLD, 15));
		comboBox_sprint.setBounds(842, 183, 193, 39);
		comboBox_sprint.setEnabled(false);
		panel.add(comboBox_sprint);

		lblSprint = new JLabel("Sprint:");
		lblSprint.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSprint.setBounds(779, 183, 64, 39);
		panel.add(lblSprint);

		btnExportar = new JButton("Exportar");
		btnExportar.setEnabled(false);
		btnExportar.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pessoa pessoa = new Pessoa();
				ExportExcel export;
				EditorExcel editor;
				try {
					export = new ExportExcel();
					editor = new EditorExcel();
					pessoa.setId_pessoa(Integer.parseInt(sendId.getText()));

					arquivo = new JFileChooser();
					arquivo.setDialogTitle("Adicionar caminho");
					arquivo.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					op = arquivo.showOpenDialog(btnExportar);
					if (op == JFileChooser.APPROVE_OPTION) {
						file = new File("");
						file = arquivo.getSelectedFile();
					}
					export.executarExcel(file.getAbsolutePath().replace("\\", "\\\\"));

					editor.editarExcel(Integer.parseInt(sendId.getText()), (String) comboBox_projeto.getSelectedItem(),
							(String) comboBox_sprint.getSelectedItem(), file.getAbsolutePath());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnExportar.setBounds(892, 395, 142, 28);
		panel.add(btnExportar);

		progressBar = new JProgressBar();
		progressBar.setBounds(197, 392, 369, 45);
		panel.add(progressBar);
		progressBar.setStringPainted(true);
		progressBar.setValue(0);

		JLabel lblProgressoDaSprint = new JLabel("Progresso da sprint:");
		lblProgressoDaSprint.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblProgressoDaSprint.setBounds(10, 392, 177, 45);
		panel.add(lblProgressoDaSprint);

		dataFim = new JFormattedTextField();
		dataFim.setEditable(false);
		dataFim.setFont(new Font("Tahoma", Font.BOLD, 15));
		dataFim.setBounds(877, 327, 158, 39);
		panel.add(dataFim);

		JLabel lblFim = new JLabel("Fim:");
		lblFim.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblFim.setBounds(833, 327, 45, 39);
		panel.add(lblFim);

		JLabel lblInicio = new JLabel("Inicio:");
		lblInicio.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblInicio.setBounds(609, 327, 57, 39);
		panel.add(lblInicio);

		dataInicio = new JFormattedTextField();
		dataInicio.setEditable(false);
		dataInicio.setFont(new Font("Tahoma", Font.BOLD, 15));
		dataInicio.setBounds(665, 327, 158, 39);
		panel.add(dataInicio);

		statusPro = new JLabel("");
		statusPro.setFont(new Font("Tahoma", Font.BOLD, 15));
		statusPro.setBounds(576, 395, 302, 42);
		panel.add(statusPro);

		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		mnNewMenu = new JMenu("Arquivos");
		menuBar.add(mnNewMenu);

		mntmNewMenuItem = new JMenuItem("Sair");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					frame.dispose();
					LoginView window = new LoginView();
					window.frmLogin.setVisible(true);
				} catch (Exception err) {

				}
			}
		});
		mntmNewMenuItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Cadastro de projeto");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public int id_pessoa;

			public void actionPerformed(ActionEvent e) {
				if (sendId.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Selecione um usuário para cadastrar um novo projeto!");
				} else {
					try {
						id_pessoa = Integer.parseInt(sendId.getText());
						CadastrarNomeDoProjetoView window = new CadastrarNomeDoProjetoView(id_pessoa, "ADM");
						window.frmCadastroDeProjeto.setVisible(true);
						restaurarProjetos();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		mntmNewMenuItem_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
		mnNewMenu.add(mntmNewMenuItem_1);

		JMenuItem menuTema = new JMenuItem("Tema");
		menuTema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] opcoes = { "Light", "Dark", "Nimbus" };
				Object tema = JOptionPane.showInputDialog(null, "Escolha um tema", "Tema", JOptionPane.PLAIN_MESSAGE,
						null, opcoes, "");
				daoConfig.definirTema(String.valueOf(tema));
				frame.dispose();

				try {
					daoConfig.definirTema(String.valueOf(tema));
					AdministradorView window = new AdministradorView();
					daoConfig.definirTema(String.valueOf(tema));
					window.frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		menuTema.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
		mnNewMenu.add(menuTema);

	}

	public void atualizarTabela() throws Exception {
		try {
			dao = new DAOADM();
			List<Object> ce = dao.buscarTodos_pessoas();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setNumRows(0);
			for (int x = 0; x != ce.size(); x++) {
				model.addRow((Object[]) ce.get(x));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void restaurarProjetos() {
		try {
			comboBox_projeto.removeAllItems();
			dao = new DAOADM();
			int id = Integer.parseInt(sendId.getText());
			ResultSet rs = dao.listarProjeto(id);

			comboBox_projeto.addItem("Selecionar Projeto");
			while (rs.next()) {
				comboBox_projeto.addItem(rs.getString(1));
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Carregar projeto: " + e.getMessage());
		}
	}

	public void restaurarSprint() {
		try {
			comboBox_sprint.removeAllItems();
			dao = new DAOADM();
			int idpessoa = Integer.valueOf(sendId.getText());
			String projeto = (String) comboBox_projeto.getSelectedItem();

			if (projeto != "") {
				ResultSet rs = dao.listarSprint(idpessoa, projeto);
				while (rs.next()) {
					if (rs.getString(2).equals("")) {
						comboBox_sprint.addItem("");
					} else {
						comboBox_sprint.addItem(rs.getString(2));
					}
				}
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Carregar projeto: " + e.getMessage());
		}
	}

	public void restaurarAbordagem() {
		try {
			dao = new DAOADM();
			int id = Integer.parseInt(sendId.getText());
			String projeto = (String) comboBox_projeto.getSelectedItem();
			String sprint = (String) comboBox_sprint.getSelectedItem();

			// recuperar qtd de histoiras positivas
			ResultSet rs = dao.recuperarQtdAbordagem_POSITIVO(id, projeto, sprint);
			while (rs.next()) {
				testePositivo.setText(rs.getString(2));
			}

			rs = dao.recuperarQtdAbordagem_NEGATIVO(id, projeto, sprint);
			while (rs.next()) {
				testeNegativo.setText(rs.getString(2));
			}

			sendQTDHistorias.setText(dao.recuperarQtdHistorias(id, projeto, sprint));
			qtdBug.setText(dao.recuperarQtdBugs(id, projeto, sprint));
			testeFinalizado.setText(dao.recuperarQtdCenariosFinalizado(id, projeto, sprint));
			testeEmAberto.setText(dao.recuperarQtdCenariosEmAberto(id, projeto, sprint));
			qtdAutomacoes.setText(dao.recuperarQtdAutomacaoSim(id, projeto, sprint));

			testeBaixo.setText(dao.recuperarPrioridade("Baixo", id, projeto, sprint));
			testeMedio.setText(dao.recuperarPrioridade("Média", id, projeto, sprint));
			testeAlto.setText(dao.recuperarPrioridade("Alta", id, projeto, sprint));
			testeCritico.setText(dao.recuperarPrioridade("Crítica", id, projeto, sprint));

			rs = dao.recuperarDataInicioEFim(id, projeto, sprint);
			while (rs.next()) {
				dataInicio.setText(rs.getString(1));
				dataFim.setText(rs.getString(2));
			}

			ResultSet rs_projeto = dao.recuperarDataEFinalizacaoDoProjeto(projeto, sprint);
			while (rs_projeto.next()) {
				if (rs_projeto.getString(2).equals("1")) {
					statusPro.setText("Finalizado");
				} else
					statusPro.setText("Em andamento");

				new DAO();
				String reformartarData = rs_projeto.getString(1);
				String ano = reformartarData.substring(6, 10); // ##-##-####
				String mesReformar = reformartarData.substring(3, 5);
				String dia = reformartarData.substring(0, 2); // ##-##-####
				Month mes = dao.retornarMes(mesReformar);
				dataStart = LocalDate.of(Integer.parseInt(ano), mes, Integer.parseInt(dia));
				data = LocalDate.now();
				long valor = ChronoUnit.DAYS.between(data, dataStart);

				if (valor > 0) {
					diasParaFinalizar.setText("" + valor);
				} else {
					diasParaFinalizar.setText("0");
				}
			}

			int valor = Integer.parseInt(diasParaFinalizar.getText());
			int valorTestesEmAberto = Integer.parseInt(testeEmAberto.getText());
			if (valor <= 2 && valorTestesEmAberto > 5) {
				statusProjeto.setForeground(Color.RED);
				statusProjeto.setText("Alto");
			} else if (valor <= 4 && valorTestesEmAberto >= 7) {
				statusProjeto.setForeground(Color.BLUE);
				statusProjeto.setText("Medio");
			} else {
				statusProjeto.setForeground(Color.BLACK);
				statusProjeto.setText("baixo");
			}

			progressBar.setValue(0);
			try {
				int valorTestesFinalizados = Integer.parseInt(testeFinalizado.getText());
				int qtd = Integer.parseInt(sendQTDHistorias.getText());
				int porcentagem = (valorTestesFinalizados * 100) / qtd;
				if (porcentagem < 50) {
					UIManager.put("nimbusOrange", Color.RED);
					progressBar.setValue(porcentagem);
				} else if (porcentagem > 50 || porcentagem < 90) {
					UIManager.put("nimbusOrange", Color.ORANGE);
					progressBar.setValue(porcentagem);
				} else if (porcentagem > 90) {
					UIManager.put("nimbusOrange", Color.GREEN);
					progressBar.setValue(porcentagem);
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Não encontramos nenhuma Sprint relacionada a este projeto! \nPor favor, selecione outro projeto =)");
		}
	}

	public void recuperarTestes_POSITIVOS() {

	}

	public void recuperarTestes_NEGATIVOS() {

	}

	public void limpar() {
		testePositivo.setText(null);
		testeNegativo.setText(null);
		sendQTDHistorias.setText(null);
		qtdBug.setText(null);
		diasParaFinalizar.setText(null);
		statusProjeto.setText(null);
		progressBar.setValue(0);
		diasParaFinalizar.setText(null);
		dataInicio.setText(null);
		dataFim.setText(null);
	}

	public void setCamposFromTabela() {
		sendId.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
		sendNOME.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));

		String txtcaminho = String.valueOf(table.getValueAt(table.getSelectedRow(), 6));
		ImageIcon imagem = new ImageIcon(txtcaminho);
		mostrarImagem.setIcon(new ImageIcon(imagem.getImage().getScaledInstance(mostrarImagem.getWidth(),
				mostrarImagem.getHeight(), Image.SCALE_DEFAULT)));

		diasParaFinalizar.setText("0");
		restaurarProjetos();
		restaurarSprint();
		comboBox_projeto.setEnabled(true);
		comboBox_sprint.setEnabled(true);
		btnExportar.setEnabled(true);
		restaurarAbordagem();
	}
}
