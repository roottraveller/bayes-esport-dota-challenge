package gg.bayes.challenge.repository.h2;

import gg.bayes.challenge.repository.model.Herohitentity;
import gg.bayes.challenge.rest.model.HeroDamage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface H2HeroHitRepository extends CrudRepository<Herohitentity, String> {

    @Query(nativeQuery = true, value = "select HeroHitEntity.toHero as target, count(HeroHitEntity.damage) as damageInstances, sum(HeroHitEntity.damage) as totalDamage from HeroHitEntity where HeroHitEntity.matchId=?1 and HeroHitEntity.byHero=?2 group by HeroHitEntity.toHero")
    List<HeroDamage> getHeroDamage(String matchId, String heroName);
}
