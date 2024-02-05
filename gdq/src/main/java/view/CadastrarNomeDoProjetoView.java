package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import dao.DAO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class CadastrarNomeDoProjetoView {

	public DAO dao;
	public CadastroDeCenariosView window;
	private JComboBox<String> comboBox_projeto;
	JFrame frmCadastroDeProjeto;
	private JButton btnCadastrar;
	public int id_informacoes;
	private JLabel sendID;

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
					CadastrarNomeDoProjetoView window = new CadastrarNomeDoProjetoView(1, "QA");
					window.frmCadastroDeProjeto.setVisible(true);
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
	public CadastrarNomeDoProjetoView(int id, String tipoDeUsuario) throws Exception {
		initialize();
		dao = new DAO();

		id_informacoes = id;
		recuperarProjeto(id);
		recuperarIdProjeto((String) comboBox_projeto.getSelectedItem());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCadastroDeProjeto = new JFrame();
		frmCadastroDeProjeto.setType(Type.UTILITY);
		frmCadastroDeProjeto.setFont(new Font("Dialog", Font.BOLD, 15));
		frmCadastroDeProjeto.setTitle("Cadastro de Projeto");
		frmCadastroDeProjeto.setBounds(500, 250, 412, 210);

		JPanel teste = new JPanel();
		teste.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Projeto",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		JLabel lblNewLabel = new JLabel("Projeto");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));

		comboBox_projeto = new JComboBox<String>();
		comboBox_projeto.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				recuperarIdProjeto((String) comboBox_projeto.getSelectedItem());
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboBox_projeto.setFont(new Font("Tahoma", Font.BOLD, 15));

		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nomeDoProjeto = JOptionPane.showInputDialog("Nome do projeto");
				if (nomeDoProjeto.equals("")) {
					JOptionPane.showMessageDialog(null,
							"Não foi inserido nenhum valor, sendo assim não foi cadastrado nenhum projeto!");
				}else{
					switch (JOptionPane.showConfirmDialog(null, "Deseja cadastrar o projeto " + nomeDoProjeto + "?",
							"Confirmação", JOptionPane.YES_NO_OPTION)) {
					case 0:
						try {
							dao = new DAO();
							dao.Salvar_nomeDoProjeto(id_informacoes, nomeDoProjeto);
						} catch (Exception ex) {
							System.out.println(ex.getMessage());
						}
						comboBox_projeto.addItem(nomeDoProjeto);
						JOptionPane.showMessageDialog(null, "Cadastro efetuado com Sucesso!!!");
						recuperarProjeto(id_informacoes);
						break;

					case 1:
						JOptionPane.showMessageDialog(null, "Cadastro não realizado!");
						break;
					}
				}
			}
		});
		btnCadastrar.setFont(new Font("Tahoma", Font.BOLD, 17));

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					switch (JOptionPane.showConfirmDialog(null,
							"Tem certeza que deseja excluir esse projeto " + comboBox_projeto.getSelectedItem() + "?",
							"Confirmar exclusão", JOptionPane.YES_NO_OPTION)) {
					case 0:
						dao = new DAO();
						dao.Excluir_nomeDoProjeto(sendID.getText(), (String) comboBox_projeto.getSelectedItem());
						JOptionPane.showMessageDialog(null, "Excluído com Sucesso!!!");
						recuperarProjeto(id_informacoes);
						sendID.setText(null);
						break;

					case 1:
						JOptionPane.showMessageDialog(null, "Projeto não foi excluido!");
						break;
					}

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Erro ao Excluir!!!");
				}
			}
		});
		btnExcluir.setBackground(new Color(255, 0, 0));
		btnExcluir.setFont(new Font("Tahoma", Font.BOLD, 17));

		sendID = new JLabel("");
		sendID.setEnabled(false);
		GroupLayout gl_teste = new GroupLayout(teste);
		gl_teste.setHorizontalGroup(
			gl_teste.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_teste.createSequentialGroup()
					.addGap(4)
					.addGroup(gl_teste.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_teste.createSequentialGroup()
							.addGroup(gl_teste.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_teste.createSequentialGroup()
									.addComponent(sendID, GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
									.addGap(14))
								.addGroup(gl_teste.createSequentialGroup()
									.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.UNRELATED)))
							.addComponent(comboBox_projeto, 0, 304, Short.MAX_VALUE))
						.addGroup(gl_teste.createSequentialGroup()
							.addComponent(btnCadastrar, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
							.addGap(9)
							.addComponent(btnExcluir, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
							.addGap(7))))
		);
		gl_teste.setVerticalGroup(
			gl_teste.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_teste.createSequentialGroup()
					.addGap(1)
					.addGroup(gl_teste.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_teste.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
							.addComponent(sendID, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_teste.createSequentialGroup()
							.addGap(2)
							.addGroup(gl_teste.createParallelGroup(Alignment.BASELINE, false)
								.addComponent(comboBox_projeto, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
							.addGap(32)))
					.addGroup(gl_teste.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_teste.createSequentialGroup()
							.addGap(10)
							.addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_teste.createSequentialGroup()
							.addGap(10)
							.addComponent(btnCadastrar, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)))
					.addGap(24))
		);
		teste.setLayout(gl_teste);
		GroupLayout groupLayout = new GroupLayout(frmCadastroDeProjeto.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addComponent(teste, GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(teste, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		frmCadastroDeProjeto.getContentPane().setLayout(groupLayout);
	}

	public void recuperarIdProjeto(String projeto) {
		try {
			dao = new DAO();
			ResultSet rs = dao.listarIdDoProjeto(id_informacoes, projeto);

			while (rs.next()) {
				sendID.setText(rs.getString(1));
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Carregar projeto: " + e.getMessage());
		}
	}

	public void recuperarProjeto(int id) {
		try {
			dao = new DAO();
			ResultSet rs = dao.listarProjetoComId(id);
			comboBox_projeto.removeAllItems();

			while (rs.next()) {
				comboBox_projeto.addItem(rs.getString(2));
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Carregar projeto: " + e.getMessage());
		}
	}
}
