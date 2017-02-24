package tikape.runko;

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
        Database database = new Database("jdbc:sqlite:forum.db");
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
            alueDao.lisaa(req.queryParams("uusialue"));
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
            lankaDao.lisaa(req.queryParams("uusilanka"), req.params(":alue"));
            res.redirect("/" + req.params(":alue"));
            return "ok";
        });
        
        get("/:alue/:lanka", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Viesti> viestit = viestiDao.findAll();
            List<Viesti> langanViestit = new ArrayList<>();
            for (Viesti kk : viestit){
                if (kk.getLanka().equals(req.params(":lanka"))){
                    langanViestit.add(kk);
                }
            }
            map.put("viestit", langanViestit);

            return new ModelAndView(map, "viestit");
        }, new ThymeleafTemplateEngine());
        
        post("/:alue/:lanka", (req, res) -> {
            viestiDao.lisaa(req.queryParams("uusiviesti"), "lahettaja", "00:00", req.params(":lanka"));
            res.redirect("/" + req.params(":alue") + "/" + req.params(":lanka"));
            return "ok";
        });
    }
}
