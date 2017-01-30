package com.mosa.entity.statemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;

@Configuration
public class EntityPersistHandlerConfig {

  @Autowired
  private StateMachine<String, String> entityStateMachine;

  @Autowired
  private EntityPersistStateChangeListener entityPersistStateChangeListener;

  @Bean
  public PersistStateMachineHandler persistStateMachineHandler() {
    PersistStateMachineHandler handler = new PersistStateMachineHandler(entityStateMachine);
    handler.addPersistStateChangeListener(entityPersistStateChangeListener);
    return handler;
  }
}
