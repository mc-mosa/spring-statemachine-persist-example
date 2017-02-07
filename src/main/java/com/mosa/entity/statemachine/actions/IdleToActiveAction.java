package com.mosa.entity.statemachine.actions;

import com.mosa.entity.model.Entity;
import com.mosa.entity.model.EntityEvents;
import com.mosa.entity.model.EntityStates;
import com.mosa.entity.repository.EntityRepository;
import com.mosa.entity.utils.EntityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class IdleToActiveAction implements Action<EntityStates, EntityEvents> {

  @Autowired
  private EntityRepository entityRepository;

  @Override
  public void execute(StateContext<EntityStates, EntityEvents> context) {
    Entity entity = (Entity) context.getMessageHeader(EntityConstants.entityHeader);
    if (entity == null) {
      System.out.println("Action: Wrong transition?");
    } else {
      System.out.println("Action: changing the idle entity to active.. " + entity);
      // After some action, commit entity state update to database
      entity.setState(EntityStates.ACTIVE);
      entityRepository.save(entity);
    }
  }
}
