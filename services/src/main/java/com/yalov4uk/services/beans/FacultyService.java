package com.yalov4uk.services.beans;

import com.yalov4uk.abstracts.BaseCrudService;
import com.yalov4uk.beans.Faculty;
import com.yalov4uk.beans.SubjectName;
import com.yalov4uk.exceptions.ServiceUncheckedException;
import com.yalov4uk.interfaces.IBaseDao;
import com.yalov4uk.interfaces.IFacultyDao;
import com.yalov4uk.interfaces.ISubjectNameDao;
import com.yalov4uk.interfaces.beans.IFacultyService;
import dto.FacultyDto;
import dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by valera on 5/17/17.
 */
@Service
public class FacultyService extends BaseCrudService<Faculty, FacultyDto> implements IFacultyService {

    private final IFacultyDao facultyDao;
    private final ISubjectNameDao subjectNameDao;

    @Autowired
    public FacultyService(IFacultyDao facultyDao, ISubjectNameDao subjectNameDao) {
        this.facultyDao = facultyDao;
        this.subjectNameDao = subjectNameDao;
    }

    public void addSubjectName(Integer facultyId, Integer subjectNameId) {
        Faculty faculty = facultyDao.find(facultyId);
        SubjectName subjectName = subjectNameDao.find(subjectNameId);
        if (faculty == null || subjectName == null || faculty.getRequiredSubjects().contains(subjectName)) {
            throw new ServiceUncheckedException("wrong input or subjectName already added to this faculty");
        }

        faculty.getRequiredSubjects().add(subjectName);
    }

    public void deleteSubjectName(Integer facultyId, Integer subjectNameId) {
        Faculty faculty = facultyDao.find(facultyId);
        SubjectName subjectName = subjectNameDao.find(subjectNameId);
        if (faculty == null || subjectName == null || !faculty.getRequiredSubjects().contains(subjectName)) {
            throw new ServiceUncheckedException("wrong input or faculty hasn't this subject name");
        }

        faculty.getRequiredSubjects().remove(subjectName);
    }

    public List<UserDto> getRegisteredUsers(FacultyDto facultyDto) {
        Faculty faculty = facultyDao.find(facultyDto.getId());
        if (faculty == null) {
            throw new ServiceUncheckedException("faculty not found");
        }

        return faculty.getRegisteredUsers()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    protected IBaseDao<Faculty> getDao() {
        return facultyDao;
    }

    protected Class<Faculty> getBeanClass() {
        return Faculty.class;
    }

    protected Class<FacultyDto> getDtoClass() {
        return FacultyDto.class;
    }
}
