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

function judgeIfSuccess() {
  fetch('/verify-guess').then(response => response.json()).then((dataPack) => {
    document.getElementById('result').innerText = dataPack["result"];
    document.getElementById('guess-input').value = dataPack["text"];
  });
}

function loadHistory() {
  fetch('/list-history').then(response => response.json()).then((dataPack) => {
    var table = document.getElementById("historyTable");

    dataPack.forEach(function (item, index) {
      var row = table.insertRow(-1);
      var cell1 = row.insertCell(0);
      var cell2 = row.insertCell(1);
      cell1.innerHTML = item["guess"];
      cell2.innerHTML = item["timestamp"];
    });
  });
}

async function addRandomQuoteUsingAsyncAwait() {
  const response = await fetch('/random-quote');
  const dataPack = await response.json();

  const quote = dataPack["quote"];
  const currentTry = dataPack["currentTry"];
  const currentTime = dataPack["currentTime"];

  document.getElementById('quote-container').innerText = quote;
  document.getElementById('count-container').innerText = currentTry;
  document.getElementById('time-container').innerText = currentTime;
}

function checkIfEmpty() {
  var val = document.getElementById('guess-input').value;
  if (val == "") {
    alert("Please input your guess.");
    return false;
  } else {
    return true; 
  }
}