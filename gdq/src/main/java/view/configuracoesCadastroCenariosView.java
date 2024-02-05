package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import dao.DAO;
import dao.DAO_configuracoes;
import model.ConfiguracoesCenarios;
import model.Login;

public class configuracoesCadastroCenariosView {

	JFrame frmConfiguraoDoCadastro;
	private JComboBox comboBox_abordagem;
	private JComboBox comboBox_criticidade;
	private JComboBox comboBox_status;
	private JCheckBox chcLimparTodosOsCampos;
	private JCheckBox chcManterOCampoDescricao;
	private JCheckBox chckbxManterAOpocaoSimNosTestesAutomatizados;
	private JCheckBox chcManterOpcaoSIMnosTestesRegressivos;
	private JPanel teste;
	public ConfiguracoesCenarios novaConfig;
	public DAO dao;
	public DAO_configuracoes daoConfig;
	private JCheckBox chcCriarCenarioNegativo;
	private JLabel lblNewLabel_1;
	private JComboBox comboBox_tema;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		FlatLightLaf.setup();
//		try {
//			EventQueue.invokeLater(new Runnable() {
//				public void run() {
//					try {
//						configuracoesCadastroCenariosView window = new configuracoesCadastroCenariosView();
//						window.frmConfiguraoDoCadastro.setVisible(true);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			});
//		} catch (Exception e) {
//		}
//	}

	/**
	 * Create the application.
	 */

	public JFrame frameAntigo = null;

