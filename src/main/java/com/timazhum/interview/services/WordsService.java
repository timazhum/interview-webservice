package com.timazhum.interview.services;

import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ArrayUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
public class WordsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, Integer> wordCountCache;

    public WordsService() {
        wordCountCache = new HashMap<>();
    }

    private String fetchFileByUrl(String url) {
        StringBuilder content = new StringBuilder();

        try {
            logger.info("url: " + url);

            URL urlObject = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(urlObject.openStream()));

            String line;
            while ((line = in.readLine()) != null) {
                content.append(line).append(" ");
            }
            in.close();
        }
        catch (MalformedURLException e) {
            logger.warn("Malformed URL: " + e.getMessage());
        }
        catch (IOException e) {
            logger.warn("I/O Error: " + e.getMessage());
        }

        return content.toString();
    }

    private String purifyFileContent(String content) {
        String purified = content.replaceAll("-", " ").toLowerCase(Locale.ROOT);

        return purified;
    }

    private Map<String, Integer> getWordCounts(String content) {
        Map<String, Integer> result = new HashMap<>();

        List<String> wordsList = Arrays.asList(content.split(" "));
        wordsList.forEach(s -> result.put(s, result.getOrDefault(s, 0) + 1));

        return result;
    }

    private Map<String, Integer> combineMaps(Map<String, Integer> first, Map<String, Integer> second) {
        Map<String, Integer> result = new HashMap<>();

        for (String key : first.keySet()) {
            result.put(key, result.getOrDefault(key, 0) + first.get(key));
        }

        for (String key : second.keySet()) {
            result.put(key, result.getOrDefault(key, 0) + second.get(key));
        }

        return result;
    }

    public void analyzeFileByUrl(String url) {
        String fetchedFile = fetchFileByUrl(url);

        String content = purifyFileContent(fetchedFile);
        Map<String, Integer> fileWordCount = getWordCounts(content);

        wordCountCache = combineMaps(wordCountCache, fileWordCount);
    }

    public int getWordFrequency(String word) {
        return wordCountCache.getOrDefault(word, 0);
    }
}
