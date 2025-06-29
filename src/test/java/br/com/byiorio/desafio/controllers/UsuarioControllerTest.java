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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.byiorio.desafio.jjson.utils.Diretorio;
import br.com.byiorio.desafio.models.UsuarioEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.config.location=classpath:application-test.properties")
@AutoConfigureMockMvc
class UsuarioControllerTest {
        @Autowired
        private MockMvc mvc;

        @BeforeEach
        void setup() throws Throwable {
                Diretorio.apagar("dbtest/usuarios");
                Diretorio.criar("dbtest/usuarios");
        }

        @AfterAll
        static void finalizar() throws Throwable {
                Diretorio.apagar("dbtest/usuarios");
        }

        @Test
        void criarUsuarioTest() throws Exception {
                // le arquivos de request e response
                // e executa o post
                String request = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./usuario/PostRequestSucesso.json"),
                                StandardCharsets.UTF_8.name());

                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./usuario/PostResponseSucesso.json"),
                                StandardCharsets.UTF_8.name());

                mvc.perform(MockMvcRequestBuilders.post("/usuarios/")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void consultarUsuariosTest() throws Exception {
                // Cria 2 usuarios
                criarUsuarioTest();
                criarUsuarioTest();

                // le arquivo de response e executa o get
                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./usuario/GetResponseAllUsers.json"),
                                StandardCharsets.UTF_8.name());

                mvc.perform(MockMvcRequestBuilders.get("/usuarios/")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNotEmpty())
                                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").isNotEmpty())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void consultarIndividualTest() throws Exception {
                // le arquivo de request e response
                // e executa o post
                String request = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./usuario/PostRequestSucesso.json"),
                                StandardCharsets.UTF_8.name());

                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./usuario/PostResponseSucesso.json"),
                                StandardCharsets.UTF_8.name());

                MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/usuarios/")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andReturn();

                // pega o id do usuario criado
                String responseBody = result.getResponse().getContentAsString();
                ObjectMapper mapper = new ObjectMapper();
                UsuarioEntity dto = mapper.readValue(responseBody, UsuarioEntity.class);

                // Consulta o usuario criado
                mvc.perform(MockMvcRequestBuilders.get("/usuarios/" + dto.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void atualizarUsuarioTest() throws Exception {
                // le arquivo de request e response
                // e executa o post
                String request = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./usuario/PutRequestSucesso.json"),
                                StandardCharsets.UTF_8.name());

                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./usuario/PutResponseSucesso.json"),
                                StandardCharsets.UTF_8.name());

                MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/usuarios/")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andReturn();

                // pega o id do usuario criado
                String responseBody = result.getResponse().getContentAsString();
                ObjectMapper mapper = new ObjectMapper();
                UsuarioEntity dto = mapper.readValue(responseBody, UsuarioEntity.class);

                // Consulta o usuario criado
                mvc.perform(MockMvcRequestBuilders.put("/usuarios/" + dto.getId())
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void inserePagamentoUsuarioTest() throws Exception {
                // le arquivo de request e response
                // e executa o post
                String request = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./usuario/PutRequestSucesso.json"),
                                StandardCharsets.UTF_8.name());

                MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/usuarios/")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andReturn();

                // pega o id do usuario criado
                String responseBody = result.getResponse().getContentAsString();
                ObjectMapper mapper = new ObjectMapper();
                UsuarioEntity dto = mapper.readValue(responseBody, UsuarioEntity.class);

                // le arquivo de request e response
                // e executa o post
                request = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./usuario/MeioPagamentoRequestSucesso.json"),
                                StandardCharsets.UTF_8.name());

                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./usuario/MeioPagamentoResponseSucesso.json"),
                                StandardCharsets.UTF_8.name());

                // Consulta o usuario criado
                mvc.perform(MockMvcRequestBuilders.post("/usuarios/" + dto.getId() + "/meios-pagamento")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void apagarUsuarioTest() throws Exception {
                // le arquivo de request e response
                // e executa o post
                String request = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./usuario/PutRequestSucesso.json"),
                                StandardCharsets.UTF_8.name());

                // Cria um usuario
                MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/usuarios/")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andReturn();

                // pega o id do usuario criado
                String responseBody = result.getResponse().getContentAsString();
                ObjectMapper mapper = new ObjectMapper();
                UsuarioEntity dto = mapper.readValue(responseBody, UsuarioEntity.class);

                // Consulta o usuario criado
                mvc.perform(MockMvcRequestBuilders.delete("/usuarios/" + dto.getId())
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        }
}
