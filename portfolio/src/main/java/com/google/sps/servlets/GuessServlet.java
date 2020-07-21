package com.google.sps.servlets;

import java.io.IOException;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

@WebServlet("/verify-guess")
public class GuessServlet extends HttpServlet {
  private String lastResult = "";
  private String lastTry = "";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Map<String, String> dataPack = new HashMap();  
    dataPack.put("result", lastResult);
    dataPack.put("text", lastTry);

    String json = new Gson().toJson(dataPack);

    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form.
    String text = getParameter(request, "guess-input", "");
    long timestamp = System.currentTimeMillis();

    lastTry = text;

    Entity guessEntity = new Entity("Guess");
    guessEntity.setProperty("guess", text);
    guessEntity.setProperty("timestamp", timestamp);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(guessEntity);

    text = text.toUpperCase();

    if (text.contains("BIG BANG THEORY")) {
        lastResult = "Right!";
    } else {
        lastResult = "Wrong~ Try again!";
    }

    response.sendRedirect("/index.html");
  }

  /**
   * @return the request parameter, or the default value if the parameter
   *         was not specified by the client
   */
  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}
