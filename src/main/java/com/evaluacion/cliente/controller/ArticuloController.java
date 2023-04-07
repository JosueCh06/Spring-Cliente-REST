package com.evaluacion.cliente.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.evaluacion.cliente.entity.Articulo;
import com.google.gson.Gson;


@Controller
@RequestMapping("/cliente")
public class ArticuloController {

	private String URL="http://localhost:8097/articulo/";
	
	
	@RequestMapping("/")
	public String index(Model model) {
		RestTemplate rt=new RestTemplate();
		ResponseEntity<Articulo[]>lista= rt.getForEntity(URL+"listar", Articulo[].class);
		model.addAttribute("articulos",lista.getBody());
		model.addAttribute("articulo", new Articulo());
		return "articulo";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute Articulo bean,RedirectAttributes redirect) {
		try {
			Gson gson=new Gson();
			String json=gson.toJson(bean);
			RestTemplate rt=new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> request = new HttpEntity<>(json, headers);
			rt.postForObject(URL+"registrar", request, String.class);
			redirect.addFlashAttribute("MENSAJE","Articulo registrado");
		} catch (Exception e) {
			e.printStackTrace();
			redirect.addFlashAttribute("MENSAJE","Error en el registro");
		}
		return "redirect:/cliente/";
	}
	
	@RequestMapping("/consulta")
	public String consulta() {
		return "consulta";
	}
	
}