	public configuracoesCadastroCenariosView(JFrame frame) {
		initialize();
		restaurarConfiguracoes();
		frameAntigo = frame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmConfiguraoDoCadastro = new JFrame();
		frmConfiguraoDoCadastro.setTitle("Configuração do cadastro de cenários");
		frmConfiguraoDoCadastro.setBounds(500, 250, 606, 357);

		teste = new JPanel();
		teste.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Configura\u00E7\u00F5es de a\u00E7\u00F5es na tela de cadastro de cen\u00E1rios", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));

		chcLimparTodosOsCampos = new JCheckBox("Limpar todos os campos após salvar o cenário");
		chcLimparTodosOsCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chcManterOCampoDescricao.setSelected(false);
			}
		});
		chcLimparTodosOsCampos.setFont(new Font("Tahoma", Font.PLAIN, 11));

		chcManterOCampoDescricao = new JCheckBox("Manter os dados do campo descrição");
		chcManterOCampoDescricao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chcLimparTodosOsCampos.setSelected(false);
			}
		});
		chcManterOCampoDescricao.setToolTipText(
				"Ao selecionar está funcionalidade, após salvar um cenário o campo descrição irá se manter.");
		chcManterOCampoDescricao.setFont(new Font("Tahoma", Font.PLAIN, 11));

		chckbxManterAOpocaoSimNosTestesAutomatizados = new JCheckBox("Manter a opção 'Sim' para testes automatizados");
		chckbxManterAOpocaoSimNosTestesAutomatizados.setFont(new Font("Tahoma", Font.PLAIN, 11));

		chcManterOpcaoSIMnosTestesRegressivos = new JCheckBox("Manter a opção 'Sim' para testes regressivos");
		chcManterOpcaoSIMnosTestesRegressivos.setFont(new Font("Tahoma", Font.PLAIN, 11));

		comboBox_abordagem = new JComboBox();
		comboBox_abordagem.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				if (comboBox_abordagem.getSelectedItem() == "Negativo") {
					chcCriarCenarioNegativo.setSelected(false);
				}
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboBox_abordagem.setModel(new DefaultComboBoxModel(new String[] { "Positivo", "Negativo" }));

		JLabel lblNewLabel = new JLabel("Selecione a opção que deseja manter no campo Abordagem");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));

		JLabel lblSelecioneAOpo = new JLabel("Selecione a opção que deseja manter no campo Criticidade");
		lblSelecioneAOpo.setFont(new Font("Tahoma", Font.PLAIN, 11));

		comboBox_criticidade = new JComboBox();
		comboBox_criticidade.setModel(new DefaultComboBoxModel(new String[] { "Baixo", "Média", "Alta", "Crítica" }));

		JLabel lblSelecioneAOpo_1 = new JLabel("Selecione a opção que deseja manter no campo Status");
		lblSelecioneAOpo_1.setFont(new Font("Tahoma", Font.PLAIN, 11));

		comboBox_status = new JComboBox();
		comboBox_status.setModel(new DefaultComboBoxModel(new String[] { "Em andamento", "OK", "NOK", "Bloqueado" }));

		chcCriarCenarioNegativo = new JCheckBox("Criar cenário negativo");
		chcCriarCenarioNegativo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox_abordagem.setSelectedItem("Positivo");
			}
		});
		chcCriarCenarioNegativo.setToolTipText(
				"Está funcionalidade duplica o cenário cadastrado porém com a opção \"Negativo/Positivo\" na abordagem do teste.");
		chcCriarCenarioNegativo.setFont(new Font("Tahoma", Font.PLAIN, 11));

		lblNewLabel_1 = new JLabel("Tema");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));

		comboBox_tema = new JComboBox();
		comboBox_tema.setModel(new DefaultComboBoxModel(
				new String[] { "Light", "Dark", "Nimbus", "IntelJ", "Darcla", "Purple", "Nature", "Gruv", "lite", "Solar" }));
		GroupLayout gl_teste = new GroupLayout(teste);
		gl_teste.setHorizontalGroup(gl_teste.createParallelGroup(Alignment.TRAILING).addGroup(gl_teste
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_teste.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_teste.createSequentialGroup()
								.addComponent(lblSelecioneAOpo, GroupLayout.PREFERRED_SIZE, 294,
										GroupLayout.PREFERRED_SIZE)
								.addGap(4).addComponent(comboBox_criticidade, GroupLayout.PREFERRED_SIZE, 234,
										GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_teste.createSequentialGroup()
								.addComponent(lblSelecioneAOpo_1, GroupLayout.PREFERRED_SIZE, 294,
										GroupLayout.PREFERRED_SIZE)
								.addGap(4).addComponent(comboBox_status, GroupLayout.PREFERRED_SIZE, 234,
										GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_teste.createSequentialGroup().addComponent(lblNewLabel).addGap(4).addGroup(gl_teste
								.createParallelGroup(Alignment.LEADING, false)
								.addGroup(Alignment.TRAILING, gl_teste.createSequentialGroup()
										.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 45,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(comboBox_tema, GroupLayout.PREFERRED_SIZE, 155,
												GroupLayout.PREFERRED_SIZE))
								.addComponent(comboBox_abordagem, GroupLayout.PREFERRED_SIZE, 234,
										GroupLayout.PREFERRED_SIZE))))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_teste.createSequentialGroup().addContainerGap(16, Short.MAX_VALUE).addGroup(gl_teste
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_teste.createSequentialGroup()
								.addComponent(chcCriarCenarioNegativo, GroupLayout.PREFERRED_SIZE, 251,
										GroupLayout.PREFERRED_SIZE)
								.addGap(27))
						.addGroup(gl_teste.createSequentialGroup()
								.addGroup(gl_teste.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(chcManterOCampoDescricao, Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(chcLimparTodosOsCampos, Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))
								.addGap(18)))
						.addGroup(gl_teste.createParallelGroup(Alignment.LEADING)
								.addComponent(chckbxManterAOpocaoSimNosTestesAutomatizados, GroupLayout.PREFERRED_SIZE,
										263, GroupLayout.PREFERRED_SIZE)
								.addComponent(chcManterOpcaoSIMnosTestesRegressivos, GroupLayout.PREFERRED_SIZE, 255,
										GroupLayout.PREFERRED_SIZE))));
		gl_teste.setVerticalGroup(gl_teste.createParallelGroup(Alignment.LEADING).addGroup(gl_teste
				.createSequentialGroup().addGap(6)
				.addGroup(gl_teste.createParallelGroup(Alignment.BASELINE)
						.addComponent(chcManterOpcaoSIMnosTestesRegressivos).addComponent(chcCriarCenarioNegativo,
								GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
				.addGap(2)
				.addGroup(gl_teste.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxManterAOpocaoSimNosTestesAutomatizados)
						.addComponent(chcLimparTodosOsCampos))
				.addGroup(gl_teste.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_teste.createSequentialGroup().addGap(2).addComponent(chcManterOCampoDescricao))
						.addGroup(gl_teste.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(comboBox_tema, GroupLayout.PREFERRED_SIZE, 26,
										GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_teste.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 23,
										GroupLayout.PREFERRED_SIZE)))
				.addGap(15)
				.addGroup(gl_teste.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox_abordagem, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
				.addGap(6)
				.addGroup(gl_teste.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSelecioneAOpo, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox_criticidade, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
				.addGap(6)
				.addGroup(gl_teste.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSelecioneAOpo_1, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox_status, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
				.addGap(21)));
		teste.setLayout(gl_teste);

		JButton btnNewButton = new JButton("Salvar");
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					dao = new DAO();
					daoConfig = new DAO_configuracoes();
					String temaAntigo = daoConfig.recuperarTema();
					String temaAtual = (String) comboBox_tema.getSelectedItem();

					novaConfig = new ConfiguracoesCenarios();
					novaConfig.setAbordagem(comboBox_abordagem.getSelectedIndex());
					novaConfig.setCriticidade(comboBox_criticidade.getSelectedIndex());
					novaConfig.setStatus(comboBox_status.getSelectedIndex());
					novaConfig.setTema((String) comboBox_tema.getSelectedItem());

					if (chcLimparTodosOsCampos.isSelected())
						novaConfig.setLimparCampos(1);
					else
						novaConfig.setLimparCampos(0);

					if (chcManterOCampoDescricao.isSelected())
						novaConfig.setManterCampoDescricao(1);
					else
						novaConfig.setManterCampoDescricao(0);

					if (chckbxManterAOpocaoSimNosTestesAutomatizados.isSelected())
						novaConfig.setOpcaoSimAutomatizados(1);
					else
						novaConfig.setOpcaoSimAutomatizados(0);

					if (chcManterOpcaoSIMnosTestesRegressivos.isSelected())
						novaConfig.setOpcaoSimRegressivo(1);
					else
						novaConfig.setOpcaoSimRegressivo(0);

					if (chcCriarCenarioNegativo.isSelected())
						novaConfig.setDuplicitadeDeCenarios(1);
					else
						novaConfig.setDuplicitadeDeCenarios(0);

					dao.SalvarConfiguracoesDoCenario(novaConfig);
					JOptionPane.showMessageDialog(null, "Salvo com Sucesso!");

					restaurarConfiguracoes();
					frmConfiguraoDoCadastro.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
					frmConfiguraoDoCadastro.dispose();
					daoConfig.definirTema();

					if (temaAntigo.toLowerCase().equals(temaAtual.toLowerCase())) {
						frmConfiguraoDoCadastro = new JFrame();
						initialize();
						frmConfiguraoDoCadastro.setVisible(true);
						restaurarConfiguracoes();
					} else {
						JOptionPane.showMessageDialog(null,
								"Ao alterar o tema, a tela de configurações será fechada automáticamente!");
						frameAntigo.dispose();
						CadastroDeCenariosView window = new CadastroDeCenariosView();
						window.frmCadastroDeCenrios.setVisible(true);
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		GroupLayout groupLayout = new GroupLayout(frmConfiguraoDoCadastro.getContentPane());
		groupLayout
				.setHorizontalGroup(
						groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addContainerGap()
										.addComponent(teste, GroupLayout.PREFERRED_SIZE, 569,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(13, Short.MAX_VALUE))
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
										.addContainerGap(238, Short.MAX_VALUE).addComponent(btnNewButton,
												GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
										.addGap(238)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(teste, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE).addContainerGap()));
		frmConfiguraoDoCadastro.getContentPane().setLayout(groupLayout);
	}

	public void restaurarConfiguracoes() {
		try {
			DAO dao = new DAO();
			ConfiguracoesCenarios config = new ConfiguracoesCenarios();

			ResultSet rs = dao.RestaurarConfiguracoesDoCenario();

			if (rs.next()) {
				comboBox_abordagem.setSelectedIndex(rs.getInt("abordagem"));
				comboBox_criticidade.setSelectedIndex(rs.getInt("criticidade"));
				comboBox_status.setSelectedIndex(rs.getInt("estatus"));

				int limparTodosOsCampos = rs.getInt("limparTodosOsCampos");
				if (limparTodosOsCampos == 1) {
					chcLimparTodosOsCampos.setSelected(true);
				} else {
					chcLimparTodosOsCampos.setSelected(false);
				}

				int manterDescricao = rs.getInt("manterCampoDescricao");
				if (manterDescricao == 1) {
					chcManterOCampoDescricao.setSelected(true);
				} else {
					chcManterOCampoDescricao.setSelected(false);
				}

				int automatizados = rs.getInt("opcaoSimAutomatizados");
				if (automatizados == 1) {
					chckbxManterAOpocaoSimNosTestesAutomatizados.setSelected(true);
				} else {
					chckbxManterAOpocaoSimNosTestesAutomatizados.setSelected(false);
				}

				int regressao = rs.getInt("opcaoSimRegressivo");
				if (regressao == 1) {
					chcManterOpcaoSIMnosTestesRegressivos.setSelected(true);
				} else {
					chcManterOpcaoSIMnosTestesRegressivos.setSelected(false);
				}

				int duplicidade = rs.getInt("duplicidade");
				if (duplicidade == 1) {
					chcCriarCenarioNegativo.setSelected(true);
				} else {
					chcCriarCenarioNegativo.setSelected(false);
				}

				comboBox_tema.setSelectedItem(rs.getString("tema"));
				config.setTema(rs.getString("tema"));

			}
		} catch (Exception e) {
			System.out.println("Erro ao buscar dados no banco!");
		}
	}

}
