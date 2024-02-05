package dao;

import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class DAO_configuracoes extends DAO {
	private String escolhaTema;

	
	public DAO_configuracoes() throws Exception {
		super();
		DAO dao = new DAO();
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
	
	public void definirTema() {
		escolhaTema = recuperarTema();
		try {
			if (escolhaTema.equals("Dark")) {
				UIManager.setLookAndFeel("com.formdev.flatlaf.themes.FlatMacDarkLaf");
			} else if (escolhaTema.equals("Light")) {
				UIManager.setLookAndFeel("com.formdev.flatlaf.themes.FlatMacLightLaf");	
			}else if (escolhaTema.equals("IntelJ")) {
				UIManager.setLookAndFeel("com.formdev.flatlaf.FlatIntelliJLaf");	
			}else if (escolhaTema.equals("Darcla")) {
				UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarculaLaf");	
			}else if (escolhaTema.equals("Purple")) {
				UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme");	
			}else if (escolhaTema.equals("Nature")) {
				UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatGradiantoNatureGreenIJTheme");	
			}else if (escolhaTema.equals("Gruv")) {
				UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatGruvboxDarkSoftIJTheme");	
			}else if (escolhaTema.equals("lite")) {
				UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme");	
			}else if (escolhaTema.equals("Solar")) {
				UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatSolarizedDarkIJTheme");	
			}else if (escolhaTema.equals("Nimbus")) {
				for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						javax.swing.UIManager.setLookAndFeel(info.getClassName());
						break;
					}
					UIManager.put("nimbusOrange", new Color(0, 204, 0));
				}
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Tema não definido");
		}
	}
	
	public void definirTema(String escolhaTema) {
		try {
			if (escolhaTema.equals("Dark")) {
				UIManager.setLookAndFeel("com.formdev.flatlaf.themes.FlatMacDarkLaf");
			} else if (escolhaTema.equals("Light")) {
				UIManager.setLookAndFeel("com.formdev.flatlaf.themes.FlatMacLightLaf");
			} else if (escolhaTema.equals("Nimbus")) {
				for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						javax.swing.UIManager.setLookAndFeel(info.getClassName());
						break;
					}
					UIManager.put("nimbusOrange", new Color(0, 204, 0));
				}
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Tema não definido");
		}
	}

}
