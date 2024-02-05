package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Pessoa;
import model.Projeto;
import util.ConnectionFactory;

public class DAOADM {

	private Connection conn;
	private PreparedStatement ps; // executa query
	private ResultSet rs; // cria uma tabela
	public Projeto projeto;
	public Pessoa pessoa;
	public static int id_sprint;

	public DAOADM() throws Exception {
		pessoa = new Pessoa();
		try {
			conn = ConnectionFactory.getConnection();
		} catch (Exception e) {
			throw new Exception("erro" + e.getMessage());
		}
	}

	public List<Object> buscarTodos_pessoas() throws SQLException, Exception {
		List<Object> pessoa = new ArrayList<Object>();
		try {
			// String sql = "SELECT * FROM tbPessoa AS p INNER JOIN tbCenarios t INNER JOIN
			// informacoes I WHERE p.id = t.id";
			String sql = "SELECT * FROM tbPessoa WHERE tipoDeUsuario_pessoa='QA' AND statusDaPessoa=0 order by id";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				String dataNow = rs.getString(9); // ####-##-##
				String dia = dataNow.substring(8, 10);
				String mes = dataNow.substring(5, 7);
				String ano = dataNow.substring(0, 4);
				String novaData = dia + "/" + mes + "/" + ano;

//				Object[] linha = {rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(5),rs.getString(7),rs.getString(8),rs.getString(9), rs.getString(25),rs.getString(26),rs.getString(31),rs.getString(32),rs.getString(33),rs.getString(35)};
				Object[] linha = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), novaData };
				pessoa.add(linha);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return pessoa;
	}
	
	public ResultSet recuperarTodos_CENARIOS(int id) throws SQLException {
		try {
			String sql = "SELECT c.* FROM tbCenarios AS c, tbPessoa AS p WHERE id_pessoa_cenarios=? = p.id;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			return ps.executeQuery();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public ResultSet listarProjeto(int id) throws SQLException {
		try {
			String sql = "SELECT nome FROM nomeDoProjeto WHERE id_pessoa=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			return ps.executeQuery();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public ResultSet listarSprint(int idPessoa, String nome) {
		try {
			String sql = "SELECT id, sprint_projeto, projeto_projeto FROM informacoes WHERE id_pessoa=? AND projeto_projeto=? ORDER BY sprint_projeto";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idPessoa);
			ps.setString(2, nome);
			return ps.executeQuery();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public ResultSet recuperarQtdAbordagem_POSITIVO(int id_abordagem, String nomeDoProjeto,
			String sprint) {
		id_sprint = idRecuperar(nomeDoProjeto, sprint);
		try {
			String sql = "SELECT abordagem_cenarios, COUNT(*) AS cenarios FROM tbCenarios WHERE id_pessoa_cenarios=? AND id_sprint_cenarios=? AND abordagem_cenarios='Positivo';";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id_abordagem); 
			ps.setInt(2, id_sprint);

			return ps.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}
	}
	
	public ResultSet recuperarQtdAbordagem_NEGATIVO(int id_abordagem, String nomeDoProjeto,
			String sprint) {
		id_sprint = idRecuperar(nomeDoProjeto, sprint);
		try {
			String sql = "SELECT abordagem_cenarios, COUNT(*) AS cenarios FROM tbCenarios WHERE id_pessoa_cenarios=? AND id_sprint_cenarios=? AND abordagem_cenarios='Negativo';";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id_abordagem); 
			ps.setInt(2, id_sprint);

			return ps.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}
	}

	
	/**
	 * Utilizado para fazer busca da quantidade de historias no banco de dados.
	 * 
	 * @param id
	 * @param nomeDoProjeto
	 * @param sprint
	 * @return 
	 * Quantidade de historias
	 */
	public String recuperarQtdHistorias(int id, String nomeDoProjeto, String sprint) {
		String qtd;
		try {
			id_sprint = idRecuperar(nomeDoProjeto, sprint);
			String sql = "SELECT count(historia_cenarios) FROM tbCenarios WHERE id_pessoa_cenarios=? AND id_sprint_cenarios=?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, id_sprint);
			rs = ps.executeQuery();
			while (rs.next()) {
				qtd = rs.getString(1);
				return qtd;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}
		return null;
	}

	public String recuperarQtdBugs(int id, String nomeDoProjeto, String sprint) {
		String qtd;
		try {
			id_sprint = idRecuperar(nomeDoProjeto, sprint);
			String sql = "SELECT count(bug_cenarios) FROM tbCenarios WHERE bug_cenarios=1 AND id_pessoa_cenarios=? AND id_sprint_cenarios=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, id_sprint);
			rs = ps.executeQuery();
			while (rs.next()) {
				qtd = rs.getString(1);
				return qtd;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}
		return null;
	}

	public String recuperarQtdCenariosFinalizado(int id, String nomeDoProjeto, String sprint) {
		String qtd;
		try {
			id_sprint = idRecuperar(nomeDoProjeto, sprint);
			String sql = "SELECT count(estatus_cenarios) FROM tbCenarios WHERE estatus_cenarios='OK' AND id_pessoa_cenarios=? AND id_sprint_cenarios=?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, id_sprint);
			rs = ps.executeQuery();	
			while (rs.next()) {
				qtd = rs.getString(1);
				return qtd;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}
		return null;
	}
	
	public String recuperarQtdCenariosEmAberto(int id, String nomeDoProjeto, String sprint) {
		String qtd;
		try {
			id_sprint = idRecuperar(nomeDoProjeto, sprint);
			String sql = "SELECT count(estatus_cenarios) FROM tbCenarios WHERE estatus_cenarios!='OK' AND id_pessoa_cenarios=? AND id_sprint_cenarios=?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, id_sprint);
			rs = ps.executeQuery();	
			while (rs.next()) {
				qtd = rs.getString(1);
				return qtd;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}
		return null;
	}


	
	public String recuperarQtdAutomacaoSim(int id, String nomeDoProjeto, String sprint) {
		String qtd;
		try {
			id_sprint = idRecuperar(nomeDoProjeto, sprint);
			String sql = "SELECT count(automacao_cenarios) FROM tbCenarios WHERE automacao_cenarios='Sim' AND id_pessoa_cenarios=? AND id_sprint_cenarios=?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, id_sprint);
			rs = ps.executeQuery();
			while (rs.next()) {
				qtd = rs.getString(1);
				return qtd;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}
		return null;
	}

	public String recuperarPrioridade(String prioridade, int id, String nomeDoProjeto, String sprint) {
		String qtd;
		try {
			id_sprint = idRecuperar(nomeDoProjeto, sprint);
			String sql = "SELECT count(prioridade_cenarios) FROM tbCenarios WHERE prioridade_cenarios=? AND id_pessoa_cenarios=? AND id_sprint_cenarios=?;";
			ps = conn.prepareStatement(sql);
			ps.setString(1, prioridade);
			ps.setInt(2, id);
			ps.setInt(3, id_sprint);
			rs = ps.executeQuery();
			while (rs.next()) {
				qtd = rs.getString(1);
				return qtd;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}
		return null;
	}
	
	public ResultSet recuperarDataInicioEFim(int id, String nomeDoProjeto, String sprint) {
		id_sprint = idRecuperar(nomeDoProjeto, sprint);
		try {
			String sql = "SELECT data_inicio_projeto, data_fim_projeto FROM informacoes WHERE id_pessoa=? AND id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, id_sprint);

			return ps.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}
	}

	int id;

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

	public ResultSet qtdDeHistorias(int id_, String pro) {
		try {
			String sql = "SELECT count(projeto_projeto) FROM tbCenarios WHERE id_pessoa_cenarios=? AND abordagem_cenarios=? AND id_sprint_cenarios=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(2, id_);
			ps.setString(1, pro);

			return ps.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}
	}

	public ResultSet recuperarDataEFinalizacaoDoProjeto(String nomedoprojeto ,String sprint) {
		try {
			id_sprint = idRecuperar(nomedoprojeto, sprint);
			String sql = "SELECT data_fim_projeto, finalizacao_projeto FROM informacoes WHERE id=?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id_sprint);
			
			return ps.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}
	}

	Month mes;
	public Month retornarMes(String mesReformar) {
		switch (mesReformar) {
		case "01":
			mes = Month.JANUARY;
			break;

		case "02":
			mes = Month.FEBRUARY;
			break;

		case "03":
			mes = Month.MARCH;
			break;

		case "04":
			mes = Month.APRIL;
			break;

		case "05":
			mes = Month.MAY;
			break;

		case "06":
			mes = Month.JUNE;
			break;

		case "07":
			mes = Month.JULY;
			break;

		case "08":
			mes = Month.AUGUST;
			break;

		case "09":
			mes = Month.SEPTEMBER;
			break;

		case "10":
			mes = Month.OCTOBER;
			break;

		case "11":
			mes = Month.NOVEMBER;
			break;

		case "12":
			return mes = Month.DECEMBER;
			
		default:
			break;
		}
		return mes;
	}

}
