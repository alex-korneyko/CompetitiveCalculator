package ua.in.dris4ecoder.web;

import spark.ModelAndView;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;
import ua.in.dris4ecoder.mathematics.Calculation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Alex Korneyko on 17.05.2016.
 */
public class Web {

    public static void showStartPage() {

        final Object[] authorization = {false, "", ""};

        Spark.get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            double result = 0;

            model.put("calculator", "templates/calculator.vtl");
            model.put("history", "templates/history.vtl");

            if(Objects.equals(request.queryParams("logOut"), "Log Out")){
                authorization[0] = false;
            }

            if (!(boolean) authorization[0])
                model.put("loginPassword", "templates/loginPass.vtl");
            else {
                model.put("userName", authorization[1]);
                model.put("loginPassword", "templates/helloThere.vtl");
            }

            String stringExpression = request.queryParams("expression");
            String login = request.queryParams("loginBox");
            String password = request.queryParams("passBox");

            if (login != null && password != null) {
                authorization[1] = login;
                authorization[2] = password;

                model.put("loginPassword", "templates/helloThere.vtl");
                model.put("userName", login);
                authorization[0] = true;
            }

            try {
                result = Calculation.getDoubleResult(stringExpression);
            } catch (IllegalArgumentException e) {
                stringExpression = stringExpression + " >>> Error! (" + e.getMessage() + ") <<<";
            }

            if (stringExpression != null) {
                if (stringExpression.charAt(stringExpression.length() - 1) != '<') {
                    stringExpression = stringExpression + " = " + result;
                }

                if (stringExpression.length() > 2 && stringExpression.charAt(stringExpression.length() - 1) == '0' && stringExpression.charAt(stringExpression.length() - 2) == '.') {
                    stringExpression = stringExpression.substring(0, stringExpression.length() - 2);
                }
            } else {
                stringExpression = "";
            }

            System.out.println(stringExpression);

            model.put("result", stringExpression);

            return new ModelAndView(model, "templates/mainPageLayout.vtl");
        }, new VelocityTemplateEngine());

        Spark.get("/registration", (request, response) -> {

            return new ModelAndView(new HashMap(), "templates/registration.vtl");
        }, new VelocityTemplateEngine());
    }
}
