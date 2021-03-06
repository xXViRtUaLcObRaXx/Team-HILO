package com.hilo.model.patientmanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(EmbeddedPagina.class)
public class Pagina {
  @Id
  @Column(name = "cfpaziente")
  private String cfPaziente;

  @Id
  @Column
  private Integer numero;

  @Column
  private String note;
  @Column
  private Double temperatura;
  @Column
  private String sintomi;

  public Pagina() {}

  public Pagina(String cfPaziente, Integer numero, String note, 
      Double temperatura, String sintomi) {
    this.cfPaziente = cfPaziente;
    this.numero = numero;
    this.note = note;
    this.temperatura = temperatura;
    this.sintomi = sintomi;
  }

  public String getCfPaziente() {
    return cfPaziente;
  }

  public void setCfPaziente(String cfPaziente) {
    this.cfPaziente = cfPaziente;
  }

  public Integer getNumero() {
    return numero;
  }

  public void setNumero(Integer numero) {
    this.numero = numero;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Double getTemperatura() {
    return temperatura;
  }

  public void setTemperatura(Double temperatura) {
    this.temperatura = temperatura;
  }

  public String getSintomi() {
    return sintomi;
  }

  public void setSintomi(String sintomi) {
    this.sintomi = sintomi;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((cfPaziente == null) ? 0 : cfPaziente.hashCode());
    result = prime * result + ((note == null) ? 0 : note.hashCode());
    result = prime * result + ((numero == null) ? 0 : numero.hashCode());
    result = prime * result + ((sintomi == null) ? 0 : sintomi.hashCode());
    result = prime * result + ((temperatura == null) ? 0 : temperatura.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Pagina other = (Pagina) obj;
    if (cfPaziente == null) {
      if (other.cfPaziente != null) {
        return false;
      }
    } else if (!cfPaziente.equals(other.cfPaziente)) {
      return false;
    }
    if (note == null) {
      if (other.note != null) {
        return false;
      }
    } else if (!note.equals(other.note)) {
      return false;
    }
    if (numero == null) {
      if (other.numero != null) {
        return false;
      }
    } else if (!numero.equals(other.numero)) {
      return false;
    }
    if (sintomi == null) {
      if (other.sintomi != null) {
        return false;
      }
    } else if (!sintomi.equals(other.sintomi)) {
      return false;
    }
    if (temperatura == null) {
      if (other.temperatura != null) {
        return false;
      }
    } else if (!temperatura.equals(other.temperatura)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Pagina {cfPaziente=" + cfPaziente + ", note=" + note + ", numero=" + numero
            + ", sintomi=" + sintomi + ", temperatura=" + temperatura + "}";
  }

}
