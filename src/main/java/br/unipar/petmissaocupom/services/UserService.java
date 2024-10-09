package br.unipar.petmissaocupom.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
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
            restTemplate.getForObject(url, String.class);
            return true; // Sucesso: userId é válido
        } catch (HttpClientErrorException e) {
            // Trata erros específicos de cliente, como 404 ou 403
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false; // Usuário não encontrado
            } else if (e.getStatusCode() == HttpStatus.FORBIDDEN) {
                return false; // Acesso negado para esse userId
            }
            throw e; // Lança exceções diferentes para tratamento posterior
        } catch (ResourceAccessException e) {
            // Trata problemas de conexão ou indisponibilidade do serviço
            throw new RuntimeException("Erro ao acessar o serviço de usuários: " + e.getMessage());
        } catch (Exception e) {
            // Para qualquer outro erro, pode-se lançar uma exceção genérica
            throw new RuntimeException("Erro desconhecido ao validar usuário: " + e.getMessage());
        }
    }

}
