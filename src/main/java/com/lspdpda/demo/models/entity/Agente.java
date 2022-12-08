package com.lspdpda.demo.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="agente")
public class Agente implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_agente;
	@NotEmpty
	private String nombre;
	@NotEmpty
	private String apellido;
	@NotEmpty
	private String fecha_nacimiento;
	@NotEmpty
	private String genero;
	@NotEmpty
	private String rango;
	private Boolean expediente;
	private Boolean suspendidos;
	public Long getId_agente() {
		return id_agente;
	}
	public void setId_agente(Long id_agente) {
		this.id_agente = id_agente;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getFecha_nacimiento() {
		return fecha_nacimiento;
	}
	public void setFecha_nacimiento(String fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getRango() {
		return rango;
	}
	public void setRango(String rango) {
		this.rango = rango;
	}
	public Boolean getExpediente() {
		return expediente;
	}
	public void setExpediente(Boolean expediente) {
		this.expediente = expediente;
	}
	public Boolean getSuspendidos() {
		return suspendidos;
	}
	public void setSuspendidos(Boolean suspendidos) {
		this.suspendidos = suspendidos;
	}
	@Override
	public String toString() {
		return "Agente [id_agente=" + id_agente + ", nombre=" + nombre + ", apellido=" + apellido
				+ ", fecha_nacimiento=" + fecha_nacimiento + ", genero=" + genero + ", rango=" + rango + ", expediente="
				+ expediente + ", suspendidos=" + suspendidos + "]";
	}

}