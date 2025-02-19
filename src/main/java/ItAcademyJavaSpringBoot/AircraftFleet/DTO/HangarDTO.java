package ItAcademyJavaSpringBoot.AircraftFleet.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HangarDTO {
    private Long id;
    private Long userId;
    private List<PlaneDTO> planes;
}

