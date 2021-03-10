/*
 * Copyright (C) open knowledge GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package de.openknowledge.projects.webshop.infrastructure;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import de.openknowledge.projects.webshop.DatabaseTestResource;
import de.openknowledge.projects.webshop.domain.bestellung.Produkt;
import de.openknowledge.projects.webshop.infrastructure.bestellung.ProduktRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * Integration test for the repository {@link ProduktRepository}.
 */
@QuarkusTest
@QuarkusTestResource(DatabaseTestResource.class)
@DBRider
@DataSet(value = "webshop.yml", strategy = SeedStrategy.CLEAN_INSERT, skipCleaningFor = "flyway_schema_history")
class ProduktRepositoryIT {

  @Inject
  private ProduktRepository repository;

  @Test
  void findByNameShouldReturnEmptyOptional() {
    Optional<Produkt> optional = repository.findByName("Fleisch");
    Assertions.assertThat(!optional.isPresent()).isTrue();
  }

  @Test
  void findByNameShouldReturnOptional() {
    Optional<Produkt> optional = repository.findByName("Salat");
    Assertions.assertThat(optional.isPresent()).isTrue();

    Produkt produkt = optional.get();
    Assertions.assertThat(produkt.id).isEqualTo(1L);
    Assertions.assertThat(produkt.getName()).isEqualTo("Salat");
    Assertions.assertThat(produkt.getPreis()).isEqualTo(BigDecimal.valueOf(8.5));
  }

  @Test
  void findAllShouldReturnThreeItems() {
    PanacheQuery<Produkt> findAllQuery = repository.findAll();
    Assertions.assertThat(findAllQuery.stream().count()).isEqualTo(3);
  }
}
