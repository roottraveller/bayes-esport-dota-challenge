package gg.bayes.challenge.repository.h2;

import gg.bayes.challenge.repository.model.HeroKillEntity;
import gg.bayes.challenge.rest.model.HeroKills;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface H2HeroKillRepository extends CrudRepository<HeroKillEntity, String> {

    @Query(nativeQuery = true, value = "select HeroKillEntity.killedByHero as hero, count(HeroKillEntity.hero) as kills from HeroKillEntity  where HeroKillEntity.matchId=?1 group by HeroKillEntity.killedByHero")
    List<HeroKills> getHeroKills(String matchId);
}
