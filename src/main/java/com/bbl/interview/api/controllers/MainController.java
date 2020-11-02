package com.bbl.interview.api.controllers;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bbl.interview.api.models.KeyAPI;
import com.bbl.interview.api.models.Key;
import com.bbl.interview.api.models.RespondResult;
import com.bbl.interview.api.models.ValueAPI;
import com.bbl.interview.api.repositories.KeyAuthRepository;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;

@RestController 

public class MainController {
	@Autowired
	private KeyAuthRepository keyAuthRepository;
	
	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!";
	}
	
	@PostMapping(path="/saveData")
	public @ResponseBody HashMap<String, String> saveData (@RequestBody Key key){
		System.out.println(key.getKey() + ", " + key.getSum());
		keyAuthRepository.save(key);
		HashMap<String, String> map = new HashMap<>();
	    map.put("result", "OK");
	    return map;
	}
	

	
	@GetMapping(path="/getData")
	public @ResponseBody Iterable<Key> getAllKeys() {
		List<Key> allData = keyAuthRepository.findAll();
		JSONArray jsonArray = new JSONArray(allData);
		FileWriter file;
		try {
			file = new FileWriter("allData.json");
			System.out.println(jsonArray.toString());
			file.write(jsonArray.toString());
			file.flush();
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(jsonArray.toString());
		return allData;
	}
	
	@GetMapping(path="/transformKeyValue")
	public @ResponseBody String transformKeyValue() {
		RestTemplate restTemplate = new RestTemplate();
		RespondResult keyAPI = restTemplate.getForObject("https://asia-east2-candidateplayground.cloudfunctions.net/key", KeyAPI.class);
		if (keyAPI.getResult().equals("OK")) {
			String authUrl = "https://asia-east2-candidateplayground.cloudfunctions.net/value";
			restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.set("authorization", ((KeyAPI) keyAPI).getKey());
		    HttpEntity request = new HttpEntity(headers);
		    ResponseEntity<ValueAPI> responseValue = restTemplate.postForEntity(authUrl, request, ValueAPI.class);
		    List<Integer> values = responseValue.getBody().getValue();
		    Integer sum = 0;
		    for (Integer i : values) {
		    	sum += i;	    	  
		    }
		    restTemplate = new RestTemplate();
		    
		    String storeURL = "http://127.0.0.1:8080/saveData";
		    // create headers
		    headers = new HttpHeaders();
		    // set `content-type` header
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    
		    Map<String, String> map = new HashMap<>();
		    map.put("key", ((KeyAPI)keyAPI).getKey());
		    map.put("sum", Integer.toString(sum));
		    System.out.println(map);
		    
		    HttpEntity<Map<String, String>> entity = new HttpEntity<>(map, headers);

		    ResponseEntity<RespondResult> response = restTemplate.postForEntity(storeURL, entity, RespondResult.class);
	 
		    return "key : " + ((KeyAPI)keyAPI).getKey() + ", sum : " + sum;
		}
		return "";
	}

}
