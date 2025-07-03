package br.com.byiorio.desafio.utils;

import java.io.IOException;

import br.com.byiorio.desafio.jjson.utils.Arquivos;
import br.com.byiorio.desafio.jjson.utils.Diretorio;
import br.com.byiorio.desafio.repositories.AvaliacaoRepository;
import br.com.byiorio.desafio.repositories.CategoriaRepository;
import br.com.byiorio.desafio.repositories.MeioPagamentoRepository;
import br.com.byiorio.desafio.repositories.ProdutoRepository;
import br.com.byiorio.desafio.repositories.UsuarioRepository;

public class ConfiguraMassa {

        public static void configuraMassaUsuario() throws IOException {
                Diretorio.apagar("dbtest/" + UsuarioRepository.NOME_PASTA);
                Diretorio.criar("dbtest/" + UsuarioRepository.NOME_PASTA);

                // Sempre copia duas massas de dados de usuario para os testes
                Arquivos.copiar("src/test/resources/usuario/99d44695-2b71-451a-97ee-1398a0b439a5.json",
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
                Diretorio.apagar("dbtest/" + CategoriaRepository.NOME_PASTA);
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

        public static void configuraMassaAvaliacao() throws IOException {
                Diretorio.apagar("dbtest/" + AvaliacaoRepository.NOME_PASTA);
                Diretorio.criar("dbtest/" + AvaliacaoRepository.NOME_PASTA);

                // Sempre copia duas massas de dados de usuario para os testes
                Arquivos.copiar(
                                "src/test/resources/avaliacao/1eb2bc27-7ae6-472f-9422-cd53fbce22f9.json",
                                "dbtest/" + AvaliacaoRepository.NOME_PASTA
                                                + "/1eb2bc27-7ae6-472f-9422-cd53fbce22f9.json");

                Arquivos.copiar(
                                "src/test/resources/avaliacao/fb0c958d-a8f3-4e2e-83b6-8fc9d33ea9bf.json",
                                "dbtest/" + AvaliacaoRepository.NOME_PASTA
                                                + "/fb0c958d-a8f3-4e2e-83b6-8fc9d33ea9bf.json");
        }

        public static void configuraMassaCategoria() throws IOException {
                Diretorio.apagar("dbtest/" + CategoriaRepository.NOME_PASTA);
                Diretorio.criar("dbtest/" + CategoriaRepository.NOME_PASTA);

                // Sempre copia duas massas de dados de usuario para os testes
                Arquivos.copiar(
                                "src/test/resources/categoria/2d4d2ab5-3a84-4249-a369-e68bcd43b30d.json",
                                "dbtest/" + CategoriaRepository.NOME_PASTA
                                                + "/2d4d2ab5-3a84-4249-a369-e68bcd43b30d.json");

                Arquivos.copiar(
                                "src/test/resources/categoria/a817459a-53c6-404f-aa92-89a2f0cea81b.json",
                                "dbtest/" + CategoriaRepository.NOME_PASTA
                                                + "/a817459a-53c6-404f-aa92-89a2f0cea81b.json");
        }

        public static void configuraMassaMeioPagamento() throws IOException {
                Diretorio.apagar("dbtest/" + MeioPagamentoRepository.NOME_PASTA);
                Diretorio.criar("dbtest/" + MeioPagamentoRepository.NOME_PASTA);

                // Sempre copia duas massas de dados de usuario para os testes
                Arquivos.copiar(
                                "src/test/resources/meiopagamento/114cbcfb-ec12-487e-b842-59fa878154ee.json",
                                "dbtest/" + MeioPagamentoRepository.NOME_PASTA
                                                + "/114cbcfb-ec12-487e-b842-59fa878154ee.json");

                Arquivos.copiar(
                                "src/test/resources/meiopagamento/85424311-7cf9-47fa-962f-7f3cb93cf499.json",
                                "dbtest/" + MeioPagamentoRepository.NOME_PASTA
                                                + "/85424311-7cf9-47fa-962f-7f3cb93cf499.json");
        }

        public static void criaTodoBancoDeDados() throws Throwable {
                ConfiguraMassa.configuraMassaUsuario();
                ConfiguraMassa.configuraMassaProduto();
                ConfiguraMassa.configuraMassaMeioPagamento();
                ConfiguraMassa.configuraMassaAvaliacao();
                ConfiguraMassa.configuraMassaCategoria();
        }
}
