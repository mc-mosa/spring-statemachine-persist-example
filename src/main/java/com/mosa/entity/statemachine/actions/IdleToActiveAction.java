package com.mosa.entity.statemachine.actions;

import com.mosa.entity.model.Entity;
import com.mosa.entity.utils.EntityConstants;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class IdleToActiveAction implements Action<String, String> {
  @Override
  public void execute(StateContext<String, String> context) {
    Entity entity = (Entity) context.getMessageHeader(EntityConstants.entityHeader);
    if (entity == null) {
      System.out.println("Action: Wrong transition?");
    } else {
      System.out.println("Action: changing the idle entity to active.. " + entity);
    }
  }
}
