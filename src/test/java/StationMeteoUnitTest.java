import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.rouen.mastergil.tptest.meteo.IWeatherProvider;
import fr.rouen.mastergil.tptest.meteo.OpenWeatherMapProvider;
import fr.rouen.mastergil.tptest.meteo.Prevision;
import fr.rouen.mastergil.tptest.meteo.StationMeteo;

@ExtendWith(MockitoExtension.class)
class StationMeteoUnitTest {
	
	@Mock
	IWeatherProvider weatherProviderMock;
	
	@InjectMocks
	StationMeteo stationMeteo;
	
	@Test
	public void shouldReturnForecastListByCityName() {
		//GIVEN
		String city = "Paris";
		List<Prevision> expectedPrevisions = List.of(
				new Prevision().setDescription("Sunny").setTempDay(25.0),
				new Prevision().setDescription("Cloudy").setTempDay(20.0)
				);
		
		when(weatherProviderMock.getForecastByCity(city)).thenReturn(expectedPrevisions);
		//WHEN
		List<Prevision> majPrev = stationMeteo.majPrevision(city);
		//THEN
		assertThat(majPrev).isNotEmpty();
		assertThat(majPrev.size()).isEqualTo(2);
		assertThat(majPrev.getFirst().getDescription()).isEqualTo("Sunny");
		assertThat(majPrev.getLast().getDescription()).isEqualTo("Cloudy");
		
		verify(weatherProviderMock, times(1)).getForecastByCity(city);
		
	}
	
	@Test 
	public void shouldThrowAnIllegalArgumentException() {
		
		//GIVEN
		lenient().when(weatherProviderMock.getForecastByCity(null)).thenReturn(null);
		
		//WHEN
		assertThatThrownBy(() ->{
			stationMeteo.majPrevision(null);
		}).isInstanceOf(IllegalArgumentException.class)
		  .hasMessage("City is not optional");
		
		//THEN
		verify(weatherProviderMock,times(0)).getForecastByCity(null);
	}
	
}
