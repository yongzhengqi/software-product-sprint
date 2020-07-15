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

/**
 * Adds a random greeting to the page.
 */
function addRandomQuote() {
  const cooperQuotes =
      ['Hard As This May Be To Believe, It’s Possible That I’m Not Boyfriend Material.', 
      'Scissors Cuts Paper. Paper Covers Rock. Rock Crushes Lizard.', 
      'I\'m Exceedingly Smart. I Graduated College At 14.', 
      'Robert Oppenheimer Was Lonely.',
      'I\'m Not Crazy. My Mother Had Me Tested.',
      'Bazinga!'];

  // Pick a random greeting.
  const cooperQuote = cooperQuotes[Math.floor(Math.random() * cooperQuotes.length)];

  // Add it to the page.
  const quoteContainer = document.getElementById('quote-container');
  quoteContainer.innerText = cooperQuote;
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