import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.rouen.mastergil.tptest.meteo.OpenWeatherMapProvider;
import fr.rouen.mastergil.tptest.meteo.Prevision;

class OpenWeatherMapProviderIT {

	@Test
	public void shouldReturnPrevisionsFromAPI() {
		//GIVEN
		OpenWeatherMapProvider openWeatherMapProvider = new OpenWeatherMapProvider();
		String city = "Paris,FR";
		
		//WHEN
		List<Prevision> previsions = openWeatherMapProvider.getForecastByCity(city);
		//THEN
		assertThat(previsions).isNotEmpty();
		assertThat(previsions.get(0).getDescription()).isNotBlank();
		assertThat(previsions.get(0).getDate()).isNotNull();
	}
	
	
}
