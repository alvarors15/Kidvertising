package com.example.Mediateca.Services;

import com.example.Mediateca.Domain.TopicDO;
import com.example.Mediateca.Repositories.TopicRepository.TopicRepository;
import com.example.Mediateca.DTOs.TopicDTO;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para la gestion de la tematica de los anuncios.
 */
@Service
@Transactional
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    /**
     * Recupera todas las tematicas de la base de datos
     *
     * @return una lista de TopicDTO.
     */
    public List<TopicDTO> getAll() {
        List<TopicDO> topicDOList = topicRepository.findAll();
        List<TopicDTO> listTopicDTO = new ArrayList<TopicDTO>();
        for (TopicDO topicDO : topicDOList) {
            TopicDTO topicDTO = new TopicDTO();
            topicDTO.fromDO(topicDO);
            listTopicDTO.add(topicDTO);
        }
        return listTopicDTO;
    }

    /**
     * Obtiene la tematica basado en el nombre de la tematica.
     *
     * @param name el nombre de la tematica a buscar en bbdd
     * @return un TopicDO correspondiente a la tematica buscado, o null si
     *         no se encuentra.
     */
    public TopicDO getDOByName(String name) {
        TopicDO topicDO = topicRepository.findByName(name);
        if (topicDO == null) {
            return null;
        }
        return topicDO;
    }

    /**
     * Crea una nueva tematica en la base de datos.
     *
     * @param name el nombre de la tematica.
     * @return el TopicDO creado.
     */
    @Transactional
    public TopicDO save(String name) {
        TopicDO topicDO = new TopicDO();
        topicDO.setTopicName(name);
        return topicRepository.save(topicDO);
    }

    /**
     * Elimina una lista de tematicas por sus identificadores.
     *
     * @param idList la lista de identificadores de las tematicas a
     *               eliminar.
     */
    @SuppressWarnings("null")
    public void deleteByIdList(List<Integer> idList) {
        topicRepository.deleteByIdList(idList);
    }

}