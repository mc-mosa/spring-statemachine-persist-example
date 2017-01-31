package com.mosa.entity.statemachine;

import com.mosa.entity.model.EntityEvents;
import com.mosa.entity.model.EntityStates;
import com.mosa.entity.statemachine.actions.IdleToActiveAction;
import com.mosa.entity.statemachine.guards.IdleToActiveGuard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableStateMachine(name = "entityStateMachine")
public class EntityStateMachineConfig extends StateMachineConfigurerAdapter<String, String> {

  @Autowired
  private IdleToActiveGuard idleToActiveGuard;

  @Autowired
  private IdleToActiveAction idleToActiveAction;

  @Override
  public void configure(StateMachineStateConfigurer<String, String> states)
      throws Exception {
    Set<String> stringStates = new HashSet<>();
    EnumSet.allOf(EntityStates.class).forEach(entity -> stringStates.add(entity.name()));
    states.withStates()
        .initial(EntityStates.IDLE.name())
        .end(EntityStates.DELETED.name())
        .states(stringStates);
  }

  @Override
  public void configure(StateMachineTransitionConfigurer<String, String> transitions)
      throws Exception {
    transitions.withExternal()
        .source(EntityStates.IDLE.name()).target(EntityStates.ACTIVE.name())
        .event(EntityEvents.ACTIVATE.name())
        .guard(idleToActiveGuard).action(idleToActiveAction)
        .and().withExternal()
        .source(EntityStates.ACTIVE.name()).target(EntityStates.IDLE.name())
        .event(EntityEvents.IDLE.name())
        .and().withExternal()
        .source(EntityStates.IDLE.name()).target(EntityStates.DELETED.name())
        .event(EntityEvents.DELETE.name())
        .and().withExternal()
        .source(EntityStates.ACTIVE.name()).target(EntityStates.DELETED.name())
        .event(EntityEvents.DELETE.name());
  }
}
