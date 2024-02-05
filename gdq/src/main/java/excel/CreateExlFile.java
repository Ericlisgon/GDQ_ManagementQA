package excel;
//Baixe o jar aqui "http://poi.apache.org/download.html"

import java.awt.EventQueue;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import dao.DAO;
import util.ConnectionFactory;
import view.LoginView;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;

public class CreateExlFile { // classe que gera o arquivo

	public DAO dao;
	private Connection conn;
	private PreparedStatement ps; // executa query
	private ResultSet rs; 

	public CreateExlFile() throws Exception {
		dao = new DAO();
		try {
			conn = ConnectionFactory.getConnection();
		} catch (Exception e) {
			throw new Exception("erro" + e.getMessage());
		}
	}

//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					executeExcel();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	public void executeExcel() {
		try {
			// local do arquivo
			String filename = "C:/MRT_Evidencia/NewExcelFile.xls";
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("FirstSheet");
			// criando as linhas
			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("protocolo");
			rowhead.createCell(1).setCellValue("codigonumerico");
			// definindo seus valores

			
			//HSSFRow row = sheet.createRow((short) 1);
			
//			row.createCell(0).setCellValue(rs.getInt(1));
//			row.createCell(1).setCellValue(rs.getString(2));
			buscarPorStatus(sheet);
			// por exemplo protocolo.getProtocolo();

			FileOutputStream fileOut = new FileOutputStream(filename);
			workbook.write(fileOut);
			fileOut.close();
			System.out.println("Seu arquivo excel foi gerado!");

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	public List<Object> buscarPorStatus(HSSFSheet sheet) throws SQLException, Exception {
		List<Object> cenarios = new ArrayList<Object>();
		try {
			String sql = "SELECT * FROM tbCenarios";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			
			
			int value=1;
			while (rs.next()) {
				HSSFRow row = sheet.createRow((short) value);
				
				row.createCell(0).setCellValue(rs.getString(1));
				row.createCell(1).setCellValue(rs.getString(2));
				row.createCell(2).setCellValue(rs.getString(3));
				row.createCell(3).setCellValue(rs.getString(4));
				row.createCell(4).setCellValue(rs.getString(5));
				row.createCell(5).setCellValue(rs.getString(6));
				row.createCell(6).setCellValue(rs.getString(7));
				row.createCell(8).setCellValue(rs.getString(8));
				row.createCell(9).setCellValue(rs.getString(9));
				row.createCell(10).setCellValue(rs.getString(10));
				row.createCell(11).setCellValue(rs.getString(11));
				row.createCell(12).setCellValue(rs.getString(12));

				value++;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return cenarios;
	}

}