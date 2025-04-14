import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.rouen.mastergil.tptest.BankAccount;
import fr.rouen.mastergil.tptest.Devise;

class BankAccountUnitTest {

	@Test
	//je ne pense pas que c'est juste a revoir
	public void shouldTestCreerCompteIfItcreatesAnAccountWhith0EURO() {
		//GIVEN
		BankAccount compte = new BankAccount();
		//WHEN
		compte.creerCompte();
		//THEN
		String solde = compte.consulterSolde();
		assertThat(solde).isEqualTo("Votre solde actuel est de 0 EURO");
		
	}
	
	@Test
	//je ne pense pas que c'est juste a revoir
	public void shouldTestcreerCompteWithParams() {
		//GIVEN
		BankAccount compte = new BankAccount();
		//WHEN
		compte.creerCompte(200, Devise.DOLLAR);
		//THEN
		String solde = compte.consulterSolde();
		assertThat(solde).isEqualTo("Votre solde actuel est de 200 DOLLAR");
		
	}
	
	@Test
	public void shouldTestconsulterSoldeIfItReturnsTheMessageThatWeRLookingFor() {
		//GIVEN
		BankAccount compte = new BankAccount();
		compte.creerCompte(200, Devise.DOLLAR);
		//WHEN
		String soldeMessage = compte.consulterSolde();
		
		//THEN
		assertThat(soldeMessage).isEqualTo("Votre solde actuel est de 200 DOLLAR");
	}
	
	@Test
	public void shouldTestIfMontantIncreasesWhenCallingdeposerArgent() {
		//GIVEN
		BankAccount compte = new BankAccount();
		compte.creerCompte(200, Devise.DOLLAR);
		//WHEN
		compte.deposerArgent(300);
		//THEN
		assertThat(compte.consulterSolde()).isEqualTo("Votre solde actuel est de 500 DOLLAR");
	}
	
	@Test
	public void shouldTestIfMontantDecreasesWhenCallingretirerArgent() {
		//GIVEN
		BankAccount compte = new BankAccount();
		compte.creerCompte(200, Devise.DOLLAR);
		//WHEN
		compte.retirerArgent(100);
		//THEN
		assertThat(compte.consulterSolde()).isEqualTo("Votre solde actuel est de 100 DOLLAR");
	}
	
	
	@Test
	public void shouldTestisSoldePositifIfItReturnsTrueWhenSoldeIsGreaterThan0() {
		//GIVEN
		BankAccount compte = new BankAccount();
		compte.creerCompte(200, Devise.DOLLAR);
		//WHEN
		Boolean expectedBool = compte.isSoldePositif();
		//THEN
		assertThat(expectedBool).isTrue();
	}
	
	@Test
	public void shouldTestisSoldePositifIfItReturnsFalseWhenMontantIsLessThan0() {
		//GIVEN
		BankAccount compte = new BankAccount();
		compte.creerCompte(-20, Devise.DOLLAR);
		//WHEN
		Boolean expectedBool = compte.isSoldePositif();
		//THEN
		assertThat(expectedBool).isFalse();
	}
	
	@Test
	public void shouldTestisSoldePositifIfItReturnsTrueWhenSoldeIsEqualsto0() {
		//GIVEN
		BankAccount compte = new BankAccount();
		compte.creerCompte(0, Devise.DOLLAR);
		//WHEN
		Boolean expectedBool = compte.isSoldePositif();
		//THEN
		assertThat(expectedBool).isTrue();
	}
	
	

}
