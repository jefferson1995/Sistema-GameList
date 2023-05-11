package com.devsuperior.dslist.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dslist.entities.dto.GameListDTO;
import com.devsuperior.dslist.entities.dto.GameMinDTO;
import com.devsuperior.dslist.services.GameListService;
import com.devsuperior.dslist.services.GameService;

@RestController
@RequestMapping(value = "/lists")
public class GameListController {

	@Autowired
	private GameListService gameListService;
	
	@Autowired
	GameService gameService;
	
	@GetMapping
	public ResponseEntity <List<GameListDTO>> findAll(){
		List<GameListDTO> result = gameListService.findAll();		
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping(value = "/{listId}/games")
	public ResponseEntity <List<GameMinDTO>> findByList(@PathVariable Long listId){
		List<GameMinDTO> result = gameService.findByList(listId);		
		return ResponseEntity.ok().body(result);
	}
	
}
