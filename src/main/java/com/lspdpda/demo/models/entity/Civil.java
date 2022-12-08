package com.lspdpda.demo.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="civil")
public class Civil implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_civil;
	@NotEmpty
	private String nombre;
	@NotEmpty
	private String apellido;
	@NotEmpty
	private String fecha_nacimiento;
	@NotEmpty
	private String genero;
	private Boolean investigacion;
	private Boolean licencia_retirada;
	
	public Long getId_civil() {
		return id_civil;
	}
	public void setId_civil(Long id_civil) {
		this.id_civil = id_civil;
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
	public Boolean getInvestigacion() {
		return investigacion;
	}
	public void setInvestigacion(Boolean investigacion) {
		this.investigacion = investigacion;
	}

	public Boolean getLicencia_retirada() {
		return licencia_retirada;
	}
	public void setLicencia_retirada(Boolean licencia_retirada) {
		this.licencia_retirada = licencia_retirada;
	}
	
	@Override
	public String toString() {
		return "Civil [id_civil=" + id_civil + ", nombre=" + nombre + ", apellido=" + apellido + ", fecha_nacimiento="
				+ fecha_nacimiento + ", genero=" + genero + ", investigacion=" + investigacion + ", licencia_retirada="
				+ licencia_retirada + "]";
	}
	

	

}
