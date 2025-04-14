import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.rouen.mastergil.tptest.BankAccountWithDAO;
import fr.rouen.mastergil.tptest.Devise;
import fr.rouen.mastergil.tptest.JdbcDAO;
import fr.rouen.mastergil.tptest.Money;

@ExtendWith(MockitoExtension.class)
class BankAccountWithDAOTest {
	@Mock
	JdbcDAO bankDaoMock;
	@Mock
	Connection connectionMock;
	@InjectMocks
	BankAccountWithDAO bankAccountWithDAO;
	
	@Test
	public void shouldBeSuccessfulWhenIsClosedAndIsReadOnlyRFalse() throws SQLException, ConnectException {
	    // GIVEN
	    when(bankDaoMock.setUpConnection()).thenReturn(connectionMock);
	    when(connectionMock.isClosed()).thenReturn(false);
	    when(connectionMock.isReadOnly()).thenReturn(false);

	    // WHEN
	    bankAccountWithDAO.creerCompte();

	    // THEN
	    verify(bankDaoMock).setUpConnection();
	    verify(connectionMock).isClosed();
	    verify(connectionMock).isReadOnly();
	    verify(connectionMock).setAutoCommit(true);
	    verify(bankDaoMock).creerCompte(); 
	}
	
	@Test 
	public void shouldThrowAConnectExceptionWhenIsClosedIsTrue() throws SQLException, ConnectException {
		//GIVEN
		when(bankDaoMock.setUpConnection()).thenReturn(connectionMock);
		when(connectionMock.isClosed()).thenReturn(true);
		when(connectionMock.isReadOnly()).thenReturn(false);
		//THEN
		assertThatThrownBy(() -> {
            //WHEN
            bankAccountWithDAO.creerCompte();
        }).isInstanceOf(ConnectException.class);
		verify(bankDaoMock, times(1)).setUpConnection();
		verify(connectionMock, times(1)).isClosed();
	    verify(connectionMock, times(1)).isReadOnly();
	    verify(connectionMock, times(1)).setAutoCommit(true);
	    verify(bankDaoMock, times(0)).creerCompte(); 
		
		
	}
	@Test
    public void shouldThrowSQLExceptionWhenIsReadOnlyThrowsException() throws SQLException {
        // GIVEN
        when(bankDaoMock.setUpConnection()).thenReturn(connectionMock);
        doThrow(SQLException.class).when(connectionMock).isReadOnly();
        lenient().when(connectionMock.isClosed()).thenReturn(false);//lenient permet de tolerer le when meme si la methode n'est jamais appelée 

        // THEN
        assertThatThrownBy(() -> {
            // WHEN
            bankAccountWithDAO.creerCompte();
        }).isInstanceOf(SQLException.class);

        verify(bankDaoMock, times(1)).setUpConnection();
        verify(connectionMock, times(1)).isReadOnly();
        verify(connectionMock, times(0)).isClosed();
        verify(bankDaoMock, times(0)).creerCompte();
    }
	
	@Test 
	public void shouldThrowSQLExceptionWhenIsReadOnlyIsTrue() throws SQLException {
		//GIVEN
		when(bankDaoMock.setUpConnection()).thenReturn(connectionMock);
		when(connectionMock.isReadOnly()).thenReturn(true);
		lenient().when(connectionMock.isClosed()).thenReturn(false);
		//WHEN
		assertThatThrownBy(() ->{
			
			bankAccountWithDAO.creerCompte();
			
		}).isInstanceOf(ConnectException.class);
		
		//THEN
		verify(bankDaoMock, times(1)).setUpConnection();
		verify(connectionMock, times(1)).isReadOnly();
	    verify(connectionMock, times(0)).isClosed();
	    verify(bankDaoMock, times(0)).creerCompte();
		
	}
	
	@Test
	public void shouldTestIfAnAccountIsCreatedWithCorrectParams() throws SQLException, ConnectException {
		//GIVEN
		int montant = 200;
		Devise devise = Devise.DOLLAR;
		when(bankDaoMock.setUpConnection()).thenReturn(connectionMock);
		when(connectionMock.isClosed()).thenReturn(false);
		when(connectionMock.isReadOnly()).thenReturn(false);
		//WHEN
		bankAccountWithDAO.creerCompte(montant, devise);
		//THEN
		verify(bankDaoMock, times(1)).setUpConnection();
		verify(connectionMock, times(1)).setAutoCommit(true);
		verify(connectionMock, times(1)).isReadOnly();
	    verify(connectionMock, times(1)).isClosed();
	    verify(bankDaoMock,times(1)).creerCompte(montant, devise);
	  
	}
	
