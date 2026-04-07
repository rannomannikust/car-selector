package ee.mannikust.carselector.repository;

import ee.mannikust.carselector.model.UserSelection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSelectionRepository extends JpaRepository<UserSelection, Long> {}
