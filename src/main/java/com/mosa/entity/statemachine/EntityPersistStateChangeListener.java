package com.mosa.entity.statemachine;

import com.mosa.entity.model.Entity;
import com.mosa.entity.model.EntityStates;
import com.mosa.entity.repository.EntityRepository;
import com.mosa.entity.utils.EntityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler.PersistStateChangeListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

@Component
public class EntityPersistStateChangeListener implements PersistStateChangeListener {

  @Autowired
  private EntityRepository entityRepository;

  @Override
  public void onPersist(State<String, String> state,
                        Message<String> message,
                        Transition<String, String> transition,
                        StateMachine<String, String> stateMachine) {
    if (message != null && message.getHeaders().containsKey(EntityConstants.entityHeader)) {
      Entity entity = message.getHeaders().get(EntityConstants.entityHeader, Entity.class);
      entity.setState(EntityStates.valueOf(state.getId()));
      System.out.println("Persisting: the new entity.. " + entity);
      entityRepository.save(entity);
    }
  }
}
