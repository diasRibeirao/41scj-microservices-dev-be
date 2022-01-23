package br.com.fiap.microservices.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "menus")
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;
	
	private String descricao;
	
	private String icone;
	
	private String rota;
	
	private Integer ordem;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "menu_roles", joinColumns = @JoinColumn(name = "menu_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<Role>();

	public Menu() {

	}

	public Menu(String nome, String descricao, String icone, String rota, Integer ordem, Set<Role> roles) {
		this.nome = nome;
		this.descricao = descricao;
		this.icone = icone;
		this.rota = rota;
		this.ordem = ordem;
		this.roles = roles;
	}
	
	public Menu(Long id, String nome, String descricao, String icone, String rota, Integer ordem, Set<Role> roles) {
		this(nome, descricao, icone, rota, ordem, roles);
		this.id = id;
	}

	

}