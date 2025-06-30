package br.com.byiorio.desafio.controllers;

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.byiorio.desafio.utils.ConfiguraMassa;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.config.location=classpath:application-test.properties")
@AutoConfigureMockMvc
class AvaliacaoControllerTest {
        @Autowired
        private MockMvc mvc;

        @BeforeEach
        void setup() throws Throwable {
                ConfiguraMassa.configuraMassaAvaliacao();
                ConfiguraMassa.configuraMassaUsuario();
                ConfiguraMassa.configuraMassaProduto();
        }

        @AfterAll
        static void finalizar() throws Throwable {
                ConfiguraMassa.apagaTodoBancoDeDados();
        }

        @Test
        void postTest() throws Exception {
                // le arquivos de request e response
                // e executa o post
                String request = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./avaliacao/PostRequestSucesso.json"),
                                StandardCharsets.UTF_8.name());

                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./avaliacao/PostResponseSucesso.json"),
                                StandardCharsets.UTF_8.name());

                MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/avaliacoes/")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.dataReview").isNotEmpty())
                                .andExpect(MockMvcResultMatchers.content().json(response)).andReturn();

                // Pega o id gerado do meio de pagamento
                String responseBody = result.getResponse().getContentAsString();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(responseBody);
                String idGerado = jsonNode.get("id").asText();

                // Verifica se o usuario tem o meio de pagamento como referencia
                mvc.perform(MockMvcRequestBuilders.get("/usuarios/99d44695-2b71-451a-97ee-1398a0b439a5")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.idsAvaliacoes").value(
                                                org.hamcrest.Matchers.hasItem(idGerado)));

                // Verifica se o usuario tem o meio de pagamento como referencia
                mvc.perform(MockMvcRequestBuilders.get("/produtos/9bce8ac2-1ddf-48ee-8bd4-2b9e8e13fa95")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.idsAvaliacoes").value(
                                                org.hamcrest.Matchers.hasItem(idGerado)));
        }

        @Test
        void getAllTest() throws Exception {

                // le arquivo de response e executa o get
                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./avaliacao/GetResponseAll.json"),
                                StandardCharsets.UTF_8.name());

                mvc.perform(MockMvcRequestBuilders.get("/avaliacoes/")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void getTest() throws Exception {

                // le arquivo de request e response
                // e executa o get
                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./avaliacao/PostResponseFixoSucesso.json"),
                                StandardCharsets.UTF_8.name());

                // Consulta o usuario criado
                mvc.perform(MockMvcRequestBuilders.get("/avaliacoes/1eb2bc27-7ae6-472f-9422-cd53fbce22f9")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void putTest() throws Exception {
                // le arquivo de request e response
                // e executa o post
                String request = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./avaliacao/PutRequestSucesso.json"),
                                StandardCharsets.UTF_8.name());

                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./avaliacao/PutResponseSucesso.json"),
                                StandardCharsets.UTF_8.name());

                // Consulta o usuario criado
                mvc.perform(MockMvcRequestBuilders.put("/avaliacoes/1eb2bc27-7ae6-472f-9422-cd53fbce22f9")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void putErroUsuarioTest() throws Exception {
                // le arquivo de request e response
                // e executa o post
                String request = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./avaliacao/PutRequestError1.json"),
                                StandardCharsets.UTF_8.name());

                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./avaliacao/PutResponseError1.json"),
                                StandardCharsets.UTF_8.name());

                // Consulta o usuario criado
                mvc.perform(MockMvcRequestBuilders.put("/avaliacoes/1eb2bc27-7ae6-472f-9422-cd53fbce22f9")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void putErroProdutoTest() throws Exception {
                // le arquivo de request e response
                // e executa o post
                String request = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./avaliacao/PutRequestError2.json"),
                                StandardCharsets.UTF_8.name());

                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./avaliacao/PutResponseError2.json"),
                                StandardCharsets.UTF_8.name());

                // Consulta o usuario criado
                mvc.perform(MockMvcRequestBuilders.put("/avaliacoes/1eb2bc27-7ae6-472f-9422-cd53fbce22f9")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void deleteTest() throws Exception {
                // Consulta o usuario criado
                mvc.perform(MockMvcRequestBuilders.delete("/avaliacoes/1eb2bc27-7ae6-472f-9422-cd53fbce22f9")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        }
}
