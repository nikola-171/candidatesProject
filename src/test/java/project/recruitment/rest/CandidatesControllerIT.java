package project.recruitment.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CandidatesControllerIT
{
    @LocalServerPort
    private int _port;

    private String _baseUrl;
    @BeforeEach
    public void init()
    {
        _baseUrl = "http://localhost:" + _port;
    }

    @Test
    public void ShouldCheckAnonymousCalls()
    {
        final TestRestTemplate template = new TestRestTemplate();

        String url = _baseUrl + "/candidates";
        ResponseEntity<?> response = template.getForEntity(url, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
