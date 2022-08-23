package gg.bayes.challenge.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Herohitentity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private long matchId;
    private String byHero;
    private String hitWith;
    private String toHero;
    private long damage;
}
