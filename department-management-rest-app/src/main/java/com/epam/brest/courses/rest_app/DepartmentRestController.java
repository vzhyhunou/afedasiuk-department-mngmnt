package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.dto.DepartmentDto;
import com.epam.brest.courses.service.DepartmentDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Home MVC controller.
 */
@RestController
public class DepartmentRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentRestController.class);

    @Autowired
    private DepartmentDtoService departmentDtoService;

    @GetMapping(value = "/departments")
    public Collection<DepartmentDto> findAll() {
        LOGGER.debug("get all departments");
        return departmentDtoService.findAllWithAvgSalary();
    }
}
