package com.example.Mediateca.Repositories.AdRepository;

import com.example.Mediateca.Domain.AdDO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdRepositoryImpl implements AdRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<AdDO> findAll() {
        String query = "SELECT * FROM anuncios";

        // Ejecuta la consulta y devuelve los resultados.
        return entityManager.createNativeQuery(query, AdDO.class).getResultList();
    }

    @Override
    public AdDO findAdById(Long id) {
        String query = "SELECT * FROM anuncios WHERE ID_ANUNCIO = :id";
        AdDO result = (AdDO) entityManager.createNativeQuery(query, AdDO.class)
                .setParameter("id", id)
                .getSingleResult();
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AdDO> findAdByUserId(Integer userId) {
        String query = "SELECT * FROM anuncios WHERE id_usuario = :userId";
        List<AdDO> result = (List<AdDO>) entityManager.createNativeQuery(query, AdDO.class)
                .setParameter("userId", userId)
                .getResultList();
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AdDO> findLastsImages() {
        String query = "SELECT * FROM anuncios WHERE MULTIMEDIA_URL LIKE '%.jpg%' OR MULTIMEDIA_URL LIKE '%.png%' OR MULTIMEDIA_URL LIKE '%.jpeg%' ORDER BY ID_ANUNCIO DESC LIMIT 20";
        return entityManager.createNativeQuery(query, AdDO.class).getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AdDO> findLastsVideos() {
        String query = "SELECT * FROM anuncios WHERE MULTIMEDIA_URL LIKE '%.mp4%' OR MULTIMEDIA_URL LIKE '%.webm%' ORDER BY ID_ANUNCIO DESC LIMIT 20";
        return entityManager.createNativeQuery(query, AdDO.class).getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AdDO> findLastsAudios() {
        String query = "SELECT * FROM anuncios WHERE MULTIMEDIA_URL LIKE '%.mp3%' OR MULTIMEDIA_URL LIKE '%.ogg%' ORDER BY ID_ANUNCIO DESC LIMIT 20";
        return entityManager.createNativeQuery(query, AdDO.class).getResultList();
    }

    @Override
    public Integer countAllAds() {
        String query = "SELECT COUNT(*) FROM anuncios";
        return (Integer) entityManager.createNativeQuery(query, Integer.class).getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Integer> countAdsByGenderRelation() {
        String query = "SELECT relacion_genero, COUNT(*) AS NUM_ANUNCIOS " +
                "FROM anuncios " +
                "WHERE RELACION_GENERO IS NOT NULL AND RELACION_GENERO != '' " +
                "GROUP BY RELACION_GENERO";

        List<Object[]> resultList = entityManager.createNativeQuery(query).getResultList();
        Map<String, Integer> genderRelationCounts = new HashMap<>();

        for (Object[] result : resultList) {
            String genderRelation = (String) result[0];
            Integer count = ((Number) result[1]).intValue();
            genderRelationCounts.put(genderRelation, count);
        }

        return genderRelationCounts;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Integer> countAdsByMaleRole() {
        String query = "SELECT cat.TIPO_ROL_GENERO_HOMBRE, COUNT(*) AS NUM_ANUNCIOS " +
                "FROM anuncios a " +
                "JOIN rol_genero_hombre cat ON a.ROL_GENERO_HOMBRE = cat.ID_ROL_GENERO_HOMBRE " +
                "WHERE a.ROL_GENERO_HOMBRE IS NOT NULL " +
                "GROUP BY cat.TIPO_ROL_GENERO_HOMBRE";

        List<Object[]> resultList = entityManager.createNativeQuery(query).getResultList();
        Map<String, Integer> maleRoleCounts = new HashMap<>();

        for (Object[] result : resultList) {
            String maleRole = (String) result[0];
            Integer count = ((Number) result[1]).intValue();
            maleRoleCounts.put(maleRole, count);
        }

        return maleRoleCounts;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Integer> countAdsByFemaleRole() {
        String query = "SELECT cat.TIPO_ROL_GENERO_MUJER, COUNT(*) AS NUM_ANUNCIOS " +
                "FROM anuncios a " +
                "JOIN rol_genero_mujer cat ON a.ROL_GENERO_MUJER = cat.ID_ROL_GENERO_MUJER " +
                "WHERE a.ROL_GENERO_MUJER IS NOT NULL " +
                "GROUP BY cat.TIPO_ROL_GENERO_MUJER";

        List<Object[]> resultList = entityManager.createNativeQuery(query).getResultList();
        Map<String, Integer> femaleRoleCounts = new HashMap<>();

        for (Object[] result : resultList) {
            String femaleRole = (String) result[0];
            Integer count = ((Number) result[1]).intValue();
            femaleRoleCounts.put(femaleRole, count);
        }

        return femaleRoleCounts;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Integer> countAdsByChildRole() {
        String query = "SELECT cat.TIPO_ROL_MENOR, COUNT(*) AS NUM_ANUNCIOS " +
                "FROM anuncios a " +
                "JOIN rol_menor cat ON a.ROL_MENOR_1 = cat.ID_ROL_MENOR " +
                "WHERE a.ROL_MENOR_1 IS NOT NULL " +
                "GROUP BY cat.TIPO_ROL_MENOR";

        List<Object[]> result = entityManager.createNativeQuery(query).getResultList();
        Map<String, Integer> childRoleCounts = new HashMap<>();
        for (Object[] row : result) {
            String childRole = (String) row[0];
            Integer numAnuncios = ((Number) row[1]).intValue();
            childRoleCounts.put(childRole, numAnuncios);
        }
        return childRoleCounts;

    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Integer> countAdsByChildGender() {
        String query = "SELECT GENERO, SUM(NUM_ANUNCIOS) AS TOTAL_ANUNCIOS " +
                "FROM ( " +
                "    SELECT GENERO_MENOR_1 AS GENERO, COUNT(*) AS NUM_ANUNCIOS " +
                "    FROM anuncios " +
                "    WHERE GENERO_MENOR_1 IS NOT NULL AND GENERO_MENOR_1 != '' " +
                "    GROUP BY GENERO_MENOR_1 " +
                ") AS GENEROS " +
                "GROUP BY GENERO";

        List<Object[]> result = entityManager.createNativeQuery(query).getResultList();
        Map<String, Integer> genderCount = new HashMap<>();
        for (Object[] row : result) {
            String gender = (String) row[0];
            Integer totalAnuncios = ((Number) row[1]).intValue();
            genderCount.put(gender, totalAnuncios);
        }
        return genderCount;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Integer> countAdsByProtagonism() {
        String query = "SELECT PROTAGONISMO, SUM(NUM_ANUNCIOS) AS TOTAL_ANUNCIOS " +
                "FROM ( " +
                "    SELECT PROTAGONISMO_MENOR_1 AS PROTAGONISMO, COUNT(*) AS NUM_ANUNCIOS " +
                "    FROM anuncios " +
                "    WHERE PROTAGONISMO_MENOR_1 IS NOT NULL AND PROTAGONISMO_MENOR_1 != '' " +
                "    GROUP BY PROTAGONISMO_MENOR_1 " +
                ") AS PROTAGONISMOS " +
                "GROUP BY PROTAGONISMO";

        List<Object[]> result = entityManager.createNativeQuery(query).getResultList();
        Map<String, Integer> protagonismCount = new HashMap<>();
        for (Object[] row : result) {
            String protagonism = (String) row[0];
            Integer totalAnuncios = ((Number) row[1]).intValue();
            protagonismCount.put(protagonism, totalAnuncios);
        }
        return protagonismCount;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Integer> countAdsByTopic() {
        String query = "SELECT cat.NOMBRE_TEMATICA, COUNT(*) AS NUM_ANUNCIOS " +
                "FROM anuncios a " +
                "JOIN tematicas cat ON a.TEMATICA = cat.ID_TEMATICA " +
                "WHERE a.TEMATICA IS NOT NULL " +
                "GROUP BY cat.NOMBRE_TEMATICA";

        List<Object[]> resultList = entityManager.createNativeQuery(query).getResultList();
        Map<String, Integer> topicCounts = new HashMap<>();

        for (Object[] result : resultList) {
            String topic = (String) result[0];
            Integer count = ((Number) result[1]).intValue();
            topicCounts.put(topic, count);
        }

        return topicCounts;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Integer> countAdsByMedia() {
        String query = "SELECT cat.NOMBRE_MEDIO, COUNT(*) AS NUM_ANUNCIOS " +
                "FROM anuncios a " +
                "JOIN medios cat ON a.NOMBRE_MEDIO = cat.ID_MEDIO " +
                "WHERE a.NOMBRE_MEDIO IS NOT NULL " +
                "GROUP BY cat.NOMBRE_MEDIO";

        List<Object[]> resultList = entityManager.createNativeQuery(query).getResultList();
        Map<String, Integer> mediaCounts = new HashMap<>();

        for (Object[] result : resultList) {
            String media = (String) result[0];
            Integer count = ((Number) result[1]).intValue();
            mediaCounts.put(media, count);
        }

        return mediaCounts;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Integer> countAdsByMediaType() {
        String query = "SELECT cat.TIPO_MEDIO_COMUNICACION, COUNT(*) AS NUM_ANUNCIOS " +
                "FROM anuncios a " +
                "JOIN medio_comunicacion cat ON a.TIPO_MEDIO_COMUNICACION = cat.ID_MEDIO_COMUNICACION " +
                "WHERE a.TIPO_MEDIO_COMUNICACION IS NOT NULL " +
                "GROUP BY cat.TIPO_MEDIO_COMUNICACION";

        List<Object[]> resultList = entityManager.createNativeQuery(query).getResultList();
        Map<String, Integer> mediaTypeCounts = new HashMap<>();

        for (Object[] result : resultList) {
            String mediaType = (String) result[0];
            Integer count = ((Number) result[1]).intValue();
            mediaTypeCounts.put(mediaType, count);
        }

        return mediaTypeCounts;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Integer> countAdsByAdvertisingType() {
        String query = "SELECT TIPO_PUBLICIDAD, COUNT(*) AS NUM_ANUNCIOS " +
                "FROM anuncios " +
                "WHERE TIPO_PUBLICIDAD IS NOT NULL AND TIPO_PUBLICIDAD != '' " +
                "GROUP BY TIPO_PUBLICIDAD";

        List<Object[]> resultList = entityManager.createNativeQuery(query).getResultList();
        Map<String, Integer> advertisingTypeCounts = new HashMap<>();

        for (Object[] result : resultList) {
            String advertisingType = (String) result[0];
            Integer count = ((Number) result[1]).intValue();
            advertisingTypeCounts.put(advertisingType, count);
        }

        return advertisingTypeCounts;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Integer> countAdsByDecade() {
        String query = "SELECT (FLOOR(ANIO / 10) * 10) AS DECADA, COUNT(*) AS NUM_ANUNCIOS " +
                "FROM anuncios " +
                "WHERE ANIO IS NOT NULL AND ANIO != '' " +
                "GROUP BY (FLOOR(ANIO / 10) * 10)";

        List<Object[]> resultList = entityManager.createNativeQuery(query).getResultList();
        Map<String, Integer> yearCounts = new HashMap<>();

        for (Object[] result : resultList) {
            Long year = (Long) result[0];
            Integer count = ((Number) result[1]).intValue();
            yearCounts.put(year.toString(), count);
        }

        return yearCounts;
    }

    @Override
    public Integer deleteByUserId(Integer userId) {
        String query = "DELETE FROM anuncios WHERE id_usuario = :userId";
        Integer deletedCount = entityManager.createNativeQuery(query)
                .setParameter("userId", userId)
                .executeUpdate();
        return deletedCount;
    }

}
