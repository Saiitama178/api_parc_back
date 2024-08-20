package com.parc.api.model.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*Java Guide
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder*/

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder

public class PaysDto {
    private int idPays;
    private String nomPays;
}
