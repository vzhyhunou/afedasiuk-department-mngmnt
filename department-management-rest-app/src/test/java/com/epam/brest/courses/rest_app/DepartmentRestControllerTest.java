package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.dto.DepartmentDto;
import com.epam.brest.courses.service.DepartmentDtoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class DepartmentRestControllerTest {

    @InjectMocks
    private DepartmentRestController controller;

    @Mock
    private DepartmentDtoService departmentDtoService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @AfterEach
    public void tearDown() {
        Mockito.verifyNoMoreInteractions(departmentDtoService);
    }

    @Test
    public void departments() throws Exception {
        Mockito.when(departmentDtoService.findAllWithAvgSalary()).thenReturn(Arrays.asList(create(0), create(1)));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/departments")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departmentName", Matchers.is("d0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departmentId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].avgSalary", Matchers.is(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].departmentName", Matchers.is("d1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].departmentId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].avgSalary", Matchers.is(101)))
        ;

        Mockito.verify(departmentDtoService).findAllWithAvgSalary();
    }

    private DepartmentDto create(int index) {
        DepartmentDto department = new DepartmentDto();
        department.setDepartmentName("d" + index);
        department.setDepartmentId(index);
        department.setAvgSalary(BigDecimal.valueOf(100 + index));
        return department;
    }

}
