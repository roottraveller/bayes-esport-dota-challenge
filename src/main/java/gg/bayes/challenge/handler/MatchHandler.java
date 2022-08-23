package gg.bayes.challenge.handler;

import gg.bayes.challenge.constants.Constants;
import gg.bayes.challenge.repository.h2.H2HeroHitRepository;
import gg.bayes.challenge.repository.h2.H2HeroItemRepository;
import gg.bayes.challenge.repository.h2.H2HeroKillRepository;
import gg.bayes.challenge.repository.h2.H2HeroSpellRepository;
import gg.bayes.challenge.repository.model.HeroHitEntity;
import gg.bayes.challenge.repository.model.HeroItemEntity;
import gg.bayes.challenge.repository.model.HeroKillEntity;
import gg.bayes.challenge.repository.model.HeroSpellEntity;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItems;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@Slf4j
public class MatchHandler {
    @Autowired
    private H2HeroKillRepository h2HeroKillRepository;
    @Autowired
    private H2HeroHitRepository h2HeroHitRepository;
    @Autowired
    private H2HeroItemRepository h2HeroItemRepository;
    @Autowired
    private H2HeroSpellRepository h2HeroSpellRepository;

    public Long ingestMatch(String str) {
        if (str.length() == 0) {
            log.error("str length is zero. aborting here");
            return 0L;
        }
        String[] split = str.split(Constants.SEPARATOR_SPACE);
        if (split.length <= 3 || split[1].equalsIgnoreCase("game")) {
            log.error("str length is less than 3 or game state updated. skipping");
            return 0L;
        }

//        for(String s : split) {
//            System.out.println(s);
//        }

        String timestamp = getTimestamp(split[0]);
        long matchId = getMatchId(split[1]);
        String heroName = getHeroName(split[1]);
        if (split.length == 5) {
            //[00:08:46.693] npc_dota_hero_snapfire buys item item_clarity
            handleBuyItem(split, timestamp, matchId, heroName);
        } else if (split.length == 6) {
            //[00:31:33.292] npc_dota_hero_mars is killed by npc_dota_hero_abyssal_underlord
            handleKill(split, matchId, heroName);
        } else if (split.length == 9) {
            //[00:08:43.460] npc_dota_hero_pangolier casts ability pangolier_swashbuckle (lvl 1) on dota_unknown
            handleSpell(split, timestamp, matchId, heroName);
        } else if (split.length == 10) {
            //[00:10:42.031] npc_dota_hero_bane hits npc_dota_hero_abyssal_underlord with dota_unknown for 51 damage (740->689)
            handleHit(split, matchId, heroName);
        } else {
            //TODO handle later or throw exception
            log.error("Invalid input for current use case, skip");
            return 0L;
        }

        return 1L;
    }


    public List<HeroKills> getMatch(long matchId) {
        return h2HeroKillRepository.getHeroKills(String.valueOf(matchId));
    }

    public List<HeroItems> getItems(long matchId, String heroName) {
        return h2HeroItemRepository.getHeroItems(String.valueOf(matchId), heroName);
    }

    public List<HeroSpells> getSpells(long matchId, String heroName) {
        return h2HeroSpellRepository.getHeroSpells(String.valueOf(matchId), heroName);
    }

    public List<HeroDamage> getDamage(long matchId, String heroName) {
        return h2HeroHitRepository.getHeroDamage(String.valueOf(matchId), heroName);
    }

    private String getHeroName(String str) {
        if (str.startsWith("npc_dota_hero_")) {
            return str.substring(14);
        }
        return str;
    }

    //TODO check if can be converted to date format etc
    private String getTimestamp(String str) {
        return str.substring(1, str.length() - 1); //remove [, ]
    }

    private long getMatchId(String str) {
//        if (str.startsWith("npc_dota_")) {
//            return str.substring(0, 8);
//        }
//        return str;
        return Constants.MATCH_ID;
    }

    private void handleHit(String[] split, long matchId, String heroName) {
        String action = split[2];
        String toHero = getHeroName(split[3]);
        String hitWith = split[5];
        long damage;
        try {
            damage = Long.parseLong(split[7]);
        } catch (NumberFormatException e) {
            log.error("NumberFormatException occur for input={}", split[7]);
            return;
        }
        if (action.equalsIgnoreCase("hits")) {
            h2HeroHitRepository.save(HeroHitEntity.builder()
                    .matchId(matchId)
                    .byHero(heroName)
                    .hitWith(hitWith)
                    .toHero(toHero)
                    .damage(damage)
                    .build());
        }
    }

    private void handleSpell(String[] split, String timestamp, long matchId, String heroName) {
        String action = split[2];
        String spell = split[3] + " " + split[4] + " " + split[5] + " " + split[6];
        String toHero = getHeroName(split[8]);
        if (action.equalsIgnoreCase("casts")) {
            h2HeroSpellRepository.save(HeroSpellEntity.builder()
                    .matchId(matchId)
                    .byHero(heroName)
                    .spell(spell)
                    .toHero(toHero)
                    .timestamp(timestamp)
                    .build());
        }
    }

    private void handleKill(String[] split, long matchId, String heroName) {
        String action = split[3];
        String killedByHero = split[5];
        if (action.equalsIgnoreCase("killed")) {
            h2HeroKillRepository.save(HeroKillEntity.builder()
                    .matchId(matchId)
                    .hero(heroName)
                    .killedByHero(killedByHero)
                    .build());
        }
    }

    private void handleBuyItem(String[] split, String timestamp, long matchId, String heroName) {
        String action = split[2];
        String item = split[4].substring(5);
        if (action.equalsIgnoreCase("buys")) {
            h2HeroItemRepository.save(HeroItemEntity.builder()
                    .matchId(matchId)
                    .hero(heroName)
                    .timestamp(timestamp)
                    .item(item)
                    .build());
        }
    }

}

