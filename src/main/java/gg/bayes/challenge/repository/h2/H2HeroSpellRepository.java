package gg.bayes.challenge.repository.h2;

import gg.bayes.challenge.repository.model.HeroSpellEntity;
import gg.bayes.challenge.rest.model.HeroSpells;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface H2HeroSpellRepository extends CrudRepository<HeroSpellEntity, String> {

    @Query(nativeQuery = true, value = "select HeroSpellEntity.spell, count(HeroSpellEntity.spell) from HeroSpellEntity where HeroSpellEntity.matchId=?1 and HeroSpellEntity.byHero=?2 group by HeroSpellEntity.spell")
    List<HeroSpells> getHeroSpells(String matchId, String heroName);

}
