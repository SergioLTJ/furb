package com.web.edicao;

import java.util.ArrayList;
import java.util.List;

import com.web.database.dao.HistoricoEdicaoDao;
import com.web.model.HistoricoEdicao;
import com.web.model.Musica;
import com.web.model.Usuario;

public class ComparadorMusicas {

	public void comparar(Usuario usuarioAlteracao, Musica musicaAntiga, Musica musicaAlterada) {
		List<HistoricoEdicao> alteracoes = new ArrayList<>(); 
		
		alteracoes = compararCampo(alteracoes, usuarioAlteracao, musicaAlterada, "Ano álbum", musicaAntiga.getAlbum().getAno(), musicaAlterada.getAlbum().getAno());
		alteracoes = compararCampo(alteracoes, usuarioAlteracao, musicaAlterada, "Nome álbum", musicaAntiga.getAlbum().getNome(), musicaAlterada.getAlbum().getNome());
		alteracoes = compararCampo(alteracoes, usuarioAlteracao, musicaAlterada, "Gênero", musicaAntiga.getGenero().getDescricao(), musicaAlterada.getGenero().getDescricao());
		alteracoes = compararCampo(alteracoes, usuarioAlteracao, musicaAlterada, "Banda", musicaAntiga.getBanda().getNome(), musicaAlterada.getBanda().getNome());
		alteracoes = compararCampo(alteracoes, usuarioAlteracao, musicaAlterada, "Informações", musicaAntiga.getInformacoes(), musicaAlterada.getInformacoes());
		alteracoes = compararCampo(alteracoes, usuarioAlteracao, musicaAlterada, "Letra", musicaAntiga.getLetra().replace("'", "\\'"), musicaAlterada.getLetra().replace("'", "\\'"));
		alteracoes = compararCampo(alteracoes, usuarioAlteracao, musicaAlterada, "Nome", musicaAntiga.getNome(), musicaAlterada.getNome());
		alteracoes = compararCampo(alteracoes, usuarioAlteracao, musicaAlterada, "Tags", musicaAntiga.getTags(), musicaAlterada.getTags());
		
		HistoricoEdicaoDao dao = new HistoricoEdicaoDao();
		dao.gerarRegistrosHistorico(alteracoes);
	}
	
	public <T> List<HistoricoEdicao> compararCampo(List<HistoricoEdicao> alteracoes, Usuario usuarioAlteracao, Musica musica, String campo, T valorAntigo, T valorNovo) {
		if (!valorAntigo.equals(valorNovo)) {
			HistoricoEdicao historico = new HistoricoEdicao();
			historico.setUsuario(usuarioAlteracao);
			historico.setCampo(campo);
			historico.setMusica(musica);
			historico.setValorAntigo(String.valueOf(valorAntigo));
			historico.setValorNovo(String.valueOf(valorNovo));
			
			alteracoes.add(historico);
		}
		
		return alteracoes;
	}
	
}
