package com.hilo.model.swabmanagement.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Questa classe rappresenta una coda di tamponi da schedulare.
 */
@Component
public class Pqueue {

  public Pqueue() {
    queue = new ArrayList<>();
  }

  /**
   * Questo metodo aggiunge un tampone alla coda dei tamponi da analizzare.
   *
   * @param s il tampone da aggiungere
   */
  public void add(Swab s) {

    if (queue.size() == 0) {
      queue.add(s);
      return;
    }
    for (int i = 0; i < queue.size(); i++) {

      if (check(s, queue.get(i)) > 0) {
        queue.add(i, s);
        return;
      }
    }
    queue.add(s);
  }

  /**
   * Restituisce la lunghezza della coda dei tamponi.
   *
   * @return la lunghezza della coda
   */
  public int size() {
    return queue.size();
  }

  /**
   * Restituisce il primo tampone della coda.
   *
   * @return il tampone in testa
   */
  public Swab getTop() {
    Swab s = queue.get(0);
    queue.remove(s);
    return s;
  }

  /**
   * Esegue la comparazione tra due tamponi per capire quale schedulare prima.
   *
   * @param o1 primo tampone
   *
   * @param o2 secondo tampone
   *
   * @return 1 se il o1 viene prima di o2, -1 se o1 viene dopo o2, 0 altrimenti
   */
  public int check(Swab o1, Swab o2) {

    double proba1 = Double.MIN_VALUE;
    double proba2 = Double.MIN_VALUE;
    boolean interno1 = false;
    boolean interno2 = false;

    //se sono interni allora avro' una probabilita' di positivita'
    if (o1.getIsInterno()) {
      proba1 = epm.findEffettuapByIdTampone(o1.getId()).getGravity();
      interno1 = true;
    }

    if (o2.getIsInterno()) {
      proba2 = epm.findEffettuapByIdTampone(o2.getId()).getGravity();
      interno2 = true;
    }

    //se sono entrambi interni
    if (interno1 && interno2) {

      if (proba1 > proba2) {
        return 1;
      } else if (proba1 < proba2) {
        return -1;
      } else {
        return 0;
      }
    } else if (!interno1 && !interno2) { //se sono entrambi esterni devo gestire le date
      return compareDates(o1, o2);

    } else if (interno1 && !interno2) {

      if (proba1 > TRESHOLD) {
        return 1;
      }
      return compareDates(o1, o2);

    } else {

      if (proba2 > TRESHOLD) {
        return -1;
      }
      return compareDates(o1, o2);
    }

  }

  /**
   * Permette di fare un confronto sulle date dei tamponi.
   *
   * @param o1 primo tampone
   *
   * @param o2 secondo tampone
   *
   * @return 1 se o1 viene prima di o2, -1 se o1 viene dopo o2, 0 altrimenti
   */
  private int compareDates(Swab o1, Swab o2) {
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
    EffettuaAs as1 = eam.findEffettuaAsByIdTampone(o1.getId());
    EffettuaAs as2 = eam.findEffettuaAsByIdTampone(o2.getId());
    //estraggo le date dai tamponi
    String timestamp1 = null;
    String timestamp2 = null;

    //se non trovo un tampone in effettuaAs allora devo cercare in EffettuaP
    try {
      timestamp1 = as1.getTimestamp();

    } catch (NullPointerException e) {
      EffettuaP p = epm.findEffettuapByIdTampone(o1.getId());
      timestamp1 = p.getTimestamp();
    }

    try {
      timestamp2 = as2.getTimestamp();
    } catch (NullPointerException e) {
      EffettuaP p = epm.findEffettuapByIdTampone(o2.getId());
      timestamp2 = p.getTimestamp();
    }

    Date t1 = null;
    Date t2 = null;
    try {
      t1 = f.parse(timestamp1);
      t2 = f.parse(timestamp2);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    if (t1.after(t2)) {
      return -1;
    } else if (t1.before(t2)) {
      return 1;
    } else {
      return 0;
    }
  }

  @Autowired
  private EffettuaAsManager eam;
  @Autowired
  private EffettuapManager epm;
  private static final double TRESHOLD = 0.49;
  private ArrayList<Swab> queue;
}
