package ee.mannikust.carselector.repository;

import ee.mannikust.carselector.model.CarBrand;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarBrandRepository extends JpaRepository<CarBrand, Long> {

  // Otsib kõik peamised margid (kellel pole vanemat ehk parent on null)
  // ja sorteerib need tähestiku järjekorras (A-Z)
  List<CarBrand> findByParentIsNullOrderByNameAsc();

  // Otsib kõik alam-mudelid konkreetse vanema (parent) ID järgi
  // ja sorteerib need samuti tähestiku järjekorras
  List<CarBrand> findByParentIdOrderByNameAsc(Long parentId);
}
