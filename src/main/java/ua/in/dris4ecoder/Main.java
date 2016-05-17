package ua.in.dris4ecoder;

import spark.Spark;
import ua.in.dris4ecoder.parsers.ParseArgs;
import ua.in.dris4ecoder.web.Web;

/**
 * Created by Alex Korneyko on 15.05.2016.
 */
public class Main {

    public static void main(String[] args) {

        int port = ParseArgs.port(args);
        if(port != 0) Spark.port(port);

        Web.showStartPage();

    }


}
