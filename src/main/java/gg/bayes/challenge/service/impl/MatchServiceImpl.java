package gg.bayes.challenge.service.impl;

import gg.bayes.challenge.constants.Constants;
import gg.bayes.challenge.handler.MatchHandler;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItems;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import gg.bayes.challenge.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MatchServiceImpl implements MatchService {
    private final MatchHandler matchHandler;

    @Autowired
    public MatchServiceImpl(MatchHandler matchHandler) {
        this.matchHandler = matchHandler;
    }

    @Override
    public long ingestMatch(String payload) {
        String[] split = payload.split(Constants.SEPARATOR_NEWLINE);
        long lineCount = 0;
        for (String str : split) {
            lineCount += matchHandler.ingestMatch(str);
        }
        return lineCount;
    }

    @Override
    public List<HeroKills> getMatch(long matchId) {
        return matchHandler.getMatch(matchId);
    }

    @Override
    public List<HeroItems> getItems(long matchId, String heroName) {
        return matchHandler.getItems(matchId, heroName);
    }

    @Override
    public List<HeroSpells> getSpells(long matchId, String heroName) {
        return matchHandler.getSpells(matchId, heroName);
    }

    @Override
    public List<HeroDamage> getDamage(long matchId, String heroName) {
        return matchHandler.getDamage(matchId, heroName);
    }
}
