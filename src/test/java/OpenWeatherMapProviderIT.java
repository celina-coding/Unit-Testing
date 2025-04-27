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
		//GIVEN
		OpenWeatherMapProvider openWeatherProvider = new OpenWeatherMapProvider();
		
		//WHEN
		List<Prevision> result = openWeatherProvider.getForecastByCity("Paris");
		
		
		//THEN
		assertThat(result.size()).isNotEqualTo(0);
		assertThat(result).isNotEmpty();
		

	}
	
	@Test
	public void shouldThrowAnExceptionWhenTheCityDoesNotExist() {
	    // GIVEN
	    OpenWeatherMapProvider provider = new OpenWeatherMapProvider();
	    
	    // WHEN & THEN 
	    assertThatThrownBy(() -> provider.getForecastByCity("helloxyz"))
	        .isInstanceOf(RuntimeException.class)
	        .hasMessageContaining("HTTP error code : 404"); 
	}
	
}