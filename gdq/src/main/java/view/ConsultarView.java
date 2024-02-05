package view;

import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import dao.DAO;
import model.Cenarios;

public class ConsultarView {

	JFrame frmConsulteCenrios;
	private JTable table;
	public DAO dao;
	public Cenarios cenario;
	public CadastroDeCenariosView home;
	protected Object frame;
	private JTextField txtHistoria;
	private JTextField txtSprint;
	private JTextField txtID;
	private JLabel lblStatus_2;
	private JTextField txtDescricao;
	private JCheckBox checkBUG;
	private JCheckBox checkAutomacao;
	private JComboBox comboPrioridade;
	private JComboBox comboRegressivo;
	private JComboBox comboAbordagem;
	private JComboBox comboStatus;

	/**
	 * Launch the application.
	 */

//	  public static void main(String[] args) { EventQueue.invokeLater(new
//	  Runnable() { public void run() { try { ConsultView window = new
//	 ConsultView(); window.frmConsulteCenrios.setVisible(true); } catch (Exception
//	  e) { e.printStackTrace(); } } }); }

	/**
	 * Create the application.
	 */
	public ConsultarView() {
		initialize();
		atualizarTabela();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() { 
		frmConsulteCenrios = new JFrame();
		frmConsulteCenrios.setTitle("Consulte cenários");
		frmConsulteCenrios.setBounds(100, 100, 1132, 703);
		frmConsulteCenrios.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 121, 1098, 535);
		frmConsulteCenrios.getContentPane().add(scrollPane);

		table = new JTable();
		table.setAutoCreateRowSorter(true);
		table.setSurrendersFocusOnKeystroke(true);
		table.setInheritsPopupMenu(true);
		table.setIgnoreRepaint(true);
		table.setFocusTraversalPolicyProvider(true);
		table.setFocusCycleRoot(true);
		table.setFillsViewportHeight(true);
		table.setDragEnabled(true);
		table.setDoubleBuffered(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					"COD", "Projeto", "Historia", "Descricao", "Abordagem", "Prioridade", "Regressivo",
					"Automacao", "Status", "BUG", "Link do BUG", "Motivo da nao execucao", "Sprint",
					"Data de cadastro"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(26);
		table.getColumnModel().getColumn(6).setPreferredWidth(63);
		table.getColumnModel().getColumn(8).setPreferredWidth(37);
		scrollPane.setViewportView(table);
		
		
		
		txtHistoria = new JTextField();
		txtHistoria.setBounds(164, 12, 460, 34);
		txtHistoria.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				filterFull();
			}
		});
		frmConsulteCenrios.getContentPane().add(txtHistoria);
		txtHistoria.setColumns(10);

		JLabel lblNewLabel = new JLabel("Sprint:");
		lblNewLabel.setBounds(634, 55, 61, 34);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		frmConsulteCenrios.getContentPane().add(lblNewLabel);

		JLabel lblStatus_1 = new JLabel("Historia:");
		lblStatus_1.setBounds(83, 11, 74, 34);
		lblStatus_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		frmConsulteCenrios.getContentPane().add(lblStatus_1);

		txtSprint = new JTextField();
		txtSprint.setBounds(689, 55, 67, 34);
		txtSprint.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				filterFull();
			}
		});
		txtSprint.setColumns(10);
		frmConsulteCenrios.getContentPane().add(txtSprint);

		JLabel lblId = new JLabel("ID");
		lblId.setBounds(10, 10, 28, 34);
		lblId.setFont(new Font("Tahoma", Font.BOLD, 15));
		frmConsulteCenrios.getContentPane().add(lblId);

		txtID = new JTextField();
		txtID.setBounds(32, 11, 41, 34);
		txtID.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				filterFull();
			}
		});
		txtID.setColumns(10);
		frmConsulteCenrios.getContentPane().add(txtID);

		lblStatus_2 = new JLabel("Descrição");
		lblStatus_2.setBounds(10, 55, 74, 34);
		lblStatus_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		frmConsulteCenrios.getContentPane().add(lblStatus_2);

		txtDescricao = new JTextField();
		txtDescricao.setBounds(91, 56, 533, 34);
		txtDescricao.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				filterFull();
			}
		});
		txtDescricao.setColumns(10);
		frmConsulteCenrios.getContentPane().add(txtDescricao);

		checkBUG = new JCheckBox("BUG");
		checkBUG.setBounds(762, 12, 67, 33);
		checkBUG.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				filterFull();
			}
		});
		checkBUG.setFont(new Font("Tahoma", Font.BOLD, 15));
		frmConsulteCenrios.getContentPane().add(checkBUG);

		checkAutomacao = new JCheckBox("Automação");
		checkAutomacao.setBounds(831, 12, 116, 34);
		checkAutomacao.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				filterFull();
			}
		});
		checkAutomacao.setFont(new Font("Tahoma", Font.BOLD, 15));
		frmConsulteCenrios.getContentPane().add(checkAutomacao);

		comboPrioridade = new JComboBox();
		comboPrioridade.setBounds(766, 57, 179, 30);
		comboPrioridade.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
				filterFull();
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				filterFull();
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				filterFull();
			}
		});
		comboPrioridade.setFont(new Font("Tahoma", Font.BOLD, 15));
		comboPrioridade.setModel(
				new DefaultComboBoxModel(new String[] {"Prioridade", "Baixo", "Média", "Alta", "Crítica"}));
		frmConsulteCenrios.getContentPane().add(comboPrioridade);

		comboRegressivo = new JComboBox();
		comboRegressivo.setBounds(955, 18, 153, 30);
		comboRegressivo.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				filterFull();
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboRegressivo.setModel(new DefaultComboBoxModel(new String[] { "Regressivo", "Sim", "Nao" }));
		comboRegressivo.setFont(new Font("Tahoma", Font.BOLD, 15));
		frmConsulteCenrios.getContentPane().add(comboRegressivo);

		comboAbordagem = new JComboBox();
		comboAbordagem.setBounds(955, 57, 153, 30);
		comboAbordagem.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				filterFull();
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboAbordagem.setModel(new DefaultComboBoxModel(new String[] { "Abordagem", "Positivo", "Negativo" }));
		comboAbordagem.setFont(new Font("Tahoma", Font.BOLD, 15));
		frmConsulteCenrios.getContentPane().add(comboAbordagem);

		comboStatus = new JComboBox();
		comboStatus.setBounds(634, 12, 122, 30);
		comboStatus.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				filterFull();
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboStatus.setModel(new DefaultComboBoxModel(new String[] { "Status", "OK", "NOK", "Bloqueado" }));
		comboStatus.setFont(new Font("Tahoma", Font.BOLD, 15));
		frmConsulteCenrios.getContentPane().add(comboStatus);

	}

	public void filterFull() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		TableRowSorter<TableModel> filtro = new TableRowSorter<TableModel>(model);
		table.setRowSorter(filtro);

		if (txtID.getText().length() != 0) {
			filtro.setRowFilter(RowFilter.regexFilter("(?i)" + txtID.getText(), 0));
		} else if ((String) comboStatus.getSelectedItem() != "Status") {
			filtro.setRowFilter(RowFilter.regexFilter("(?i)" + (String) comboStatus.getSelectedItem(), 8));
		} else if (txtSprint.getText().length() != 0) {
			filtro.setRowFilter(RowFilter.regexFilter("(?i)" + "Sprint "+txtSprint.getText(), 12));
		} else if (txtHistoria.getText().length() != 0) {
			filtro.setRowFilter(RowFilter.regexFilter("(?i)" + txtHistoria.getText(), 2));
		} else if (txtDescricao.getText().length() != 0) {
			filtro.setRowFilter(RowFilter.regexFilter("(?i)" + txtDescricao.getText(), 3));
		} else if (checkBUG.isSelected()) {
			filtro.setRowFilter(RowFilter.regexFilter("(?i)" + "Sim", 9));
		} else if (checkAutomacao.isSelected()) {
			filtro.setRowFilter(RowFilter.regexFilter("(?i)" + "Sim", 7));
		} else if ((String) comboPrioridade.getSelectedItem() != "Prioridade") {
			filtro.setRowFilter(RowFilter.regexFilter("(?i)" + (String) comboPrioridade.getSelectedItem(), 5));
		} else if ((String) comboRegressivo.getSelectedItem() != "Regressivo") {
			filtro.setRowFilter(RowFilter.regexFilter("(?i)" + (String) comboRegressivo.getSelectedItem(), 6));
		} else if ((String) comboAbordagem.getSelectedItem() != "Abordagem") {
			filtro.setRowFilter(RowFilter.regexFilter("(?i)" + (String) comboAbordagem.getSelectedItem(), 4));
		} else {
			filtro.setRowFilter(null);
		}
	}

	public void atualizarTabela() {
		try {
			dao = new DAO();
			List<Object> ce = dao.buscarTodos();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setNumRows(0);
			for (int x = 0; x != ce.size(); x++) {
				model.addRow((Object[]) ce.get(x));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
