package com.abdelrehim.mostafa.telephonebook.controller;

import com.abdelrehim.mostafa.telephonebook.model.TelephoneEntry;
import com.abdelrehim.mostafa.telephonebook.service.TelephoneEntryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TelephoneBookController.class)
@DisplayName("Controller /phoneNumber")
class TelephoneBookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TelephoneEntryService telephoneEntryService;

    static final private String ENDPOINT = "/phoneNumber";

    @Test
    @DisplayName("POST")
    public void test_createEntry() {
        TelephoneEntry telephoneEntry = telephoneEntryFactory();
        doReturn(telephoneEntry).when(telephoneEntryService).save(any(TelephoneEntry.class));

        try {
            mockMvc.perform(
                    post(ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(telephoneEntry))
            ).andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().string(asJsonString(telephoneEntry)));
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    @DisplayName("GET")
    public void test_getVacancies() {
        TelephoneEntry telephoneEntry1 = telephoneEntryFactory();
        TelephoneEntry telephoneEntry2 = telephoneEntryFactory();

        List<TelephoneEntry> entries = Arrays.asList(telephoneEntry1, telephoneEntry2);

        doReturn(entries).when(telephoneEntryService).findAll();

        try {
            mockMvc.perform(get(ENDPOINT))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().string(asJsonString(entries)));
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    @DisplayName("GET - no items")
    public void test_getVacancies_empty() {

        doReturn(Collections.EMPTY_LIST).when(telephoneEntryService).findAll();

        try {
            mockMvc.perform(get(ENDPOINT))
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Nested
    @DisplayName("/{id}")
    class id {

        static final private String ENDPOINT = "/phoneNumber/{id}";

        @Test
        @DisplayName("PUT")
        public void test_updateEntry() {
            TelephoneEntry originalTelephoneEntry = telephoneEntryFactory();

            TelephoneEntry newTelephoneEntry = new TelephoneEntry();
            newTelephoneEntry.setPhoneNumber("87369220");
            newTelephoneEntry.setName("test name");

            TelephoneEntry updatedTelephoneEntry = new TelephoneEntry();
            updatedTelephoneEntry.setId(originalTelephoneEntry.getId());
            updatedTelephoneEntry.setPhoneNumber(newTelephoneEntry.getPhoneNumber());
            updatedTelephoneEntry.setName(newTelephoneEntry.getName());

            doReturn(Optional.of(originalTelephoneEntry)).when(telephoneEntryService)
                    .findById(originalTelephoneEntry.getId());

            doReturn(updatedTelephoneEntry).when(telephoneEntryService)
                    .update(originalTelephoneEntry.getId(), newTelephoneEntry);

            try {
                mockMvc.perform(put(ENDPOINT, originalTelephoneEntry.getId())
                        .content(asJsonString(newTelephoneEntry))
                        .contentType(MediaType.APPLICATION_JSON))

                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().string(asJsonString(updatedTelephoneEntry)));
            } catch (Exception e) {
                e.printStackTrace();
                assert false;
            }
        }

        @Test
        @DisplayName("GET")
        public void test_getEntry() {
            TelephoneEntry telephoneEntry = telephoneEntryFactory();
            doReturn(Optional.of(telephoneEntry)).when(telephoneEntryService).findById(telephoneEntry.getId());

            try {
                mockMvc.perform(get(ENDPOINT, telephoneEntry.getId()))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                        .andExpect(content().string(asJsonString(telephoneEntry)));
            } catch (Exception e) {
                e.printStackTrace();
                assert false;
            }
        }

        @Test
        @DisplayName("DELETE")
        public void test_deleteEntry() {
            TelephoneEntry telephoneEntry = telephoneEntryFactory();
            doReturn(Optional.of(telephoneEntry)).when(telephoneEntryService).findById(telephoneEntry.getId());

            try {
                mockMvc.perform(delete(ENDPOINT, telephoneEntry.getId()))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                e.printStackTrace();
                assert false;
            }
        }
    }

    public static final Random random = new Random();

    public static TelephoneEntry telephoneEntryFactory() {
        TelephoneEntry entry = new TelephoneEntry();
        entry.setId(random.nextLong());
        entry.setPhoneNumber("9386428388");

        return entry;
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String asJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}