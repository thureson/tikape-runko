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
        Database database = new Database("jdbc:sqlite:forum.db");
        database.init();


        AlueDao alueDao = new AlueDao(database);
        LankaDao lankaDao = new LankaDao(database);
        ViestiDao viestiDao = new ViestiDao(database);
        

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alueet", alueDao.findAllByName());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        post("/", (req, res) -> {   //teksti <= 30
            if (!alueDao.sisaltaa(req.queryParams("uusialue")) && req.queryParams("uusialue").length() <= 30){
                alueDao.lisaa(req.queryParams("uusialue"), 0, LocalDateTime.now().toLocalDate().toString() + " | " + LocalDateTime.now().toLocalTime().toString().subSequence(0, 8));
                res.redirect("/");
                return "ok";
            } else {
                res.redirect("/");
                return "ok";
            }
        });

        get("/:alue", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Lanka> langat = lankaDao.findTenByAika();
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
        
        post("/:alue", (req, res) -> {    // teksti <= 30
            if (!lankaDao.sisaltaa(req.queryParams("uusilanka"), req.params(":alue")) && req.queryParams("uusilanka").length() <= 30){
                lankaDao.lisaa(req.queryParams("uusilanka"), req.params(":alue"), 0, LocalDateTime.now().toLocalDate().toString() + " | " + LocalDateTime.now().toLocalTime().toString().subSequence(0, 8));
                alueDao.paivita(req.params(":alue"));
                res.redirect("/" + req.params(":alue"));
                return "ok";
            } else {
                res.redirect("/" + req.params(":alue"));
                return "ok";
            }
        });
        
        get("/:alue/:lanka", (req, res) -> {
            HashMap map = new HashMap<>();
//            List<Viesti> viestit = viestiDao.findAll();
            List<Viesti> langanViestit = new ArrayList<>();
            try {
                List<Viesti> viestit = viestiDao.findAllReverse();
                int viesteja = Integer.parseInt(req.queryParams("viesteja"));
                for (Viesti kk : viestit){
                    if (kk.getLanka().equals(req.params(":lanka")) && kk.getAlue().equals(req.params(":alue"))){
                        langanViestit.add(kk);
                        if (langanViestit.size() == viesteja){
                            break;
                        }
                    }
                }
            } catch (Exception e){
                List<Viesti> viestit = viestiDao.findAll();
                System.out.println("ei sivu");
                for (Viesti kk : viestit){
                    if (kk.getLanka().equals(req.params(":lanka")) && kk.getAlue().equals(req.params(":alue"))){
                        langanViestit.add(kk);
                    }
                }             
            }
                    
//            for (Viesti kk : viestit){
//                if (kk.getLanka().equals(req.params(":lanka")) && kk.getAlue().equals(req.params(":alue"))){
//                    langanViestit.add(kk);
//                }
//            }

            map.put("viestit", langanViestit);
            map.put("lanka", req.params(":lanka"));
            map.put("alue", req.params(":alue"));
            

            return new ModelAndView(map, "viestit");
        }, new ThymeleafTemplateEngine());
        
        post("/:alue/:lanka", (req, res) -> {   // teksti <= 30 ja lahettaja <= 15 
            if (req.queryParams("uusiviesti").length() <= 30 && req.queryParams("lahettaja").length() <= 15){
                viestiDao.lisaa(req.queryParams("uusiviesti"), req.queryParams("lahettaja"), LocalDateTime.now().toLocalDate().toString() + " | " + LocalDateTime.now().toLocalTime().toString().subSequence(0, 8), req.params(":lanka"), req.params(":alue"));
                lankaDao.paivita(req.params(":lanka"), req.params(":alue"));
                alueDao.paivita(req.params(":alue"));  
                res.redirect("/" + req.params(":alue") + "/" + req.params(":lanka"));
                return "ok";
            } else {
                res.redirect("/" + req.params(":alue") + "/" + req.params(":lanka"));
                return "ok";
            }  
        });
    }
}
