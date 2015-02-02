package models;

// Generated 28-ene-2015 11:14:19 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Entrenador generated by hbm2java
 */
public class Entrenador implements java.io.Serializable {

	private Integer id;
	private String nombre;
	private Set pokemons = new HashSet(0);

	public Entrenador() {
	}

	public Entrenador(String nombre, Set pokemons) {
		this.nombre = nombre;
		this.pokemons = pokemons;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set getPokemons() {
		return this.pokemons;
	}

	public void setPokemons(Set pokemons) {
		this.pokemons = pokemons;
	}

}
