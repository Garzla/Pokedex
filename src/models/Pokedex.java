package models;

// Generated 28-ene-2015 11:14:19 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Pokedex generated by hbm2java
 */
public class Pokedex implements java.io.Serializable {

	private int id;
	private Tipo tipoByTipo2;
	private Tipo tipoByTipo;
	private String nombre;
	private int habilidad;
	private Set pokemons = new HashSet(0);

	public Pokedex() {
	}

	public Pokedex(int id, Tipo tipoByTipo, int habilidad) {
		this.id = id;
		this.tipoByTipo = tipoByTipo;
		this.habilidad = habilidad;
	}

	public Pokedex(int id, Tipo tipoByTipo2, Tipo tipoByTipo, String nombre,
			int habilidad, Set pokemons) {
		this.id = id;
		this.tipoByTipo2 = tipoByTipo2;
		this.tipoByTipo = tipoByTipo;
		this.nombre = nombre;
		this.habilidad = habilidad;
		this.pokemons = pokemons;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Tipo getTipoByTipo2() {
		return this.tipoByTipo2;
	}

	public void setTipoByTipo2(Tipo tipoByTipo2) {
		this.tipoByTipo2 = tipoByTipo2;
	}

	public Tipo getTipoByTipo() {
		return this.tipoByTipo;
	}

	public void setTipoByTipo(Tipo tipoByTipo) {
		this.tipoByTipo = tipoByTipo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getHabilidad() {
		return this.habilidad;
	}

	public void setHabilidad(int habilidad) {
		this.habilidad = habilidad;
	}

	public Set getPokemons() {
		return this.pokemons;
	}

	public void setPokemons(Set pokemons) {
		this.pokemons = pokemons;
	}

}
