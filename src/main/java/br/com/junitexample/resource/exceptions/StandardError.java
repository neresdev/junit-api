package br.com.junitexample.resource.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class StandardError extends Exception{

    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;

}
