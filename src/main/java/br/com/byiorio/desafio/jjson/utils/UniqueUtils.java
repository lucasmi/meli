package br.com.byiorio.desafio.jjson.utils;

import java.lang.reflect.Field;

import org.springframework.util.ReflectionUtils;

import br.com.byiorio.desafio.jjson.annotations.Unique;
import br.com.byiorio.desafio.jjson.exceptions.JpaJsonException;
import br.com.byiorio.desafio.jjson.model.AcaoEnum;
import br.com.byiorio.desafio.jjson.model.SequenceDTO;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class UniqueUtils {
    private static final String DB_CONTROLE = "controle";
    private static final String DB_CONTROLE_FILE = "index.json";

    public static void processaIndice(Object entity, AcaoEnum acao, String id, String dbPasta) { // NOSONAR
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Unique.class)) {
                try {
                    // Pega valor do parametor
                    ReflectionUtils.makeAccessible(field);
                    Unique uniqueAnnotation = field.getAnnotation(Unique.class);
                    String uniqueIndexName = uniqueAnnotation.uniqueIndexName();

                    // Se nao tiver valor definido da erro
                    if (uniqueIndexName == null) {
                        throw new IllegalArgumentException("Coloque um texto unico do sistema para controle de valor");
                    }

                    // pega o valor da antoacao e verifica tem tem o diretorio criado
                    Object valor = field.get(entity);
                    persistirIndice(acao, id, uniqueIndexName, valor, dbPasta);

                } catch (IllegalAccessException e) {
                    throw new JpaJsonException("Erro ao acessar valor do campo anotado com @Unique");
                }

            }
        }

    }

    private static void persistirIndice(AcaoEnum acao, String id, String uniqueIndexName, Object valor,
            String dbPasta) {
        String caminhoDiretorio = dbPasta + "/" + DB_CONTROLE + "/" + uniqueIndexName;
        String caminhoCompleto = dbPasta + "/" + DB_CONTROLE + "/" + uniqueIndexName + "/" + DB_CONTROLE_FILE;

        SequenceDTO squequence = new SequenceDTO();

        // verifica se existe um arquivo com o
        if (!Arquivos.verifica(caminhoDiretorio) && !Diretorio.verifica(caminhoDiretorio)) {
            Diretorio.criar(caminhoDiretorio);

            // Salva arquivo
            Arquivos.salvar(caminhoCompleto, squequence);
        } else {

            // Carrega sequence
            squequence = Arquivos.ler(caminhoCompleto, SequenceDTO.class);
        }

        // Verifica se no hadhset tem o valor solicitado para inserir
        if (AcaoEnum.REMOVER.equals(acao)) {

            Object valorPeloID = squequence.getIdValor().get(id);
            squequence.getIdValor().remove(id);
            squequence.getValorId().remove(valorPeloID);

        } else if (AcaoEnum.INSERIR.equals(acao)) {

            // verifica se ja existe o valor
            if (squequence.getValorId().containsKey(valor)) {
                throw new JpaJsonException("Chave duplicada " + valor);
            }

            // Salva squence
            squequence.getIdValor().put(id, valor);
            squequence.getValorId().put(valor, id);

        } else if (AcaoEnum.ATUALIZAR.equals(acao)) {
            // Verifica se existe alguem usando o valor
            if (squequence.getValorId().containsKey(valor)) {
                String idEncontrado = squequence.getValorId().get(valor);

                if (!idEncontrado.equals(id)) {
                    throw new JpaJsonException("Chave duplicada " + valor);
                }
            }

            // Salva sequence
            squequence.getIdValor().put(id, valor);
            squequence.getValorId().put(valor, id);

        }

        // Salva arquivo
        Arquivos.salvar(caminhoCompleto, squequence);
    }

}