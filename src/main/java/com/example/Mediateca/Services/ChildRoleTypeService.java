package com.example.Mediateca.Services;

import com.example.Mediateca.Domain.ChildRoleTypeDO;
import com.example.Mediateca.Repositories.ChildRoleTypeRepository.ChildRoleTypeRepository;
import com.example.Mediateca.DTOs.ChildRoleTypeDTO;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para la gestion de tipos de roles de menores.
 * Este servicio ofrece metodos para obtener, crear y eliminar
 * tipos de roles de menores.
 */
@Service
@Transactional
public class ChildRoleTypeService {

    @Autowired
    private ChildRoleTypeRepository childTypeRoleRepository;

    /**
     * Recupera todos los tipos de roles de menores de la base de datos
     *
     * @return una lista de ChildRoleTypeDTO.
     */
    public List<ChildRoleTypeDTO> getAll() {
        List<ChildRoleTypeDO> childRoleTypeDOList = childTypeRoleRepository.findAll();
        List<ChildRoleTypeDTO> listChildRoleTypeDTO = new ArrayList<ChildRoleTypeDTO>();
        for (ChildRoleTypeDO childRoleTypeDO : childRoleTypeDOList) {
            ChildRoleTypeDTO childRoleTypeDTO = new ChildRoleTypeDTO();
            childRoleTypeDTO.fromDO(childRoleTypeDO);
            listChildRoleTypeDTO.add(childRoleTypeDTO);
        }
        return listChildRoleTypeDTO;
    }

    /**
     * Obtiene el tipo de rol basado en el nombre del rol.
     *
     * @param role el nombre del rol de menor.
     * @return un ChildRoleTypeDTO correspondiente al rol buscado, o null si
     *         no se encuentra.
     */
    public ChildRoleTypeDO getDOByRole(String role) {
        ChildRoleTypeDO childRoleTypeDO = childTypeRoleRepository.findByRole(role);
        if (childRoleTypeDO == null) {
            return null;
        }
        return childRoleTypeDO;
    }

    /**
     * Crea un nuevo tipo de rol de menor en la base de datos.
     *
     * @param childRoleType el nombre del nuevo rol de menor.
     * @return el ChildRoleTypeDO creado.
     */
    @Transactional
    public ChildRoleTypeDO save(String childRoleType) {
        ChildRoleTypeDO childRoleTypeDO = new ChildRoleTypeDO();
        childRoleTypeDO.setChildRole(childRoleType);
        return childTypeRoleRepository.save(childRoleTypeDO);
    }

    /**
     * Elimina una lista de tipos de roles de menores por sus identificadores.
     *
     * @param idList la lista de identificadores de los tipos de roles de menores a
     *               eliminar.
     */
    @SuppressWarnings("null")
    public void deleteByIdList(List<Integer> idList) {
        childTypeRoleRepository.deleteByIdList(idList);
    }
}