package br.com.byiorio.desafio.jjson.repository;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import br.com.byiorio.desafio.jjson.config.JpajsonConfig;
import br.com.byiorio.desafio.jjson.entity.EstadoEnum;
import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;
import br.com.byiorio.desafio.jjson.exceptions.JpaJsonException;
import br.com.byiorio.desafio.jjson.model.AcaoEnum;
import br.com.byiorio.desafio.jjson.utils.Arquivos;
import br.com.byiorio.desafio.jjson.utils.BlockOnUpdateUtil;
import br.com.byiorio.desafio.jjson.utils.Diretorio;
import br.com.byiorio.desafio.jjson.utils.IdGeneratorUtil;
import br.com.byiorio.desafio.jjson.utils.ManyToOneUtil;
import br.com.byiorio.desafio.jjson.utils.OneToManyUtil;
import br.com.byiorio.desafio.jjson.utils.UniqueUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public abstract class CrudJpaJsonRepository implements IAcoesBasicas, IJpaJsonRepository<IJapJsonEntity> {
    private static final String JSON_EXTENSAO = ".json";

    JpajsonConfig jpajsonConfig;

    protected CrudJpaJsonRepository(JpajsonConfig jpajsonConfig) {
        this.jpajsonConfig = jpajsonConfig;
        this.configurar();
    }

    @Override
    public void configurar() {
        // Cria diretorio onde serao armazenados os Jsons
        String caminhoCompleto = this.jpajsonConfig.getNome().concat("/").concat(getNome());

        // Verifica se o diretorio existe, se nao existir cria
        if (!Diretorio.verifica(caminhoCompleto)) {
            Diretorio.criar(caminhoCompleto);
        }
    }

    @Override
    public <E extends IJapJsonEntity> E alteraEstado(E entidade, EstadoEnum estado) {
        // Log do processo de deleção
        log.info("alteraEstado tipo: {} estado: {}", entidade.getClass().getSimpleName(), estado);

        boolean novoArquivo = false;

        // Verifica se o id da entidade é nulo, se for, gera um novo id
        if (entidade.getId() == null) {
            // Se o id for nulo, gera um novo id
            IdGeneratorUtil.processIdAnnotations(entidade);
            novoArquivo = true;

            UniqueUtils.processaIndice(entidade, AcaoEnum.INSERIR, entidade.getId(), this.jpajsonConfig.getNome());
        } else {
            UniqueUtils.processaIndice(entidade, AcaoEnum.ATUALIZAR, entidade.getId(), this.jpajsonConfig.getNome());
        }

        // Verifica se o id da entidade é vazio, se for, gera um novo arquivo
        String caminhoArquivo = jpajsonConfig.getNome().concat("/").concat(getNome()).concat("/")
                .concat(entidade.getId())
                .concat(JSON_EXTENSAO);

        // Verifica se existe o id na base
        if (!novoArquivo && !Arquivos.verifica(caminhoArquivo)) {
            throw new JpaJsonException("O Id " + entidade.getId() + " nao encontrado na base " + getNome());
        }

        // Verifica se o relacionamento existe
        // Importante checar antes de salvar para evitar problemas de relacionamento
        ManyToOneUtil.salvarRelacionamento(entidade);
        OneToManyUtil.salvarRelacionamento(estado, entidade, novoArquivo);

        if (!Arquivos.verifica(caminhoArquivo) && novoArquivo) {
            // Se for arquivo novo salva
            Arquivos.salvar(caminhoArquivo, entidade);

        } else if (Arquivos.verifica(caminhoArquivo) && !novoArquivo) {
            // Verifica se é necessário travar a alteracao do relacionamento
            BlockOnUpdateUtil.verificaRelacionamento(entidade);

            // Se for arquivo antigo, verifica se existe
            Arquivos.salvar(caminhoArquivo, entidade);
        } else {
            throw new JpaJsonException("Erro ao salvar arquivo no caminho " + caminhoArquivo);
        }

        return entidade;
    }

    @Override
    public <E extends IJapJsonEntity> E salvar(E entidade) {
        return this.alteraEstado(entidade, null);
    }

    public <E extends IJapJsonEntity> E salvar(String id, E entidade) {
        // Log do processo de deleção
        log.info("salvar entidade: {} com id: {}", entidade.getClass().getSimpleName(), id);

        entidade.setId(id);
        return salvar(entidade);
    }

    @Override
    public <E extends IJapJsonEntity> E buscar(String id, Class<E> clazz) {
        // Log do processo de deleção
        log.info("buscar entidade: {} id: {}", clazz.getSimpleName(), id);

        // Nao pode buscar id null
        if (id == null) {
            return null;
        }

        // Gera caminhho
        String caminhoArquivo = jpajsonConfig.getNome().concat("/").concat(getNome()).concat("/").concat(id)
                .concat(JSON_EXTENSAO);

        // Le arquivo
        if (Arquivos.verifica(caminhoArquivo)) {
            return Arquivos.ler(caminhoArquivo, clazz);
        } else {
            throw new JpaJsonException("o id " + id + " nao foi encontrado na base " + getNome());
        }

    }

    public <E extends IJapJsonEntity> void removeEstado(String id, Class<E> clazz, EstadoEnum estado) {
        // Log do processo de deleção
        log.info("removeEstado entidade: {} id: {}", clazz.getSimpleName(), id);

        // gera caminhho
        String caminhoArquivo = jpajsonConfig.getNome().concat("/").concat(getNome()).concat("/").concat(id)
                .concat(JSON_EXTENSAO);

        // Apagar Arquivo
        if (Arquivos.verifica(caminhoArquivo)) {
            // Necessário para acessar os IDs relacionados
            E entidade = this.buscar(id, clazz);

            // Apaga relacionamentos
            OneToManyUtil.apagarRelacionados(entidade, estado);
            ManyToOneUtil.apagarRelacionados(entidade);

            // Remove Sequence
            UniqueUtils.processaIndice(entidade, AcaoEnum.REMOVER, entidade.getId(), this.jpajsonConfig.getNome());

            // Apaga arquivo principal
            Arquivos.apagar(caminhoArquivo);
        } else {
            if (estado == null) {
                throw new JpaJsonException("o id " + id + " nao foi encontrado na base " + getNome());
            }
        }
    }

    public <E extends IJapJsonEntity> void apagar(String id, Class<E> clazz) {
        // Log do processo de deleção
        log.info("apagar entidade: {} id: {}", clazz.getSimpleName(), id);
        this.removeEstado(id, clazz, null);
    }

    @Override
    public <E extends IJapJsonEntity> List<E> buscarTodos(Class<E> clazz) {
        // Log do processo de deleção
        log.info("buscarTodos entidade: {} ", clazz.getSimpleName());

        // Pega todos os arquivos
        LinkedList<String> todosArquivos = new LinkedList<>(
                Diretorio.listaArquivos(jpajsonConfig.getNome().concat("/").concat(getNome())));

        // Consulta todos os arquivos e adiciona na lista
        LinkedList<E> entidades = new LinkedList<>();
        todosArquivos.forEach(id -> entidades.add(this.buscar(id, clazz)));

        return entidades;
    }

    // Feito pela IA
    public <E extends IJapJsonEntity> List<E> buscarPorCampo(Class<E> clazz, String nomeCampo, Object valor) {
        List<E> resultado = new LinkedList<>();
        List<E> todasEntidades = this.buscarTodos(clazz);

        for (E entidade : todasEntidades) {
            try {
                Field field = clazz.getDeclaredField(nomeCampo);
                field.setAccessible(true);
                Object valorCampo = field.get(entidade);
                if (valorCampo != null && valorCampo.equals(valor)) {
                    resultado.add(entidade);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Trate o erro conforme sua necessidade
                log.warn("Erro ao acessar campo {}: {}", nomeCampo, e.getMessage());
            }
        }
        return resultado;
    }
}