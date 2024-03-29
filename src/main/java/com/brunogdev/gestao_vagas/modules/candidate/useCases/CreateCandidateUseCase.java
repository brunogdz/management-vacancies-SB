package com.brunogdev.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.brunogdev.gestao_vagas.exceptions.UserFoundException;
import com.brunogdev.gestao_vagas.modules.candidate.CandidateEntity;
import com.brunogdev.gestao_vagas.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder PasswordEncoder;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository
                .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        var password = PasswordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(password);



        return this.candidateRepository.save(candidateEntity);
    }
}
