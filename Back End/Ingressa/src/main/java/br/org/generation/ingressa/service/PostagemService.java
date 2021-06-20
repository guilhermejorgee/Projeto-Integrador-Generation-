package br.org.generation.ingressa.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;


import br.org.generation.ingressa.model.Postagem;
import br.org.generation.ingressa.model.Usuario;
import br.org.generation.ingressa.repository.PostagemRepository;
import br.org.generation.ingressa.repository.UsuarioRepository;

@Service
public class PostagemService {
	
	@Autowired
	private PostagemRepository postagemRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	public Postagem verificacaoPostagem(Postagem postagem) {
		
		Optional<Usuario> usuario = usuarioRepository.findById(postagem.getUsuario().getId());
			
		
		if(usuario.get().isUsuarioEmpregador() == true) {
			return postagemRepository.save(postagem);
		}
		else {
			if(postagem.getCargo() != null) {
				return null;
			}
			else {
				return postagemRepository.save(postagem);
			}
		}

	}

	public Postagem curtir(Long id) {

		Postagem postagem = buscarPostagemPeloId(id);

		postagem.setQtCurtidas(postagem.getQtCurtidas() + 1);

		return postagemRepository.save(postagem);

	}

	public Postagem descurtir(Long id) {

		Postagem postagem = buscarPostagemPeloId(id);

		if (postagem.getQtCurtidas() > 0) {

			postagem.setQtCurtidas(postagem.getQtCurtidas() - 1);

		} else {

			postagem.setQtCurtidas(0);

		}

		return postagemRepository.save(postagem);

	}
	
	public List<Postagem> postagensEmAlta() {
		
		List<Postagem> postagens = postagemRepository.pesquisaPostagensEmAlta();
		
		Collections.sort(postagens, Collections.reverseOrder(Comparator.comparing(Postagem::getQtCurtidas)));
		
		return postagens;
		
	}

	private Postagem buscarPostagemPeloId(Long id) {

		Postagem postagemSalva = postagemRepository.findById(id).orElse(null);

		if (postagemSalva == null) {

			throw new EmptyResultDataAccessException(1);
		}

		return postagemSalva;
	}

}
