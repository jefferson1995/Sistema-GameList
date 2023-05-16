package com.devsuperior.dslist.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dslist.dto.GameListDTO;
import com.devsuperior.dslist.entities.GameList;
import com.devsuperior.dslist.projections.GameMinProjection;
import com.devsuperior.dslist.repositories.GameListRepository;
import com.devsuperior.dslist.repositories.GameRepository;


@Service
public class GameListService {

	@Autowired
	private GameListRepository gameListRepository;
	
	@Autowired
	private GameRepository gameRepository;

	@Transactional(readOnly = true)
	public List<GameListDTO> findAll() {
		List<GameList> result = gameListRepository.findAll();
		return result.stream().map(x -> new GameListDTO(x)).toList();
	}
	
	@Transactional
	public void move(Long listId, int sourceIndex, int destinationIndex) {

		List<GameMinProjection> list = gameRepository.searchByList(listId); // Obtém uma lista de jogos do repositório com o ID fornecido

		GameMinProjection obj = list.remove(sourceIndex);
		list.add(destinationIndex, obj); // Insere o jogo obj na posição destinationIndex da lista

		/*
		 * Ao determinar o min e o max, é possível definir os limites do loop for para que ele 
		 * itere apenas sobre os índices que foram afetados pela mudança. Isso evita a necessidade de
		 * iterar por toda a lista, o que seria desnecessário e menos eficiente.
		 */
		
		int min = sourceIndex < destinationIndex ? sourceIndex : destinationIndex;
		int max = sourceIndex < destinationIndex ? destinationIndex : sourceIndex;

		for (int i = min; i <= max; i++) {
			gameListRepository.updateBelongingPosition(listId, list.get(i).getId(), i); //
		}
	}

	

}
