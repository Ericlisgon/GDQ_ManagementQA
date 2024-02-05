package model;

public class Cenarios {
	public int codTeste;
	public String historia;
	public String descricao;
	public String abordagem;
	public String prioridade;
	public String escopoDeRegressao;
	public String automacao;
	public String status;
	public boolean bug;
	public String linkDoBug;
	public String motivoDaNaoExecusao;
	public String dataInicio;
	public String dataFim;
	
	
	public Cenarios() {
		
	}
	
	public Cenarios(int codTeste, String historia, String descricao, String abordagem, String prioridade,
			String escopoDeRegressao, String automacao, String status, boolean bug, String linkDoBug,
			String motivoDaNaoExecusao,String dataInicio, String dataFim) {
		super();
		this.codTeste = codTeste;
		this.historia = historia;
		this.descricao = descricao;
		this.abordagem = abordagem;
		this.prioridade = prioridade;
		this.escopoDeRegressao = escopoDeRegressao;
		this.automacao = automacao;
		this.status = status;
		this.bug = bug;
		this.linkDoBug = linkDoBug;
		this.motivoDaNaoExecusao = motivoDaNaoExecusao;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
	}

	public int getCodTeste() {
		return codTeste;
	}


	public void setCodTeste(int codTeste) {
		this.codTeste = codTeste;
	}


	public String getHistoria() {
		return historia;
	}


	public void setHistoria(String historia) {
		this.historia = historia;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public String getAbordagem() {
		return abordagem;
	}


	public void setAbordagem(String abordagem) {
		this.abordagem = abordagem;
	}


	public String getPrioridade() {
		return prioridade;
	}


	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}


	public String getEscopoDeRegressao() {
		return escopoDeRegressao;
	}


	public void setEscopoDeRegressao(String escopoDeRegressao) {
		this.escopoDeRegressao = escopoDeRegressao;
	}


	public String getAutomacao() {
		return automacao;
	}


	public void setAutomacao(String automacao) {
		this.automacao = automacao;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public boolean getBug() {
		return bug;
	}


	public void setBug(boolean bug) {
		this.bug = bug;
	}


	public String getLinkDoBug() {
		return linkDoBug;
	}


	public void setLinkDoBug(String linkDoBug) {
		this.linkDoBug = linkDoBug;
	}


	public String getMotivoDaNaoExecusao() {
		return motivoDaNaoExecusao;
	}


	public void setMotivoDaNaoExecusao(String motivoDaNaoExecusao) {
		this.motivoDaNaoExecusao = motivoDaNaoExecusao;
	}
	
	public String getDataInicio() {
		return dataInicio;
	}


	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	
	public String getDataFim() {
		return dataFim;
	}


	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}
	
}
