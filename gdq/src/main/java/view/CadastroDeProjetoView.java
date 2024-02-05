package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dao.DAO;
import dao.DAO_projeto;
import model.Projeto;

public class CadastroDeProjetoView {

	JFrame frame;
	private DAO_projeto daoprojeto;
	private Projeto projeto;
	private DAO dao;
	private CadastroDeCenariosView home;
	
	private JComboBox combobox_projeto;
	private JTextField SendQA;
	private JTextField sendStoryPoitns;
	private JTextField sendTotalDeDevs;
	public JTable table;
	private JTextField sendSprint;
	private JLabel sendId;
	private String qtdHistorias;
	private JCheckBox finalizado;
	private JDateChooser sendDataInicio;
	private JDateChooser sendDataFim;
	

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
					CadastroDeProjetoView window = new CadastroDeProjetoView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CadastroDeProjetoView() throws Exception{
		initialize();
		projeto = new Projeto();
		daoprojeto = new DAO_projeto();
		//dao = new DAO();
		atualizarTabela_informacoes();
		recuperarValorDoNomeDoProjeto();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
		frame = new JFrame();
		frame.setBounds(100, 100, 1055, 523);
		
		projeto = new Projeto();
		daoprojeto = new DAO_projeto();
		
		JScrollPane scrollPane = new JScrollPane();
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				setCamposFromTabela();
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Projeto", "QA", "QTD Historias", "Historias Entregues", "Story Points", "Total Devs", "Sprint", "Data de inicio", "Data de fim", "Finalizado", "Data de criação"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(23);
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Projeto:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		combobox_projeto = new JComboBox();
		
		JLabel lblQa = new JLabel("QA:");
		lblQa.setHorizontalAlignment(SwingConstants.CENTER);
		lblQa.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		SendQA = new JTextField();
		SendQA.setEnabled(false);
		SendQA.setEditable(false);
		SendQA.setColumns(10);
		
		JLabel lblStoryPoints = new JLabel("Story Points:");
		lblStoryPoints.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		sendStoryPoitns = new JTextField();
		sendStoryPoitns.setColumns(10);
		sendStoryPoitns.setText("0");
		
		sendSprint = new JTextField();
		sendSprint.setColumns(10);
		
		JLabel lblTotalDevs = new JLabel("Total DEVs:");
		lblTotalDevs.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		sendTotalDeDevs = new JTextField();
		sendTotalDeDevs.setColumns(10);
		sendTotalDeDevs.setText("0");
		
		sendId = new JLabel("");
		sendId.setBorder(new LineBorder(Color.BLACK, 1, true));
		sendId.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnNewButton = new JButton("Cadastrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					projeto = new Projeto();
					projeto.setId(0);
					projeto.setProjeto((String) combobox_projeto.getSelectedItem());
					projeto.setQa(SendQA.getText());
					projeto.setStoryPoints(Integer.parseInt(sendStoryPoitns.getText()));
					projeto.setTotalDevs(Integer.parseInt(sendTotalDeDevs.getText()));
					projeto.setSprint("Sprint "+sendSprint.getText());
					
					Date valueDataInicio = sendDataInicio.getDate();
					long converterDataInicio = valueDataInicio.getTime();
					java.sql.Date dataInicio = new java.sql.Date(converterDataInicio);
					String novaData = daoprojeto.converterData(dataInicio);
					projeto.setDataInicio(novaData);
					
					Date valueDataFim = sendDataFim.getDate();
					long converterDataFim = valueDataFim.getTime();
					java.sql.Date dataFim = new java.sql.Date(converterDataFim);
					String novaDataFim = daoprojeto.converterData(dataFim);
					projeto.setDataFim(novaDataFim);
					
					projeto.setData_criacao(daoprojeto.getDateNow());
					projeto.setFimDoProjeto(finalizado.isSelected());
					
					// abrir conexao
					dao = new DAO();

					// salvar
					dao.Salvar_informacoesProjeto(projeto);
					atualizarTabela_informacoes();
					JOptionPane.showMessageDialog(null, "Cadastrado com Sucesso!!!");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Erro ao cadastar!!! "+ e1.getMessage());
				}
			}
		});
		btnNewButton.setBackground(new Color(0, 255, 0));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.setBackground(new Color(30, 144, 255));
		btnAlterar.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// criado objeto leitor para pegar os dados da tela
					daoprojeto = new DAO_projeto();
					projeto = new Projeto();
					projeto.setId(Integer.parseInt(sendId.getText()));
					projeto.setProjeto((String) combobox_projeto.getSelectedItem());
					projeto.setQa(SendQA.getText());
					projeto.setStoryPoints(Integer.parseInt(sendStoryPoitns.getText()));
					projeto.setTotalDevs(Integer.parseInt(sendTotalDeDevs.getText()));
					projeto.setSprint(sendSprint.getText());
					
					Date valueDataInicio = sendDataInicio.getDate();
					long converterDataInicio = valueDataInicio.getTime();
					java.sql.Date dataInicio = new java.sql.Date(converterDataInicio);
					String novaData = daoprojeto.converterData(dataInicio);
					projeto.setDataInicio(novaData);
					
					Date valueDataFim = sendDataFim.getDate();
					long converterDataFim = valueDataFim.getTime();
					java.sql.Date dataFim = new java.sql.Date(converterDataFim);
					String novaDataFim = daoprojeto.converterData(dataFim);
					projeto.setDataFim(novaDataFim);

					projeto.setFimDoProjeto(finalizado.isSelected());
					
					dao = new DAO();
					// Alterar
					daoprojeto.Alterar_informacoes(projeto);
					JOptionPane.showMessageDialog(null, "Alterado com Sucesso!!!");
					atualizarTabela_informacoes();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Erro ao Alterar!!!" + e1.getMessage());
				}
			}
		});
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBackground(Color.RED);
		btnExcluir.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					daoprojeto = new DAO_projeto();
					int codigo = Integer.parseInt(sendId.getText());

					switch (JOptionPane.showConfirmDialog(null, "Deseja excluir esse cenário?", "Confirmação",
							JOptionPane.YES_NO_OPTION)) {
					case 0:
						daoprojeto.Excluir_informacao(codigo);
						JOptionPane.showMessageDialog(null, "Excluido com Sucesso!!!");
						break;

					case 1:
						JOptionPane.showMessageDialog(null, "Cenário não foi excluido!");
						break;
					}
					atualizarTabela_informacoes();
				} catch (Exception exe) {
					JOptionPane.showMessageDialog(null, "Erro ao Excluir!!!");
				}
			}
		});
	
		
		JLabel lblSprint = new JLabel("Sprint:");
		lblSprint.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		JLabel lblId = new JLabel("ID:");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		JLabel lblInicio = new JLabel("Inicio:");
		lblInicio.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		JLabel lblFim = new JLabel("Fim:");
		lblFim.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		finalizado = new JCheckBox("Finalizado");
		finalizado.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		JButton btnNovo = new JButton("Novo");
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnNovo.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNovo.setBackground(new Color(255, 255, 255));
		
		sendDataInicio = new JDateChooser();
		sendDataInicio.setDateFormatString("dd/MM/yyyy");
		
		sendDataFim = new JDateChooser();
		sendDataFim.setDateFormatString("dd/MM/yyyy");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1021, Short.MAX_VALUE)
					.addGap(10))
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
									.addGap(8)
									.addComponent(combobox_projeto, 0, 534, Short.MAX_VALUE)
									.addGap(0))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(10)
									.addComponent(lblQa, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(SendQA, GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
									.addGap(10)
									.addComponent(btnNovo, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
									.addGap(9)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(btnAlterar, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
										.addComponent(btnExcluir, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))))
							.addGap(22)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addGroup(groupLayout.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
										.addComponent(sendDataInicio, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblInicio)
										.addPreferredGap(ComponentPlacement.RELATED, 146, Short.MAX_VALUE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(6)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
											.addGroup(groupLayout.createSequentialGroup()
												.addComponent(lblFim, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED, 140, Short.MAX_VALUE))
											.addGroup(groupLayout.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
												.addComponent(sendDataFim, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)))))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblTotalDevs, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
									.addGap(3)
									.addComponent(sendTotalDeDevs, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(44)
							.addComponent(lblId, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(sendId, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)))
					.addGap(60)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(finalizado, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblStoryPoints, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(102)
										.addComponent(sendStoryPoitns, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblSprint, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(sendSprint, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)))
							.addGap(9)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblId, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(sendId, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addGap(4)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(8)
									.addComponent(lblNewLabel))
								.addComponent(combobox_projeto, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(7)
									.addComponent(SendQA, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(5)
									.addComponent(lblQa, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
							.addGap(7)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnNovo, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(3)
									.addComponent(btnAlterar, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
									.addGap(6)
									.addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(4)
									.addComponent(lblTotalDevs))
								.addComponent(sendTotalDeDevs, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
							.addGap(20)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(sendDataInicio, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(2)
									.addComponent(lblInicio, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblFim, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
								.addComponent(sendDataFim, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(7)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblStoryPoints, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
								.addComponent(sendStoryPoitns, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSprint, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
								.addComponent(sendSprint, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(finalizado, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)))
					.addGap(10)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
					.addGap(9))
		);
		frame.getContentPane().setLayout(groupLayout);
	}

	public void recuperarValorDoNomeDoProjeto() {
		try {
			dao = new DAO();
			ResultSet rs = dao.listarProjeto();

			while (rs.next()) {
				int id = rs.getInt(1);
				combobox_projeto.addItem(rs.getString(2));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	public void limparCampos() {
		combobox_projeto.setSelectedIndex(0);
		SendQA.setText(null);
		sendStoryPoitns.setText("0");
		sendTotalDeDevs.setText("0");
		sendSprint.setText("");
		sendDataInicio.setDate(null);
		sendDataFim.setDate(null);
		finalizado.setSelected(false);
	}
	
	public void atualizarTabela_informacoes() {
		try {
			dao = new DAO();
			List<Object> ce = dao.buscarTodaInformacoes();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setNumRows(0);
			for (int x = 0; x != ce.size(); x++) {
				model.addRow((Object[]) ce.get(x));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void setCamposFromTabela() {
		sendId.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
		combobox_projeto.setSelectedItem(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
		SendQA.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
		sendStoryPoitns.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 5)));
		sendTotalDeDevs.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 6)));
		sendSprint.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 7)));
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date valueDate;
		try {
			valueDate = format.parse(String.valueOf(table.getValueAt(table.getSelectedRow(), 8)));
			sendDataInicio.setDate(valueDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Date valueDateFim;
		try {
			valueDateFim = format.parse(String.valueOf(table.getValueAt(table.getSelectedRow(), 9)));
			sendDataFim.setDate(valueDateFim);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String value = String.valueOf(table.getValueAt(table.getSelectedRow(), 10));
		
		if(value.equals("Ativo")) {
			finalizado.setSelected(false);
		}else finalizado.setSelected(true);
		
	}
}
