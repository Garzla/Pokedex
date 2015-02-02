package models;

// Generated 28-ene-2015 11:14:19 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Pokemon generated by hbm2java
 */
public class Pokemon implements java.io.Serializable {

	private Integer id;
	private Entrenador entrenador;
	private Habilidad habilidad;
	private Pokedex pokedex;
	private Integer nivel;
	private String mote;
	private Set movimientos = new HashSet(0);

	public Pokemon() {
	}

	public Pokemon(Entrenador entrenador, Habilidad habilidad, Pokedex pokedex,
			Integer nivel, String mote, Set movimientos) {
		this.entrenador = entrenador;
		this.habilidad = habilidad;
		this.pokedex = pokedex;
		this.nivel = nivel;
		this.mote = mote;
		this.movimientos = movimientos;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Entrenador getEntrenador() {
		return this.entrenador;
	}

	public void setEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;
	}

	public Habilidad getHabilidad() {
		return this.habilidad;
	}

	public void setHabilidad(Habilidad habilidad) {
		this.habilidad = habilidad;
	}

	public Pokedex getPokedex() {
		return this.pokedex;
	}

	public void setPokedex(Pokedex pokedex) {
		this.pokedex = pokedex;
	}

	public Integer getNivel() {
		return this.nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public String getMote() {
		return this.mote;
	}

	public void setMote(String mote) {
		this.mote = mote;
	}

	public Set getMovimientos() {
		return this.movimientos;
	}

	public void setMovimientos(Set movimientos) {
		this.movimientos = movimientos;
	}

}