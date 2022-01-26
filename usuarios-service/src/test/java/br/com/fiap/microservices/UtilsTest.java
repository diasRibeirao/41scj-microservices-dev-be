package br.com.fiap.microservices;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import br.com.fiap.microservices.utils.Utils;

public class UtilsTest {
	@Mock
	Calendar calendar;


	@Test
	public void buildCodigoAtivacaoTest() {
		String buildCodigoAtivacao = Utils.buildCodigoAtivacao();
		assertTrue(buildCodigoAtivacao instanceof String);
		//size of buildCodigoAtivacao 
		
		assertThat(buildCodigoAtivacao.length()).isEqualTo(4);

	}
}
