package gg.bayes.challenge.repository.h2;

import gg.bayes.challenge.repository.model.HeroItemEntity;
import gg.bayes.challenge.rest.model.HeroItems;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface H2HeroItemRepository extends CrudRepository<HeroItemEntity, String> {

    @Query(nativeQuery = true, value = "select HeroItemEntity.item, HeroItemEntity.timestamp from HeroItemEntity where HeroItemEntity.matchId=?1 and HeroItemEntity.hero=?2")
    List<HeroItems> getHeroItems(String matchId, String heroName);
}
