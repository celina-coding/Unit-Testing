import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

import org.junit.jupiter.api.Test;

import fr.rouen.mastergil.tptest.meteo.Prevision;

class PrevisionUnitTest {

	@Test
	public void shouldTestGetDate() {
		//GIVEN
		Prevision prevision = new Prevision();
		Date expectedDate =  new Date();
		prevision.setDate(expectedDate);
		//WHEN
		Date actualDate = prevision.getDate();
		//THEN
		assertThat(actualDate).isEqualToIgnoringHours(expectedDate);
	}
	
	@Test 
	public void shouldTestSetDate() {
		//GIVEN
		Prevision prevision = new Prevision();
		Date date =  new Date();
		//WHEN
		Prevision pr = prevision.setDate(date);
		//THEN
		assertThat(pr).isSameAs(prevision);
		assertThat(prevision.getDate()).isEqualTo(date);
	}
	
	@Test
	public void shouldTestSetTempMin() {
		//GIVEN
		double tempMin = 3600.00;
		Prevision prevision = new Prevision();
		//WHEN
		Prevision pr = prevision.setTempMin(tempMin);
		
		//THEN
		assertThat(pr.getTempMin()).isEqualTo(tempMin);
	}
	
	@Test
	public void shouldTestSetTempMax() {
		//GIVEN
		Prevision prevision = new Prevision();
		double tempMax = 4.00;
		//WHEN
		Prevision pr = prevision.setTempMax(tempMax);
		//THEN
		assertThat(pr.getTempMax()).isEqualTo(tempMax);
		
	}
	
	@Test
	public void shouldTestSetTempDay() {
		//GIVEN
		Prevision prevision = new Prevision();
		double tempDay = 8.00;
		//WHEN
		Prevision pr = prevision.setTempDay(tempDay);
		//THEN
		assertThat(pr.getTempDay()).isEqualTo(tempDay);
	}
	
	@Test 
	public void shouldTestSetTempNight() {
		//GIVEN
		Prevision prevision = new Prevision();
		double tempNight = 8.00;
		//WHEN
		Prevision pr = prevision.setTempNight(tempNight);
		//THEN
		assertThat(pr.getTempNight()).isEqualTo(tempNight);
	}
	
	@Test 
	public void shouldTestSetDescription() {
		//GIVEN
		Prevision prevision = new Prevision();
		String desc = "this is the prevision of the day";
		//WHEN
		Prevision pr = prevision.setDescription(desc);
		
		//THEN
		assertThat(pr.getDescription()).isEqualTo(desc);
	}
	
	@Test
	public void shouldTestToString() {
		//GIVEN
		Prevision prevision = new Prevision();
		Date expectedDate =  new Date();
		prevision.setDate(expectedDate);
		double tempMin = 3600.00;
		prevision.setTempMin(tempMin);
		double tempMax = 4.00;
		prevision.setTempMax(tempMax);
		double tempDay = 8.00;
		prevision.setTempDay(tempDay);
		double tempNight = 8.00;
		prevision.setTempNight(tempNight);
		String desc = "this is the prevision of the day";
		prevision.setDescription(desc);
		String expectedToString = "Prevision{" +
	            "date=" + expectedDate +
	            ", tempMin=" + tempMin +
	            ", tempMax=" + tempMax +
	            ", tempDay=" + tempDay +
	            ", tempNight=" + tempNight +
	            ", description='" + desc + "'" +
	            "}";
		
		//WHEN
		String prev = prevision.toString();
		//THEN
		assertThat(prev).isEqualTo(expectedToString);
	}


}
