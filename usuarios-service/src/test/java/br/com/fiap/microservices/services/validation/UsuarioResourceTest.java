package br.com.fiap.microservices.services.validation;

import static com.jayway.jsonassert.JsonAssert.with;
import static com.jayway.jsonpath.JsonPath.read;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

import br.com.fiap.microservices.GenericControlerITest;
import br.com.fiap.microservices.entities.dto.LoginDTO;
import br.com.fiap.microservices.entities.dto.RoleDTO;
import br.com.fiap.microservices.entities.dto.UsuarioAdicionarDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DatabaseTearDown(value = "/datasets/usuarios/zerar-banco.sql", type = DatabaseOperation.TRUNCATE_TABLE)
public class UsuarioResourceTest extends GenericControlerITest {

	@Test
	public void deveRetornarTodosUsuarios() {
		String jsonReturned = given().when().get("/usuarios/").then().extract().asString();
		// verificar o que o json retorna aqui

		with(jsonReturned).assertThat("$[0].nome", equalTo("Emerson"));
		with(jsonReturned).assertThat("$[0].sobrenome", equalTo("Dias de Oliveira"));
		with(jsonReturned).assertThat("$[0].email", equalTo("emersondiaspd@gmail.com"));
		with(jsonReturned).assertThat("$[0].login", equalTo("11988298946"));
		with(jsonReturned).assertThat("$[0].telefone", equalTo("11988298946"));
		with(jsonReturned).assertThat("$[0].situacao", equalTo(2));

	}

	// ver como faz com post
	@Test
	public void deveRetornarUsuarioPeloId() {
		LoginDTO loginDTO = new LoginDTO("11988298946", "$2a$10$ni/paTq3aKJpIFnTSZDnCOasamoFaY.bx3orFQrhK11owDss2seNy",
				"ROLE_PARCEIROS");
		String jsonReturned = given().contentType("application/json").body(loginDTO).when().post("/usuarios/login")
				.then().extract().asString();
		// verificar o que o json retorna aqui

		with(jsonReturned).assertThat("nome", equalTo("Emerson"));
		with(jsonReturned).assertThat("sobrenome", equalTo("Dias de Oliveira"));
		with(jsonReturned).assertThat("email", equalTo("emersondiaspd@gmail.com"));
		with(jsonReturned).assertThat("login", equalTo("11988298946"));
		with(jsonReturned).assertThat("telefone", equalTo("11988298946"));
		with(jsonReturned).assertThat("situacao", equalTo(2));
	}

	@Test
	public void deveBuscarUsuarioPeloLogin() {
		String jsonReturned = given().when().get("/usuarios/emailzinho@oi.com").then().extract().asString();
		// verificar o que o json retorna aqui
		String jsonValidacaoDados = read(jsonReturned, "$..[?(@.data_referencia == '2019-09-10')]").toString();
		with(jsonValidacaoDados).assertThat("$[0]", equalTo("pipipopo"));
	}

	@Test(expected = Test.None.class)
	public void deveCriarUmNovoUsuario() {
		RoleDTO roleDTO = new RoleDTO(1L, "ROLE_PARCEIROS");
		Set<RoleDTO> roles = new HashSet<RoleDTO>();
		roles.add(roleDTO);
		ThreadLocalRandom random = ThreadLocalRandom.current();
		long nextLong = random.nextLong(10_000_000_000L, 100_000_000_000L);
		UsuarioAdicionarDTO usuarioAdicionarDTO = new UsuarioAdicionarDTO("Marco", "Santos", Long.toString(nextLong),
				Long.toString(nextLong), "123456", "123456", roles);
		given().contentType("application/json").body(usuarioAdicionarDTO).when()
				.post("/usuarios/").then().extract().asString();

	
	}

}
