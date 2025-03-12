package org.burgas.identityserver.controller;

import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static java.lang.System.out;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest
@AutoConfigureMockMvc
class IdentityControllerTest {

    MockMvc mockMvc;

    @Autowired
    public IdentityControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ROLE_ADMIN"})
    void getAllIdentities() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/identities/all")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> result.getResponse().setContentType(APPLICATION_JSON_VALUE))
                .andExpect(result -> result.getResponse().setCharacterEncoding("UTF-8"))
                .andDo(
                        result -> out.println(
                                new String(result.getResponse().getContentAsByteArray())
                        )
                )
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ROLE_ADMIN"})
    void createIdentity() throws Exception {
        @Language("JSON") String content = """
                {
                  "username": "burgasvv",
                  "password": "cg3audio",
                  "email": "burgasvv@gmail.com",
                  "authorityId": 2
                }
                """;
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/identities/create")
                                .contentType(APPLICATION_JSON)
                                .content(content)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> result.getResponse().setCharacterEncoding("UTF-8"))
                .andExpect(
                        result -> out.println(
                                new String(result.getResponse().getContentAsByteArray())
                        )
                )
                .andReturn();
    }

    @Test
    void updateIdentity() {
    }

    @Test
    void deleteById() {
    }
}