package gg.bayes.challenge;

import gg.bayes.challenge.constants.Constants;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItems;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import gg.bayes.challenge.service.impl.MatchServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@SpringBootTest
class DotaChallengeApplicationTests {
    @Autowired
    private MatchServiceImpl matchService;

    @BeforeAll
    static void init() {
//        ingestDataFromFile();
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void test() {
        ingestDataFromFile();
//        String heroName = "pangolier";
        String heroName = "abyssal_underlord";
        List<HeroKills> heroKillsList = matchService.getMatch(Constants.MATCH_ID);
        for (HeroKills heroKills : heroKillsList) {
            System.out.println(heroKills.getHero() + " " + heroKills.getKills());
        }

        List<HeroDamage> heroDamageList = matchService.getDamage(Constants.MATCH_ID, heroName);
        for (HeroDamage heroDamage : heroDamageList) {
            System.out.println(heroDamage.getTarget() + " " + heroDamage.getDamageInstances() + " " + heroDamage.getTarget());
        }

        List<HeroItems> heroItemsList = matchService.getItems(Constants.MATCH_ID, heroName);
        for (HeroItems heroItems : heroItemsList) {
            System.out.println(heroItems.getItem() + " " + heroItems.getTimestamp());
        }

        List<HeroSpells> heroSpellsList = matchService.getSpells(Constants.MATCH_ID, heroName);
        for (HeroSpells heroSpells : heroSpellsList) {
            System.out.println(heroSpells.getSpell() + " " + heroSpells.getCasts());
        }
    }


    private void ingestDataFromFile() {
        String[] filePaths = new String[]{"data/combatlog_1.txt", "data/combatlog_2.txt"};
        String line = null;
        long lineCount = 0;
        for (String filePath : filePaths) {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                while ((line = br.readLine()) != null) {
                    System.out.println(++lineCount + "- " + line);
                    matchService.ingestMatch(line);
                }
            } catch (IOException e) {
                System.out.println(line);
                e.printStackTrace();
            }
        }
    }


}
