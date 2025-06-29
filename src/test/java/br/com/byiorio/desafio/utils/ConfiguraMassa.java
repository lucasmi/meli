package br.com.byiorio.desafio.utils;

import java.io.IOException;

import br.com.byiorio.desafio.jjson.utils.Arquivos;
import br.com.byiorio.desafio.jjson.utils.Diretorio;
import br.com.byiorio.desafio.repositories.AvaliacaoRepository;
import br.com.byiorio.desafio.repositories.MeioPagamentoRepository;
import br.com.byiorio.desafio.repositories.ProdutoRepository;
import br.com.byiorio.desafio.repositories.UsuarioRepository;

public class ConfiguraMassa {

    public static void configuraMassaUsuario() throws IOException {
        Diretorio.apagar("dbtest/" + UsuarioRepository.NOME_PASTA);
        Diretorio.criar("dbtest/" + UsuarioRepository.NOME_PASTA);

        // Sempre copia duas massas de dados de usuario para os testes
        Arquivos.copiar(
                "src/test/resources/usuario/99d44695-2b71-451a-97ee-1398a0b439a5.json",
                "dbtest/" + UsuarioRepository.NOME_PASTA
                        + "/99d44695-2b71-451a-97ee-1398a0b439a5.json");

        Arquivos.copiar(
                "src/test/resources/usuario/f33d57ea-d316-4167-92a8-8f2258b71abd.json",
                "dbtest/" + UsuarioRepository.NOME_PASTA
                        + "/f33d57ea-d316-4167-92a8-8f2258b71abd.json");
    }

    public static void apagaTodoBancoDeDados() throws IOException {
        Diretorio.apagar("dbtest/" + UsuarioRepository.NOME_PASTA);
        Diretorio.apagar("dbtest/" + AvaliacaoRepository.NOME_PASTA);
        Diretorio.apagar("dbtest/" + ProdutoRepository.NOME_PASTA);
        Diretorio.apagar("dbtest/" + MeioPagamentoRepository.NOME_PASTA);
    }

    public static void configuraMassaProduto() throws IOException {
        Diretorio.apagar("dbtest/" + ProdutoRepository.NOME_PASTA);
        Diretorio.criar("dbtest/" + ProdutoRepository.NOME_PASTA);

        // Sempre copia duas massas de dados de usuario para os testes
        Arquivos.copiar(
                "src/test/resources/produto/9bce8ac2-1ddf-48ee-8bd4-2b9e8e13fa95.json",
                "dbtest/" + ProdutoRepository.NOME_PASTA
                        + "/9bce8ac2-1ddf-48ee-8bd4-2b9e8e13fa95.json");

        Arquivos.copiar(
                "src/test/resources/produto/9d04d49e-89a7-4e3f-9304-2253e5f36d2a.json",
                "dbtest/" + ProdutoRepository.NOME_PASTA
                        + "/9d04d49e-89a7-4e3f-9304-2253e5f36d2a.json");
    }
}
