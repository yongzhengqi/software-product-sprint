package com.google.sps.servlets;

import java.io.IOException;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.*;   
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;  
import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

@WebServlet("/list-history")
public class HistoryServlet extends HttpServlet {
  private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Guess").addSort("timestamp", SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    // System.out.println("Post done.");

    List<Map> history = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      long id = entity.getKey().getId();
      String guess = (String) entity.getProperty("guess");
    //   System.out.println("Guess: " + guess);
      long timestamp = (long) entity.getProperty("timestamp");
      Instant timeInstance = Instant.ofEpochMilli(timestamp);
      LocalDateTime localDateTime = LocalDateTime.ofInstant(timeInstance, java.time.ZoneId.of("Asia/Shanghai"));
      String currentTime = dtf.format(localDateTime);

      Map<String, String> dataPack = new HashMap();  
      dataPack.put("guess", guess);
      dataPack.put("timestamp", currentTime);
      history.add(dataPack);
    }

    String json =  new Gson().toJson(history);

    response.setContentType("application/json;");
    response.getWriter().println(json);
  }
}

