package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Pessoa;
import model.Projeto;
import util.ConnectionFactory;

public class DAO_projeto {

	private Connection conn;
	private PreparedStatement ps; // executa query
	private ResultSet rs; // cria uma tabela
	public Projeto projeto;
	public Pessoa pessoa;
	public DAO dao;

	public DAO_projeto() throws Exception {
		try {
			conn = ConnectionFactory.getConnection();
		} catch (Exception e) {
			throw new Exception("erro" + e.getMessage());
		}
	}


	public void Alterar_informacoes(Projeto projeto) throws Exception {
		try {
			String sql = "update informacoes set projeto_projeto=?, qa_projeto=?, storyPoints_projeto=?, totalDevs_projeto=?, sprint_projeto=?, finalizacao_projeto=?, data_inicio_projeto=?, data_fim_projeto=?"
					+ "where id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, projeto.getProjeto());
			ps.setString(2, projeto.getQa());
			ps.setInt(3, projeto.getStoryPoints());
			ps.setInt(4, projeto.getTotalDevs());
			ps.setString(5, projeto.getSprint());
			ps.setBoolean(6, projeto.getFimDoProjeto());
			ps.setString(7, projeto.getDataInicio());
			ps.setString(8, projeto.getDataFim());
			ps.setInt(9, projeto.getId());
			ps.executeUpdate();

		} catch (Exception e) {
			throw new Exception("erro ao alterar" + e.getMessage());
		}
	}

	public void Excluir_informacao(int id) throws Exception {

		try {
			String sql = "DELETE FROM informacoes WHERE id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();

		} catch (Exception e) {
			throw new Exception("erro ao excluir" + e.getMessage());
		}
	}
	
	public String converterData(java.util.Date value) {
		String reform = value.toString();
		String dia = reform.substring(8, 10);
		String mes = reform.substring(5, 7);
		String ano = reform.substring(0, 4);
		String novaData = dia + "/" + mes + "/" + ano;
		
		return novaData;
	}
	
	public String getDateNow() {
        LocalDate dataDeHoje = LocalDate.now();
        int diaAtual = dataDeHoje.getDayOfMonth();
        int mesAtual = dataDeHoje.getMonthValue();
        int anoAtual = dataDeHoje.getYear();

        return anoAtual + "-" + mesAtual + "-" + diaAtual;
    }
	

}
