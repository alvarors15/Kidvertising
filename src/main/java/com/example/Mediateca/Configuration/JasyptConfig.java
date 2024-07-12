package com.example.Mediateca.Configuration;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.iv.NoIvGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuracion para Jasypt, una biblioteca de cifrado de Java.
 * 
 * Configura el cifrado de cadenas usando Jasypt.
 */
@Configuration
public class JasyptConfig {

    /**
     * Configura y proporciona un cifrado de cadenas usando Jasypt.
     * 
     * @return una instancia de StringEncryptor configurada para cifrar y descifrar
     *         cadenas.
     */
    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(System.getenv("JASYPT_ENCRYPTOR_PASSWORD"));
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setIvGenerator(new NoIvGenerator());
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        System.out.println(config);
        return encryptor;
    }
}