	@Test
	public void shouldTestConsulterSolde() throws SQLException, ConnectException {
		//GIVEN
		when(bankDaoMock.setUpConnection()).thenReturn(connectionMock);
		when(connectionMock.isClosed()).thenReturn(false);
		when(connectionMock.isReadOnly()).thenReturn(false);
		when(bankDaoMock.getSolde()).thenReturn(new Money(200,Devise.DOLLAR));
		//WHEN
		
		assertThat(bankAccountWithDAO.consulterSolde()).isEqualTo("Votre solde actuel est de 200 DOLLAR");
		
		//THEN
		verify(bankDaoMock, times(1)).setUpConnection();
		verify(connectionMock, times(1)).setAutoCommit(true);
		verify(connectionMock, times(1)).isReadOnly();
	    verify(connectionMock, times(1)).isClosed();

	}

	@Test
	public void shouldTestDeposerArgent() throws SQLException, ConnectException{
		//GIVEN
		Money money = new Money(200, Devise.DOLLAR);
		when(bankDaoMock.setUpConnection()).thenReturn(connectionMock);
		when(connectionMock.isClosed()).thenReturn(false);
		when(connectionMock.isReadOnly()).thenReturn(false);
		when(bankDaoMock.getSolde()).thenReturn(money);
		//WHEN
		Money solde = bankAccountWithDAO.deposerArgent(500);
		assertThat(solde.getMontant()).isEqualTo(700);
		assertThat(solde.getDevise()).isEqualTo(Devise.DOLLAR);
		
		//THEN
		verify(bankDaoMock, times(1)).setUpConnection();
		verify(connectionMock, times(1)).setAutoCommit(true);
		verify(connectionMock, times(1)).isReadOnly();
	    verify(connectionMock, times(1)).isClosed();
	    verify(bankDaoMock, times(1)).getSolde();
	    verify(bankDaoMock, times(1)).saveMoney(money);
		
	}
	
	@Test 
	public void shouldTestRetirerArgent() throws SQLException, ConnectException{
		//GIVEN
		int montantARetirer = 200;
		Money money = new Money(700, Devise.DOLLAR);
		int expectedMontant = 500;
		Devise expectedDevise = Devise.DOLLAR;
		when(bankDaoMock.setUpConnection()).thenReturn(connectionMock);
		when(connectionMock.isClosed()).thenReturn(false);
		when(connectionMock.isReadOnly()).thenReturn(false);
		when(bankDaoMock.getSolde()).thenReturn(money);
		//WHEN
		Money solde = bankAccountWithDAO.retirerArgent(montantARetirer);
		assertThat(solde.getMontant()).isEqualTo(expectedMontant);
		assertThat(solde.getDevise()).isEqualTo(expectedDevise);
		//THEN
		verify(bankDaoMock, times(1)).setUpConnection();
		verify(connectionMock, times(1)).setAutoCommit(true);
		verify(connectionMock, times(1)).isReadOnly();
	    verify(connectionMock, times(1)).isClosed();
	    verify(bankDaoMock, times(1)).getSolde();
	    verify(bankDaoMock, times(1)).saveMoney(money);
		
	}
	
	@Test
	public void shouldTestIsSoldePositif() throws SQLException, ConnectException{
		
		//GIVEN
		Money money = new Money(700, Devise.DOLLAR);
		when(bankDaoMock.setUpConnection()).thenReturn(connectionMock);
		when(connectionMock.isClosed()).thenReturn(false);
		when(connectionMock.isReadOnly()).thenReturn(false);
		when(bankDaoMock.getSolde()).thenReturn(money);
		//WHEN
		boolean isPositif = bankAccountWithDAO.isSoldePositif();
		assertThat(isPositif).isEqualTo(true);
		
		//THEN
		verify(bankDaoMock, times(1)).setUpConnection();
		verify(connectionMock, times(1)).setAutoCommit(true);
		verify(connectionMock, times(1)).isReadOnly();
	    verify(connectionMock, times(1)).isClosed();
	    verify(bankDaoMock, times(1)).getSolde();
		
	}
	
	

}
