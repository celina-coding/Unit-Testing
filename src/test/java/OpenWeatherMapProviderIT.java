import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.WireMockServer;
import fr.rouen.mastergil.tptest.meteo.OpenWeatherMapProvider;
import fr.rouen.mastergil.tptest.meteo.Prevision;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OpenWeatherMapProviderTest {

    private WireMockServer wireMockServer;

    @BeforeEach
    void setup() {
        // Démarrer le serveur WireMock sur le port 8080
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
        configureFor("localhost", 8080);
        
        // Modifier l'URL de l'API pour les tests
        System.setProperty("openweather.api.url", "http://localhost:8080");
    }

    @Test
    void shouldReturnWeatherForecast() {
        // Configuration de la réponse mockée
        stubFor(get(urlPathEqualTo("/data/2.5/forecast/daily"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
               ));

        OpenWeatherMapProvider provider = new OpenWeatherMapProvider();
        List<Prevision> result = provider.getForecastByCity("Paris");

        // Vérifications
        assertThat(result).hasSize(12);
        Prevision forecast = result.get(0);
        assertThat(forecast.getDescription()).isEqualTo("légère pluie");
        assertThat(forecast.getTempMin()).isEqualTo(281.8);
        assertThat(forecast.getTempMax()).isEqualTo(290.71);
    }

    @AfterEach
    void tearDown() {
        // Arrêter le serveur WireMock après chaque test
        wireMockServer.stop();
        System.clearProperty("openweather.api.url");
    }
}