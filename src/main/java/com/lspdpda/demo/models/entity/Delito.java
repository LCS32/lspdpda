package com.lspdpda.demo.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "delito")
public class Delito implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_delito;
	@NotEmpty
	private String fecha;
	@NotEmpty
	private String delito_cometido;

	private int multa;
	@NotEmpty
	private String sentencia;
	@NotEmpty
	private String descripcion;

	private Boolean archivado;

	private String foto_archivo_url;

	private String foto_inventario_url;

	@ManyToOne
	@JoinColumn(name = "id_agente")
	@NotNull
	private Agente agente;

	@ManyToOne
	@JoinColumn(name = "id_civil")
	@NotNull
	private Civil civil;

	public Long getId_delito() {
		return id_delito;
	}

	public void setId_delito(Long id_delito) {
		this.id_delito = id_delito;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getDelito_cometido() {
		return delito_cometido;
	}

	public void setDelito_cometido(String delito_cometido) {
		this.delito_cometido = delito_cometido;
	}

	public int getMulta() {
		return multa;
	}

	public void setMulta(int multa) {
		this.multa = multa;
	}

	public String getSentencia() {
		return sentencia;
	}

	public void setSentencia(String sentencia) {
		this.sentencia = sentencia;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getArchivado() {
		return archivado;
	}

	public void setArchivado(Boolean archivado) {
		this.archivado = archivado;
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

	public String getFoto_archivo_url() {
		return foto_archivo_url;
	}

	public void setFoto_archivo_url(String foto_archivo_url) {
		this.foto_archivo_url = foto_archivo_url;
	}

	public String getFoto_inventario_url() {
		return foto_inventario_url;
	}

	public void setFoto_inventario_url(String foto_inventario_url) {
		this.foto_inventario_url = foto_inventario_url;
	}

	@Override
	public String toString() {
		return "Delito [id_delito=" + id_delito + ", fecha=" + fecha + ", delito_cometido=" + delito_cometido
				+ ", multa=" + multa + ", sentencia=" + sentencia + ", descripcion=" + descripcion + ", archivado="
				+ archivado + ", foto_archivo_url=" + foto_archivo_url + ", foto_inventario_url=" + foto_inventario_url
				+ ", agente=" + agente + ", civil=" + civil + "]";
	}

}
