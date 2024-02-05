package excel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;

import dao.DAO;
import util.ConnectionFactory;

public class EditorExcel {
	public DAO dao;
	private Connection conn;
	private PreparedStatement ps; // executa query
	private ResultSet rs;

	public EditorExcel() throws Exception {
		dao = new DAO();
		try {
			conn = ConnectionFactory.getConnection();
		} catch (Exception e) {
			throw new Exception("erro" + e.getMessage());
		}
	}

	public void editarExcel(int id, String projeto, String sprint, String caminho) throws Exception {
		try {
			// Abrindo uma pasta de trabalho existente.
			Workbook workbook = new Workbook("C:\\MRT_Evidencia\\MRT.xlsx");

			// Obtendo a referência da planilha
			// Worksheet sheet = workbook.getWorksheets().get(0);
			Worksheet sheet = workbook.getWorksheets().get("MRT SP1");

			// Exportar dados para o modelo do Excel (na segunda linha, primeira coluna)
//			buscar(sheet, id, projeto, sprint);
			buscarPorProjeto(sheet, id, projeto);

			// Salve o arquivo Excel
			workbook.save("C:\\MRT_Evidencia\\" + projeto.replace(" ", "_") + "_" + sprint.replace(" ", "_") + ".xlsx",
					SaveFormat.XLSX);
			workbook.save(caminho + "\\" + projeto.replace(" ", "_") + "_" + sprint.replace(" ", "_") + ".xlsx",
					SaveFormat.XLSX);
			System.out.println("Deu certo no xlsx");
			JOptionPane.showMessageDialog(null, "Edição de excel para " + projeto.toUpperCase().replace(" ", "_") + "_"
					+ sprint.toUpperCase().replace(" ", "_") + " realizado com sucesso!");
		} catch (Exception e) {
			System.out.println("Deu erro!!!");
		}
	}

	public void buscar(Worksheet sheet, int id, String projeto, String sprint) throws SQLException, Exception {
		try {
			String sql = "SELECT c.*, i.sprint_projeto FROM tbCenarios AS c, informacoes AS i WHERE c.id_sprint_cenarios = i.id AND i.finalizacao_projeto = 0";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			int value = 16;
			while (rs.next()) {
				Object[] linha = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11), rs.getString(15) };

				sheet.getCells().importObjectArray(linha, value, 0, false);
				value++;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void buscarPorProjeto(Worksheet sheet, int id, String projeto) throws SQLException, Exception {
		try {
			String sql = "SELECT c.*, i.sprint_projeto, i.projeto_projeto FROM tbCenarios AS c, informacoes AS i WHERE c.id_sprint_cenarios = i.id = ? AND i.finalizacao_projeto = 0 AND i.projeto_projeto = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, projeto);
//			ps.setInt(2, id);
//			ps.setInt(3, id_sprint);
			rs = ps.executeQuery();

			int value = 16;
			while (rs.next()) {
				Object[] linha = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11), rs.getString(15) };

				sheet.getCells().importObjectArray(linha, value, 0, false);
				value++;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

}
