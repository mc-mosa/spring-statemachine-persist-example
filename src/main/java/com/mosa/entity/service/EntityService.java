package com.mosa.entity.service;

import com.google.common.collect.Lists;
import com.mosa.entity.model.Entity;
import com.mosa.entity.model.EntityEvents;
import com.mosa.entity.repository.EntityRepository;
import com.mosa.entity.utils.EntityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntityService {

  @Autowired
  private EntityRepository entityRepository;

  @Autowired
  private PersistStateMachineHandler persistStateMachineHandler;

  public List<Entity> getEntities() {
    return Lists.newArrayList(entityRepository.findAll());
  }

  public Entity getEntity(Long id) {
    return entityRepository.findOne(id);
  }

  public Entity createEntity(Entity entity) {
    return entityRepository.save(entity);
  }

  public Boolean updateState(Long id, EntityEvents event) {
    Entity entity = entityRepository.findOne(id);
    return persistStateMachineHandler.handleEventWithState(
        MessageBuilder.withPayload(event.name()).setHeader(EntityConstants.entityHeader, entity).build(),
        entity.getState().name()
    );
  }
}
