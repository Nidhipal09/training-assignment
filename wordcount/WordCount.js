const fileHandler = require("fs") // to read data from files 
const axios = require("axios"); // to fetch data from a site
const cheerio = require("cheerio"); // to convert html of the site to text

let wordsCountForOneUrl = new Map(); //map to count words frequencies on one site
let wordsCountForAllUrls = new Map(); //map to count words frequencies across all urls

const sortMapAndReturnTopThreeWords = () => {
  //sorting in descending order of words frequencies and then taking top 3 frequencies words
  wordsCountForOneUrl = new Map([...wordsCountForOneUrl.entries()].sort(
    function (a, b) {
      return b[1] - a[1]
    }
  ).slice(0, 3))
}

const updatefrequencyForAUrl = (wordFromFile) => {

  return function innerFunction(wordsFromUrl) {

    let wordCount = wordsFromUrl.filter(word => 
      word.toLowerCase() === wordFromFile.toLowerCase()
    ).length //total count of the word in the site

    wordsCountForOneUrl = new Map([...wordsCountForOneUrl, [wordFromFile, wordCount]])

    //updating count of the word in the map which stores word total count
    if (wordsCountForAllUrls.has(wordFromFile)) {
      const oldCount = wordsCountForAllUrls.get(wordFromFile)
      wordsCountForAllUrls = new Map([...wordsCountForAllUrls, [wordFromFile, oldCount + wordCount]])
    }
    else {
      wordsCountForAllUrls = new Map([...wordsCountForAllUrls, [wordFromFile, wordCount]])
    }
  }

}

const wordCount = async (urlsArray, wordsArray) => {
  for (let index = 0; index < urlsArray.length; index++) {
    let url = urlsArray[index]
    let wordsFromUrl = ""

    await axios.get(url).then((response) => { //using axios to fetch url data 
      let html = response.data; //html content of the site

      const $ = cheerio.load(html);
      wordsFromUrl = $("body").text().toLowerCase().split(/\W+/) //html to text


    }).catch(function (err) {
      console.log("Invalid url");
      process.exit(1)
    });

    wordsCountForOneUrl = new Map();

    for (let index = 0; index < wordsArray.length; index++) {
      let wordFromFile = wordsArray[index]
      updatefrequencyForAUrl(wordFromFile)(wordsFromUrl)
    }

    sortMapAndReturnTopThreeWords()

    console.log("Top 3 frequency words for url- " + url + "\n");
    wordsCountForOneUrl.forEach((value, key) => {
      console.log(key);
      console.log(value);
    })
    console.log("-----------------------\n")
  }

  console.log("Frequency of all words across all urls\n");
  wordsCountForAllUrls.forEach((value, key) => {
    console.log(key)
    console.log(value)
  })
}

const checkValidationForWordsAndUrls = () => {
  let wordsArray = [];
  let urlsArray = [];
  try {
    urlsArray = fileHandler.readFileSync("urls.txt", "utf-8");
    urlsArray = urlsArray?.length == 0 ? null : urlsArray.split(", ") //get urls from urls.txt file and using optional chaining by ?
    if (urlsArray == null) {
      console.log("No urls found in urls.txt file.");
      return;
    }
  }
  catch (err) {
    console.log("urls.txt file doesn't exists");
    return
  }
  try {
    wordsArray = fileHandler.readFileSync("words.txt", "utf-8");
    wordsArray = wordsArray?.length == 0 ? null : wordsArray.split(", ") //get words from words.txt file and using optional chaining by ?
    if (wordsArray == null) {
      console.log("No words found in words.txt file.");
      return;
    }
  }
  catch (err) {
    console.log("words.txt file doesn't exists");
    return
  }

  wordCount(urlsArray, wordsArray)
}

checkValidationForWordsAndUrls()