package marlon.leoner.projeto.avaliacao.tecnica.service;

import marlon.leoner.projeto.avaliacao.tecnica.domain.response.ValidateCpfResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class IntegrationService {

    @Value("${cpf.validator.token}")
    private String token;

    @Value("${cpf.validator.type}")
    private String type;

    public Boolean isCpfValid(String cpf) {
        ValidateCpfResponse validate = this.validateCpf(cpf);
        if (Objects.isNull(validate)) {
            return false;
        }

        return validate.getValid();
    }

    private ValidateCpfResponse validateCpf(String cpf) {
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("cpf", cpf);
        params.put("type", type);

        UriComponents url = UriComponentsBuilder
                .fromHttpUrl("https://api.invertexto.com/v1/validator?token={token}&value={cpf}&type={type}")
                .buildAndExpand(params);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ValidateCpfResponse> result = restTemplate.getForEntity(url.toString(), ValidateCpfResponse.class);
        if (result.getStatusCode() == HttpStatus.OK) {
            return result.getBody();
        }

        return null;
    }

}
