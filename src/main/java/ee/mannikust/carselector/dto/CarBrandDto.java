package ee.mannikust.carselector.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarBrandDto {
    private Long id;
    private String displayName; // Siia paneme nime koos HTML-tühikutega, nt "&nbsp;&nbsp;C 160"
}