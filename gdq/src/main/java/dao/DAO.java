package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Cenarios;
import model.ConfiguracoesCenarios;
import model.Pessoa;
import model.Projeto;
import util.ConnectionFactory;

public class DAO {

	public Connection conn;
	public PreparedStatement ps;
	public ResultSet rs;
	public Projeto projeto;
	public ConfiguracoesCenarios config;
	public Pessoa pessoa;
	public static int idPessoa;
	public static int id;
	public String nomePessoa;
	private PreparedStatement psEmail;

	public DAO() throws Exception {
		pessoa = new Pessoa();
		try {
			conn = ConnectionFactory.getConnection();
		} catch (Exception e) {
			throw new Exception("erro" + e.getMessage());
		}
	}

	public void formatacaoDeView() {
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
	}

	/**
	 * Validar se possui casdastro na base. 
	 * 
	 * 
	 * @param nome
	 * @param email
	 * @param senha
	 * @param verificarTipoDeUsuario
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 * 
	 * @author Ericlis Gonçalves dos Santos
	 */

	public Boolean validarLoginDAO(String nome, String email, String senha)
			throws SQLException, Exception {
		try {
			String sql = "SELECT id, nome_pessoa, email_pessoa, senha_pessoa, tipoDeUsuario_pessoa FROM tbPessoa WHERE (nome_pessoa=? OR email_pessoa=?) AND senha_pessoa=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, nome);
			ps.setString(2, email);
			ps.setString(3, senha);

			rs = ps.executeQuery();
			
			if (rs.next()) {
				idPessoa = Integer.parseInt(rs.getString(1));
				pessoa.setId_pessoa(idPessoa);
				return true;
			} else{
				return false;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}

	}
	
	public String recuperarTema() {
		try {
			String sql = "SELECT tema FROM configuracoesDeCenarios WHERE id_pessoa_config=?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idPessoa);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getString(1);
			} else {
				return null;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}
	}

	public String verificarUsuario(String nome, String email, String senha) {
		try {
			String value;
			String sql = "SELECT nome_pessoa, email_pessoa, senha_pessoa, tipoDeUsuario_pessoa FROM tbPessoa WHERE (nome_pessoa=? OR email_pessoa=?) AND senha_pessoa=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, nome);
			ps.setString(2, email);
			ps.setString(3, senha);

			rs = ps.executeQuery();

			if (rs.next()) {
				return value = rs.getString(4);
			} else {
				return "";
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}
	}

	public int idRecuperar(String nome, String sprint) {
		try {
			String sql = "SELECT id FROM informacoes WHERE projeto_projeto=? AND sprint_projeto=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, nome);
			ps.setString(2, sprint);

			rs = ps.executeQuery();

			if (rs.next()) {
				id = Integer.parseInt(rs.getString(1));
				pessoa.setId_pessoa(id);
				return id;
			} else {
				return id;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return id;
		}
	}

	public void Salvar(Cenarios cenario, String nome, String sprint) throws Exception {
		id = idRecuperar(nome, sprint);
		try {
			String sql = "INSERT INTO tbCenarios(id, historia_cenarios, descricao_cenarios, abordagem_cenarios, prioridade_cenarios, escopoDeRegressao_cenarios, automacao_cenarios,"
					+ " estatus_cenarios, bug_cenarios, linkDoBug_cenarios, motivoDaNaoExecusao_cenarios, dataDoCadastro_cenarios, id_sprint_cenarios, id_pessoa_cenarios, statusDoCenario)"
					+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setString(2, cenario.getHistoria());
			ps.setString(3, cenario.getDescricao());
			ps.setString(4, cenario.getAbordagem());
			ps.setString(5, cenario.getPrioridade());
			ps.setString(6, cenario.getEscopoDeRegressao());
			ps.setString(7, cenario.getAutomacao());
			ps.setString(8, cenario.getStatus());
			ps.setBoolean(9, cenario.getBug());
			ps.setString(10, cenario.getLinkDoBug());
			ps.setString(11, cenario.getMotivoDaNaoExecusao());
			LocalDateTime dataDeHoje = LocalDateTime.now();
			ps.setString(12, dataDeHoje.toString());
			ps.setInt(13, id);
			ps.setInt(14, idPessoa);
			ps.setInt(15, 0);
			ps.executeUpdate();

		} catch (Exception e) {
			throw new Exception("erro ao salvar" + e.getMessage());
		}

	}

	public void SalvarConfiguracoesDoCenario(ConfiguracoesCenarios configuracoes) throws Exception {
		try {
			String sql = "UPDATE configuracoesDeCenarios SET id=?, abordagem=?, criticidade=?, limparTodosOsCampos=?, manterCampoDescricao=?, "
					+ "opcaoSimAutomatizados=?, opcaoSimRegressivo=?, estatus=?, duplicidade=?, tema=? WHERE id_pessoa_config=?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setInt(2, configuracoes.getAbordagem());
			ps.setInt(3, configuracoes.getCriticidade());
			ps.setInt(4, configuracoes.getLimparCampos());
			ps.setInt(5, configuracoes.getManterCampoDescricao());
			ps.setInt(6, configuracoes.getOpcaoSimAutomatizados());
			ps.setInt(7, configuracoes.getOpcaoSimRegressivo());
			ps.setInt(8, configuracoes.getStatus());
			ps.setInt(9, configuracoes.getDuplicitadeDeCenarios());
			ps.setString(10, configuracoes.getTema());
			ps.setInt(11, idPessoa);
			ps.executeUpdate();

		} catch (Exception e) {
			throw new Exception("erro ao salvar" + e.getMessage());
		}

	}

	public ResultSet RestaurarConfiguracoesDoCenario() throws Exception {
		try {
			String sql = "SELECT * FROM configuracoesDeCenarios WHERE id_pessoa_config=?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idPessoa);
			return ps.executeQuery();
		} catch (Exception e) {
			throw new Exception("erro ao salvar" + e.getMessage());
		}
	}
	
	public void Alterar(Cenarios cenario, String nome, String sprint) throws Exception {
		id = idRecuperar(nome, sprint);
		try {
			String sql = "UPDATE tbCenarios SET historia_cenarios=?, descricao_cenarios=?, abordagem_cenarios=?, prioridade_cenarios=?, escopoDeRegressao_cenarios=?,"
					+ " automacao_cenarios=?, estatus_cenarios=?, bug_cenarios=?, linkDoBug_cenarios=?, motivoDaNaoExecusao_cenarios=? ,id_sprint_cenarios=?"
					+ " WHERE id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, cenario.getHistoria());
			ps.setString(2, cenario.getDescricao());
			ps.setString(3, cenario.getAbordagem());
			ps.setString(4, cenario.getPrioridade());
			ps.setString(5, cenario.getEscopoDeRegressao());
			ps.setString(6, cenario.getAutomacao());
			ps.setString(7, cenario.getStatus());
			ps.setBoolean(8, cenario.getBug());
			ps.setString(9, cenario.getLinkDoBug());
			ps.setString(10, cenario.getMotivoDaNaoExecusao());
			ps.setInt(11, id);
			ps.setInt(12, cenario.getCodTeste());
			ps.executeUpdate();

		} catch (Exception e) {
			throw new Exception("erro ao alterar" + e.getMessage());
		}
	}

	public void Excluir(int codTeste) throws Exception {

		try {
			String sql = "DELETE FROM tbCenarios " + " WHERE id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, codTeste);
			ps.executeUpdate();

		} catch (Exception e) {
			throw new Exception("erro ao excluir" + e.getMessage());
		}
	}

	public void EditarStatusParaExcluido(int codTeste) throws Exception {

		try {
			String sql = "UPDATE tbCenarios SET statusDoCenario='1' WHERE id=?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, codTeste);
			ps.executeUpdate();

		} catch (Exception e) {
			throw new Exception("erro ao excluir" + e.getMessage());
		}
	}

	public void EditarStatusParaRecuperado(int codTeste) throws Exception {
		try {
			String sql = "UPDATE tbCenarios SET statusDoCenario='0' WHERE id=?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, codTeste);
			ps.executeUpdate();

		} catch (Exception e) {
			throw new Exception("erro ao recuperar" + e.getMessage());
		}
	}

	public void EditarStatusParaRecuperadoPessoa(int codTeste) throws Exception {
		try {
			String sql = "UPDATE tbPessoa SET statusDaPessoa='0' WHERE id=?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, codTeste);
			ps.executeUpdate();

		} catch (Exception e) {
			throw new Exception("erro ao recuperar" + e.getMessage());
		}
	}

	public List<Object> buscarPorStatus(String status) throws SQLException, Exception {
		List<Object> cenarios = new ArrayList<Object>();
		try {
			String sql = "SELECT * FROM tbCenarios where estatus_projeto=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, status);
			rs = ps.executeQuery();

			while (rs.next()) {
				Object[] linha = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11), rs.getString(12) };
				cenarios.add(linha);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return cenarios;
	}

	public List<Object> buscarPorSprint(String sprint) throws SQLException, Exception {
		List<Object> cenarios = new ArrayList<Object>();
		try {
			String sql = "SELECT * FROM tbCenarios where sprint_projeto=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, sprint);
			rs = ps.executeQuery();

			while (rs.next()) {
				Object[] linha = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11), rs.getString(12) };
				cenarios.add(linha);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return cenarios;
	}

	public List<Object> buscarPorHistoria(String selCompleto) throws SQLException, Exception {
		List<Object> cenarios = new ArrayList<Object>();
		try {
			// String sql = "SELECT * FROM tbCenarios where historia like '%%'";
			String sql = selCompleto;
			ps = conn.prepareStatement(sql);
			// ps.setString(1, historia);
			rs = ps.executeQuery();

			while (rs.next()) {
				Object[] linha = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11), rs.getString(12), rs.getString(13) };
				cenarios.add(linha);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return cenarios;
	}

	public Projeto buscarTodos_informacoes(String nomeDoProjeto, String sprint) throws SQLException, Exception {
		String sql = "SELECT i.id, i.qa_projeto, i.storyPoints_projeto, i.totalDevs_projeto,"
				+ " i.sprint_projeto, i.data_inicio_projeto, i.data_fim_projeto, i.dataCriacao_projeto, i.finalizacao_projeto"
				+ " FROM informacoes AS i WHERE i.projeto_projeto=? AND i.sprint_projeto=? AND i.finalizacao_projeto=0";
		ps = conn.prepareStatement(sql);
		ps.setString(1, nomeDoProjeto);
		ps.setString(2, sprint);
		rs = ps.executeQuery();

		if (rs.next()) {
			int id = rs.getInt("id");
//			String project = rs.getString("projeto_projeto");
			String qa = rs.getString("qa_projeto");
			int storyPoints = rs.getInt("storyPoints_projeto");
			int totalDevs = rs.getInt("totalDevs_projeto");
			String dataInicio = rs.getString("data_inicio_projeto");
			String dataFim = rs.getString("data_fim_projeto");
			String data_criacao = rs.getString("dataCriacao_projeto");
			boolean fimDoProjeto = rs.getBoolean("finalizacao_projeto");

			projeto = new Projeto(id, nomeDoProjeto, qa, storyPoints, totalDevs, sprint, dataInicio, dataFim,
					data_criacao, fimDoProjeto);
		}
		return projeto;
	}

	public int countQtdDeCenariosPorSprint(int id_sprint) throws SQLException {
		String sql = "SELECT count(historia_cenarios) FROM tbCenarios WHERE id_pessoa_cenarios=? AND id_sprint_cenarios=?;";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, idPessoa);
		ps.setInt(2, id_sprint);
		rs = ps.executeQuery();

		if (rs.next()) {
			return rs.getInt(1);
		} else {
			return 0;
		}
	}

	public int countQtdDeCenariosPorSprint_OK(int id_sprint) throws SQLException {
		String sql = "SELECT count(historia_cenarios) FROM tbCenarios WHERE id_pessoa_cenarios=? AND id_sprint_cenarios=? AND estatus_cenarios='OK';";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, idPessoa);
		ps.setInt(2, id_sprint);
		rs = ps.executeQuery();

		if (rs.next()) {
			return rs.getInt(1);
		} else {
			return 0;
		}
	}

	public ResultSet listarSprint(String nome) throws SQLException {
		try {
			String sql = "SELECT id, sprint_projeto, projeto_projeto FROM informacoes WHERE id_pessoa=? AND projeto_projeto=? AND finalizacao_projeto=0 ORDER BY sprint_projeto;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idPessoa);
			ps.setString(2, nome);
			return ps.executeQuery();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public ResultSet listarSprintExport(String nome) throws SQLException {
		try {
			String sql = "SELECT id, sprint_projeto, projeto_projeto FROM informacoes WHERE qa_projeto=? AND projeto_projeto=? AND finalizacao_projeto=0;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idPessoa);
			ps.setString(2, nome);
			return ps.executeQuery();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	private String restaurar;

	public List<Object> buscarTodos() throws SQLException, Exception {
		List<Object> cenarios = new ArrayList<Object>();
		try {
			String sql = "SELECT c.*, i.sprint_projeto, i.projeto_projeto FROM tbCenarios AS c, informacoes AS i WHERE c.id_sprint_cenarios = i.id AND c.id_pessoa_cenarios=? AND i.finalizacao_projeto=0 AND c.statusDoCenario=0 ORDER BY dataDoCadastro_cenarios DESC , sprint_projeto;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idPessoa);
			rs = ps.executeQuery();

			while (rs.next()) {
				String verificar = rs.getString(9);
				if (verificar.equals("0")) {
					restaurar = "Não";
				} else if (verificar.equals("1")) {
					restaurar = "Sim";
				}
				String data = rs.getString(12); // ####-##-##
				String dia = data.substring(8, 10);
				String mes = data.substring(5, 7);
				String ano = data.substring(0, 4);
				String hora = data.substring(11, 19);

				data = dia + "/" + mes + "/" + ano;

				String dataFormatada = dia + "/" + mes + "/" + ano + " " + hora;

				Object[] linha = { rs.getString(1), rs.getString(17), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), restaurar, rs.getString(10),
						rs.getString(11), rs.getString(16), dataFormatada };
				cenarios.add(linha);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return cenarios;
	}

	public List<Object> buscarTodosCenariosExcluidos() throws SQLException, Exception {
		List<Object> cenarios = new ArrayList<Object>();
		try {
			String sql = "SELECT c.*, i.sprint_projeto, i.projeto_projeto FROM tbCenarios AS c, informacoes AS i WHERE c.id_sprint_cenarios = i.id AND c.id_pessoa_cenarios=? AND i.finalizacao_projeto=0 AND c.statusDoCenario=1 ORDER BY dataDoCadastro_cenarios DESC , sprint_projeto;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idPessoa);
			rs = ps.executeQuery();

			while (rs.next()) {
				String verificar = rs.getString(9);
				if (verificar.equals("0")) {
					restaurar = "Não";
				} else if (verificar.equals("1")) {
					restaurar = "Sim";
				}
				String data = rs.getString(12); // ####-##-##
				String dia = data.substring(8, 10);
				String mes = data.substring(5, 7);
				String ano = data.substring(0, 4);

				data = dia + "/" + mes + "/" + ano;

				Object[] linha = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), restaurar, rs.getString(10),
						rs.getString(11), rs.getString(16), data, rs.getString(17) };
				cenarios.add(linha);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return cenarios;
	}

	public LocalDateTime getDateNow() {
		LocalDateTime dataDeHoje = LocalDateTime.now();
//		int diaAtual = dataDeHoje.getDayOfMonth();
//		int mesAtual = dataDeHoje.getMonthValue();
//		int anoAtual = dataDeHoje.getYear();

//		return anoAtual + "-" + mesAtual + "-" + diaAtual + " "+ ;
		return dataDeHoje;
	}

	/**
	 * @param nomeDoProjeto
	 */

	public void Salvar_nomeDoProjeto(int id, String nomeDoProjeto) {
		try {
			String sql = "INSERT INTO nomeDoProjeto(id, nome, id_pessoa)" + "VALUES (?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setString(2, nomeDoProjeto);
			ps.setInt(3, id);
			ps.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Excluir projeto
	 * 
	 * @param id
	 * @param nomeDoProjeto
	 */

	public void Excluir_nomeDoProjeto(String id, String nomeDoProjeto) {
		try {
			String sql = "DELETE FROM nomeDoProjeto WHERE nome=? AND id=?;";
			ps = conn.prepareStatement(sql);
			ps.setString(1, nomeDoProjeto);
			ps.setString(2, id);
			ps.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public ResultSet listarProjeto() throws SQLException {
		try {
			String sql = "SELECT id, nome, id_pessoa FROM nomeDoProjeto WHERE id_pessoa=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idPessoa);
			return ps.executeQuery();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public ResultSet listarIdDoProjeto(int id_informacoes, String projeto) throws SQLException {
		try {
			String sql = "SELECT id, nome FROM nomeDoProjeto WHERE nome=? AND id_pessoa=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, projeto);
			ps.setInt(2, id_informacoes);
			return ps.executeQuery();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public ResultSet listarProjetoComId(int id) throws SQLException {
		try {
			String sql = "SELECT id, nome FROM nomeDoProjeto WHERE id_pessoa=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			return ps.executeQuery();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public ResultSet listarProjetoExportExcel() throws SQLException {
		try {
			String sql = "SELECT n.id, n.nome, p.nome_pessoa FROM nomeDoProjeto AS n, tbPessoa AS p WHERE n.id_pessoa=? AND p.id=?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idPessoa);
			ps.setInt(2, idPessoa);
			return ps.executeQuery();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void Salvar_informacoesProjeto(Projeto projeto) throws Exception {
		try {
			nomePessoa = recuperarNome(idPessoa);
			String sql = "INSERT INTO informacoes(id, projeto_projeto, qa_projeto, storyPoints_projeto, totalDevs_projeto, sprint_projeto, data_inicio_projeto, data_fim_projeto, dataCriacao_projeto, finalizacao_projeto, id_pessoa)"
					+ "values (?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, projeto.getId());
			ps.setString(2, projeto.getProjeto());
			ps.setString(3, nomePessoa);
//			ps.setInt(4, projeto.getQtdHistorias());
//			ps.setInt(5, projeto.getHistoriasEntregues());
			ps.setInt(4, projeto.getStoryPoints());
			ps.setInt(5, projeto.getTotalDevs());
			ps.setString(6, projeto.getSprint());
			ps.setString(7, projeto.getDataInicio());
			ps.setString(8, projeto.getDataFim());
			ps.setString(9, projeto.getData_criacao());
			ps.setBoolean(10, projeto.getFimDoProjeto());
			ps.setInt(11, idPessoa);
			ps.executeUpdate();

		} catch (Exception e) {
			throw new Exception("erro ao salvar" + e.getMessage());
		}
	}

	public String recuperarNome(int id) {
		try {
			String sql = "SELECT nome_pessoa FROM tbPessoa WHERE id=?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				return nomePessoa = rs.getString(1);
			}

		} catch (Exception e) {
		}
		return nomePessoa;
	}

	public List<Object> buscarTodaInformacoes() throws SQLException, Exception {
		List<Object> projetos = new ArrayList<Object>();
		try {
			String sql = "SELECT i.* FROM informacoes AS i INNER JOIN tbPessoa as p WHERE i.id_pessoa =? AND p.id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idPessoa);
			ps.setInt(2, idPessoa);
			rs = ps.executeQuery();

			while (rs.next()) {

				String dataNow = rs.getString(11); // ####-##-##
				String dia = dataNow.substring(8, 10);
				String mes = dataNow.substring(5, 7);
				String ano = dataNow.substring(0, 4);
				String novaData = dia + "/" + mes + "/" + ano;

				String finalizado = rs.getString(12);
				String fim;

				if (finalizado.equals("0")) {
					fim = "Ativo";
				} else {
					fim = "Finalizado";
				}

				Object[] linha = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), fim,
						novaData };
				projetos.add(linha);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return projetos;
	}

}
