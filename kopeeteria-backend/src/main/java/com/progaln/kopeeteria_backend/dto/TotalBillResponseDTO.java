package com.progaln.kopeeteria_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TotalBillResponseDTO {
    
    private double regularBillTotal;
    private double discountedBillTotal;

}
