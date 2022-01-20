package br.com.fiap.microservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.microservices.entities.Usuario;
import br.com.fiap.microservices.entities.dto.NotificacaoSendDTO;
import br.com.fiap.microservices.entities.dto.UsuarioAtivarDTO;
import br.com.fiap.microservices.entities.dto.UsuarioEsqueceuSenhaDTO;
import br.com.fiap.microservices.entities.enums.Notificacao;
import br.com.fiap.microservices.entities.enums.SituacaoUsuario;
import br.com.fiap.microservices.feignclients.NotificacoesFeignClients;
import br.com.fiap.microservices.repositories.UsuarioRepository;
import br.com.fiap.microservices.services.exceptions.AtivarUsuarioException;
import br.com.fiap.microservices.services.exceptions.ObjectNotFoundException;
import br.com.fiap.microservices.utils.Utils;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private NotificacoesFeignClients notificacoesFeignClients;

	public List<Usuario> findAll() {
		return repository.findAll();
	}

	public Usuario find(Long id) {
		Optional<Usuario> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
	}

	public Usuario findByTelefone(String telefone) {
		Optional<Usuario> obj = repository.findByTelefone(telefone);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Telefone: " + telefone + ", Tipo: " + Usuario.class.getName()));
	}
	
	public Usuario findByLogin(String login) {
		Optional<Usuario> obj = repository.findByLogin(login);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Login: " + login + ", Tipo: " + Usuario.class.getName()));
	}

	public Usuario insert(Usuario usuario) {
		usuario.setId(null);
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuario.setCodigoAtivar(Utils.buildCodigoAtivacao());
		usuario.setDataLimiteAtivar(Utils.addDiasDataAtual(7));
		usuario = repository.save(usuario);
		notificacoesFeignClients.sms(buildNotificacao(usuario, Notificacao.NOVO_USUARIO));
		return usuario;
	}

	public Usuario ativar(UsuarioAtivarDTO usuarioAtivarDTO) {
		Usuario usuarioUpdate = findByTelefone(usuarioAtivarDTO.getTelefone());

		if (!usuarioUpdate.getSituacao().equals(SituacaoUsuario.AG_ATIVACAO.getId())) {
			throw new AtivarUsuarioException("Não é possível ativar, usuario já ativado.");
		}

		if (usuarioUpdate.getDataLimiteAtivar().before(Utils.dataAtual())) {
			throw new AtivarUsuarioException("Não é possível ativar, data limite expirou.");
		}

		if (!usuarioUpdate.getCodigoAtivar().equals(usuarioAtivarDTO.getCodigo())) {
			throw new AtivarUsuarioException("Não é possível ativar, código inválido.");
		}

		updateData(usuarioUpdate);

		return repository.save(usuarioUpdate);
	}

	public Usuario esqueceuSenha(UsuarioEsqueceuSenhaDTO usuarioEsqueceuSenhaDTO) {
		Usuario usuarioUpdate = findByTelefone(usuarioEsqueceuSenhaDTO.getTelefone());
		usuarioUpdate.setCodigoAtivar(Utils.buildCodigoAtivacao());
		usuarioUpdate.setDataLimiteAtivar(Utils.addDiasDataAtual(7));
		usuarioUpdate.setSituacao(SituacaoUsuario.SOLICITOU_NOVA_SENHA.getId());

		notificacoesFeignClients.sms(buildNotificacao(usuarioUpdate, Notificacao.ESQUECEU_SENHA));
		return repository.save(usuarioUpdate);
	}

	public Usuario update(Usuario usuario) {
		Usuario usuarioUpdate = find(usuario.getId());
		updateData(usuarioUpdate, usuario);
		return repository.save(usuarioUpdate);
	}

	private void updateData(Usuario usuarioUpdate) {
		usuarioUpdate.setSituacao(SituacaoUsuario.ATIVO.getId());
		usuarioUpdate.setCodigoAtivar(null);
		usuarioUpdate.setDataLimiteAtivar(null);
	}

	private void updateData(Usuario usuarioUpdate, Usuario usuario) {
		usuarioUpdate.setNome(usuario.getNome());
		usuarioUpdate.setSobrenome(usuario.getSobrenome());
		usuarioUpdate.setEmail(usuario.getEmail());
		usuarioUpdate.setTelefone(usuario.getTelefone());
		usuarioUpdate.setSituacao(usuario.getSituacao());
	}

	private NotificacaoSendDTO buildNotificacao(Usuario usuario, Notificacao notificacao) {
		return new NotificacaoSendDTO("+55" + usuario.getTelefone(),
				String.format(notificacao.getDescricao(), usuario.getCodigoAtivar()));
	}

}
