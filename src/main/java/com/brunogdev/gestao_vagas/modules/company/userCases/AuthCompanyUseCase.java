package com.brunogdev.gestao_vagas.modules.company.userCases;

import java.time.Instant;
import java.util.Arrays;
import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.brunogdev.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import com.brunogdev.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import com.brunogdev.gestao_vagas.modules.company.repositories.CompanyRepository;
import java.time.Duration;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretkey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO)
            throws AuthenticationException {
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Username / password incorrect");
                });

        // validate the password
        var passwordMatches =
                this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretkey);

        var expires_in = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("javagas")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withSubject(company.getId().toString()).withExpiresAt(expires_in)
                .withClaim("roles", Arrays.asList("COMPANY")).sign(algorithm);

        var authCompanyResponseDTO = AuthCompanyResponseDTO.builder().access_token(token)
                .expires_in(expires_in.toEpochMilli()).build();
        return authCompanyResponseDTO;
    }
}
