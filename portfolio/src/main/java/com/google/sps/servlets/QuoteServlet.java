// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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

@WebServlet("/random-quote")
public class QuoteServlet extends HttpServlet {

  private List<String> quotes;
  private int tryCount;
  private DateTimeFormatter dtf;  

  @Override
  public void init() {
    quotes = new ArrayList<>();
    quotes.add("Hard As This May Be To Believe, It’s Possible That I’m Not Boyfriend Material.");
    quotes.add("I\'m Exceedingly Smart. I Graduated College At 14.");
    quotes.add("Robert Oppenheimer Was Lonely.");
    quotes.add("I\'m Not Crazy. My Mother Had Me Tested.");
    quotes.add("Bazinga!");

    tryCount = 0;

    dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String quote = quotes.get((int) (Math.random() * quotes.size()));
    int currentTry = ++tryCount;
    String currentTime = dtf.format(LocalDateTime.now());

    Map<String, String> dataPack = new HashMap();  
    dataPack.put("quote", quote);
    dataPack.put("currentTry", "Try #" + Integer.toString(currentTry) + ".");
    dataPack.put("currentTime", "At " + currentTime + ".");
    Gson gson = new Gson();
    String json = gson.toJson(dataPack);

    response.setContentType("application/json;");
    response.getWriter().println(json);
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

