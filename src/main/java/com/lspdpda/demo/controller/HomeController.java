package com.lspdpda.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping({ "/index", "/home", "/", "/..." })
	public String index() {
		return "home";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/forbidden")
	public String forbidden() {
		return "forbidden";
	}

}
