package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import dao.DAO;
import excel.EditorExcel;
import excel.ExportExcel;
import model.Pessoa;

public class ExportView {

	JFrame frmExport;
	public DAO dao;
	public CadastroDeCenariosView window;
	private JComboBox<String> comboBox_projeto;
	private JComboBox<String> comboBox_sprint;
	private JCheckBox regressivo;
	private JButton btnExportar;
	private JTextField txtcaminho;
	private JFileChooser arquivo;
	private File file;
	private int op;
	Pessoa pessoa = new Pessoa();

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
					ExportView window = new ExportView();
					window.frmExport.setVisible(false);
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
	public ExportView() throws Exception {
		initialize();
		dao = new DAO();
		recuperarProjeto();
		restaurarDadosComboBoxSprint();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmExport = new JFrame();
		frmExport.setFont(new Font("Dialog", Font.BOLD, 15));
		frmExport.setType(Type.UTILITY);
		frmExport.setTitle("Exportar");
		frmExport.setBounds(500, 250, 450, 355);
		frmExport.getContentPane().setLayout(null);

		btnExportar = new JButton("Exportar");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				arquivo = new JFileChooser();
				arquivo.setDialogTitle("Adicionar imagem");
				arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
				op = arquivo.showOpenDialog(btnExportar);
				if (op == JFileChooser.APPROVE_OPTION) {
					file = new File("");
					file = arquivo.getSelectedFile();
					txtcaminho.setText(file.getAbsolutePath());
				}

				ExportExcel export;
				EditorExcel editor;
				try {
					export = new ExportExcel();
					editor = new EditorExcel();

					export.executarExcel(String.valueOf(file.getAbsolutePath().replace("\\", "\\\\")));
					editor.editarExcel(pessoa.getId_pessoa(), (String) comboBox_projeto.getSelectedItem(),
							(String) comboBox_sprint.getSelectedItem(), file.getAbsolutePath().replace("\\", "\\\\"));
					System.out.println("ID= " + pessoa.getId_pessoa());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnExportar.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnExportar.setBounds(75, 267, 271, 41);
		frmExport.getContentPane().add(btnExportar);

		JPanel teste = new JPanel();
		teste.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Projeto",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		teste.setBounds(6, 10, 420, 202);
		frmExport.getContentPane().add(teste);
		teste.setLayout(null);

		JLabel lblNewLabel = new JLabel("Projeto");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 16, 56, 33);
		teste.add(lblNewLabel);

		comboBox_projeto = new JComboBox<String>();
		comboBox_projeto.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				comboBox_sprint.removeAllItems();
				restaurarDadosComboBoxSprint();
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboBox_projeto.setBounds(76, 18, 334, 33);
		teste.add(comboBox_projeto);

		comboBox_sprint = new JComboBox<String>();
		comboBox_sprint.setBounds(76, 64, 165, 33);
		teste.add(comboBox_sprint);

		JLabel lblSprint = new JLabel("Sprint");
		lblSprint.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSprint.setBounds(10, 66, 56, 31);
		teste.add(lblSprint);

		regressivo = new JCheckBox("Testes regressivos");
		regressivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (regressivo.isSelected()) {
					comboBox_projeto.setEnabled(false);
					comboBox_sprint.setEnabled(false);
				} else {
					comboBox_projeto.setEnabled(true);
					comboBox_sprint.setEnabled(true);
				}
			}
		});
		regressivo.setBounds(120, 169, 189, 27);
		teste.add(regressivo);
		regressivo.setFont(new Font("Tahoma", Font.BOLD, 15));
	}

	Vector<Integer> id_informacoes = new Vector<Integer>();

	public void recuperarProjeto() {
		try {
			dao = new DAO();
			ResultSet rs = dao.listarProjeto();

			while (rs.next()) {
				id_informacoes.addElement(rs.getInt(1));
				comboBox_projeto.addItem(rs.getString(2));
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Carregar projeto: " + e.getMessage());
		}
	}

	public void restaurarDadosComboBoxSprint() {
		try {
			dao = new DAO();
			String nomeDoProjeto = (String) comboBox_projeto.getSelectedItem();
			ResultSet rs = dao.listarSprintExport(nomeDoProjeto);

			while (rs.next()) {
				id_informacoes.addElement(rs.getInt(1));
				comboBox_sprint.addItem(rs.getString(2));
			}
			// id = recuperarId();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Carregar sprint: " + e.getMessage());
		}
	}
}
