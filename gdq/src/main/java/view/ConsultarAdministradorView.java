package view;

import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import dao.DAO;
import model.Cenarios;

public class ConsultarAdministradorView {

	JFrame frmConsulteCenrios;
	private JTable table;
	public DAO dao;
	public Cenarios cenario;
	public CadastroDeCenariosView home;
	protected Object frame;
	private JLabel lblStatus_2;
	private JTextField txtDescricao;

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
	public ConsultarAdministradorView() {
		initialize();
		atualizarTabela();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmConsulteCenrios = new JFrame();
		frmConsulteCenrios.setTitle("Consultar Administrador");
		frmConsulteCenrios.setBounds(100, 100, 1132, 703);
		SpringLayout springLayout = new SpringLayout();
		frmConsulteCenrios.getContentPane().setLayout(springLayout);

		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 121, SpringLayout.NORTH, frmConsulteCenrios.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, frmConsulteCenrios.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 656, SpringLayout.NORTH, frmConsulteCenrios.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 1108, SpringLayout.WEST, frmConsulteCenrios.getContentPane());
		frmConsulteCenrios.getContentPane().add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Historia", "Descricao", "Abordagem", "Prioridade", "Regressivo", "Automacao", "Status", "BUG", "Link do BUG", "Motivo da nao execucao", "sprint", "Data"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(26);
		table.getColumnModel().getColumn(6).setPreferredWidth(63);
		table.getColumnModel().getColumn(8).setPreferredWidth(37);
		scrollPane.setViewportView(table);

		lblStatus_2 = new JLabel("Pesquisa");
		springLayout.putConstraint(SpringLayout.NORTH, lblStatus_2, 7, SpringLayout.NORTH, frmConsulteCenrios.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblStatus_2, 0, SpringLayout.WEST, scrollPane);
		springLayout.putConstraint(SpringLayout.SOUTH, lblStatus_2, -80, SpringLayout.NORTH, scrollPane);
		lblStatus_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		frmConsulteCenrios.getContentPane().add(lblStatus_2);

		txtDescricao = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, txtDescricao, 6, SpringLayout.EAST, lblStatus_2);
		springLayout.putConstraint(SpringLayout.NORTH, txtDescricao, 10, SpringLayout.NORTH, frmConsulteCenrios.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, txtDescricao, 44, SpringLayout.NORTH, frmConsulteCenrios.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtDescricao, -10, SpringLayout.EAST, frmConsulteCenrios.getContentPane());
		txtDescricao.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				filterFull();
			}
		});
		txtDescricao.setColumns(10);
		frmConsulteCenrios.getContentPane().add(txtDescricao);

	}

	public void filterFull() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		TableRowSorter<TableModel> filtro = new TableRowSorter<TableModel>(model);
		table.setRowSorter(filtro);

//		if (txtID.getText().length() != 0) {
//			filtro.setRowFilter(RowFilter.regexFilter("(?i)" + txtID.getText(), 0));
//		}
//		} else {
//			filtro.setRowFilter(null);
//		}
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
