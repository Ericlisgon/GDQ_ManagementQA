package model;


public class Pessoa {
	
	private int id_pessoa;
	private String nome;
	private String email;
	private String empresa;
	private String cargo;
	private String senha;
	private String caminhoDaFoto;
	private String tipoDeUsuario;
	private String data_pessoa;
	private String squad;
	
	public Pessoa() {
		
	}
	
	public Pessoa(int id_pessoa, String nome, String email, String empresa, String cargo, String senha, String caminhoDaFoto, String tipoDeUsuario, String data_pessoa, String squad) {
		super();
		this.id_pessoa = id_pessoa;
		this.nome = nome;
		this.email = email;
		this.empresa = empresa;
		this.cargo = cargo;
		this.senha = senha;
		this.caminhoDaFoto = caminhoDaFoto;
		this.tipoDeUsuario = tipoDeUsuario;
		this.data_pessoa = data_pessoa;
		this.squad = squad;
	}

	public int getId_pessoa() {
		return id_pessoa;
	}

	public void setId_pessoa(int id_pessoa) {
		this.id_pessoa = id_pessoa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCaminhoDaFoto() {
		return caminhoDaFoto;
	}

	public void setCaminhoDaFoto(String caminhoDaFoto) {
		this.caminhoDaFoto = caminhoDaFoto;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getTipoDeUsuario() {
		return tipoDeUsuario;
	}

	public void setTipoDeUsuario(String tipoDeUsuario) {
		this.tipoDeUsuario = tipoDeUsuario;
	}

	public String getData_pessoa() {
		return data_pessoa;
	}

	public void setData_pessoa(String data_pessoa) {
		this.data_pessoa = data_pessoa;
	}

	public String getSquad() {
		return squad;
	}

	public void setSquad(String squad) {
		this.squad = squad;
	}
	
}
