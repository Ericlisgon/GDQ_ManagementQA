package excel;

import com.aspose.cells.License;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;

import dao.DAO;
import util.ConnectionFactory;

public class ExportExcel {
	public DAO dao;
	private Connection conn;
	private PreparedStatement ps; // executa query
	private ResultSet rs;

	public ExportExcel() throws Exception {
		dao = new DAO();
		try {
			conn = ConnectionFactory.getConnection();
		} catch (Exception e) {
			throw new Exception("erro" + e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		
	}
	
	public void criacaoDeArquivoExcel_QA(String projeto, String sprint) throws Exception {
		try {
			
			License license = new License(); 
	        license.setLicense("Aspose.Cells.lic");
			// Inicializar um objeto Workbook
			Workbook workbook = new Workbook();

			// Obtendo a referência da planilha
			Worksheet worksheet = workbook.getWorksheets().get(0);
			buscar(worksheet);
			
			String nome = "";

			// Salvando o arquivo Excel
//			workbook.save("C:\\MRT_Evidencia\\"+nome+".xlsx");
			workbook.save("C:\\MRT_Evidencia\\teste.xlsx");
			System.out.println("Deu certo no xlsx");
			JOptionPane.showMessageDialog(null, "Exportaçao de excel para "+nome.toUpperCase()+" criado com sucesso!");
		} catch (Exception e) {
			System.out.println("Deu erro!!!");
		}
	}


	public void executarExcel(String caminho) throws Exception {
		try {
			// Inicializar um objeto Workbook
			Workbook workbook = new Workbook();

			// Obtendo a referência da planilha
			Worksheet worksheet = workbook.getWorksheets().get(0);
			buscar(worksheet);
			
			String nome = "";

			// Salvando o arquivo Excel
//			workbook.save("C:\\MRT_Evidencia\\"+nome+".xlsx");
			workbook.save(caminho+"\\teste.xlsx");
//			workbook.save("C:\\MRT_Evidencia\\teste.xlsx");
			
			System.out.println("Deu certo no xlsx");
			JOptionPane.showMessageDialog(null, "Exportaçao de excel para "+nome.toUpperCase()+" criado com sucesso!");
		} catch (Exception e) {
			System.out.println("Deu erro!!!");
		}
	}

	public List<Object> buscar(Worksheet worksheet) throws SQLException, Exception {
		List<Object> cenarios = new ArrayList<Object>();
		try {
			String sql = "SELECT c.*, i.sprint_projeto FROM tbCenarios AS c, informacoes AS i WHERE c.id_sprint_cenarios = i.id AND i.finalizacao_projeto = 0";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			int value = 1;
			
			Object[] linha = { "id", "Historias", "Cenarios", "Abordagem", "Prioridade",
					"Regressivo", "Automacao", "Status", "BUG", "link do bug",
					"Motivo de não testar", "status do cenario", "sprint"};
			worksheet.getCells().importObjectArray(linha, value, 0, false);
			
			value = 2;
			while (rs.next()) {
				Object[] linhaContinuacao = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11), rs.getString(15), rs.getString(16) };
				
				worksheet.getCells().importObjectArray(linhaContinuacao, value, 0, false);
				value++;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return cenarios;
	}
	
	public List<Object> buscarPorProjetoESprint(Worksheet worksheet) throws SQLException, Exception {
		List<Object> cenarios = new ArrayList<Object>();
		try {
			String sql = "SELECT c.*, i.sprint_projeto FROM tbCenarios AS c, informacoes AS i WHERE c.id_sprint_cenarios = i.id AND i.finalizacao_projeto ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			int value = 1;
			while (rs.next()) {
				Object[] linha = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11), rs.getString(15) };
				
				worksheet.getCells().importObjectArray(linha, value, 0, false);
				value++;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return cenarios;
	}

}
