package com.mosa.entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "entity")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Entity {
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "state")
  @Enumerated(value = EnumType.STRING)
  private EntityStates state;
}
