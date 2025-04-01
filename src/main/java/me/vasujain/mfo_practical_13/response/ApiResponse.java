package me.vasujain.mfo_practical_13.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private HttpStatus status;
    private String message;
    private T data;
    private LocalDate timestamp;
}