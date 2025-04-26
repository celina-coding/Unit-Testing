import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.tomakehurst.wiremock.WireMockServer;
import fr.rouen.mastergil.tptest.meteo.OpenWeatherMapProvider;
import fr.rouen.mastergil.tptest.meteo.Prevision;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OpenWeatherMapProviderTest {
	
	@Test
	public void shouldReturnWeatherForecast() {
		//connexion au serveur directement
		//GIVEN 
		//démarrer le serveur wireMock sur le port 8080
		WireMockServer wireMockServer = new WireMockServer(8080);
		wireMockServer.start();
		configureFor("localhost", 8080);
		
		//mocker la reponse du serveur 
		stubFor(get(urlPathEqualTo("/data/2.5/forecast/daily"))
				.withQueryParam("APPID", equalTo("661608d780618193596c7321268a4717"))
                .withQueryParam("lang", equalTo("fr"))
                .withQueryParam("units", equalTo("metrics"))
                .withQueryParam("cnt", equalTo("12"))
                .withQueryParam("mode", equalTo("json"))
                .withQueryParam("q", equalTo("Paris"))
	            .willReturn(aResponse()
	                .withStatus(200)
	                .withHeader("Content-Type", "application/json")
	               ));
		OpenWeatherMapProvider openWeatherProvider = new OpenWeatherMapProvider();
		
		//WHEN
		List<Prevision> result = openWeatherProvider.getForecastByCity("Paris");
		
		
		//THEN
		assertThat(result.size()).isNotEqualTo(0);
		assertThat(result.getFirst().getDescription()).isEqualTo("partiellement nuageux");
		assertThat(result.getFirst().getTempMin()).isEqualTo(281.02);
		assertThat(result.getFirst().getTempMax()).isEqualTo(291.71);
		wireMockServer.stop();

	}
	
	@Test 
	public void shouldReturnAnExceptionWhenServerStatusIsDifferentThan200() {
		//utilisation de proxy pour la redirection temporaire vers le port 3300
	    // GIVEN
	    WireMockServer wireMockServer = new WireMockServer(3300);
	    wireMockServer.start();
	    configureFor("localhost", 3300);
	    
	  
	    // Solution TEMPORAIRE pour rediriger les requêtes
	    System.setProperty("http.proxyHost", "localhost");
	    System.setProperty("http.proxyPort", "3300");
	    
	    try {
	    	  // Mock configuration
		    stubFor(get(urlPathEqualTo("/data/2.5/forecast/daily"))
		    		.withQueryParam("APPID", equalTo("661608d780618193596c7321268a4717"))
	                .withQueryParam("lang", equalTo("fr"))
	                .withQueryParam("units", equalTo("metrics"))
	                .withQueryParam("cnt", equalTo("12"))
	                .withQueryParam("mode", equalTo("json"))
	                .withQueryParam("q", equalTo("Paris"))
		        .willReturn(aResponse()
		            .withStatus(404)
		            .withHeader("Content-Type", "application/json")
		            .withBody("{\"message\": \"Not found\"}")));

	        OpenWeatherMapProvider provider = new OpenWeatherMapProvider();
	        
	        // WHEN & THEN
	        assertThatThrownBy(() -> provider.getForecastByCity("Paris"))
	            .isInstanceOf(RuntimeException.class)
	            .hasMessage("Failed : HTTP error code : 404");
	    } finally {
	        // Nettoyage
	        System.clearProperty("http.proxyHost");
	        System.clearProperty("http.proxyPort");
	        wireMockServer.stop();
	    }
	}
	
	
	@Test
	public void shouldHandleJsonParsingException() {
	    // GIVEN
	    WireMockServer wireMockServer = new WireMockServer(3300);
	    wireMockServer.start();
	    configureFor("localhost", 3300);
	    
	    // Configuration du proxy temporaire
	    System.setProperty("http.proxyHost", "localhost");
	    System.setProperty("http.proxyPort", "3300");
	    
	    try {
	        // Mock une réponse HTTP 200 mais avec un JSON invalide
	        stubFor(get(urlPathEqualTo("/data/2.5/forecast/daily"))
	        		.withQueryParam("APPID", equalTo("661608d780618193596c7321268a4717"))
	                .withQueryParam("lang", equalTo("fr"))
	                .withQueryParam("units", equalTo("metrics"))
	                .withQueryParam("cnt", equalTo("12"))
	                .withQueryParam("mode", equalTo("json"))
	                .withQueryParam("q", equalTo("Paris"))
	            .willReturn(aResponse()
	                .withStatus(200)
	                .withHeader("Content-Type", "application/json")
	                .withBody("Invalid JSON"))); 
	        OpenWeatherMapProvider provider = new OpenWeatherMapProvider();
	        
	        // WHEN
	        List<Prevision> result = provider.getForecastByCity("Paris");
	        
	        // THEN
	        assertThat(result).isEmpty(); 
	        
	    } finally {
	        // Nettoyage
	        System.clearProperty("http.proxyHost");
	        System.clearProperty("http.proxyPort");
	        wireMockServer.stop();
	    }
	}

  
}