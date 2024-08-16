package com.mapping.learn.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "role")
public class Role {

  @Id
  @Column(name = "id")
  @Builder.Default
  private String id = UUID.randomUUID().toString();

  @Column(name = "role_name")
  private String roleName;

  @Column(name = "role_description")
  private String roleDescription;

}
