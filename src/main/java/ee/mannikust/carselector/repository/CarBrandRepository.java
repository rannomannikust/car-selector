package ee.mannikust.carselector.repository;

import ee.mannikust.carselector.model.CarBrand;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarBrandRepository extends JpaRepository<CarBrand, Long> {

  List<CarBrand> findByParentIsNullOrderByNameAsc();

  List<CarBrand> findByParentIdOrderByNameAsc(Long parentId);
}
