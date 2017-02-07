package com.mosa.entity.service;

import com.google.common.collect.Lists;
import com.mosa.entity.model.Entity;
import com.mosa.entity.model.EntityEvents;
import com.mosa.entity.model.EntityStates;
import com.mosa.entity.repository.EntityRepository;
import com.mosa.entity.utils.EntityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EntityService {

  @Autowired
  private EntityRepository entityRepository;

  @Autowired
  private StateMachineFactory<EntityStates, EntityEvents> entityStateMachineFactory;

  private Map<String, StateMachine<EntityStates, EntityEvents>> stateMachines = new HashMap<>();

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
    final String machineId = "entity" + entity.getId();
    StateMachine<EntityStates, EntityEvents> stateMachine;
    if (stateMachines.containsKey(machineId)) {
      stateMachine = stateMachines.get(machineId);
    } else {
      stateMachine = entityStateMachineFactory.getStateMachine(machineId);
      stateMachines.put(machineId, stateMachine);
    }
    return stateMachine.sendEvent(MessageBuilder.withPayload(event).setHeader(EntityConstants.entityHeader, entity).build());
  }
}
