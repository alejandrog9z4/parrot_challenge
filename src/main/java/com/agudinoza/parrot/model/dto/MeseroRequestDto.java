package com.agudinoza.parrot.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeseroRequestDto {


    @JsonFormat(pattern = "^[a-zA-Z]")
    private String nombre;


    @JsonFormat(pattern = "/^[^\\.\\s][\\w\\-\\.{2,}]+@([\\w-]+\\.)+[\\w-]{2,}$/gm")
    private String mail;
}
