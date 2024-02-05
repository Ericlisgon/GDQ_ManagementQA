package model;

public class ConfiguracoesCenarios {
	public int limparCampos;
	public int manterCampoDescricao;
	public int opcaoSimRegressivo;
	public int opcaoSimAutomatizados;
	public int abordagem;
	public int criticidade;
	public int status;
	public int duplicitadeDeCenarios;
	public String tema;
	
	public ConfiguracoesCenarios() {
		
	}
	
	public ConfiguracoesCenarios(int limparCampos, int manterCampoDescricao, int opcaoSimRegressivo,
			int opcaoSimAutomatizados, int abordagem, int criticidade, int status, int duplicitadeDeCenarios, String tema) {
		super();
		this.limparCampos = limparCampos;
		this.manterCampoDescricao = manterCampoDescricao;
		this.opcaoSimRegressivo = opcaoSimRegressivo;
		this.opcaoSimAutomatizados = opcaoSimAutomatizados;
		this.abordagem = abordagem;
		this.criticidade = criticidade;
		this.status = status;
		this.duplicitadeDeCenarios = duplicitadeDeCenarios;
		this.tema = tema;
	}
	
	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public int getLimparCampos() {
		return limparCampos;
	}
	public void setLimparCampos(int limparCampos) {
		this.limparCampos = limparCampos;
	}
	public int getManterCampoDescricao() {
		return manterCampoDescricao;
	}
	public void setManterCampoDescricao(int manterCampoDescricao) {
		this.manterCampoDescricao = manterCampoDescricao;
	}
	public int getOpcaoSimRegressivo() {
		return opcaoSimRegressivo;
	}
	public void setOpcaoSimRegressivo(int opcaoSimRegressivo) {
		this.opcaoSimRegressivo = opcaoSimRegressivo;
	}
	public int getOpcaoSimAutomatizados() {
		return opcaoSimAutomatizados;
	}
	public void setOpcaoSimAutomatizados(int opcaoSimAutomatizados) {
		this.opcaoSimAutomatizados = opcaoSimAutomatizados;
	}
	public int getAbordagem() {
		return abordagem;
	}
	public void setAbordagem(int abordagem) {
		this.abordagem = abordagem;
	}
	public int getCriticidade() {
		return criticidade;
	}
	public void setCriticidade(int criticidade) {
		this.criticidade = criticidade;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public int getDuplicitadeDeCenarios() {
		return duplicitadeDeCenarios;
	}

	public void setDuplicitadeDeCenarios(int duplicitadeDeCenarios) {
		this.duplicitadeDeCenarios = duplicitadeDeCenarios;
	}

}
