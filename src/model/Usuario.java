package model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Usuario {
	private int id;
	private String nome;
	private String cpf;
	private String email;
	@NotNull(message = "Campo tipo não pode estar vazio!")
	private TipoU tipo;
	@NotEmpty(message = "Campo login não pode estar vazio!")
	private String login;
	@NotEmpty(message = "Campo senha não pode estar vazio!")
	private String senha;

	public Usuario() {

	}

	public Usuario(int id, String nome, String cpf, String email, TipoU tipo, String login, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.tipo = tipo;
		this.login = login;
		this.senha = senha;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public TipoU getTipo() {
		return tipo;
	}

	public void setTipo(TipoU tipo) {
		this.tipo = tipo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", tipo=" + tipo + ", login=" + login
				+ ", senha=" + senha + "]";
	}
}
