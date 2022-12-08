package com.lspdpda.demo.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.lspdpda.demo.models.enums.EstadoInvestigacion;




@Entity
@Table(name="investigacion")
public class Investigacion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_investigacion;
	private String pdf_url;
	


	@ManyToOne
	@JoinColumn(name="id_agente")
	@NotNull
	private Agente agente;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estado" , columnDefinition = "VARCHAR(50) DEFAULT 'ACTIVA'")
	private EstadoInvestigacion estado;
	
	@OneToOne
	@JoinColumn(name="id_civil")
	@NotNull
	private Civil civil;

	public Long getId_investigacion() {
		return id_investigacion;
	}

	public void setId_investigacion(Long id_investigacion) {
		this.id_investigacion = id_investigacion;
	}


	public Agente getAgente() {
		return agente;
	}

	public void setAgente(Agente agente) {
		this.agente = agente;
	}

	public Civil getCivil() {
		return civil;
	}

	public void setCivil(Civil civil) {
		this.civil = civil;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPdf_url() {
		return pdf_url;
	}

	public void setPdf_url(String pdf_url) {
		this.pdf_url = pdf_url;
	}

	public EstadoInvestigacion getEstado() {
		return estado;
	}

	public void setEstado(EstadoInvestigacion estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Investigacion [id_investigacion=" + id_investigacion + ", pdf_url=" + pdf_url + ", agente=" + agente
				+ ", estado=" + estado + ", civil=" + civil + "]";
	}

	
	

	
}
