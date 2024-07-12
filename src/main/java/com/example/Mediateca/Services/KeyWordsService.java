package com.example.Mediateca.Services;

import com.example.Mediateca.Domain.KeyWordsDO;
import com.example.Mediateca.Repositories.KeyWordsRepository.KeyWordsRepository;
import com.example.Mediateca.DTOs.KeyWordsDTO;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para la gestion de palabras clave de los anuncios.
 */
@Service
@Transactional
public class KeyWordsService {

    @Autowired
    private KeyWordsRepository keyWordsRepository;

    /**
     * Recupera todas las palabras clave de la base de datos
     *
     * @return una lista de KeyWordsDTO.
     */
    public List<KeyWordsDTO> getAll() {
        List<KeyWordsDO> keyWordsDOList = keyWordsRepository.findAll();
        List<KeyWordsDTO> listKeyWordsDTO = new ArrayList<KeyWordsDTO>();
        for (KeyWordsDO keyWordsDO : keyWordsDOList) {
            KeyWordsDTO keyWordsDTO = new KeyWordsDTO();
            keyWordsDTO.fromDO(keyWordsDO);
            listKeyWordsDTO.add(keyWordsDTO);
        }
        return listKeyWordsDTO;
    }

    /**
     * Busca y devuelve una lista de palabras clave basada en una lista de entrada.
     * Sirve para validar si las palabras clave existen en la base de datos.
     *
     * @param words la lista de palabras a buscar en bbdd
     * @return la lista de palabras encontradas en bbdd
     */
    public List<String> getWordsByList(List<String> words) {
        List<String> keyWordsList = keyWordsRepository.findWordsByList(words);
        return keyWordsList;
    }

    /**
     * Crea una nueva palabra clave en la base de datos.
     *
     * @param word la palabra clave.
     * @return el KeyWordsDO creado.
     */
    @Transactional
    public KeyWordsDO save(String word) {
        KeyWordsDO keyWordsDO = new KeyWordsDO();
        keyWordsDO.setWord(word);
        return keyWordsRepository.save(keyWordsDO);
    }

    /**
     * Elimina una lista de palabras clave por sus identificadores.
     *
     * @param idList la lista de identificadores de las palabras clave a
     *               eliminar.
     */
    @SuppressWarnings("null")
    public void deleteByIdList(List<Integer> idList) {
        keyWordsRepository.deleteByIdList(idList);
    }
}