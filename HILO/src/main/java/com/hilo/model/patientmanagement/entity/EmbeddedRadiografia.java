package com.hilo.model.patientmanagement.entity;

import com.sun.istack.NotNull;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;


@Embeddable
public class EmbeddedRadiografia implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull
  private String cfpaziente;

  @NotNull
  private Integer numero;

  public EmbeddedRadiografia() {
  }

  public String getCfpaziente() {
    return cfpaziente;
  }

  public void setCfpaziente(String cfpaziente) {
    this.cfpaziente = cfpaziente;
  }

  public int getNumero() {
    return numero;
  }

  public void setNumero(Integer numero) {
    this.numero = numero;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmbeddedRadiografia that = (EmbeddedRadiografia) o;
    return numero == that.numero
            && Objects.equals(cfpaziente, that.cfpaziente);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cfpaziente, numero);
  }
}
