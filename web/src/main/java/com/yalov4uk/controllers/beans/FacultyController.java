package com.yalov4uk.controllers.beans;

import com.yalov4uk.abstracts.BaseCrudController;
import com.yalov4uk.interfaces.abstracts.IBaseCrudService;
import com.yalov4uk.interfaces.beans.IFacultyService;
import dto.FacultyDto;
import dto.services.FacultyAndSubjectNameDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by valera on 5/17/17.
 */
@RestController
@RequestMapping(value = "/faculties")
public class FacultyController extends BaseCrudController<FacultyDto> {

    private final IFacultyService facultyService;

    @Autowired
    public FacultyController(IFacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody FacultyDto faculty) {
        return createCrud(faculty);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<FacultyDto> update(@RequestBody FacultyDto faculty) {
        return updateCrud(faculty);
    }

    @RequestMapping(value = "/addSubjectName", method = RequestMethod.POST)
    public ResponseEntity addSubjectName(@RequestBody FacultyAndSubjectNameDto facultyAndSubjectName) {
        facultyService.addSubjectName(facultyAndSubjectName.getFaculty(), facultyAndSubjectName.getSubjectName());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteSubjectName", method = RequestMethod.POST)
    public ResponseEntity deleteSubjectName(@RequestBody FacultyAndSubjectNameDto facultyAndSubjectName) {
        facultyService.deleteSubjectName(facultyAndSubjectName.getFaculty(), facultyAndSubjectName.getSubjectName());
        return new ResponseEntity(HttpStatus.OK);
    }

    protected IBaseCrudService<FacultyDto> getService(){
        return facultyService;
    }
}
