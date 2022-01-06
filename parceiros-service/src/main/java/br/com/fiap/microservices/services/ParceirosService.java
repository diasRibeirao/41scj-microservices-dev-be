package br.com.fiap.microservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.microservices.entities.Parceiro;
import br.com.fiap.microservices.entities.dto.NotificacaoSendDTO;
import br.com.fiap.microservices.entities.dto.ParceiroAtivarDTO;
import br.com.fiap.microservices.entities.dto.VeiculoDTO;
import br.com.fiap.microservices.entities.enums.SituacaoParceiro;
import br.com.fiap.microservices.feignclients.NotificacoesFeignClients;
import br.com.fiap.microservices.feignclients.VeiculosFeignClients;
import br.com.fiap.microservices.repositories.ParceirosRepository;
import br.com.fiap.microservices.services.exceptions.AtivarParceiroException;
import br.com.fiap.microservices.services.exceptions.ObjectNotFoundException;
import br.com.fiap.microservices.utils.Utils;

@Service
public class ParceirosService {

	@Autowired
	private ParceirosRepository repository;

	@Autowired
	private VeiculosFeignClients veiculosFeignClients;

	@Autowired
	private NotificacoesFeignClients notificacoesFeignClients;

	public List<Parceiro> findAll() {
		return repository.findAll();
	}

	public Parceiro find(Long id) {
		Optional<Parceiro> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Parceiro.class.getName()));
	}

	public Parceiro findByTelefone(String telefone) {
		Optional<Parceiro> obj = repository.findByTelefone(telefone);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Telefone: " + telefone + ", Tipo: " + Parceiro.class.getName()));
	}

	public List<VeiculoDTO> findByParceiroId(Long id) {
		return veiculosFeignClients.findByParceiroId(id).getBody();
	}

	public Parceiro insert(Parceiro parceiro) {
		parceiro.setId(null);
		parceiro.setCodigoAtivar(Utils.buildCodigoAtivacao());
		parceiro.setDataLimiteAtivar(Utils.addDiasDataAtual(7));
		parceiro = repository.save(parceiro);
		notificacoesFeignClients.sms(buildNotificacao(parceiro));
		return parceiro;
	}
	
	public Parceiro ativar(ParceiroAtivarDTO parceiroAtivarDTO) {
		Parceiro parceiroUpdate = findByTelefone(parceiroAtivarDTO.getTelefone());
				
		if (!parceiroUpdate.getSituacao().equals(SituacaoParceiro.AG_ATIVACAO.getId())) {
			throw new AtivarParceiroException("Não é possível ativar, parceiro já ativado.");
		}
		
		if (parceiroUpdate.getDataLimiteAtivar().before(Utils.dataAtual())) {
			throw new AtivarParceiroException("Não é possível ativar, data limite expirou.");
		}
		
		if (!parceiroUpdate.getCodigoAtivar().equals(parceiroAtivarDTO.getCodigo())) {
			throw new AtivarParceiroException("Não é possível ativar, código inválido.");
		}
		
		updateData(parceiroUpdate);
		
		return repository.save(parceiroUpdate);
	}

	public Parceiro update(Parceiro parceiro) {
		Parceiro parceiroUpdate = find(parceiro.getId());
		updateData(parceiroUpdate, parceiro);
		return repository.save(parceiroUpdate);
	}
	
	private void updateData(Parceiro parceiroUpdate) {
		parceiroUpdate.setSituacao(SituacaoParceiro.ATIVO.getId());
		parceiroUpdate.setCodigoAtivar(null);
		parceiroUpdate.setDataLimiteAtivar(null);
	}

	private void updateData(Parceiro parceiroUpdate, Parceiro parceiro) {
		parceiroUpdate.setNome(parceiro.getNome());
		parceiroUpdate.setSobrenome(parceiro.getSobrenome());
		parceiroUpdate.setEmail(parceiro.getEmail());
		parceiroUpdate.setTelefone(parceiro.getTelefone());
		parceiroUpdate.setSituacao(parceiro.getSituacao());
	}

	private NotificacaoSendDTO buildNotificacao(Parceiro parceiro) {
		return new NotificacaoSendDTO("+55" + parceiro.getTelefone(),
				"Olá nosso parceiro, seu código de ativação na VanCerta é " + parceiro.getCodigoAtivar());
	}

}
