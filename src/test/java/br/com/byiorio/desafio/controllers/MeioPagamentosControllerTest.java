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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ResourceUtils;

import br.com.byiorio.desafio.utils.ConfiguraMassa;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.config.location=classpath:application-test.properties")
@AutoConfigureMockMvc
class MeioPagamentosControllerTest {
        @Autowired
        private MockMvc mvc;

        @BeforeEach
        void setup() throws Throwable {
                ConfiguraMassa.configuraMassaAvaliacao();
                ConfiguraMassa.configuraMassaUsuario();
                ConfiguraMassa.configuraMassaProduto();
                ConfiguraMassa.configuraMassaMeioPagamento();
        }

        @AfterAll
        static void finalizar() throws Throwable {
                ConfiguraMassa.apagaTodoBancoDeDados();
        }

        @Test
        void getAllTest() throws Exception {

                // le arquivo de response e executa o get
                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./meiopagamento/GetResponseAll.json"),
                                StandardCharsets.UTF_8.name());

                mvc.perform(MockMvcRequestBuilders.get("/meio-pagamentos/")
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
                                ResourceUtils.getFile("classpath:./meiopagamento/PostResponseFixoSucesso.json"),
                                StandardCharsets.UTF_8.name());

                // Consulta o usuario criado
                mvc.perform(MockMvcRequestBuilders.get("/meio-pagamentos/114cbcfb-ec12-487e-b842-59fa878154ee")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void apagarUsuarioTest() throws Exception {
                // Consulta o usuario criado
                mvc.perform(MockMvcRequestBuilders.delete("/meio-pagamentos/114cbcfb-ec12-487e-b842-59fa878154ee")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        }
}
