import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.rouen.mastergil.tptest.Devise;
import fr.rouen.mastergil.tptest.Money;

class MoneyUnitTest {

	@Test
	public void constructorWithoutParams_shouldInitializeObjectWith0Euro() {
		//GIVEN
		Money money = new Money();
		Devise expectedDevise = Devise.EURO;
		int expectedMontant = 0;
		//WHEN
		Devise actualDevise = money.getDevise();
		int actualMontant = money.getMontant();
		//THEN
		assertEquals(expectedDevise, actualDevise);
		assertEquals(expectedMontant, actualMontant);
		
	}
	
	@Test
	public void constructorWithParams_shouldInitializeObjectWithTHeProperParams() {
		//GIVEN
		Money money = new Money(200,Devise.DOLLAR);
		Devise expectedDevise = Devise.DOLLAR;
		int expectedMontant = 200;
		//WHEN
		Devise actualDevise = money.getDevise();
		int actualMontant = money.getMontant();
		
		//THEN
		assertEquals(expectedDevise, actualDevise);
		assertEquals(expectedMontant, actualMontant);
	}
	
	@Test
	public void shouldReturnTrueWhenMontantIsPositif() {
		//GIVEN
		Money money = new Money(200,Devise.DOLLAR);
		//WHEN
		Boolean positif = money.isPositif();
		//THEN
		assertTrue(positif);
	}
	
	@Test
	public void shouldReturnFalseWhenMontantIsNegatif() {
		//GIVEN
		Money money = new Money(-200,Devise.DOLLAR);
		//WHEN
		Boolean positif = money.isPositif();
		//THEN
		assertFalse(positif);

	}
	
	@Test
	public void shouldReturnTrueWhenMontantIsEqualsToZero() {
		//GIVEN
				Money money = new Money(0,Devise.DOLLAR);
				//WHEN
				Boolean positif = money.isPositif();
				//THEN
				assertTrue(positif);
	}
	
	
	@Test
	public void shouldThrowAnIllegalExceptionWhenDeviseIsSettedToNull() throws Exception {
		//GIVEN
		Money money = new Money(200,Devise.DOLLAR);
		//WHEN & THEN
		assertThrows(IllegalArgumentException.class, () ->{
			money.setDevise(null);
		});
		
	}
	
	@Test
	public void shouldTestgetMontant() {
		//GIVEN
		Money money = new Money(200,Devise.DOLLAR);
		//WHEN
		int montant = money.getMontant();
		//THEN
		assertEquals(200, montant);
	}
	
	@Test
	public void shouldTestgetDevise() {
		//GIVEN
				Money money = new Money(200,Devise.DOLLAR);
				//WHEN
				Devise devise = money.getDevise();
				//THEN
				assertEquals(Devise.DOLLAR,devise);
	}
	
}
