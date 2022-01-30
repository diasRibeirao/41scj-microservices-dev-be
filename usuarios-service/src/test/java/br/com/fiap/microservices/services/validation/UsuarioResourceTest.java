package br.com.fiap.microservices.services.validation;

import static com.jayway.jsonassert.JsonAssert.with;
import static io.restassured.RestAssured.given;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

import br.com.fiap.microservices.entities.dto.LoginDTO;
import br.com.fiap.microservices.entities.dto.RoleDTO;
import br.com.fiap.microservices.entities.dto.UsuarioAdicionarDTO;
import br.com.fiap.microservices.entities.dto.UsuarioAtivarDTO;
import br.com.fiap.microservices.entities.dto.UsuarioAtualizarDTO;


//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class, SqlScriptsTestExecutionListener.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql( "classpath:/db/integration/V1_Create_Table_Usuarios_test.sql" )
@Sql( scripts = "classpath:/db/integration/V2__Populate_Table_Usuarios_test.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)

@Sql( scripts = "classpath:/db/integration/zerar-banco.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioResourceTest  {

	@Test
	public void deveRetornarTodosUsuarios() {
		String jsonReturned = given().when().get("/usuarios/").then().extract().asString();
		// verificar o que o json retorna aqui

		with(jsonReturned).assertEquals("$[0].nome", "Emerson");
		with(jsonReturned).assertEquals("$[0].sobrenome","Dias de Oliveira");
		with(jsonReturned).assertEquals("$[0].email", ("emersondiaspd@gmail.com"));
		with(jsonReturned).assertEquals("$[0].login", ("11988298946"));
		with(jsonReturned).assertEquals("$[0].telefone", ("11988298946"));
		with(jsonReturned).assertEquals("$[0].situacao", (2));

	}

	// ver como faz com post
	@Test
	public void deveRetornarUsuarioPeloId() {
		String jsonReturned = given().when().get("/usuarios/1")
				.then().extract().asString();
		// verificar o que o json retorna aqui

		with(jsonReturned).assertEquals("nome", ("Emerson"));
		with(jsonReturned).assertEquals("sobrenome", ("Dias de Oliveira"));
		with(jsonReturned).assertEquals("email", ("emersondiaspd@gmail.com"));
		with(jsonReturned).assertEquals("login", ("11988298946"));
		with(jsonReturned).assertEquals("telefone", ("11988298946"));
		with(jsonReturned).assertEquals("situacao", (2));
	}

	@Test
	public void deveBuscarUsuarioPeloLogin() {
		RoleDTO roleDTO = new RoleDTO(1L, "ROLE_PARCEIROS");
		Set<RoleDTO> roles = new HashSet<RoleDTO>();
		roles.add(roleDTO);
		LoginDTO usuarioAdicionar = new LoginDTO(
				"11988298946", "123456", "ROLE_PARCEIROS");
		String jsonReturned = given().contentType("application/json").body(usuarioAdicionar).when()
		.post("/usuarios/login/").then().extract().asString();
		with(jsonReturned).assertEquals("nome", ("Emerson"));
		with(jsonReturned).assertEquals("sobrenome", ("Dias de Oliveira"));
		with(jsonReturned).assertEquals("email", ("emersondiaspd@gmail.com"));
		with(jsonReturned).assertEquals("login", ("11988298946"));
		with(jsonReturned).assertEquals("telefone", ("11988298946"));
		with(jsonReturned).assertEquals("situacao", (2));

	}

	@Test()
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
	
	@Test()
	public void deveAtualizarUsuario() { 
		RoleDTO roleDTO = new RoleDTO(1L, "ROLE_PARCEIROS");
		Set<RoleDTO> roles = new HashSet<RoleDTO>();
		roles.add(roleDTO);
		UsuarioAtualizarDTO usuarioAtualizarDTO = new UsuarioAtualizarDTO("Marco Novo", "Santos", "teste@teste.com", "11987654321", 1,roles);
		String jsonReturned = given().contentType("application/json").body(usuarioAtualizarDTO).when()
		.put("/usuarios/2").then().extract().asString();
	}
	
	@Test()
	public void deveReenviarCodigoAtivacao() {
		String jsonReturned = given().contentType("application/json").when()
				.post("/usuarios/reenviar-codigo/2").then().extract().asString();

	}

	@Test
	public void deveAtivarNovoUsuario() {
		UsuarioAtivarDTO usuarioAtivarDTO = new UsuarioAtivarDTO("12345678901", "1234",1 );
		String jsonReturned = given().contentType("application/json").body(usuarioAtivarDTO).when()
		.post("/usuarios/ativar").then().extract().asString();
		with(jsonReturned).assertEquals("situacao", (2));
	}


}
