package br.com.fiap.microservices.resources;

import static com.jayway.jsonassert.JsonAssert.with;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

import br.com.fiap.microservices.GenericControlerITest;
import br.com.fiap.microservices.entities.dto.LoginDTO;
import br.com.fiap.microservices.entities.dto.RoleDTO;
import br.com.fiap.microservices.entities.dto.UsuarioAdicionarDTO;
import br.com.fiap.microservices.entities.dto.UsuarioAtivarDTO;
import br.com.fiap.microservices.entities.dto.UsuarioAtualizarDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@Sql({ "/db/integration/V2__Populate_Table_Usuarios_test.sql" })
//@Sql("/db/integration/V2__Populate_Table_Usuarios_test.sql")
//@Sql("/db/integration/V3__Create_Table_Roles_test.sql.sql")
//@Sql("/db/integration/V4__Populate_Table_Roles_test.sql")
//@Sql("/db/integration/V5__Create_Table_Usuarios_Role_test.sql")
//@Sql("/db/integration/V6__Populate_Table_Usuarios_Role_test.sql")
//@Sql("/db/integration/V7__Create_Table_Menus_test.sql")
//@Sql("/db/integration/V8__Populate_Table_Menus_test.sql")
//@Sql("/db/integration/V9__Create_Table_Menus_Role_test.sql")
//@Sql("/db/integration/V10__Populate_Table_Menus_Role_test.sql")
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
		String jsonReturned = given().when().get("/usuarios/1")
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
		RoleDTO roleDTO = new RoleDTO(1L, "ROLE_PARCEIROS");
		Set<RoleDTO> roles = new HashSet<RoleDTO>();
		roles.add(roleDTO);
		LoginDTO usuarioAdicionar = new LoginDTO(
				"11988298946", "123456", "ROLE_PARCEIROS");
		String jsonReturned = given().contentType("application/json").body(usuarioAdicionar).when()
		.post("/usuarios/login/").then().extract().asString();
		with(jsonReturned).assertThat("nome", equalTo("Emerson"));
		with(jsonReturned).assertThat("sobrenome", equalTo("Dias de Oliveira"));
		with(jsonReturned).assertThat("email", equalTo("emersondiaspd@gmail.com"));
		with(jsonReturned).assertThat("login", equalTo("11988298946"));
		with(jsonReturned).assertThat("telefone", equalTo("11988298946"));
		with(jsonReturned).assertThat("situacao", equalTo(2));

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
	
	@Test(expected = Test.None.class)
	public void deveAtualizarUsuario() { 
		RoleDTO roleDTO = new RoleDTO(1L, "ROLE_PARCEIROS");
		Set<RoleDTO> roles = new HashSet<RoleDTO>();
		roles.add(roleDTO);
		UsuarioAtualizarDTO usuarioAtualizarDTO = new UsuarioAtualizarDTO("Marco Novo", "Santos", "teste@teste.com", "11987654321", 1,roles);
		String jsonReturned = given().contentType("application/json").body(usuarioAtualizarDTO).when()
		.put("/usuarios/2").then().extract().asString();
	}
	
	@Test(expected = Test.None.class)
	public void deveReenviarCodigoAtivacao() {
		String jsonReturned = given().contentType("application/json").when()
				.post("/usuarios/reenviar-codigo/2").then().extract().asString();

	}

	@Test
	public void deveAtivarNovoUsuario() {
		UsuarioAtivarDTO usuarioAtivarDTO = new UsuarioAtivarDTO("12345678901", "1234",1 );
		String jsonReturned = given().contentType("application/json").body(usuarioAtivarDTO).when()
		.post("/usuarios/ativar").then().extract().asString();
		with(jsonReturned).assertThat("situacao", equalTo(2));
	}


}
