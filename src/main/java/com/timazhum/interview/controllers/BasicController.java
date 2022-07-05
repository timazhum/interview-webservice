package com.timazhum.interview.controllers;

import com.timazhum.interview.models.UserPrincipal;
import com.timazhum.interview.services.WordsService;
import com.timazhum.interview.viewmodels.PostRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RestController
public class BasicController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WordsService wordsService;

//    @GetMapping(value = "/getmethod", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public String getMethod(@AuthenticationPrincipal UserPrincipal principal, ModelMap modelMap) {
//        return new JSONObject().put("responsefield", "value").toString();
//    }
//
//    @GetMapping(value = "/getmethodwithbody", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public String getMethodWithBody(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserPrincipal principal, ModelMap modelMap) {
//        return new JSONObject().put("responsefield", "value").toString();
//    }

    @PostMapping(value = "/words", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String analyzeFileWordCount(@RequestBody PostRequestDto postRequestBody, @AuthenticationPrincipal UserPrincipal principal, ModelMap modelMap) throws JSONException {
        wordsService.analyzeFileByUrl(postRequestBody.getUrl());

        return new JSONObject().put("status", "OK").toString();
    }

    @GetMapping(value = "/words/{word}")
    @ResponseBody
    public String getWordFrequency(@PathVariable String word, @AuthenticationPrincipal UserPrincipal principal, ModelMap modelMap) throws JSONException {
        int frequency = wordsService.getWordFrequency(word);
        return new JSONObject().put("frequency", Integer.toString(frequency)).toString();
    }
}
