package ua.in.dris4ecoder.web;

import spark.ModelAndView;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;
import ua.in.dris4ecoder.dataBase.Users;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex Korneyko on 18.05.2016.
 */
public class Registration {

    public static void registrationPages() {

        Spark.get("/registration", (request, response) -> {
            Map<String, String> model = new HashMap<>();

            return new ModelAndView(model, "templates/registrationPage.vtl");
        }, new VelocityTemplateEngine());

        Spark.get("/registerResultPage", (request, response) -> {
            Map<String, String> model = new HashMap<>();
            String userName = "";
            String userPass1 = "";
            String userPass2 = "";

            if (request.queryParams().contains("regUserName")) userName = request.queryParams("regUserName");
            if (request.queryParams().contains("regPass")) userPass1 = request.queryParams("regPass");
            if (request.queryParams().contains("regConfirmPass")) userPass2 = request.queryParams("regConfirmPass");

            if (userName == null || userPass1 == null || userPass2 == null) {
                model.put("registerResult", "Something wrong!");
                return new ModelAndView(model, "templates/registerResultPage.vtl");
            }

            if (userName.equals("") || userPass1.equals("") || userPass2.equals("")) {
                model.put("registerResult", "You must fill in all fields!");
                return new ModelAndView(model, "templates/registerResultPage.vtl");
            }

            if (!userPass1.equals(userPass2)) {
                model.put("registerResult", "Passwords do not match");
                return new ModelAndView(model, "templates/registerResultPage.vtl");
            }

            if(!Users.register(userName,userPass1)) {
                model.put("registerResult", "This name already exists");
                return new ModelAndView(model, "templates/registerResultPage.vtl");
            }

            model.put("registerResult", "Congratulations, you are registered!");

            return new ModelAndView(model, "templates/registerResultPage.vtl");
        }, new VelocityTemplateEngine());
    }


}
