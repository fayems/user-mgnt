package fr.af.test.offer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor @Builder
public class ResponseDto {
    private Integer status;
    private Object message;

}
