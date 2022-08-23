package gg.bayes.challenge;

import gg.bayes.challenge.repository.h2.H2HeroHitRepository;
import gg.bayes.challenge.repository.h2.H2HeroItemRepository;
import gg.bayes.challenge.repository.h2.H2HeroKillRepository;
import gg.bayes.challenge.repository.h2.H2HeroSpellRepository;
import gg.bayes.challenge.service.impl.MatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@SpringBootApplication
@EnableJpaRepositories("gg.bayes.challenge.repository.*")
@EntityScan("gg.bayes.challenge.*")
public class DotaChallengeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DotaChallengeApplication.class, args);
//        context.getBean(H2HeroHitRepository.class);
//        context.getBean(H2HeroItemRepository.class);
//        context.getBean(H2HeroKillRepository.class);
//        context.getBean(H2HeroSpellRepository.class);
    }



}
