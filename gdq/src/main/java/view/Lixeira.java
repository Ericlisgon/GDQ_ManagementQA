package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import dao.DAO;
import dao.DAO_pessoa;
import model.Pessoa;

public class Lixeira {

	JFrame lixeiraView;
	private DAO dao;
	private DAO_pessoa daoPessoa;
	private JTable tabela_cenarios;
	private JTextField codCenario;
	private JTextField historia;
	private JTextField sprint;
	public String tipoDeUsuario;
	private JLabel lblHistoria;
	private JTextField id;

	/**
	 * Launch the application.
	 * 
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {
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
					Lixeira window = new Lixeira("3");
					window.lixeiraView.setVisible(true);
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
	public Lixeira(String verificarTipoDeUsuario) throws Exception {
		initialize();
		dao = new DAO();
		new Pessoa();
		atualizarInformacoesDaTabela(verificarTipoDeUsuario);
		
		tipoDeUsuario = verificarTipoDeUsuario;
		if (verificarTipoDeUsuario.equals("QA")) {
			atualizarTabelaQA();
		} else if (verificarTipoDeUsuario.equals("ADMINISTRADOR")) {
			lblHistoria.setText("Nome:");
			atualizarTabelaADMINISTRADOR();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws ParseException
	 */
	public void initialize() throws ParseException {
		lixeiraView = new JFrame();
		lixeiraView.setBounds(20, 20, 1499, 499);

		JScrollPane scrollPane = new JScrollPane();

		tabela_cenarios = new JTable();
		tabela_cenarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					setCamposFromTabela();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		tabela_cenarios.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tabela_cenarios.setModel(new DefaultTableModel(new Object[][] {}, new String[] {""}));
		scrollPane.setViewportView(tabela_cenarios);

		// ========================================== MENU

		JMenuBar menuBar = new JMenuBar();
		lixeiraView.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Arquivos");
		menuBar.add(mnNewMenu);

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
				lixeiraView.dispose();
			}
		});
		menu_exit.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		mnNewMenu.add(menu_exit);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setForeground(Color.BLACK);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tipoDeUsuario.equals("QA")) {
					try {
						dao = new DAO();
						int cod = Integer.parseInt(id.getText());

						switch (JOptionPane.showConfirmDialog(null, "Deseja excluir esse cenário?", "Confirmação",
								JOptionPane.YES_NO_OPTION)) {
						case 0:
							dao.Excluir(cod);
							JOptionPane.showMessageDialog(null, "Excluido com Sucesso!!!");
							atualizarTabelaQA();
							break;

						case 1:
							JOptionPane.showMessageDialog(null, "Cenário não foi excluido!");
							break;
						}

						atualizarTabelaQA();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Erro ao Excluir!!!");
					}
				} else if (tipoDeUsuario.equals("ADMINISTRADOR")) {
					try {
						daoPessoa = new DAO_pessoa();
						int id_pessoa = Integer.parseInt(id.getText());

						switch (JOptionPane.showConfirmDialog(null,
								"Tem certeza que deseja excluir esse usuário? TODOS OS DADOS SERÃO APAGADOS!",
								"Confirmar exclusão", JOptionPane.YES_NO_OPTION)) {
						case 0:
							daoPessoa.Excluir_pessoa(id_pessoa);
							JOptionPane.showMessageDialog(null, "Excluido com Sucesso!!!");
							atualizarTabelaADMINISTRADOR();
							break;

						case 1:
							JOptionPane.showMessageDialog(null, "Usuário não foi excluido!");
							break;
						}

						atualizarTabelaADMINISTRADOR();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Erro ao Excluir!!!");
					}
				}
			}
		});
		btnExcluir.setFont(new Font("Trebuchet MS", Font.BOLD, 32));
		btnExcluir.setBackground(new Color(255, 0, 0));

		JButton btnRecuperar = new JButton("Recuperar");
		btnRecuperar.setForeground(Color.BLACK);
		btnRecuperar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tipoDeUsuario.equals("QA")) {
					try {
						dao = new DAO();
						int cod = Integer.parseInt(id.getText());
						
						switch (JOptionPane.showConfirmDialog(null, "Deseja recuperar esse cenário?", "Confirmação",
								JOptionPane.YES_NO_OPTION)) {
								case 0:
									dao.EditarStatusParaRecuperado(cod);
									JOptionPane.showMessageDialog(null, "Recuperado com Sucesso!!!");
									atualizarTabelaQA();
									id.setText(null);
									break;
									
								case 1:
									JOptionPane.showMessageDialog(null, "Cenário não foi recuperado!");
									break;
						}
						
						atualizarTabelaQA();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Erro ao tentar Recuperar!!!");
					}
					
				}else if (tipoDeUsuario.equals("ADMINISTRADOR")) {
					try {
						dao = new DAO();
						int cod = Integer.parseInt(id.getText());
						
						switch (JOptionPane.showConfirmDialog(null, "Deseja recuperar esse usuário?", "Confirmação",
								JOptionPane.YES_NO_OPTION)) {
								case 0:
									dao.EditarStatusParaRecuperadoPessoa(cod);
									JOptionPane.showMessageDialog(null, "Recuperado com Sucesso!!!");
									atualizarTabelaADMINISTRADOR();
									id.setText(null);
									break;
									
								case 1:
									JOptionPane.showMessageDialog(null, "Usuário não foi recuperado!");
									break;
						}
						atualizarTabelaADMINISTRADOR();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Erro ao tentar Recuperar!!!");
					}
				}
			}
		});
		btnRecuperar.setFont(new Font("Trebuchet MS", Font.BOLD, 32));
		btnRecuperar.setBackground(new Color(135, 206, 250));

		JLabel lblNewLabel = new JLabel("COD:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));

		codCenario = new JTextField();
		codCenario.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				TableRowSorter<TableModel> filtro = null;
				DefaultTableModel model = (DefaultTableModel) tabela_cenarios.getModel();
				filtro = new TableRowSorter<TableModel>(model);
				tabela_cenarios.setRowSorter(filtro);

				if (codCenario.getText().length() == 0) {
					filtro.setRowFilter(null);
				} else {
					filtro.setRowFilter(RowFilter.regexFilter("(?i)" + codCenario.getText(), 0));
				}
			}
		});
		codCenario.setColumns(10);

		lblHistoria = new JLabel("Historia:");
		lblHistoria.setHorizontalAlignment(SwingConstants.CENTER);
		lblHistoria.setFont(new Font("Tahoma", Font.BOLD, 15));

		historia = new JTextField();
		historia.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				TableRowSorter<TableModel> filtro = null;
				DefaultTableModel model = (DefaultTableModel) tabela_cenarios.getModel();
				filtro = new TableRowSorter<TableModel>(model);
				tabela_cenarios.setRowSorter(filtro);

				if (historia.getText().length() == 0) {
					filtro.setRowFilter(null);
				} else {
					filtro.setRowFilter(RowFilter.regexFilter("(?i)" + historia.getText(), 1));
				}
			}
		});
		historia.setColumns(10);

		JLabel lblSprint = new JLabel("Sprint:");
		lblSprint.setHorizontalAlignment(SwingConstants.CENTER);
		lblSprint.setFont(new Font("Tahoma", Font.BOLD, 15));

		sprint = new JTextField();
		sprint.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				TableRowSorter<TableModel> filtro = null;
				DefaultTableModel model = (DefaultTableModel) tabela_cenarios.getModel();
				filtro = new TableRowSorter<TableModel>(model);
				tabela_cenarios.setRowSorter(filtro);

				if (sprint.getText().length() == 0) {
					filtro.setRowFilter(null);
				} else {
					filtro.setRowFilter(RowFilter.regexFilter("(?i)" + sprint.getText(), 11));
				}
			}
		});
		sprint.setColumns(10);
		
		id = new JTextField();
		id.setEnabled(false);
		id.setEditable(false);
		id.setVisible(false);
		id.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(lixeiraView.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(codCenario, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblHistoria, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
							.addGap(10)
							.addComponent(historia, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblSprint, GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(sprint, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(id, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
							.addGap(243)
							.addComponent(btnRecuperar, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
							.addGap(8)
							.addComponent(btnExcluir, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1466, Short.MAX_VALUE))
					.addGap(9))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnRecuperar, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(27)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(codCenario, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
										.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
									.addGap(18))
								.addGroup(Alignment.LEADING, groupLayout.createParallelGroup(Alignment.TRAILING)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(historia, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblSprint, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
										.addComponent(sprint, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(id, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
									.addComponent(lblHistoria, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))))
					.addGap(10)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
					.addGap(12))
		);
		lixeiraView.getContentPane().setLayout(groupLayout);
	}

	public void setCamposFromTabela() throws SQLException, Exception {
		id.setText(String.valueOf(tabela_cenarios.getValueAt(tabela_cenarios.getSelectedRow(), 0)));
	}

	public void atualizarTabelaQA() throws Exception {
		try {
			List<Object> ce = dao.buscarTodosCenariosExcluidos();
			DefaultTableModel model = (DefaultTableModel) tabela_cenarios.getModel();
			model.setNumRows(0);
			for (int x = 0; x != ce.size(); x++) {
				model.addRow((Object[]) ce.get(x));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void atualizarInformacoesDaTabela(String tipo) {
		if(tipo.equals("QA")) {
			tabela_cenarios.setModel(new DefaultTableModel(new Object[][] {},
					new String[] { "COD", "Historia", "Descricao", "Abordagem", "Prioridade", "Regressivo", "Automacao",
							"Status", "BUG", "Link do BUG", "Motivo da nao execucao", "Sprint", "Data de cadastro",
							"Projeto" }));
		}else if(tipo.equals("ADMINISTRADOR")) {
			
			tabela_cenarios.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "id", "Nome", "Email", "Empresa",
					"Cargo", "Senha", "Caminho da foto", "Tipo de usuário", "Data cadastro", "Squad" }));
		}
	}

	public void atualizarTabelaADMINISTRADOR() throws Exception {
		try {
			daoPessoa = new DAO_pessoa();
			List<Object> ce = daoPessoa.buscarTodos_pessoasINATIVAS();
			DefaultTableModel model = (DefaultTableModel) tabela_cenarios.getModel();
			model.setNumRows(0);
			for (int x = 0; x != ce.size(); x++) {
				model.addRow((Object[]) ce.get(x));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
