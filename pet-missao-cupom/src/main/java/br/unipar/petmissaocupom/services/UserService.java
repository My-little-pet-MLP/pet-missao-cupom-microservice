package br.unipar.petmissaocupom.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    private final RestTemplate restTemplate;

    @Value("${user.service.url}") // URL do microserviço de usuários
    private String userServiceUrl;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isUserValid(String userId) {
        String url = userServiceUrl + "/users/" + userId; // Monta a URL do microserviço

        try {
            // Faz a requisição ao microserviço de usuários
            String response = restTemplate.getForObject(url, String.class);
            return response != null; // Se a resposta não for nula, o userId é válido
        } catch (Exception e) {
            return false; // Retorna falso se ocorrer algum erro
        }
    }

}
