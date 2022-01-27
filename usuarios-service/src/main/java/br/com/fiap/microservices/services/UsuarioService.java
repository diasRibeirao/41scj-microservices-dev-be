package br.com.fiap.microservices.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.microservices.entities.Role;
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
		return obj.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
	}

	public Usuario findByTelefone(String telefone) {
		Optional<Usuario> obj = repository.findByTelefone(telefone);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
	}

	public Usuario validarLogin(String login, String senha, String role) {		
		Optional<Usuario> obj = repository.findByLogin(login);

		if (obj.isEmpty() || !containsRole(obj.get().getRoles(), role)
				|| !passwordEncoder.matches(senha, obj.get().getSenha())) {
			throw new ObjectNotFoundException("Usuário não encontrado");
		}
		// demais validações. Ex se está ativo, etc...

		return obj.get();
	} 

	public boolean containsRole(final Set<Role> list, final String nome) {
		return list.stream().filter(o -> o.getNome().equals(nome)).findFirst().isPresent();
	}

	public Usuario insert(Usuario usuario) {
		usuario.setId(null);
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuario.setCodigoAtivar(Utils.buildCodigoAtivacao());
		usuario.setDataLimiteAtivar(Utils.addDiasDataAtual(7));
		usuario = repository.save(usuario);
		notificacoesFeignClients.sms(buildNotificacao(usuario, Notificacao.NOVO_USUARIO));
		notificacoesFeignClients.slack("Usuário " + usuario.getNome() + " " + usuario.getSobrenome() + " cadastrado com sucesso!");
		return usuario;
	}

	public Usuario ativar(UsuarioAtivarDTO usuarioAtivarDTO) {
		Usuario usuarioUpdate = findByTelefone(Utils.removeMaskCelular(usuarioAtivarDTO.getTelefone()));

		if (usuarioAtivarDTO.getIdNotificacao().equals(Notificacao.NOVO_USUARIO.getId())) {
			if (!usuarioUpdate.getSituacao().equals(SituacaoUsuario.AG_ATIVACAO.getId())) {
				throw new AtivarUsuarioException("Não é possível ativar, usuario já ativado.");
			}
		}
		
		if (usuarioAtivarDTO.getIdNotificacao().equals(Notificacao.ESQUECEU_SENHA.getId())) {
			if (!usuarioUpdate.getSituacao().equals(SituacaoUsuario.SOLICITOU_NOVA_SENHA.getId())) {
				throw new AtivarUsuarioException("Não é possível salvar, usuário não solicitou esqueceu senha.");
			}
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
	
	public Usuario resetPassword(Long id, String senha, String confirmarSenha) {
		Usuario usuario = find(id);
		
		if (!senha.equals(confirmarSenha)) {
			throw new AtivarUsuarioException("As senhas não conferem");
		}
		
		usuario.setSenha(passwordEncoder.encode(senha));

		updateData(usuario);

		return repository.save(usuario);
	}

	public Usuario esqueceuSenha(UsuarioEsqueceuSenhaDTO usuarioEsqueceuSenhaDTO) {
		Usuario usuarioUpdate = findByTelefone(Utils.removeMaskCelular(usuarioEsqueceuSenhaDTO.getTelefone()));
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

	public Usuario reenviarCodigo(Long id) {
		Usuario usuario = find(id);

		if (!usuario.getSituacao().equals(SituacaoUsuario.AG_ATIVACAO.getId())) {
			throw new ObjectNotFoundException("Reenvio apenas para usuário aguardando ativação.");
		}

		usuario.setCodigoAtivar(Utils.buildCodigoAtivacao());
		usuario.setDataLimiteAtivar(Utils.addDiasDataAtual(7));
		usuario = repository.save(usuario);
		notificacoesFeignClients.sms(buildNotificacao(usuario, Notificacao.NOVO_USUARIO));
		return usuario;
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
