package com.brunogdev.gestao_vagas.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity(name = "company")
@Data
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank()
    @Pattern(regexp = "\\S+", message = "This Field [username] can not have space")
    private String username;

    @Email(message = "This Field needs to contain a valid email")
    private String email;

    @Length(min = 10, max = 100, message = "The password needs to have minimun 10 characters")
    private String password;
    private String webiste;
    private String name;
    private String description;

    @CreationTimestamp
    private LocalDateTime createAt;
}
