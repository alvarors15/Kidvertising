package com.example.Mediateca.Services;

import com.example.Mediateca.DTOs.GenderRoleFemaleTypeDTO;
import com.example.Mediateca.Domain.GenderRoleFemaleTypeDO;
import com.example.Mediateca.Repositories.GenderRoleFemaleTypeRepository.GenderRoleFemaleTypeRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para la gestion de tipos de roles de la mujer.
 * Este servicio ofrece metodos para obtener, crear y eliminar
 * tipos de roles de la mujer.
 */
@Service
@Transactional
public class GenderRoleFemaleTypeService {

    @Autowired
    private GenderRoleFemaleTypeRepository genderRoleFemaleTypeRepository;

    /**
     * Recupera todos los tipos de roles de la mujer de la base de datos
     *
     * @return una lista de GenderRoleFemaleTypeDTO.
     */
    public List<GenderRoleFemaleTypeDTO> getAll() {
        List<GenderRoleFemaleTypeDO> genderRoleFemaleTypeDOList = genderRoleFemaleTypeRepository.findAll();
        List<GenderRoleFemaleTypeDTO> listGenderRoleFemaleTypeDTO = new ArrayList<GenderRoleFemaleTypeDTO>();
        for (GenderRoleFemaleTypeDO genderRoleFemaleTypeDO : genderRoleFemaleTypeDOList) {
            GenderRoleFemaleTypeDTO genderRoleFemaleTypeDTO = new GenderRoleFemaleTypeDTO();
            genderRoleFemaleTypeDTO.fromDO(genderRoleFemaleTypeDO);
            listGenderRoleFemaleTypeDTO.add(genderRoleFemaleTypeDTO);
        }
        return listGenderRoleFemaleTypeDTO;
    }

    /**
     * Obtiene el tipo de rol basado en el nombre del rol.
     *
     * @param role el nombre del rol de la mujer.
     * @return un GenderRoleFemaleTypeDTO correspondiente al rol buscado, o null si
     *         no se encuentra.
     */
    public GenderRoleFemaleTypeDO getDOByRole(String role) {
        GenderRoleFemaleTypeDO genderRoleFemaleTypeDO = genderRoleFemaleTypeRepository.findByRole(role);
        if (genderRoleFemaleTypeDO == null) {
            return null;
        }
        return genderRoleFemaleTypeDO;
    }

    /**
     * Crea un nuevo tipo de rol de la mujer en la base de datos.
     *
     * @param role el nombre del nuevo rol de la mujer.
     * @return el GenderRoleFemaleTypeDO creado.
     */
    @Transactional
    public GenderRoleFemaleTypeDO save(String role) {
        GenderRoleFemaleTypeDO genderRoleFemaleTypeDO = new GenderRoleFemaleTypeDO();
        genderRoleFemaleTypeDO.setGenderRoleFemaleType(role);
        return genderRoleFemaleTypeRepository.save(genderRoleFemaleTypeDO);
    }

    /**
     * Elimina una lista de tipos de roles de la mujer por sus identificadores.
     *
     * @param idList la lista de identificadores de los tipos de roles de la mujer a
     *               eliminar.
     */
    @SuppressWarnings("null")
    public void deleteByIdList(List<Integer> idList) {
        genderRoleFemaleTypeRepository.deleteByIdList(idList);
    }
}