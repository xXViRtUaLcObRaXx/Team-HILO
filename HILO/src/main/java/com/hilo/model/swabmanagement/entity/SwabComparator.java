package com.hilo.model.swabmanagement.entity;

import com.hilo.controller.ErrorController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Questa classe stabilisce la relazione d'ordine tra i tamponi che sono all'
 * interno della coda dei tamponi da effettuare.
 */
@Component
public class SwabComparator implements Comparator<Swab> {
  @Autowired
  Ryan ryan;
  @Autowired
  private EffettuapManager epMan;
  @Autowired
  private EffettuaAsManager asMan;
  @Autowired
  private ErrorController error;


  /**
   * Questo metodo viene richiamato ad ogni aggiunta di un nuovo tampone su tutti i tamponi
   * per poter stabilire quale deve essere schedulato per prima.
   *
   * @param o1 primo tampone da effettuare
   *
   * @param o2 secondo tampone da effettuare
   *
   * @return 1 se o1 viene prima di o2, -1 se o1 viene dopo o2, 0 altrimenti
   */
  @Override
  public int compare(Swab o1, Swab o2) {
    System.out.println(asMan);
    System.out.println(epMan);
    double proba1 = Double.MIN_VALUE;
    double proba2 = Double.MIN_VALUE;
    boolean interno1 = false;
    boolean interno2 = false;

    //se sono interni allora avro' una probabilita' di positivita'
    if (o1.getIsInterno()) {
      proba1 = ryan.getProba(o1);
      interno1 = true;
    }

    if (o2.getIsInterno()) {
      proba2 = ryan.getProba(o2);
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
    EffettuaAs as1 = asMan.findEffettuaAsByIdTampone(o1.getId());
    EffettuaAs as2 = asMan.findEffettuaAsByIdTampone(o2.getId());
    //estraggo le date dai tamponi
    String timestamp1 = null;
    String timestamp2 = null;

    //se non trovo un tampone in effettuaAs allora devo cercare in EffettuaP
    try {
      timestamp1 = as1.getTimestamp();

    } catch (NullPointerException e) {
      EffettuaP p = epMan.findEffettuapByIdTampone(o1.getId());
      timestamp1 = p.getTimestamp();
    }

    try {
      timestamp2 = as2.getTimestamp();
    } catch (NullPointerException e) {
      EffettuaP p = epMan.findEffettuapByIdTampone(o2.getId());
      timestamp2 = p.getTimestamp();
    }

    Date t1 = null;
    Date t2 = null;
    try {
      t1 = f.parse(timestamp1);
      t2 = f.parse(timestamp2);
    } catch (ParseException e) {
      error.manageError(e);
    }
    if (t1.after(t2)) {
      return -1;
    } else if (t1.before(t2)) {
      return 1;
    } else {
      return 0;
    }
  }

  
  private static final double TRESHOLD = 0.8;
}
