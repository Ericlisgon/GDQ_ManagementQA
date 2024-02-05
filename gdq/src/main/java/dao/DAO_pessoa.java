package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import model.Cenarios;
import model.Pessoa;
import model.Projeto;
import util.ConnectionFactory;

public class DAO_pessoa {

	private Connection conn;
	private PreparedStatement ps; // executa query
	private ResultSet rs; // cria uma tabela
	public Projeto projeto;
	public Pessoa pessoa;

	public DAO_pessoa() throws Exception {
		try {
			conn = ConnectionFactory.getConnection();
		} catch (Exception e) {
			throw new Exception("erro" + e.getMessage());
		}
	}

	public void SalvarPessoa(Pessoa pessoa) throws Exception {
		try {
			String sql = "INSERT INTO tbPessoa(id, nome_pessoa, email_pessoa, empresa_pessoa, cargo_pessoa, senha_pessoa, caminhoDaFoto_pessoa, tipoDeUsuario_pessoa, data_pessoa, squad_pessoa, statusDaPessoa)"
					+ " values(?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setString(2, pessoa.getNome());
			ps.setString(3, pessoa.getEmail());
			ps.setString(4, pessoa.getEmpresa());
			ps.setString(5, pessoa.getCargo());
			ps.setString(6, pessoa.getSenha());
			ps.setString(7, pessoa.getCaminhoDaFoto());
			ps.setString(8, pessoa.getTipoDeUsuario());
			ps.setString(9, pessoa.getData_pessoa());
			ps.setString(10, pessoa.getSquad());
			ps.setInt(11, 0);
			
			ps.executeUpdate();
		} catch (Exception e) {
			throw new Exception("erro ao salvar" + e.getMessage());
		}

	}
	
	/**
	 * Vericiacao de usuario duplicado!
	 * 
	 * @param nome
	 * @param email
	 * @param senha
	 * @param verificarTipoDeUsuario
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	
	public Boolean validarDuplicidade(String nome, String email, String senha, String verificarTipoDeUsuario)
			throws SQLException, Exception {
		try {
			String sql = "SELECT nome_pessoa, email_pessoa, senha_pessoa, tipoDeUsuario_pessoa FROM tbPessoa WHERE nome_pessoa=? AND email_pessoa=? AND senha_pessoa=? AND tipoDeUsuario_pessoa=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, nome);
			ps.setString(2, email);
			ps.setString(3, senha);
			ps.setString(4, verificarTipoDeUsuario);

			rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}

	}
	
	public List<Object> buscarTodos_pessoasATIVAS() throws SQLException, Exception {
		List<Object> pessoa = new ArrayList<Object>();
		try {
//			String sql = "SELECT * FROM tbPessoa WHERE statusDaPessoa=0 ORDER BY data_pessoa DESC;";
			String sql = "SELECT * FROM tbPessoa WHERE statusDaPessoa=0 ORDER BY id";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				String dataNow = rs.getString(9); //####-##-##
				String dia = dataNow.substring(8, 10);
				String mes = dataNow.substring(5, 7);
				String ano = dataNow.substring(0, 4);
				String novaData = dia+"/"+mes+"/"+ano;
				
				Object[] linha = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6),rs.getString(8),rs.getString(7),novaData,rs.getString(9)};
				pessoa.add(linha);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return pessoa;
	}
	
	public List<Object> buscarTodos_pessoasINATIVAS() throws SQLException, Exception {
		List<Object> pessoa = new ArrayList<Object>();
		try {
			String sql = "SELECT * FROM tbPessoa WHERE statusDaPessoa=1 ORDER BY data_pessoa DESC;";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				String dataNow = rs.getString(9); //####-##-##
				String dia = dataNow.substring(8, 10);
				String mes = dataNow.substring(5, 7);
				String ano = dataNow.substring(0, 4);
				String novaData = dia+"/"+mes+"/"+ano;
				
				Object[] linha = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),novaData,rs.getString(10)};
				pessoa.add(linha);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return pessoa;
	}
	
	public void Alterar_pessoas(Pessoa pessoa) throws Exception {

		try {
			String sql = "UPDATE tbPessoa SET nome_pessoa=?, email_pessoa=?, empresa_pessoa=?, cargo_pessoa=?, senha_pessoa=?, caminhoDaFoto_pessoa=?, tipoDeUsuario_pessoa=?, squad_pessoa=?"
					+ "WHERE id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, pessoa.getNome());
			ps.setString(2, pessoa.getEmail());
			ps.setString(3, pessoa.getEmpresa());
			ps.setString(4, pessoa.getCargo());
			ps.setString(5, pessoa.getSenha());
			ps.setString(6, pessoa.getCaminhoDaFoto());
			ps.setString(7, pessoa.getTipoDeUsuario());
			ps.setString(8, pessoa.getSquad());
			ps.setInt(9, pessoa.getId_pessoa());
			ps.executeUpdate();
			
		} catch (Exception e) {
			throw new Exception("erro ao alterar" + e.getMessage());
		}
	}
	
	public void Excluir_pessoa(int id) throws Exception {
		try {
			String sql = "DELETE FROM informacoes WHERE id_pessoa=?;\r\n"
						+ "DELETE FROM tbPessoa WHERE id=?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, id);
			ps.executeUpdate();

		} catch (Exception e) {
			throw new Exception("erro ao excluir" + e.getMessage());
		}
	}
	
	public void EditarParaEXCLUIDO(int id) throws Exception {
		try {
			String sql = "UPDATE tbPessoa SET statusDaPessoa='1' WHERE id=?;";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			throw new Exception("erro ao mudar status" + e.getMessage());
		}
	}
	
	public String getDateNow() {
        LocalDate dataDeHoje = LocalDate.now();
        int diaAtual = dataDeHoje.getDayOfMonth();
        int mesAtual = dataDeHoje.getMonthValue();
        int anoAtual = dataDeHoje.getYear();

        return anoAtual + "-" + mesAtual + "-" + diaAtual;
    }
	
}
