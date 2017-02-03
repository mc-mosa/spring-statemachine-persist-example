package com.mosa.entity.statemachine.guards;

import com.mosa.entity.model.Entity;
import com.mosa.entity.utils.EntityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

@Component
public class IdleToActiveGuard implements Guard<String, String> {

  private final static Logger logger = LoggerFactory.getLogger(IdleToActiveGuard.class);

  @Override
  public boolean evaluate(StateContext<String, String> context) {
    Entity entity = (Entity) context.getMessageHeader(EntityConstants.entityHeader);
    if (entity == null) {
      logger.debug("Guard: Wrong transition?");
    } else {
      logger.debug("Guard: protecting the transition.. {}", entity);
    }
    return entity != null;
  }
}
