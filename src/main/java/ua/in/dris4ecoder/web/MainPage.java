package ua.in.dris4ecoder.web;

import spark.ModelAndView;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;
import ua.in.dris4ecoder.dataBase.UserQueryResult;
import ua.in.dris4ecoder.dataBase.Users;
import ua.in.dris4ecoder.mathematics.Calculation;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static ua.in.dris4ecoder.dataBase.UserQueryResult.*;

/**
 * Created by Alex Korneyko on 17.05.2016.
 */
public class MainPage {

    public static void startPage() {

        Map<String, String> userAuthorized = new HashMap<>();

        Spark.get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            double result = 0;
            String sessionID = request.session().id();

            model.put("usrNotFound", "");
            model.put("calculator", "templates/calculatorTemplate.vtl");
            model.put("history", "templates/historyTemplate.vtl");

            if (userAuthorized.containsKey(sessionID)) {
                model.put("loginPassword", "templates/helloThereTemplate.vtl");
                model.put("userName", userAuthorized.get(sessionID));
            } else {
                model.put("loginPassword", "templates/loginPassTemplate.vtl");
            }

            if (request.queryParams().contains("loginButton")) {
                UserQueryResult loginResult = Users.login(request.queryParams("loginBox"), request.queryParams("passBox"));
                if (loginResult == USER_FOUND) {
                    model.put("loginPassword", "templates/helloThereTemplate.vtl");
                    model.put("userName", request.queryParams("loginBox"));
                    userAuthorized.put(request.session().id(), model.get("userName").toString());
                } else {
                    model.put("usrNotFound", "User not found or wrong password!");
                }
            }

            if (request.queryParams().contains("logOut")) {
                userAuthorized.remove(request.session().id());
                model.put("loginPassword", "templates/loginPassTemplate.vtl");
                return new ModelAndView(model, "templates/mainPageLayout.vtl");
            }

            String stringExpression = request.queryParams("expression");

            try {
                result = Calculation.getDoubleResult(stringExpression);
            } catch (IllegalArgumentException e) {
                stringExpression = stringExpression + " >>> Error! (" + e.getMessage() + ") <<<";
            }

            if (stringExpression != null) {
                if (stringExpression.charAt(stringExpression.length() - 1) != '<') {
                    stringExpression = stringExpression + " = " + result;
                }

                if (stringExpression.length() > 2 && stringExpression.charAt(stringExpression.length() - 1) == '0'
                        && stringExpression.charAt(stringExpression.length() - 2) == '.') {
                    stringExpression = stringExpression.substring(0, stringExpression.length() - 2);
                }
            } else {
                stringExpression = "";
            }

            System.out.println((new Date()).toString() + " : " + userAuthorized.get(sessionID) + " ->> " + stringExpression);

            model.put("result", stringExpression);

            return new ModelAndView(model, "templates/mainPageLayout.vtl");
        }, new VelocityTemplateEngine());
    }
}
