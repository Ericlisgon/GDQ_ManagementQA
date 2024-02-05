package model;

public class Projeto {
	private int id;
	private String projeto;
	private String qa;
	private int storyPoints;
	private int totalDevs;
	private String sprint;
	private String dataInicio;
	private String dataFim;
	private String data_criacao;
	private Boolean fimDoProjeto;

	public Projeto() {

	}

	public Projeto(int id, String projeto, String qa, int storyPoints, int totalDevs, String sprint, String dataInicio,
			String dataFim, String data_criacao, Boolean fimDoProjeto) {
		super();
		this.id = id;
		this.projeto = projeto;
		this.qa = qa;
		this.storyPoints = storyPoints;
		this.totalDevs = totalDevs;
		this.sprint = sprint;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.data_criacao = data_criacao;
		this.fimDoProjeto = fimDoProjeto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjeto() {
		return projeto;
	}

	public void setProjeto(String projeto) {
		this.projeto = projeto;
	}

	public String getQa() {
		return qa;
	}

	public void setQa(String qa) {
		this.qa = qa;
	}
	
	public int getStoryPoints() {
		return storyPoints;
	}

	public void setStoryPoints(int storyPoints) {
		this.storyPoints = storyPoints;
	}

	public int getTotalDevs() {
		return totalDevs;
	}

	public void setTotalDevs(int totalDevs) {
		this.totalDevs = totalDevs;
	}

	public String getData_criacao() {
		return data_criacao;
	}

	public void setData_criacao(String data_criacao) {
		this.data_criacao = data_criacao;
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

	public String getSprint() {
		return sprint;
	}

	public void setSprint(String sprint) {
		this.sprint = sprint;
	}

	public Boolean getFimDoProjeto() {
		return fimDoProjeto;
	}

	public void setFimDoProjeto(Boolean fimDoProjeto) {
		this.fimDoProjeto = fimDoProjeto;
	}

}
