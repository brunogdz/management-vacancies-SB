package com.brunogdev.gestao_vagas.modules.candidate;

import java.util.UUID;
import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CandidateEntity {

    private UUID id;
    private String name;

    @NotBlank()
    @Pattern(regexp = "\\S+", message = "This Field [username] can not have space")
    private String username;

    @Email(message = "This Field needs to contain a valid email")
    private String email;

    @Length(min = 10, max = 100, message = "The password needs to have minimun 10 characters")
    private String password;
    private String description;
    private String curriculum;

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }


}
