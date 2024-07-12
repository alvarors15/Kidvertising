package com.example.Mediateca.Services;

import com.example.Mediateca.Domain.GenderRoleMaleTypeDO;
import com.example.Mediateca.Repositories.GenderRoleMaleTypeRepository.GenderRoleMaleTypeRepository;
import com.example.Mediateca.DTOs.GenderRoleMaleTypeDTO;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para la gestion de tipos de roles del hombre.
 * Este servicio ofrece metodos para obtener, crear y eliminar
 * tipos de roles del hombre.
 */
@Service
@Transactional
public class GenderRoleMaleTypeService {

    @Autowired
    private GenderRoleMaleTypeRepository genderRoleMaleTypeRepository;

    /**
     * Recupera todos los tipos de roles del hombre de la base de datos
     *
     * @return una lista de GenderRoleMaleTypeDTO.
     */
    public List<GenderRoleMaleTypeDTO> getAll() {
        List<GenderRoleMaleTypeDO> genderRoleMaleTypeDOList = genderRoleMaleTypeRepository.findAll();
        List<GenderRoleMaleTypeDTO> listGenderRoleMaleTypeDTO = new ArrayList<GenderRoleMaleTypeDTO>();
        for (GenderRoleMaleTypeDO genderRoleMaleTypeDO : genderRoleMaleTypeDOList) {
            GenderRoleMaleTypeDTO genderRoleMaleTypeDTO = new GenderRoleMaleTypeDTO();
            genderRoleMaleTypeDTO.fromDO(genderRoleMaleTypeDO);
            listGenderRoleMaleTypeDTO.add(genderRoleMaleTypeDTO);
        }
        return listGenderRoleMaleTypeDTO;
    }

    /**
     * Obtiene el tipo de rol basado en el nombre del rol.
     *
     * @param role el nombre del rol del hombre.
     * @return un GenderRoleMaleTypeDTO correspondiente al rol buscado, o null si
     *         no se encuentra.
     */
    public GenderRoleMaleTypeDO getDOByRole(String role) {
        GenderRoleMaleTypeDO genderRoleMaleTypeDO = genderRoleMaleTypeRepository.findByRole(role);
        if (genderRoleMaleTypeDO == null) {
            return null;
        }
        return genderRoleMaleTypeDO;
    }

    /**
     * Crea un nuevo tipo de rol del hombre en la base de datos.
     *
     * @param role el nombre del nuevo rol del hombre.
     * @return el GenderRoleMaleTypeDTO creado.
     */
    @Transactional
    public GenderRoleMaleTypeDO save(String role) {
        GenderRoleMaleTypeDO genderRoleMaleTypeDO = new GenderRoleMaleTypeDO();
        genderRoleMaleTypeDO.setGenderRoleMaleType(role);
        return genderRoleMaleTypeRepository.save(genderRoleMaleTypeDO);
    }

    /**
     * Elimina una lista de tipos de roles del hombre por sus identificadores.
     *
     * @param idList la lista de identificadores de los tipos de roles del hombre a
     *               eliminar.
     */
    @SuppressWarnings("null")
    public void deleteByIdList(List<Integer> idList) {
        genderRoleMaleTypeRepository.deleteByIdList(idList);
    }
}