package ee.mannikust.carselector.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import ee.mannikust.carselector.dto.CarBrandDto;
import ee.mannikust.carselector.model.CarBrand;
import ee.mannikust.carselector.repository.CarBrandRepository;

//@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CarBrandServiceTest {

    @Mock
    private CarBrandRepository repository;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private CarBrandService carBrandService;

    

    @Test
    void testGetHierarchicalCarBrands() {
        when(messageSource.getMessage(anyString(), any(), anyString(), any()))
                .thenReturn("translated");

        // Siin me defineerime vastused käsitsi, andmebaasi pole vaja!
        CarBrand parent = new CarBrand();
        parent.setId(1L);
        parent.setName("Audi");

        CarBrand child = new CarBrand();
        child.setId(2L);
        child.setName("A4");
        
        // 2. Õpetame Mock-objektid vastama (Sellega väldime "Table not found" viga)
        when(repository.findByParentIsNullOrderByNameAsc()).thenReturn(List.of(parent));
        when(repository.findByParentIdOrderByNameAsc(1L)).thenReturn(List.of(child));
        when(repository.findByParentIdOrderByNameAsc(2L)).thenReturn(List.of());
        
        when(messageSource.getMessage(anyString(), any(), anyString(), any(Locale.class)))
                .thenAnswer(i -> i.getArgument(0));

        // 3. Käivitame
        List<CarBrandDto> result = carBrandService.getHierarchicalCarBrands(Locale.ENGLISH);

        // 4. Kontrollime
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.get(1).getDisplayName().contains("nbsp"));
    }
}
