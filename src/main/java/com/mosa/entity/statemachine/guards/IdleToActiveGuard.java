package com.mosa.entity.statemachine.guards;

import com.mosa.entity.model.Entity;
import com.mosa.entity.model.EntityEvents;
import com.mosa.entity.model.EntityStates;
import com.mosa.entity.utils.EntityConstants;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

@Component
public class IdleToActiveGuard implements Guard<EntityStates, EntityEvents> {
  @Override
  public boolean evaluate(StateContext<EntityStates, EntityEvents> context) {
    Entity entity = (Entity) context.getMessageHeader(EntityConstants.entityHeader);
    if (entity == null) {
      System.out.println("Guard: Wrong transition?");
    } else {
      System.out.println("Guard: protecting the transition.. " + entity);
    }
    return entity != null;
  }
}
