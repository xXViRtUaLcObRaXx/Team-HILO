package com.hilo.controller;

import com.hilo.model.healthworkermanagement.entity.HealthWorker;
import com.hilo.model.healthworkermanagement.entity.HealthWorkerManagement;
import com.hilo.model.swabmanagement.entity.Swab;
import com.hilo.model.swabmanagement.entity.SwabManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HealthController {
  @Autowired
  private HealthWorkerManagement hwm;

  @Autowired
  private SwabManager sm;


  public List<HealthWorker> getAll() {
    return hwm.findAll();
  }

  public Swab inserisciRisultato(Integer idTampone, String risultato) {
    Swab s = sm.findById(idTampone);
    s.setRisultato(risultato);
    sm.updateSwab(s);
    return s;
  }
}
