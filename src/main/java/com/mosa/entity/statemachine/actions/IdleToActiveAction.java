package com.mosa.entity.statemachine.actions;

import com.mosa.entity.model.Entity;
import com.mosa.entity.utils.EntityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class IdleToActiveAction implements Action<String, String> {

  private final static Logger logger = LoggerFactory.getLogger(IdleToActiveAction.class);

  @Override
  public void execute(StateContext<String, String> context) {
    Entity entity = (Entity) context.getMessageHeader(EntityConstants.entityHeader);
    if (entity == null) {
      logger.debug("Action: Wrong transition?");
    } else {
      logger.debug("Action: changing the idle entity to active.. {}", entity);
    }
  }
}
