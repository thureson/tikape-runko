package tikape.runko;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AlueDao;
import tikape.runko.database.Database;
import tikape.runko.database.LankaDao;
import tikape.runko.database.ViestiDao;
import tikape.runko.domain.Lanka;
import tikape.runko.domain.Viesti;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:test.db");
        database.init();


        AlueDao alueDao = new AlueDao(database);
        LankaDao lankaDao = new LankaDao(database);
        ViestiDao viestiDao = new ViestiDao(database);
        

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alueet", alueDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        post("/", (req, res) -> {
            alueDao.lisaa(req.queryParams("uusialue"), 0);
            res.redirect("/");
            return "ok";
        });

        get("/:alue", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Lanka> langat = lankaDao.findAll();
            List<Lanka> alueenLangat = new ArrayList<>();
            for (Lanka kk : langat){
                if (kk.getAlue().equals(req.params(":alue"))){
                    alueenLangat.add(kk);
                }
            }
            map.put("langat", alueenLangat);
            map.put("alue", req.params(":alue"));

            return new ModelAndView(map, "langat");
        }, new ThymeleafTemplateEngine());
        
        post("/:alue", (req, res) -> {         
            lankaDao.lisaa(req.queryParams("uusilanka"), req.params(":alue"), 0);
            System.out.println("/" + req.params(":alue"));
            res.redirect("/" + req.params(":alue"));
            return "ok";
        });
        
        get("/:alue/:lanka", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Viesti> viestit = viestiDao.findAll();
            List<Viesti> langanViestit = new ArrayList<>();
            Lanka haettu = lankaDao.etsiOtsikolla(req.params(":lanka"));
            String alue = haettu.getAlue();
            for (Viesti kk : viestit){
                if (kk.getLanka().equals(req.params(":lanka")) && kk.getAlue().equals(alue)){
                    langanViestit.add(kk);
                }
            }
            map.put("alue2", alue);
            map.put("viestit", langanViestit);
            map.put("lanka", req.params(":lanka"));
            

            return new ModelAndView(map, "viestit");
        }, new ThymeleafTemplateEngine());
        
        post("/:alue/:lanka", (req, res) -> {
            Lanka haettu = lankaDao.etsiOtsikolla(req.params(":lanka"));
            String alue = haettu.getAlue();
            viestiDao.lisaa(req.queryParams("uusiviesti"), req.queryParams("lahettaja"), LocalDateTime.now().toLocalTime().toString() + " " + LocalDateTime.now().toLocalDate().toString(), req.params(":lanka"), alue);
            res.redirect("/" + alue + "/" + req.params(":lanka"));
            return "ok";
        });
    }
}
